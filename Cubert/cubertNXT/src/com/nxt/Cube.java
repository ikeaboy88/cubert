package com.nxt;

import lejos.nxt.Button;
import lejos.nxt.SensorPort;
import lejos.util.Delay;

public class Cube {

	public Movement move;
	public ColorDetector detect;
	
	public Cube() {
		move = new Movement(true);
		detect = new ColorDetector(SensorPort.S3, true);
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
