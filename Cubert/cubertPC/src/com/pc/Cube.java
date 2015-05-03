package com.pc;

public class Cube {

	// Solved state for each cubie (White side of the cube showing up, green side facing front)
	// 		values in the array: x - no color (facing inside), r - red, g - green, b - blue, w - white, y - yellow, o - orange)
	// 		positions in the array: Top, bottom, left, right, front, back)
	public final char[] cubie_00_solved = {'x', 'Y', 'O', 'x', 'x', 'B'}; // Corner: 	Yellow	, orange, blue
	public final char[] cubie_01_solved = {'x', 'Y', 'x', 'x', 'x', 'B'}; // Edge:		Yellow	, blue
	public final char[] cubie_02_solved = {'x', 'Y', 'x', 'R', 'x', 'B'}; // Corner: 	Yellow	, red	, blue
	public final char[] cubie_03_solved = {'x', 'x', 'O', 'x', 'x', 'B'}; // Edge:		Orange	, blue
	public final char[] cubie_04_solved = {'x', 'x', 'x', 'R', 'x', 'B'}; // Edge:		Red		, blue
	public final char[] cubie_05_solved = {'W', 'x', 'O', 'x', 'x', 'B'}; // Corner: 	White	, orange, blue
	public final char[] cubie_06_solved = {'W', 'x', 'x', 'x', 'x', 'B'}; // Edge:		White	, blue
	public final char[] cubie_07_solved = {'W', 'x', 'x', 'R', 'x', 'B'}; // Corner: 	White	, red	, blue
	public final char[] cubie_08_solved = {'x', 'Y', 'O', 'x', 'x', 'x'}; // Edge:		Yellow	, orange
	public final char[] cubie_09_solved = {'x', 'Y', 'x', 'R', 'x', 'x'}; // Edge:		Yellow	, red
	public final char[] cubie_10_solved = {'W', 'x', 'O', 'x', 'x', 'x'}; // Edge:		White	, orange
	public final char[] cubie_11_solved = {'W', 'x', 'x', 'R', 'x', 'x'}; // Edge:		White	, red
	public final char[] cubie_12_solved = {'x', 'Y', 'O', 'x', 'G', 'x'}; // Corner: 	Yellow	, orange, green
	public final char[] cubie_13_solved = {'x', 'Y', 'x', 'x', 'G', 'x'}; // Edge:		Yellow	, green
	public final char[] cubie_14_solved = {'x', 'Y', 'x', 'R', 'G', 'x'}; // Corner: 	Yellow	, red	, green
	public final char[] cubie_15_solved = {'x', 'x', 'O', 'x', 'G', 'x'}; // Edge:		Orange	, green
	public final char[] cubie_16_solved = {'x', 'x', 'x', 'R', 'G', 'x'}; // Edge:		Red		, green
	public final char[] cubie_17_solved = {'W', 'x', 'O', 'x', 'G', 'x'}; // Corner: 	White	, orange, green
	public final char[] cubie_18_solved = {'W', 'x', 'x', 'x', 'G', 'x'}; // Edge:		White	, green
	public final char[] cubie_19_solved = {'W', 'x', 'x', 'R', 'G', 'x'}; // Corner: 	White	, red	, green
	
	public final char[][] cube_solved = {	cubie_00_solved, cubie_01_solved, cubie_02_solved, cubie_03_solved,
											cubie_04_solved, cubie_05_solved, cubie_06_solved, cubie_07_solved,
											cubie_08_solved, cubie_09_solved, cubie_10_solved, cubie_11_solved,
											cubie_12_solved, cubie_13_solved, cubie_14_solved, cubie_15_solved,
											cubie_16_solved, cubie_17_solved, cubie_18_solved,cubie_19_solved
										};
	
	public char[] 	cubie_00, cubie_01, cubie_02, cubie_03, cubie_04, cubie_05 ,cubie_06, cubie_07, cubie_08,
					cubie_09, cubie_10, cubie_11, cubie_12, cubie_13, cubie_14, cubie_15, cubie_16, cubie_17,
					cubie_18, cubie_19;
	
	public char[][] cube_scrambled;
	
	// Orientation of the cube itself - which centers are facing in which direction
	public char[] cube_orientation = {'x', 'x', 'x', 'x', 'x', 'x'};
	
	public Cube() {

	}
	
	//TODO:
	public char[][] orderScanResult(char[] scan_result_vector) {
		// overall faces count: 54
		// center at 0, 9, 18, 27, 36, 45
		// corner at 2+13+44, 4+29+42, 6+35+53, 8+15+51, 11+22+38, 17+24+49, 20+31+40, 26+33+47
		// edge at 1+14, 3+43, 5+28, 7+52, 10+23, 12+37, 16+50, 19+32, 21+39, 25+48, 30+41, 34+46 

		// Center faces
		cube_orientation[0] = scan_result_vector[0]; // Top
		cube_orientation[1] = scan_result_vector[18];// Bottom
		cube_orientation[2] = scan_result_vector[36];// Left
		cube_orientation[3] = scan_result_vector[45];// Right
		cube_orientation[4] = scan_result_vector[9]; // Front
		cube_orientation[5] = scan_result_vector[27];// Back
		
		//TODO: Group all corners and edges into actual cubies
		if (cube_orientation[0] == 'W' && cube_orientation[4] == 'G') {
			cubie_00[0] = 'x';
			cubie_00[1] = scan_result_vector[20];
			cubie_00[2] = scan_result_vector[40];
			cubie_00[3] = 'x';
			cubie_00[4] = 'x';
			cubie_00[5] = scan_result_vector[31];

			cubie_01[0] = 'x';
			cubie_01[1] = scan_result_vector[19];
			cubie_01[2] = 'x';
			cubie_01[3] = 'x';
			cubie_01[4] = 'x';
			cubie_01[5] = scan_result_vector[32];
			
			cubie_02[0] = 'x';
			cubie_02[1] = scan_result_vector[26];
			cubie_02[2] = 'x';
			cubie_02[3] = scan_result_vector[47];
			cubie_02[4] = 'x';
			cubie_02[5] = scan_result_vector[33];

			cubie_03[0] = 'x';
			cubie_03[1] = 'x';
			cubie_03[2] = scan_result_vector[41];
			cubie_03[3] = 'x';
			cubie_03[4] = 'x';
			cubie_03[5] = scan_result_vector[30];

			cubie_04[0] = 'x';
			cubie_04[1] = 'x';
			cubie_04[2] = 'x';
			cubie_04[3] = scan_result_vector[46];
			cubie_04[4] = 'x';
			cubie_04[5] = scan_result_vector[34];
		}
		
		//TODO: Put cubies into the right cube index (according to the centers)
		cube_scrambled[0][0] = 'b'; //actually put complete cubie signature into cube_scrambled array
		cube_scrambled[0][1] = 'l';
		cube_scrambled[0][2] = 'u';
		cube_scrambled[0][3] = 'b';
		//...
		
		return cube_scrambled;
	}
	
}
