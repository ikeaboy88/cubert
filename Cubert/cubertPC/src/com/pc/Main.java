package com.pc;

import java.util.Arrays;
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
				System.out.println("im solving...");
				
				//get scan result vector
				scan_result_vector = connect_PC.getScanResultVector();
				
				System.out.println("scan result vector pc:");
				for(int i = 0; i <scan_result_vector.length; i++){
					
					System.out.println(scan_result_vector[i]);
				}
				
				//INIT Cube Object with scan result vector
				Cube cube = new Cube(scan_result_vector);

//				Cube cube = new Cube(null);
//				cube.permuteCube('B');
//				cube.permuteCube('f');
//				cube.permuteCube('d');
//				cube.permuteCube('l');
//				cube.permuteCube('R');
				
				Solver magic = new Solver(cube);
//				char[]solving_sequence = new char[]{'r','L','D','f','b'};

				//Calculate solving sequence
				List<Character> sequence = magic.calculateSolvingSequence();
				
				//fill char array with values from List
				System.out.println("best path: ");
//				char[]solving_sequence = new char[sequence.size()]; 
				for(int i= 0; i < sequence.size(); i++){
//				solving_sequence[i] = sequence.get(i);
				System.out.println(sequence.get(i));
//				
				}
				
				//determine length of solving sequence
				int sequence_length = sequence.size();

//				for (Character character : sequence) {
//					System.out.println(character);
//				}
				
				//send LENGTH of solvinf sequence to nxt
				connect_PC.sendSolvingSequenceLength(sequence_length);
		
				//send solving sequence to nxt
				connect_PC.sendSolvingSequence(sequence);
				
				if (cube.cube_orientation == null) {
					System.out.println("SCAN ERROR");
				}
			}
			
			
		}while(mode[0] != -1);
	
		System.exit(0);
		
	}
}
