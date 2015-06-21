package com.pc;

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

			int count = 0;
			for (char[] cube_solved_vector : cube.cube_solved) {
				System.out.println();
				System.out.println();
				System.out.println("Cubie : " + count + " solved: ");
				for (char color : cube_solved_vector) {
					System.out.print(" " + color);
				}
				System.out.println();
				System.out.println("Cubie : " + count + " scrambled: ");
				for (char color : cube.cube_scrambled[count]) {
					System.out.print(" " + color);
				}
				count++;
			}
	}
}
