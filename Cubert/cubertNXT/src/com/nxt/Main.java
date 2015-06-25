package com.nxt;

import lejos.nxt.Button;
import lejos.nxt.LCD;

public class Main {

	public static void main(String[] args) {
		// Create object to execute movements on Cubert
		Cube cube = new Cube();
		char[] solving_sequence = new char[] {'T', 'r', 'D', 'b', 'd', 'L', 't', 'l'};
		char[] dummy_orientation = new char[] {'W', 'Y', 'O', 'R', 'G', 'B'};
//		colorDetector.calibrate();
		// ***********************************
		LCD.drawString("Press button", 0, 0);
		LCD.drawString("to start", 0, 1);
		LCD.drawString("calibration", 0, 2);
		Button.waitForAnyPress();
		cube.setInitialDummyCubeOrientation(dummy_orientation); 
		cube.executeSolvingSequence(solving_sequence);
		LCD.clear();
		LCD.drawString("Calibration", 0, 0);
		LCD.drawString("running", 0, 1);
//		cube.executeCompleteScan(true);
		LCD.clear();
		LCD.drawString("Press button", 0, 0);
		LCD.drawString("to start", 0, 1);
		LCD.drawString("recognition", 0, 2);
		Button.waitForAnyPress();
		LCD.clear();
		
		// "Runtime loop"
		while (true) {
			// Exit loop when escape button was pressed
			if (Button.ESCAPE.isDown()) {
				break;
			}
			// ...execute code below
//			/* Connection between NXT and PC */
//			Connection connect_NXT = new Connection();
//			connect_NXT.connectToPC();
//			connect_NXT.sendScanResultVector(cube.executeCompleteScan());
//			//TODO: save validated cube orientation from pc cube class in nxt cube class
//			connect_NXT.disconnectFromPC();
//			Button.waitForAnyPress();
		}
		// ***********************************
	}

}
