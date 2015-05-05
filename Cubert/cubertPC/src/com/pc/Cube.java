package com.pc;

public class Cube {

	// Orientation of the cube itself - which centers are facing in which direction
	public char[] cube_orientation = new char[6];
	public char[][] cube_scrambled = null;
	public char[][] cube_solved = null;
	
	public Cube(char[] scan_result_vector) {
		if (scan_result_vector != null) {
			cube_orientation = getCubeOrientation(scan_result_vector);
			if (cube_orientation != null) {
				cube_solved = createSolvedState(cube_orientation);
				cube_scrambled = createScrambledState(scan_result_vector);
			}
		}
	}
	
	private char[] getCubeOrientation(char[] scan_result_vector) {
		
		// Set cube orientation from scan result
		// Center faces at 0, 9, 18, 27, 36, 45
		cube_orientation[0] = scan_result_vector[0]; 	// Top
		cube_orientation[1] = scan_result_vector[18];	// Bottom
		cube_orientation[2] = scan_result_vector[36];	// Left
		cube_orientation[3] = scan_result_vector[45];	// Right
		cube_orientation[4] = scan_result_vector[9]; 	// Front
		cube_orientation[5] = scan_result_vector[27];	// Back
		
		cube_orientation = validateCenters(cube_orientation);
		
		return cube_orientation;
	}
	
	private char[][] createSolvedState(char[] cube_orientation) {
		
		// Solved state for each cubie - depending on the cube's initial orientation
		// 		values in the array: x - no color (facing inside), r - red, g - green, b - blue, w - white, y - yellow, o - orange)
		// 		positions in the array: Top, bottom, left, right, front, back)
		char[] cubie_00_solved = {'x'					, cube_orientation[1]	, cube_orientation[2]	, 'x'					, 'x'					, cube_orientation[5]	}; // Corner: 	Yellow	, orange, blue
		char[] cubie_01_solved = {'x'					, cube_orientation[1]	, 'x'					, 'x'					, 'x'					, cube_orientation[5]	}; // Edge:		Yellow	, blue
		char[] cubie_02_solved = {'x'					, cube_orientation[1]	, 'x'					, cube_orientation[3]	, 'x'					, cube_orientation[5]	}; // Corner: 	Yellow	, red	, blue
		char[] cubie_03_solved = {'x'					, 'x'					, cube_orientation[2]	, 'x'					, 'x'					, cube_orientation[5]	}; // Edge:		Orange	, blue
		char[] cubie_04_solved = {'x'					, 'x'					, 'x'					, cube_orientation[3]	, 'x'					, cube_orientation[5]	}; // Edge:		Red		, blue
		char[] cubie_05_solved = {cube_orientation[0]	, 'x'					, cube_orientation[2]	, 'x'					, 'x'					, cube_orientation[5]	}; // Corner: 	White	, orange, blue
		char[] cubie_06_solved = {cube_orientation[0]	, 'x'					, 'x'					, 'x'					, 'x'					, cube_orientation[5]	}; // Edge:		White	, blue
		char[] cubie_07_solved = {cube_orientation[0]	, 'x'					, 'x'					, cube_orientation[3]	, 'x'					, cube_orientation[5]	}; // Corner: 	White	, red	, blue
		char[] cubie_08_solved = {'x'					, cube_orientation[1]	, cube_orientation[2]	, 'x'					, 'x'					, 'x'					}; // Edge:		Yellow	, orange
		char[] cubie_09_solved = {'x'					, cube_orientation[1]	, 'x'					, cube_orientation[3]	, 'x'					, 'x'					}; // Edge:		Yellow	, red
		char[] cubie_10_solved = {cube_orientation[0]	, 'x'					, cube_orientation[2]	, 'x'					, 'x'					, 'x'					}; // Edge:		White	, orange
		char[] cubie_11_solved = {cube_orientation[0]	, 'x'					, 'x'					, cube_orientation[3]	, 'x'					, 'x'					}; // Edge:		White	, red
		char[] cubie_12_solved = {'x'					, cube_orientation[1]	, cube_orientation[2]	, 'x'					, cube_orientation[4]	, 'x'					}; // Corner: 	Yellow	, orange, green
		char[] cubie_13_solved = {'x'					, cube_orientation[1]	, 'x'					, 'x'					, cube_orientation[4]	, 'x'					}; // Edge:		Yellow	, green
		char[] cubie_14_solved = {'x'					, cube_orientation[1]	, 'x'					, cube_orientation[3]	, cube_orientation[4]	, 'x'					}; // Corner: 	Yellow	, red	, green
		char[] cubie_15_solved = {'x'					, 'x'					, cube_orientation[2]	, 'x'					, cube_orientation[4]	, 'x'					}; // Edge:		Orange	, green
		char[] cubie_16_solved = {'x'					, 'x'					, 'x'					, cube_orientation[3]	, cube_orientation[4]	, 'x'					}; // Edge:		Red		, green
		char[] cubie_17_solved = {cube_orientation[0]	, 'x'					, cube_orientation[2]	, 'x'					, cube_orientation[4]	, 'x'					}; // Corner: 	White	, orange, green
		char[] cubie_18_solved = {cube_orientation[0]	, 'x'					, 'x'					, 'x'					, cube_orientation[4]	, 'x'					}; // Edge:		White	, green
		char[] cubie_19_solved = {cube_orientation[0]	, 'x'					, 'x'					, cube_orientation[3]	, cube_orientation[4]	, 'x'					}; // Corner: 	White	, red	, green
		
		char[][] cube_solved = { 	cubie_00_solved, cubie_01_solved, cubie_02_solved, cubie_03_solved,
									cubie_04_solved, cubie_05_solved, cubie_06_solved, cubie_07_solved,
									cubie_08_solved, cubie_09_solved, cubie_10_solved, cubie_11_solved,
									cubie_12_solved, cubie_13_solved, cubie_14_solved, cubie_15_solved,
									cubie_16_solved, cubie_17_solved, cubie_18_solved, cubie_19_solved
								};
		
		return cube_solved;
	}
	
