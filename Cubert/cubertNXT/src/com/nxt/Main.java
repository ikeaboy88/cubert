package com.nxt;

import lejos.nxt.Button;
import lejos.nxt.LCD;

public class Main {

	public static void main(String[] args) {
		// Create object to execute movements on Cubert
		Cube cube = new Cube();

//		colorDetector.calibrate();
		// ***********************************
		LCD.drawString("Press button", 0, 0);
		LCD.drawString("to start", 0, 1);
		Button.waitForAnyPress();
		LCD.clear();
		
		// "Runtime loop"
		while (true) {
			// Exit loop when escape button was pressed
			if (Button.ESCAPE.isDown()) {
				break;
			}
			// ...execute code below
			cube.executeCompleteScan();
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
