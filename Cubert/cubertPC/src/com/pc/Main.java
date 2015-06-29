package com.pc;

import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		Connection connect_PC = new Connection();
		connect_PC.connectToNXT();
		Calibration cal = new Calibration(connect_PC);
		char[]scan_result_vector = new char[54];
		byte[]mode = new byte[1];

		
		do{
			
			//get mode and react dependant on mode (int) value
			mode =connect_PC.getMode();
			
			//when recieveing mode 0, calibrattion mode 
			if (mode[0] == 0) {
				System.out.println("calibration mode");
				cal.calibrate();
				System.out.println("calibration done");
			}
			
			if(mode[0] == 1){
				System.out.println("sending RGB reference values to NXT...");
				int[]ref_RGB_calibration = cal.readCalibrationFromFile();
				connect_PC.sendRGBCalibration(ref_RGB_calibration);
				System.out.println("done");
			}
			
			if( mode[0] == 2){
				System.out.println("waiting for cubert finishing complete scan...");
				
				//get scan result vector
				scan_result_vector = connect_PC.getScanResultVector();
				
				System.out.println("scan result vector pc:");
				int orange = 0;
				int red = 0;
				int green = 0;
				int white = 0;
				int yellow = 0;
				int blue = 0;
				
				for(int i = 0; i <scan_result_vector.length; i++){
					
					//skip middle cubies 
					if(i != 0 && i != 18 && i != 36 && i != 45 && i != 9 && i != 27){
						switch(scan_result_vector[i]){
						case 'R': red++;
						break; 
						case 'O': orange++;
						break;
						case 'G': green++; 
						break; 
						case 'B' : blue++;
						break; 
						case 'W' : white++;
						break; 
						case 'Y' : yellow++;
						break;
						default: System.out.println("keine farbe erkannt!");
						}
					}
					
					System.out.println(scan_result_vector[i]);
					
				}
				if(red != 8 || orange != 8 || green != 8 || blue != 8 || white != 8 || yellow != 8)
				{
					System.out.println("red: "+ red);
					System.out.println("orange: "+ orange);
					System.out.println("blue: "+ blue);
					System.out.println("green: "+green);
					System.out.println("white: "+ white);
					System.out.println("yellow: "+yellow);
					
					//tell nxt that solving sequence can't be computed
					connect_PC.sendMode(-1);
					continue;
				}
				
				connect_PC.sendMode(1);
				//INIT Cube Object with scan result vector
				Cube cube = new Cube(scan_result_vector);

				Solver magic = new Solver(cube);

				//Calculate solving sequence
				List<Character> sequence = magic.calculateSolvingSequence();
				
				//fill char array with values from List
				System.out.println("best path: ");
				
				for(int i= 0; i < sequence.size(); i++){
				System.out.println(sequence.get(i));
				}
				
				//determine length of solving sequence
				int sequence_length = sequence.size();

				//send LENGTH of solving sequence to nxt
				connect_PC.sendSolvingSequenceLength(sequence_length);
		
				//send solving sequence to nxt
				connect_PC.sendSolvingSequence(sequence);
				System.out.println("Cubert, solve the cube!");
				
				connect_PC.sendMode(-1);
				
				if (cube.cube_orientation == null) {
					System.out.println("SCAN ERROR");
				}
			}
			
		}while(mode[0] != -1);
		System.out.println("Und Tschüss!");
		System.exit(0);		
	}
}
