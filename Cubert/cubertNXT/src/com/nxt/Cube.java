package com.nxt;

import lejos.nxt.SensorPort;
import lejos.util.Delay;

public class Cube {

	Movement move;
	ColorDetector detect;
	
	public Cube() {
		move = new Movement(true);
		detect = new ColorDetector(SensorPort.S3, true);
	}
	
	public void executeCompleteScan() {
		
		// scan center
		move.moveSensorToCenter();
		Delay.msDelay(200);
		detect.detectColor(200, 5);
		
		//Scan edges
		move.moveSensorToEdge();
		Delay.msDelay(200);
		detect.detectColor(200, 5);
		//Button.waitForAnyPress();
		for (int i = 0; i < 7; i++) {
			Delay.msDelay(200);
			move.rotateTable(45);
			if (i % 2 == 1) {
				move.moveSensorToEdge();
			} else {
				move.moveSensorToCorner();
			}
			detect.detectColor(200, 5);
		}
		Delay.msDelay(200);
		move.rotateTable(45);
		move.moveSensorToEdge();
		Delay.msDelay(200);
		move.removeSensor();
		
		//Tilt cube
		move.holdCube();
		Delay.msDelay(1000);
		move.tiltCube();
		Delay.msDelay(1000);
		move.releaseCube();
		Delay.msDelay(1000);
	}
	
}
