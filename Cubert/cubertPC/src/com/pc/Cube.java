package com.pc;

public class Cube {

	// Orientation of the cube itself - which centers are facing in which direction
	public char[] cube_orientation;
	public char[][] cube_scrambled;
	public char[][] cube_solved;
	
	public Cube(char[] scan_result_vector) {
		if (scan_result_vector != null) {
			cube_orientation = getCubeOrientation(scan_result_vector);
			cube_solved = createSolvedState(cube_orientation);
			cube_scrambled = createScrambledState(scan_result_vector);
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
}
