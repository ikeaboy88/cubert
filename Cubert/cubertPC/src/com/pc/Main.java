package com.pc;

import java.util.Arrays;

import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		Connection connect_PC = new Connection();
		connect_PC.connectToNXT();
		Calibration cal = new Calibration(connect_PC);

		//safe recieved int value from datainputstream in byte buffer
		byte[] mode = connect_PC.getMode();
		
		//when recieveing mode 0, calibrattion mode 
		if (mode[0] == 0) {
			System.out.println("calibration mode");
			cal.calibrate();
			System.out.println("calibration done");
		}
		//when mode not 0 or calibration is done...
			System.out.println("sending RGB reference values to NXT...");
			int[]ref_RGB_calibration = cal.readCalibrationFromFile();
			connect_PC.sendRGBCalibration(ref_RGB_calibration);
			System.out.println("done");
			Cube cube = new Cube(connect_PC.getScanResultVector());
			if (cube.cube_orientation == null) {
				System.out.println("SCAN ERROR");
			}

			System.out.println();
			if (Arrays.equals(cube.cube_scrambled[count], cube_solved_cubie)) {
				System.out.println("IDENTICAL");
			} else {
				System.out.println("DIFFERENT");
			}
			count ++;
		}
		
		System.out.println();
		if (Arrays.deepEquals(cube.cube_solved, cube.cube_scrambled)) {
			System.out.println("CUBE IS SOLVED");
		} else {
			System.out.println("CUBE IS SCRAMBLED");
		}
		
		List<Character> solving_sequence = magic.calculateSolvingSequence();
		for (Character character : solving_sequence) {
			System.out.println(character);
		}
		
		System.out.println("END");
	}
}
