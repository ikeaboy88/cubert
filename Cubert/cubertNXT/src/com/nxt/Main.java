package com.nxt;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lejos.nxt.Button;
import lejos.nxt.LCD;

public class Main {

	public static void main(String[] args) {
		// Create object to execute movements on Cubert
		Cube cube = new Cube();
		
		/* Connection between NXT and PC */
		Connection connect_NXT = new Connection();
		connect_NXT.connectToPC();
		
		
			// "Runtime loop"
			while (true) {
				// Exit loop when escape button was pressed
				if (Button.ESCAPE.isDown()) {
					break;
				}
				// ...execute code below
				LCD.drawString("Press RIGHT button", 0, 0);
				LCD.drawString("to start", 0, 1);
				LCD.drawString("calibration", 0, 2);
				
				//calibration mode (0)
				if (Button.waitForAnyPress() == Button.ID_RIGHT) {
					LCD.clear();
					connect_NXT.sendMode(0);
					LCD.drawString("Calibrating", 0, 1);
					cube.executeCompleteScan(true);
					List<Integer>rgb_values = new ArrayList<Integer>();
					LCD.clear();
					for(int [] reference_rgb : cube.detect.rgb_ref){
						for (int i = 0; i <reference_rgb.length; i++)
						{
							rgb_values.add(reference_rgb[i]);
						}
					}
				
				connect_NXT.sendRGBCalibration(rgb_values);
				LCD.drawString("calibration done, press BT", 1, 1);
				Button.waitForAnyPress();
				LCD.clear();
				}
				
				//when not calibration mode = 1
				LCD.drawString("Safe Ref_RGB values...", 0, 1);
				cube.detect.rgb_ref=connect_NXT.safeReferenceRGBValues();
				Button.waitForAnyPress();	
				LCD.clear();
				LCD.drawString("ref: "+cube.detect.rgb_ref[2][2], 0, 1);
				connect_NXT.sendScanResultVector(cube.executeCompleteScan());	
				Button.waitForAnyPress();	
			}
//		colorDetector.calibrate();
		// ***********************************
//		LCD.drawString("Press button", 0, 0);
//		LCD.drawString("to start", 0, 1);
//		LCD.drawString("calibration", 0, 2);
//		Button.waitForAnyPress();
//		LCD.clear();
//		LCD.drawString("Calibration", 0, 0);
//		LCD.drawString("running", 0, 1);
//		cube.executeCompleteScan(true);
//		LCD.clear();
//		LCD.drawString("Press button", 0, 0);
//		LCD.drawString("to start", 0, 1);
//		LCD.drawString("recognition", 0, 2);
//		Button.waitForAnyPress();
//		LCD.clear();
		

		// ***********************************

	}

}
