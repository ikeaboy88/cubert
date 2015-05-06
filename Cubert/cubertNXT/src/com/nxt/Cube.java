package com.nxt;

import lejos.nxt.Button;
import lejos.nxt.SensorPort;
import lejos.util.Delay;


public class Cube {

	// Current orientation - updated after every move
	public char[] initial_orientation = new char[6];
	private char[] current_orientation = new char[6];
	public Movement move;
	public ColorDetector detect;
	
	public Cube() {
		move = new Movement(true);
		detect = new ColorDetector(SensorPort.S3, true);
	}
	
	// TODO
	public char[] permuteCube(char face) {
		
		int initial_face_index = 0;
		
		if (initial_orientation[0] != 0) {
			
			// Set the initial orientation as current if not set yet
			if (current_orientation[0] == 0) {
				current_orientation = initial_orientation;
			}
			
			switch (face) {
				case 't':
					initial_face_index = 0;
					break;
				case 'd':
					initial_face_index = 1;
					break;
				case 'l':
					initial_face_index = 2;
					break;
				case 'r':
					initial_face_index = 3;
					break;
				case 'f':
					initial_face_index = 4;
					break;
				case 'b':
					initial_face_index = 5;
					break;
				default:
					// no valid parameter
					break;
			}
			
			while (current_orientation[1] != initial_orientation[initial_face_index]) {
				//change orientation until bottom value of current orientation is equal to the initial index value
				//TODO: Maybe another litte A* for the shortest way to orientate the cube correct
				
				// 5.
				move.rotateTable(90);
				// 1-5
				move.holdCube();
				move.tiltCube();
				move.releaseCube();
				// 6.
				move.holdCube();
				move.tiltCube();
				move.releaseCube();
				
			}
			//permute current bottom face -90 degrees
			move.holdCube();
			move.rotateTable(-90);
			move.releaseCube();
			
			// Update orientation
		}
		
		return null;
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
			move.moveSensorToCenter();
			Delay.msDelay(200);
			if (reference_scan) {
				current_rgb_vector = detect.getAverageRgbVector(200, 5);
				detect.rgb_ref[j][0] += current_rgb_vector[0];
				detect.rgb_ref[j][1] += current_rgb_vector[1];
				detect.rgb_ref[j][2] += current_rgb_vector[2];
			} else {
				scan_result_vector[index] = detect.detectColor(200, 5);
			}
			index += 1;
			
			//Scan edge
			move.moveSensorToEdge();
			Delay.msDelay(200);
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
				Delay.msDelay(200);
				move.rotateTable(45);
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
			Delay.msDelay(200);
			move.rotateTable(45);
			//After 4 sides do an additional quarter turn
			if (j == 3) {
				move.rotateTable(90);
			}
			move.moveSensorToEdge();
			Delay.msDelay(200);
			move.removeSensor();
			
			//Tilt cube (2x after scanning the 5th side, not after scanning last side)
			move.holdCube();
			Delay.msDelay(1000);
			move.tiltCube();
			if (j == 4) {
				Delay.msDelay(300);
				move.tiltCube();
			}
			Delay.msDelay(1000);
			move.releaseCube();
			Delay.msDelay(1000);
			 
			//Cancel scan by holding down orange button on NXT
			if (Button.ESCAPE.isDown()) {
				return null;
			}
		}
		move.rotateTable(-90);
		Delay.msDelay(1000);
		move.holdCube();
		Delay.msDelay(1000);
		move.tiltCube();
		Delay.msDelay(1000);
		move.releaseCube();
		Delay.msDelay(1000);
		
		if (reference_scan) {
			for (int k = 0; k < 6; k++) {
				detect.rgb_ref[k][0] = detect.rgb_ref[k][0] / 9;
				detect.rgb_ref[k][1] = detect.rgb_ref[k][1] / 9;
				detect.rgb_ref[k][2] = detect.rgb_ref[k][2] / 9;
			}
		}
		return scan_result_vector;
	}
}
