package com.nxt;

import lejos.nxt.Button;
import lejos.nxt.SensorPort;
import lejos.util.Delay;


public class Cube {

	// Current orientation - updated after every move
	public char[] initial_orientation = new char[6];
	public char[] current_orientation = new char[6];
	public Movement move;
	public ColorDetector detect;
	private int number_tilts = 0;
	
	public Cube() {
		move = new Movement(true);
		detect = new ColorDetector(SensorPort.S3, true);
	}
	
	public void executeSolvingSequence(char[] solving_sequence) {
		
		for (char action : solving_sequence) {
			permuteCube(action);
		}
		move.releaseCube();
	}
	
	private void permuteCube(char action) {
		
		int initial_face_index = 0;
		
		// Is the initial and current orientation set?
		if (initial_orientation[0] != 0 && current_orientation[0] != 0) {

			switch (action) {
				case 't':
					initial_face_index = 1;
					break;
				case 'T':
					initial_face_index = -1;
					break;
				case 'd':
					initial_face_index = 2;
					break;
				case 'D':
					initial_face_index = -2;
					break;
				case 'l':
					initial_face_index = 3;
					break;
				case 'L':
					initial_face_index = -3;
					break;
				case 'r':
					initial_face_index = 4;
					break;
				case 'R':
					initial_face_index = -4;
					break;
				case 'f':
					initial_face_index = 5;
					break;
				case 'F':
					initial_face_index = -5;
					break;
				case 'b':
					initial_face_index = 6;
					break;
				case 'B':
					initial_face_index = -6;
					break;
				default:
					// no valid parameter
					break;
			}
			char target_downface = initial_orientation[Math.abs(initial_face_index) - 1];
			
			//change orientation of cube so that the target face is at the bottom (Looking at the cube from the sensor)
			orientateCubeForPermutation(target_downface);

			//permute current bottom face clockwise or counterclockwise according to the action
			move.holdCube();
			Delay.msDelay(500);
			move.rotateTable((int) (Math.signum(initial_face_index) * 105));
			Delay.msDelay(300);
			move.rotateTable((int) (Math.signum(initial_face_index) * -15));
			Delay.msDelay(300);
		}
	}
	
	private void orientateCubeForPermutation(char target_downface) {
		
		// Current right needed as downface
		if (current_orientation[3] == target_downface)
		{
			// rotate 90 degrees clockwise, then tilt once
			move.releaseCube();
			number_tilts = 0;
			exectueQuarterRotationClockwise();
			executeTilt();
			return;
		}
		// Current left needed as downface
		if (current_orientation[2] == target_downface)
		{
			// rotate 270 degrees COUNTER-clockwise, then tilt once					
			move.releaseCube();
			number_tilts = 0;
			exectueQuarterRotationClockwise();
			exectueQuarterRotationClockwise();
			exectueQuarterRotationClockwise();
			executeTilt();
			return;
		}
		// Current front needed as downface
		if (current_orientation[4] == target_downface)
		{
			// rotate 180 degrees COUNTER-clockwise, then tilt twice					
			move.releaseCube();
			number_tilts = 0;
			exectueQuarterRotationClockwise();
			exectueQuarterRotationClockwise();
			executeTilt();
			return;
		}
		// Current back needed as downface
		if (current_orientation[5] == target_downface)
		{
			// tilt once
			executeTilt();
			return;
		}
		// Current top needed as downface
		if (current_orientation[0] == target_downface)
		{
			// tilt twice
			executeTilt();
			executeTilt();
			return;
		}
	}
	
	private void exectueQuarterRotationClockwise() {
		
		Delay.msDelay(300);
		move.rotateTable(90);
		Delay.msDelay(300);
		
		// Update cube orientation after rotation
		char old_left = current_orientation[2];
		char old_right = current_orientation[3];
		char old_front = current_orientation[4];
		char old_back = current_orientation[5];
		current_orientation[2] = old_back; // new left is old back
		current_orientation[3] = old_front; // new right is old front
		current_orientation[4] = old_left; // new front is old left
		current_orientation[5] = old_right; // new back is old right
	}
	
	private void executeTilt() {
		
//		number_tilts++;
//		if (number_tilts > 2)
//		{
//			move.releaseCube();
//			Delay.msDelay(500);
//		}
		move.holdCube();
		Delay.msDelay(200);
		move.tiltCube();
		Delay.msDelay(700);
		
		// Update cube orientation after tilt
		char old_top = current_orientation[0];
		char old_down = current_orientation[1];
		char old_front = current_orientation[4];
		char old_back = current_orientation[5];
		current_orientation[0] = old_front; // new top is old front
		current_orientation[1] = old_back; // new down is old back
		current_orientation[4] = old_down; // new front is old down
		current_orientation[5] = old_top; // new back is old top
	}
	
	public char[] executeCompleteScan() {
		return executeCompleteScan(false);
	}
	
