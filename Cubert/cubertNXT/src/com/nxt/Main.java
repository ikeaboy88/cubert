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
		Movement move = new Movement();
		ColorDetector colorDetector = new ColorDetector();

//		colorDetector.calibrate();
		// ***********************************
		// "Runtime loop"
		Button.waitForAnyPress();
		while (true) {
			// After pressing a button...

			// Exit loop when escape button was pressed
			if (Button.ESCAPE.isDown()) {
				break;
			}
			// ...execute code below
			/*
			Button.waitForAnyPress();
			move.moveSensorToCenter();
			Delay.msDelay(2000);
			move.moveSensorToEdge();
			Delay.msDelay(2000);
			move.removeSensor();
			Delay.msDelay(2000);
			move.holdCube();
			Delay.msDelay(1000);
			move.tiltCube();
			Delay.msDelay(1000);
			move.releaseCube();
			Delay.msDelay(2000);
			move.moveSensorToCenter();
			Delay.msDelay(2000);
			move.moveSensorToEdge();
			Delay.msDelay(2000);
			move.removeSensor();
			Delay.msDelay(2000);
			move.rotateTable(90);
			Delay.msDelay(2000);
			move.holdCube();
			Delay.msDelay(1000);
			move.tiltCube();
			Delay.msDelay(1000);
			move.releaseCube();
			*/
			
			//Scan center and side
			move.moveSensorToCenter();
			Delay.msDelay(200);
			colorDetector.detectColor();
			//Button.waitForAnyPress();
			move.moveSensorToEdge();
			Delay.msDelay(200);
			colorDetector.detectColor();
			//Button.waitForAnyPress();
			move.removeSensor();
			
			//Tilt cube
			move.holdCube();
			Delay.msDelay(400);
			move.tiltCube();
			Delay.msDelay(400);
			move.releaseCube();
			
			
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
