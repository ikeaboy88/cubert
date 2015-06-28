package com.nxt;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorHTSensor;
import lejos.robotics.Color;

public class Main {

	public static void main(String[] args) {
		
		
//		do{
//			
//		LCD.drawString(""+test.getColorID(), 0,0);
//		LCD.drawString(""+test.getColor().getRed(), 0, 1);
//		LCD.drawString(""+test.getColor().getGreen(), 0, 2);
//		LCD.drawString(""+test.getColor().getBlue(), 0, 3);
//		}while(true);
		
		
		
		
		/* Connection between NXT and PC */
		Connection connect_NXT = new Connection();
		connect_NXT.connectToPC();

		// Create object to execute movements on Cubert
		Cube cube = new Cube();
		
		
//		LCD.clear();
//		LCD.drawString("White", 0, 0);
//		Button.waitForAnyPress();
//		int white = cube.detect.initWhiteBalance();
//		LCD.drawString("white: "+white, 1, 0);
//		Button.waitForAnyPress();
//		LCD.clear();
//		LCD.drawString("Black", 0, 0);
//		Button.waitForAnyPress();
//		int black = cube.detect.initBlackLevel();
//		LCD.drawString("black: "+black, 1, 0);
//		Button.waitForAnyPress();
//		System.exit(0);
		
//		do{
//			Button.waitForAnyPress();
//			cube.move.moveSensorToCenter();
//			LCD.clear();
//			LCD.drawString(""+cube.detect.getColorID(), 0,0);
//			LCD.drawString(""+cube.detect.getRGBNormalized(Color.RED), 0, 1);
//			LCD.drawString(""+cube.detect.getRGBNormalized(Color.GREEN), 0, 2);
//			LCD.drawString(""+cube.detect.getRGBNormalized(Color.BLUE), 0, 3);
//			Button.waitForAnyPress();
//			LCD.clear();
////			LCD.clear();
////			cube.move.moveSensorToEdge();
////			LCD.clear();
////			LCD.drawString(""+cube.detect.getColorID(), 0,0);
////			LCD.drawString(""+cube.detect.getColor().getRed(), 0, 1);
////			LCD.drawString(""+cube.detect.getColor().getGreen(), 0, 2);
////			LCD.drawString(""+cube.detect.getColor().getBlue(), 0, 3);
////			Button.waitForAnyPress();
//			cube.move.moveSensorToEdge();
//			cube.move.removeSensor();
//			
//		}while(true);
		
		
		boolean ready_for_scan = false; 
		
//		cube.executeCompleteScan();
		
		
		// "Runtime loop"
		while (true) {
			// Exit loop when escape button was pressed
			if (Button.ESCAPE.isDown()) {
				connect_NXT.sendMode(-1);
				break;
			}
			// ...execute code below
			
			
			//calibration mode (0)
			
			do{
				LCD.drawString("Press RIGHT button", 0, 0);
				LCD.drawString("to start", 0, 1);
				LCD.drawString("calibration", 0, 2);
				LCD.drawString("LEFT To", 0, 3);
				LCD.drawString("INIT Cube", 0, 4);
				
				//calibration mode
				if (Button.waitForAnyPress() == Button.ID_RIGHT) {
					LCD.clear();
					connect_NXT.sendMode(0);
					LCD.drawString("Calibrating", 0, 1);
					cube.executeCompleteScan(true);
					List<Integer>rgb_values = new ArrayList<Integer>();
					LCD.clear();
					
					int count = 0;
					LCD.drawString("Reference:"	, 0, 0);
					for(int [] reference_rgb : cube.detect.rgb_ref){
						
						LCD.drawString(reference_rgb[0]+", "+reference_rgb[1]+", "+reference_rgb[2], 0, count);
						count++;
						for (int i = 0; i <reference_rgb.length; i++)
						{
							rgb_values.add(reference_rgb[i]);
							
						}
					}
					
					Button.waitForAnyPress();
					LCD.clear();
					connect_NXT.sendRGBCalibration(rgb_values);
					LCD.drawString("calibration done, press BT", 1, 1);
					Button.waitForAnyPress();
					LCD.clear();
					ready_for_scan = true; 
				}
				
				//INIT ref_rgb values from txt.file on pc
				//TODO: make clear that .txt file contains reference values!!
				//TODO: prevent negative size exception in solving sequence array!!
				if (Button.waitForAnyPress() == Button.ID_LEFT){
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
					Button.waitForAnyPress();	
					LCD.clear();
					ready_for_scan = true;
				}
				
				
			}while(!ready_for_scan);
				
			
			//solving mode
			if(ready_for_scan){
				connect_NXT.sendMode(2);
				
				
//				char[] dummy_orientation = new char[] {'W', 'Y', 'O', 'R', 'G', 'B'};
//				cube.setInitialDummyCubeOrientation(dummy_orientation); 
				
				char[]complete_scan = new char[54];
//				char[] complete_scan = new char[]{'W','O','B','B','W','O','W','B','B','G','O','O','W','O','G','O','W','O','Y','Y','Y','G','G','Y','G','G','Y','B','B','R','R','R','R','R','R','R','O','O','Y','Y','B','B','B','Y','Y','R','G','G','W','W','R','W','W','G'};
				complete_scan = cube.executeCompleteScan();
				
				//execute complete scan and send scan result vector to pc
				
				//DUMMY scan_result_vector
				connect_NXT.sendScanResultVector(complete_scan);	
			
				Button.waitForAnyPress();
				
				//get length of solving sequence
				int solving_sequence_length = connect_NXT.getSolvingSequenceLength();
				
				//get actual solving sequence
				char[] solving_sequence = connect_NXT.getSolvingSequence(solving_sequence_length);
				LCD.clear();
				
				LCD.drawString("chars:", 0, 0);
				Button.waitForAnyPress();
				LCD.clear();
				for(int i = 0; i < solving_sequence.length; i++){
					LCD.drawChar(solving_sequence[i], i, 0);
				}
				
				LCD.drawString("solving?:", 0, 8);
				Button.waitForAnyPress();
				
				//permute cube according to solving sequence
				cube.executeSolvingSequence(solving_sequence);
				
				ready_for_scan = false; 
				
				Button.waitForAnyPress();
			}
		}
		// ***********************************

	}

}
