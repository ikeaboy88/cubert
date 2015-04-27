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
		while (true) {
			// After pressing a button...
			Button.waitForAnyPress();

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
			
			move.moveSensorToCenter();
			Delay.msDelay(1000);
			while (true) {
				// After pressing a button...
				Delay.msDelay(300);
				colorDetector.detectColor();
				// Exit loop when escape button was pressed
				if (Button.ESCAPE.isDown()) {
					break;
				}
			}
			Button.waitForAnyPress();
			move.moveSensorToEdge();
			Delay.msDelay(1000);
			while (true) {
				// After pressing a button...
				Delay.msDelay(300);
				colorDetector.detectColor();
				// Exit loop when escape button was pressed
				if (Button.ESCAPE.isDown()) {
					break;
				}
			}
			Button.waitForAnyPress();
			move.removeSensor();
			
			
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
