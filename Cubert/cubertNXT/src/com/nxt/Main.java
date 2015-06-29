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
		
		/* Connection between NXT and PC */
		Connection connect_NXT = new Connection();
		connect_NXT.connectToPC();

		// Create object to execute movements on Cubert
		Cube cube = new Cube();
		
		boolean ready_for_scan = false;
		int mode = 0;
		
		// "Runtime loop"
		while (true) {
			// Exit loop when escape button was pressed
			// ...execute code below
			
			do{
				
				if (Button.ESCAPE.isDown()) {
					connect_NXT.sendMode(-1);
					System.exit(0);
				}
				
				LCD.clear();
				LCD.drawString("Press RIGHT button", 0, 0);
				LCD.drawString("to start", 0, 1);
				LCD.drawString("calibration", 0, 2);
				LCD.drawString("LEFT To", 0, 3);
				LCD.drawString("INIT Cube", 0, 4);
				
				if(mode == -1){
					ready_for_scan = true; 
				}
				
				//calibration mode
				else if (Button.waitForAnyPress() == Button.ID_RIGHT) {
					LCD.clear();
					connect_NXT.sendMode(0);
					LCD.drawString("Calibrating", 0, 1);
					cube.executeCompleteScan(true);
					List<Integer>rgb_values = new ArrayList<Integer>();
					LCD.clear();
					
					int count = 0;
					
					//DEBUG
//					LCD.drawString("Reference:"	, 0, 0);
					for(int [] reference_rgb : cube.detect.rgb_ref){
						
						
						//DEBUG
//						LCD.drawString(reference_rgb[0]+", "+reference_rgb[1]+", "+reference_rgb[2], 0, count);
//						count++;
						for (int i = 0; i <reference_rgb.length; i++)
						{
							rgb_values.add(reference_rgb[i]);
							
						}
					}
					
//					Button.waitForAnyPress();
//					LCD.clear();
					connect_NXT.sendRGBCalibration(rgb_values);
//					LCD.drawString("calibration done, press BT", 1, 1);
//					Button.waitForAnyPress();
					LCD.clear();
					ready_for_scan = true; 
				}
				
				//INIT ref_rgb values from txt.file on pc
				//TODO: make clear that .txt file contains reference values!!
				//TODO: prevent negative size exception in solving sequence array!!
				else if (Button.waitForAnyPress() == Button.ID_LEFT){
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
				
			
			//solving mode only when calibrated or initialized and pressed enter
			LCD.clear();
			LCD.drawString("Press enter to solve cube..", 0, 0);
			if(ready_for_scan && Button.waitForAnyPress() == Button.ID_ENTER){
				connect_NXT.sendMode(2);
				
				char[]complete_scan = new char[54];
				complete_scan = cube.executeCompleteScan();
				
				//execute complete scan and send scan result vector to pc
				connect_NXT.sendScanResultVector(complete_scan);	
				
				//ask pc if it was able to compute solvinf sequence
				mode = connect_NXT.getMode();
			
//				Button.waitForAnyPress();
				
				//only read input stream when pc determined solving sequence otherwise start from beginning 
				if(mode == 1){
					//get length of solving sequence
					int solving_sequence_length = connect_NXT.getSolvingSequenceLength();
					
					//get actual solving sequence
					char[] solving_sequence = connect_NXT.getSolvingSequence(solving_sequence_length);
					LCD.clear();
					
					//DEBUG
//				LCD.drawString("chars:", 0, 0);
//				Button.waitForAnyPress();
//				LCD.clear();
//				for(int i = 0; i < solving_sequence.length; i++){
//					LCD.drawChar(solving_sequence[i], i, 0);
//				}
					
//				LCD.drawString("solving?:", 0, 8);
//				Button.waitForAnyPress();
					
					//permute cube according to solving sequence
					cube.executeSolvingSequence(solving_sequence);
					
					ready_for_scan = false; 
					
					mode = connect_NXT.getMode();
					
					Button.waitForAnyPress();
				}
			}
		}
		// ***********************************

	}

}