	//TODO:
	private char[][] createScrambledState(char[] scan_result_vector) {
		// overall faces count: 54
		// corner at 2+13+44, 4+29+42, 6+35+53, 8+15+51, 11+22+38, 17+24+49, 20+31+40, 26+33+47
		// edge at 1+14, 3+43, 5+28, 7+52, 10+23, 12+37, 16+50, 19+32, 21+39, 25+48, 30+41, 34+46 

		//TODO: Group all corners and edges into actual cubies
		
		char[] cubie_00 = {'x'						, scan_result_vector[20]	, scan_result_vector[40]	, 'x'						, 'x'						, scan_result_vector[31]};	// Corner
		char[] cubie_01 = {'x'						, scan_result_vector[19]	, 'x'						, 'x'						, 'x'						, scan_result_vector[32]};	// Edge
		char[] cubie_02 = {'x'						, scan_result_vector[26]	, 'x'						, scan_result_vector[47]	, 'x'						, scan_result_vector[33]};	// Corner
		char[] cubie_03 = {'x'						, 'x'						, scan_result_vector[41]	, 'x'						, 'x'						, scan_result_vector[30]};	// Edge
		char[] cubie_04 = {'x'						, 'x'						, 'x'						, scan_result_vector[46]	, 'x'						, scan_result_vector[34]};	// Edge
		char[] cubie_05 = {scan_result_vector[4]	, 'x'						, scan_result_vector[42]	, 'x'						, 'x'						, scan_result_vector[29]};	// Corner
		char[] cubie_06 = {scan_result_vector[5]	, 'x'						, 'x'						, 'x'						, 'x'						, scan_result_vector[28]};	// Edge
		char[] cubie_07 = {scan_result_vector[6]	, 'x'						, 'x'						, scan_result_vector[53]	, 'x'						, scan_result_vector[35]};	// Corner
		char[] cubie_08 = {'x'						, scan_result_vector[21]	, scan_result_vector[39]	, 'x'						, 'x'						, 'x'					};	// Edge
		char[] cubie_09 = {'x'						, scan_result_vector[25]	, 'x'						, scan_result_vector[48]	, 'x'						, 'x'					};	// Edge
		char[] cubie_10 = {scan_result_vector[3]	, 'x'						, scan_result_vector[43]	, 'x'						, 'x'						, 'x'					};	// Edge
		char[] cubie_11 = {scan_result_vector[7]	, 'x'						, 'x'						, scan_result_vector[52]	, 'x'						, 'x'					};	// Edge
		char[] cubie_12 = {'x'						, scan_result_vector[22]	, scan_result_vector[38]	, 'x'						, scan_result_vector[11]	, 'x'					};	// Corner
		char[] cubie_13 = {'x'						, scan_result_vector[23]	, 'x'						, 'x'						, scan_result_vector[10]	, 'x'					};	// Edge
		char[] cubie_14 = {'x'						, scan_result_vector[24]	, 'x'						, scan_result_vector[49]	, scan_result_vector[17]	, 'x'					};	// Corner
		char[] cubie_15 = {'x'						, 'x'						, scan_result_vector[37]	, 'x'						, scan_result_vector[12]	, 'x'					};	// Edge
		char[] cubie_16 = {'x'						, 'x'						, 'x'						, scan_result_vector[50]	, scan_result_vector[16]	, 'x'					};	// Edge
		char[] cubie_17 = {scan_result_vector[2]	, 'x'						, scan_result_vector[44]	, 'x'						, scan_result_vector[13]	, 'x'					};	// Corner
		char[] cubie_18 = {scan_result_vector[1]	, 'x'						, 'x'						, 'x'						, scan_result_vector[14]	, 'x'					};	// Edge
		char[] cubie_19 = {scan_result_vector[8]	, 'x'						, 'x'						, scan_result_vector[51]	, scan_result_vector[15]	, 'x'					};	// Corner
		
		char[][] cube_scrambled = {	cubie_00, cubie_01, cubie_02, cubie_03, cubie_04,
									cubie_05 ,cubie_06, cubie_07, cubie_08,	cubie_09,
									cubie_10, cubie_11, cubie_12, cubie_13, cubie_14,
									cubie_15, cubie_16, cubie_17, cubie_18, cubie_19
								};
		
		return cube_scrambled;
	}