	public char[] executeCompleteScan(boolean reference_scan) {
		char[] scan_result_vector = new char[54];
		int[] current_rgb_vector = { 0, 0, 0 };
		int index = 0;
		
		// Scan all 6 sides of the cube
		for (int j = 0; j < 6; j++) {
			
			// scan center
			move.rotateTable(45);
			move.moveSensorToCenter();
			//Delay.msDelay(200);
			if (reference_scan) {
				current_rgb_vector = detect.getAverageRgbVector(200, 5);
				detect.rgb_ref[j][0] += current_rgb_vector[0];
				detect.rgb_ref[j][1] += current_rgb_vector[1];
				detect.rgb_ref[j][2] += current_rgb_vector[2];
			} else {
				scan_result_vector[index] = detect.detectColor(200, 5);
			}
			index += 1;
			move.moveSensorToEdge();
			move.moveSensorToCorner();
			move.rotateTable(-45);
			
			//Scan edge
			move.moveSensorToEdge();
			//Delay.msDelay(200);
			if (reference_scan) {
				current_rgb_vector = detect.getAverageRgbVector(200, 5);
				detect.rgb_ref[j][0] += current_rgb_vector[0];
				detect.rgb_ref[j][1] += current_rgb_vector[1];
				detect.rgb_ref[j][2] += current_rgb_vector[2];
			} else {
				scan_result_vector[index] = detect.detectColor(200, 5);
			}
			index += 1;
	
			//Scan remaining 7 cubie-surfaces clockwise on the current upper side
			for (int i = 0; i < 7; i++) {
				//Delay.msDelay(200);
//				move.removeSensor(); //
				move.rotateTable(45);
//				move.moveSensorToCenter(); //
				if (i % 2 == 1) {
					move.moveSensorToEdge();
				} else {
					move.moveSensorToCorner();
				}
				if (reference_scan) {
					current_rgb_vector = detect.getAverageRgbVector(200, 5);
					detect.rgb_ref[j][0] += current_rgb_vector[0];
					detect.rgb_ref[j][1] += current_rgb_vector[1];
					detect.rgb_ref[j][2] += current_rgb_vector[2];
				} else {
					scan_result_vector[index] = detect.detectColor(200, 5);
				}
				index += 1;
			}
			
			//Prepare for tilt
			//Delay.msDelay(200);
			move.rotateTable(45);
			//After 4 sides do an additional quarter turn
			if (j == 3) {
				move.rotateTable(90);
			}
			move.moveSensorToEdge();
			//Delay.msDelay(200);
			move.removeSensor();
			
			//Tilt cube (2x after scanning the 5th side, not after scanning last side)
			move.holdCube();
			Delay.msDelay(1000);
			move.tiltCube();
			if (j == 4) {
				Delay.msDelay(300);
				move.holdCube();
				move.tiltCube();
			}
			Delay.msDelay(1000);
			move.releaseCube();
			Delay.msDelay(500);
			 
			//Cancel scan by holding down orange button on NXT
			if (Button.ESCAPE.isDown()) {
				return null;
			}
		}
		move.rotateTable(-90);
		Delay.msDelay(500);
		move.holdCube();
		//Delay.msDelay(1000);
		move.tiltCube();
		Delay.msDelay(500);
		move.releaseCube();
		Delay.msDelay(300);
		
		if (reference_scan) {
			for (int k = 0; k < 6; k++) {
				detect.rgb_ref[k][0] = detect.rgb_ref[k][0] / 9;
				detect.rgb_ref[k][1] = detect.rgb_ref[k][1] / 9;
				detect.rgb_ref[k][2] = detect.rgb_ref[k][2] / 9;
			}
		}
		
		setInitialCubeOrientation(scan_result_vector);
		
		return scan_result_vector;
	}
	
	public char[] setInitialCubeOrientation(char[] scan_result_vector) {
		
		// Set cube orientation from scan result
		// Center faces at 0, 9, 18, 27, 36, 45
		initial_orientation[0] = scan_result_vector[0]; 	// Top
		initial_orientation[1] = scan_result_vector[18];	// Bottom
		initial_orientation[2] = scan_result_vector[36];	// Left
		initial_orientation[3] = scan_result_vector[45];	// Right
		initial_orientation[4] = scan_result_vector[9]; 	// Front
		initial_orientation[5] = scan_result_vector[27];	// Back
		
		// Also set it as current orientation
		current_orientation[0] = scan_result_vector[0]; 	// Top
		current_orientation[1] = scan_result_vector[18];	// Bottom
		current_orientation[2] = scan_result_vector[36];	// Left
		current_orientation[3] = scan_result_vector[45];	// Right
		current_orientation[4] = scan_result_vector[9]; 	// Front
		current_orientation[5] = scan_result_vector[27];	// Back
		
		return initial_orientation;
	}

	public char[] setInitialDummyCubeOrientation(char[] dummy_orientation) {
		
		// Set cube orientation from scan result
		// Center faces at 0, 9, 18, 27, 36, 45
		initial_orientation[0] = dummy_orientation[0]; 	// Top
		initial_orientation[1] = dummy_orientation[1];	// Bottom
		initial_orientation[2] = dummy_orientation[2];	// Left
		initial_orientation[3] = dummy_orientation[3];	// Right
		initial_orientation[4] = dummy_orientation[4]; 	// Front
		initial_orientation[5] = dummy_orientation[5];	// Back
		
		// Also set it as current orientation
		current_orientation[0] = dummy_orientation[0]; 	// Top
		current_orientation[1] = dummy_orientation[1];	// Bottom
		current_orientation[2] = dummy_orientation[2];	// Left
		current_orientation[3] = dummy_orientation[3];	// Right
		current_orientation[4] = dummy_orientation[4]; 	// Front
		current_orientation[5] = dummy_orientation[5];	// Back
		
		return initial_orientation;
	}
}
