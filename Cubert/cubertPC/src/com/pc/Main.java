package com.pc;

import java.util.Arrays;
import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		Connection connect_PC = new Connection();
		connect_PC.connectToNXT();
		Calibration cal = new Calibration(connect_PC);
		Cube cube = new Cube(null);
		Solver magic = new Solver(cube);

		cube.permuteCube('d');
		cube.permuteCube('f');
		cube.permuteCube('d');
		cube.permuteCube('l');
		
		List<Character> sequence = magic.calculateSolvingSequence();
		for (Character character : sequence) {
			System.out.println(character);
		}
		
		
//		char[]scan_result_vector = new char[54];
		char[]solving_sequence = new char[]{'L','D','F','D'};

		//safe recieved int value from datainputstream in byte buffer
//		byte[] mode = connect_PC.getMode();
		
		connect_PC.sendSolvingSequence(solving_sequence);
		byte[] mode =connect_PC.getMode();
		
		if(mode[0] == -1){
			System.exit(0);
		}
		//when recieveing mode 0, calibrattion mode 
//		if (mode[0] == 0) {
//			System.out.println("calibration mode");
//			cal.calibrate();
//			System.out.println("calibration done");
//			
//		}
//	
//		if(mode[0] == 1){
//			System.out.println("sending RGB reference values to NXT...");
//			int[]ref_RGB_calibration = cal.readCalibrationFromFile();
//			connect_PC.sendRGBCalibration(ref_RGB_calibration);
//			System.out.println("done");
//		}
////		else{
//			//cube has already to be scanned until pc can recieve data
//			System.out.println("need solving sequence!!");
//			mode = connect_PC.getMode();
//		
//			//nxt ready to send data
//			if( mode[0] == 2){
//				System.out.println("im solving...");
//				scan_result_vector = connect_PC.getScanResultVector();
//				
//				System.out.println("scan result vector pc:");
//				for(int i = 0; i <scan_result_vector.length; i++){
//					
//					System.out.println(scan_result_vector[i]);
//				}
//				if (cube.cube_orientation == null) {
//					System.out.println("SCAN ERROR");
//				}
//			}
		}
}