	public char[] validateCenters(char[] cube_orientation) {
		
		char[][] valid_pairs = {{'W', 'Y'}, {'G', 'B'}, {'R', 'O'}};
		int valid_count = 0;
		char[] test_pair = new char[2];
		char[] new_cube_orientation = {'x', 'x', 'x', 'x', 'x', 'x'};
		
		for (int i = 0; i < 6; i += 2) {
			boolean valid = false;
		
			test_pair[0] = cube_orientation[i];
			test_pair[1] = cube_orientation[i+1];
	
			for (char[] valid_pair: valid_pairs) {
				if ( (test_pair[0] == valid_pair[0] && test_pair[1] == valid_pair[1]) || (test_pair[0] == valid_pair[1] && test_pair[1] == valid_pair[0]) ) {
					valid = true;
					valid_count += 1;
					new_cube_orientation[i] = test_pair[0];
					new_cube_orientation[i+1] = test_pair[1];
				}
			}
			System.out.println(test_pair[0] + " & " + test_pair[1] + " is valid pair: " + valid);
		}
	
		// One pair incorrect
		if (valid_count == 2) {
			
			// Top-Bottom pair incorrect
			if (new_cube_orientation[0] == 'x') {
				// Check left
				switch (new_cube_orientation[2]) {
				
				case 'W':
					// Check front
					switch (new_cube_orientation[4]) {
						case 'O':
							new_cube_orientation[0] = 'G';
							new_cube_orientation[1] = 'B';
							break;
						case 'B':
							new_cube_orientation[0] = 'O';
							new_cube_orientation[1] = 'R';
							break;
						case 'R':
							new_cube_orientation[0] = 'B';
							new_cube_orientation[1] = 'G';
							break;
						case 'G':
							new_cube_orientation[0] = 'R';
							new_cube_orientation[1] = 'O';
							break;
					}
					break;
				
				case 'Y':
					switch (new_cube_orientation[4]) {
						case 'O':
							new_cube_orientation[0] = 'B';
							new_cube_orientation[1] = 'G';
							break;
						case 'B':
							new_cube_orientation[0] = 'R';
							new_cube_orientation[1] = 'O';
							break;
						case 'R':
							new_cube_orientation[0] = 'G';
							new_cube_orientation[1] = 'B';
							break;
						case 'G':
							new_cube_orientation[0] = 'O';
							new_cube_orientation[1] = 'R';
							break;
					}	
					break;
					
				case 'O':
					switch (new_cube_orientation[4]) {
						case 'W':
							new_cube_orientation[0] = 'B';
							new_cube_orientation[1] = 'G';
							break;
						case 'B':
							new_cube_orientation[0] = 'Y';
							new_cube_orientation[1] = 'W';
							break;
						case 'Y':
							new_cube_orientation[0] = 'G';
							new_cube_orientation[1] = 'B';
							break;
						case 'G':
							new_cube_orientation[0] = 'W';
							new_cube_orientation[1] = 'Y';
							break;
					}	
					break;
				
				case 'R':
					switch (new_cube_orientation[4]) {
						case 'W':
							new_cube_orientation[0] = 'G';
							new_cube_orientation[1] = 'B';
							break;
						case 'B':
							new_cube_orientation[0] = 'W';
							new_cube_orientation[1] = 'Y';
							break;
						case 'Y':
							new_cube_orientation[0] = 'B';
							new_cube_orientation[1] = 'G';
							break;
						case 'G':
							new_cube_orientation[0] = 'Y';
							new_cube_orientation[1] = 'W';
							break;
					}	
					break;
				
				case 'G':
					switch (new_cube_orientation[4]) {
						case 'W':
							new_cube_orientation[0] = 'O';
							new_cube_orientation[1] = 'R';
							break;
						case 'R':
							new_cube_orientation[0] = 'W';
							new_cube_orientation[1] = 'Y';
							break;
						case 'Y':
							new_cube_orientation[0] = 'R';
							new_cube_orientation[1] = 'O';
							break;
						case 'O':
							new_cube_orientation[0] = 'Y';
							new_cube_orientation[1] = 'W';
							break;
					}	
					break;
				
				case 'B':
					switch (new_cube_orientation[4]) {
						case 'W':
							new_cube_orientation[0] = 'R';
							new_cube_orientation[1] = 'O';
							break;
						case 'O':
							new_cube_orientation[0] = 'W';
							new_cube_orientation[1] = 'Y';
							break;
						case 'Y':
							new_cube_orientation[0] = 'O';
							new_cube_orientation[1] = 'R';
							break;
						case 'R':
							new_cube_orientation[0] = 'Y';
							new_cube_orientation[1] = 'W';
							break;
					}	
					break;
				}
			}
			
			// Left-Right pair incorrect
			if (new_cube_orientation[2] == 'x') {
				// Check top
				switch (new_cube_orientation[0]) {
				
				case 'W':
					// Check front
					switch (new_cube_orientation[4]) {
						case 'O':
							new_cube_orientation[2] = 'B';
							new_cube_orientation[3] = 'G';
							break;
						case 'B':
							new_cube_orientation[2] = 'R';
							new_cube_orientation[3] = 'O';
							break;
						case 'R':
							new_cube_orientation[2] = 'G';
							new_cube_orientation[3] = 'B';
							break;
						case 'G':
							new_cube_orientation[2] = 'O';
							new_cube_orientation[3] = 'R';
							break;
					}
					break;
				
				case 'Y':
					switch (new_cube_orientation[4]) {
						case 'O':
							new_cube_orientation[2] = 'G';
							new_cube_orientation[3] = 'B';
							break;
						case 'B':
							new_cube_orientation[2] = 'O';
							new_cube_orientation[3] = 'R';
							break;
						case 'R':
							new_cube_orientation[2] = 'B';
							new_cube_orientation[3] = 'G';
							break;
						case 'G':
							new_cube_orientation[2] = 'R';
							new_cube_orientation[3] = 'O';
							break;
					}	
					break;
					
				case 'O':
					switch (new_cube_orientation[4]) {
						case 'W':
							new_cube_orientation[2] = 'G';
							new_cube_orientation[3] = 'B';
							break;
						case 'B':
							new_cube_orientation[2] = 'W';
							new_cube_orientation[3] = 'Y';
							break;
						case 'Y':
							new_cube_orientation[2] = 'B';
							new_cube_orientation[3] = 'G';
							break;
						case 'G':
							new_cube_orientation[2] = 'Y';
							new_cube_orientation[3] = 'W';
							break;
					}	
					break;
				
				case 'R':
					switch (new_cube_orientation[4]) {
						case 'W':
							new_cube_orientation[2] = 'B';
							new_cube_orientation[3] = 'G';
							break;
						case 'B':
							new_cube_orientation[2] = 'Y';
							new_cube_orientation[3] = 'W';
							break;
						case 'Y':
							new_cube_orientation[2] = 'G';
							new_cube_orientation[3] = 'B';
							break;
						case 'G':
							new_cube_orientation[2] = 'W';
							new_cube_orientation[3] = 'Y';
							break;
					}	
					break;
				
				case 'G':
					switch (new_cube_orientation[4]) {
						case 'W':
							new_cube_orientation[2] = 'R';
							new_cube_orientation[3] = 'O';
							break;
						case 'R':
							new_cube_orientation[2] = 'Y';
							new_cube_orientation[3] = 'W';
							break;
						case 'Y':
							new_cube_orientation[2] = 'O';
							new_cube_orientation[3] = 'R';
							break;
						case 'O':
							new_cube_orientation[2] = 'W';
							new_cube_orientation[3] = 'Y';
							break;
					}	
					break;
				
				case 'B':
					switch (new_cube_orientation[4]) {
						case 'W':
							new_cube_orientation[2] = 'O';
							new_cube_orientation[3] = 'R';
							break;
						case 'O':
							new_cube_orientation[2] = 'Y';
							new_cube_orientation[3] = 'W';
							break;
						case 'Y':
							new_cube_orientation[2] = 'R';
							new_cube_orientation[3] = 'O';
							break;
						case 'R':
							new_cube_orientation[2] = 'W';
							new_cube_orientation[3] = 'Y';
							break;
					}	
					break;
				}
			}
			
			// Front-Back pair incorrect
			if (new_cube_orientation[4] == 'x') {
				// Check top
				switch (new_cube_orientation[0]) {
				
				case 'W':
					// Check left
					switch (new_cube_orientation[2]) {
						case 'O':
							new_cube_orientation[4] = 'G';
							new_cube_orientation[5] = 'B';
							break;
						case 'B':
							new_cube_orientation[4] = 'O';
							new_cube_orientation[5] = 'R';
							break;
						case 'R':
							new_cube_orientation[4] = 'B';
							new_cube_orientation[5] = 'G';
							break;
						case 'G':
							new_cube_orientation[4] = 'R';
							new_cube_orientation[5] = 'O';
							break;
					}
					break;
				
				case 'Y':
					switch (new_cube_orientation[2]) {
						case 'O':
							new_cube_orientation[4] = 'B';
							new_cube_orientation[5] = 'G';
							break;
						case 'B':
							new_cube_orientation[4] = 'R';
							new_cube_orientation[5] = 'O';
							break;
						case 'R':
							new_cube_orientation[4] = 'G';
							new_cube_orientation[5] = 'B';
							break;
						case 'G':
							new_cube_orientation[4] = 'O';
							new_cube_orientation[5] = 'R';
							break;
					}	
					break;
					
				case 'O':
					switch (new_cube_orientation[2]) {
						case 'W':
							new_cube_orientation[4] = 'B';
							new_cube_orientation[5] = 'G';
							break;
						case 'B':
							new_cube_orientation[4] = 'Y';
							new_cube_orientation[5] = 'W';
							break;
						case 'Y':
							new_cube_orientation[4] = 'G';
							new_cube_orientation[5] = 'B';
							break;
						case 'G':
							new_cube_orientation[4] = 'W';
							new_cube_orientation[5] = 'Y';
							break;
					}	
					break;
				
				case 'R':
					switch (new_cube_orientation[2]) {
						case 'W':
							new_cube_orientation[4] = 'G';
							new_cube_orientation[5] = 'B';
							break;
						case 'B':
							new_cube_orientation[4] = 'W';
							new_cube_orientation[5] = 'Y';
							break;
						case 'Y':
							new_cube_orientation[4] = 'B';
							new_cube_orientation[5] = 'G';
							break;
						case 'G':
							new_cube_orientation[4] = 'Y';
							new_cube_orientation[5] = 'W';
							break;
					}	
					break;
				
				case 'G':
					switch (new_cube_orientation[2]) {
						case 'W':
							new_cube_orientation[4] = 'O';
							new_cube_orientation[5] = 'R';
							break;
						case 'R':
							new_cube_orientation[4] = 'W';
							new_cube_orientation[5] = 'Y';
							break;
						case 'Y':
							new_cube_orientation[4] = 'R';
							new_cube_orientation[5] = 'O';
							break;
						case 'O':
							new_cube_orientation[4] = 'Y';
							new_cube_orientation[5] = 'W';
							break;
					}	
					break;
				
				case 'B':
					switch (new_cube_orientation[2]) {
						case 'W':
							new_cube_orientation[4] = 'R';
							new_cube_orientation[5] = 'O';
							break;
						case 'O':
							new_cube_orientation[4] = 'W';
							new_cube_orientation[5] = 'Y';
							break;
						case 'Y':
							new_cube_orientation[4] = 'O';
							new_cube_orientation[5] = 'R';
							break;
						case 'R':
							new_cube_orientation[4] = 'Y';
							new_cube_orientation[5] = 'W';
							break;
					}	
					break;
				}
			}
		}
		if (valid_count < 2) {
			return null;
		}
		return new_cube_orientation;
	}
}
