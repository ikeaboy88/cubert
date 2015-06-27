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
		char[] dummy_orientation = new char[] {'W', 'Y', 'O', 'R', 'G', 'B'};
		cube.setInitialDummyCubeOrientation(dummy_orientation); 
		
		/* Connection between NXT and PC */
		Connection connect_NXT = new Connection();
		connect_NXT.connectToPC();
		
		char[] solving_sequence = connect_NXT.getSolvingSequence();
		LCD.clear();
		LCD.drawString("chars:", 0, 0);
		Button.waitForAnyPress();
		LCD.clear();
		for(int i = 0; i < solving_sequence.length; i++){
			LCD.drawChar(solving_sequence[i], i, 0);
		}
		
		LCD.drawString("solving?:", 0, 8);
		Button.waitForAnyPress();
		cube.executeSolvingSequence(solving_sequence);
		
		Button.waitForAnyPress();
		connect_NXT.sendMode(-1);
		
		// "Runtime loop"
//			while (true) {
//				// Exit loop when escape button was pressed
//				if (Button.ESCAPE.isDown()) {
//					break;
//				}
//				// ...execute code below
//				LCD.drawString("Press RIGHT button", 0, 0);
//				LCD.drawString("to start", 0, 1);
//				LCD.drawString("calibration", 0, 2);
//				
//				//calibration mode (0)
//				if (Button.waitForAnyPress() == Button.ID_RIGHT) {
//					LCD.clear();
//					connect_NXT.sendMode(0);
//					LCD.drawString("Calibrating", 0, 1);
//					cube.executeCompleteScan(true);
//					List<Integer>rgb_values = new ArrayList<Integer>();
//					LCD.clear();
//					for(int [] reference_rgb : cube.detect.rgb_ref){
//						for (int i = 0; i <reference_rgb.length; i++)
//						{
//							rgb_values.add(reference_rgb[i]);
//						}
//					}
//				
//				connect_NXT.sendRGBCalibration(rgb_values);
//				LCD.drawString("calibration done, press BT", 1, 1);
//				Button.waitForAnyPress();
//				LCD.clear();
//				}
//				
//				LCD.drawString("Press RIGHT button to", 0, 0);
//				LCD.drawString("initialize", 0, 1);
//				
//				if (Button.waitForAnyPress() == Button.ID_RIGHT){
//					LCD.clear();
//					connect_NXT.sendMode(1);
//					LCD.drawString("Safe Ref_RGB values...", 0, 1);
//					cube.detect.rgb_ref=connect_NXT.safeReferenceRGBValues();
//				
//					int count=0;
//					LCD.clear();
//					for(int[] c : cube.detect.rgb_ref){
//						LCD.drawString(c[0]+","+c[1]+","+c[2], 0, count);
//						count++;
//					}
//					Button.waitForAnyPress();	
//					LCD.clear();
//				}
//				
////				LCD.drawString("ref: "+cube.detect.rgb_ref[2][2], 0, 1);
//				
//				//execute complete scan and send scan result vector to pc
//			
//				char[]complete_scan = new char[54];
//				
////				connect_NXT.sendMode(2);
//				complete_scan = cube.executeCompleteScan();
//				LCD.clear();
//				connect_NXT.sendMode(2);
//				LCD.drawString("start solving?", 0, 3);
//				Button.waitForAnyPress();
//				LCD.clear();
//				LCD.drawString("calculate solving sequence!", 0, 3);
//				connect_NXT.sendScanResultVector(complete_scan);
////				connect_NXT.sendScanResultVector(cube.executeCompleteScan());	
//				Button.waitForAnyPress();	
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
