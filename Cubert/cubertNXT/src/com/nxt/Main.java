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
		
		LCD.drawString("Press RIGHT button", 0, 0);
		LCD.drawString("to start", 0, 1);
		LCD.drawString("calibration", 0, 2);
		if (Button.waitForAnyPress() == Button.ID_RIGHT) {
			connect_NXT.sendMode(0);
			LCD.drawString("Calibrating", 0, 1);
			cube.executeCompleteScan(true);
		int[][] ref = cube.detect.rgb_ref;
		List<Integer>rgb_values = new ArrayList<Integer>();
		for(int [] reference_rgb : ref){
			for (int i = 0; i <reference_rgb.length; i++)
			{
				rgb_values.add(reference_rgb[i]);
			}
		}
		
		String rgb_string = "";
		for(int i = 0; i<rgb_values.size(); i++){
			rgb_string += rgb_values.get(i)+",";
		}
		rgb_string = rgb_string.substring(0, rgb_string.length()-1);
		
		LCD.clear();
		/* Connection between NXT and PC */
		connect_NXT.sendRGBCalibration(rgb_string);
		LCD.drawString("calibration done, press BT", 1, 1);
		Button.waitForAnyPress();
		LCD.clear();
		}
			
			//get reference_rgb vector from file via dis
			
			// "Runtime loop"
//			while (true) {
				// Exit loop when escape button was pressed
//				if (Button.ESCAPE.isDown()) {
//					break;
//				}
				// ...execute code below
				
				connect_NXT.sendMode(1);
				connect_NXT.sendScanResultVector(cube.executeCompleteScan());
				Button.waitForAnyPress();
//			}
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
