package com.nxt;

import java.util.ArrayList;
import java.util.List;

import lejos.nxt.Button;
import lejos.nxt.LCD;

public class Calibration {
	
	public static void main(String[] args) {
		
		Cube cube = new Cube();
		
		LCD.drawString("Press RIGHT button", 0, 0);
		LCD.drawString("to start", 0, 1);
		LCD.drawString("calibration", 0, 2);
		if (Button.waitForAnyPress() == Button.ID_RIGHT) {
			LCD.drawString("Calibrating", 0, 1);
			cube.executeCompleteScan(true);
		}
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
		
		LCD.clear();
		/* Connection between NXT and PC */
		Connection connect_NXT = new Connection();
		connect_NXT.connectToPC();
		connect_NXT.sendRGBCalibration(rgb_string);
	}
}
