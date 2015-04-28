package com.nxt;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.MotorPort;
import lejos.nxt.TachoMotorPort;
import lejos.nxt.comm.NXTConnection;
import lejos.nxt.comm.USB;
import lejos.nxt.comm.USBConnection;
import lejos.util.Delay;

public class Main {

	public static void main(String[] args) {
		// Create object to execute movements on Cubert
		Movement move = new Movement(true);
		ColorDetector colorDetector = new ColorDetector(true);

//		colorDetector.calibrate();
		// ***********************************
		// "Runtime loop"
		LCD.drawString("Press button", 0, 0);
		LCD.drawString("to start", 0, 1);
		Button.waitForAnyPress();
		LCD.clear();
		
		while (true) {
			// After pressing a button...

			// Exit loop when escape button was pressed
			if (Button.ESCAPE.isDown()) {
				break;
			}
			// ...execute code below
			
			//Scan center
			move.moveSensorToCenter();
			Delay.msDelay(200);
			colorDetector.detectColor(200, 5);
			//Button.waitForAnyPress();
			
			//Scan edges
			move.moveSensorToEdge();
			Delay.msDelay(200);
			colorDetector.detectColor(200, 5);
			//Button.waitForAnyPress();
			for (int i = 0; i < 7; i++) {
				Delay.msDelay(200);
				move.rotateTable(45);
				if (i % 2 == 1) {
					move.moveSensorToEdge();
				} else {
					move.moveSensorToCorner();
				}
				colorDetector.detectColor(200, 5);
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
		// ***********************************

		/* Connection between NXT and PC */
		// Connection connect_NXT = new Connection();
		// connect_NXT.connectToPC();
		// connect_NXT.sendDatatoPC(connect_NXT.getSendInt());
		// connect_NXT.recieveDatafromPC();
		// connect_NXT.closeStreams();
	}

}
