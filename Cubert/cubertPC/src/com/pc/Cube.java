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
		// Corner
		cubie_00[0] = 'x';						// Top
		cubie_00[1] = scan_result_vector[20];	// Bottom
		cubie_00[2] = scan_result_vector[40];	// Left
		cubie_00[3] = 'x';						// Right
		cubie_00[4] = 'x';						// Front
		cubie_00[5] = scan_result_vector[31];	// Back

		// Edge
		cubie_01[0] = 'x';						// Top
		cubie_01[1] = scan_result_vector[19];	// Bottom
		cubie_01[2] = 'x';						// Left
		cubie_01[3] = 'x';						// Right
		cubie_01[4] = 'x';						// Front
		cubie_01[5] = scan_result_vector[32];	// Back
		
		// Corner
		cubie_02[0] = 'x';						// Top
		cubie_02[1] = scan_result_vector[26];	// Bottom
		cubie_02[2] = 'x';						// Left
		cubie_02[3] = scan_result_vector[47];	// Right
		cubie_02[4] = 'x';						// Front
		cubie_02[5] = scan_result_vector[33];	// Back
		
		// Edge
		cubie_03[0] = 'x';						// Top
		cubie_03[1] = 'x';						// Bottom
		cubie_03[2] = scan_result_vector[41];	// Left
		cubie_03[3] = 'x';						// Right
		cubie_03[4] = 'x';						// Front
		cubie_03[5] = scan_result_vector[30];	// Back

		// Edge
		cubie_04[0] = 'x';						// Top
		cubie_04[1] = 'x';						// Bottom
		cubie_04[2] = 'x';						// Left
		cubie_04[3] = scan_result_vector[46];	// Right
		cubie_04[4] = 'x';						// Front
		cubie_04[5] = scan_result_vector[34];	// Back
		
		// Corner
		cubie_05[0] = scan_result_vector[4];	// Top
		cubie_05[1] = 'x';						// Bottom
		cubie_05[2] = scan_result_vector[42];	// Left
		cubie_05[3] = 'x';						// Right
		cubie_05[4] = 'x';						// Front
		cubie_05[5] = scan_result_vector[29];	// Back
		
		// Edge
		cubie_06[0] = scan_result_vector[5];	// Top
		cubie_06[1] = 'x';						// Bottom
		cubie_06[2] = 'x';						// Left
		cubie_06[3] = 'x';						// Right
		cubie_06[4] = 'x';						// Front
		cubie_06[5] = scan_result_vector[28];	// Back
		
		// Corner
		cubie_07[0] = scan_result_vector[6];	// Top
		cubie_07[1] = 'x';						// Bottom
		cubie_07[2] = 'x';						// Left
		cubie_07[3] = scan_result_vector[53];	// Right
		cubie_07[4] = 'x';						// Front
		cubie_07[5] = scan_result_vector[35];	// Back

		// Edge
		cubie_08[0] = 'x';						// Top
		cubie_08[1] = scan_result_vector[21];	// Bottom
		cubie_08[2] = scan_result_vector[39];	// Left
		cubie_08[3] = 'x';						// Right
		cubie_08[4] = 'x';						// Front
		cubie_08[5] = 'x';						// Back
		
		// Edge
		cubie_09[0] = 'x';						// Top
		cubie_09[1] = scan_result_vector[25];	// Bottom
		cubie_09[2] = 'x';						// Left
		cubie_09[3] = scan_result_vector[48];	// Right
		cubie_09[4] = 'x';						// Front
		cubie_09[5] = 'x';						// Back
		
		// Edge
		cubie_10[0] = scan_result_vector[3];	// Top
		cubie_10[1] = 'x';						// Bottom
		cubie_10[2] = scan_result_vector[43];	// Left
		cubie_10[3] = 'x';						// Right
		cubie_10[4] = 'x';						// Front
		cubie_10[5] = 'x';						// Back
		
		// Edge
		cubie_11[0] = scan_result_vector[7];	// Top
		cubie_11[1] = 'x';						// Bottom
		cubie_11[2] = 'x';						// Left
		cubie_11[3] = scan_result_vector[52];	// Right
		cubie_11[4] = 'x';						// Front
		cubie_11[5] = 'x';						// Back
		
		// Corner
		cubie_12[0] = 'x';						// Top
		cubie_12[1] = scan_result_vector[22];	// Bottom
		cubie_12[2] = scan_result_vector[38];	// Left
		cubie_12[3] = 'x';						// Right
		cubie_12[4] = scan_result_vector[11];	// Front
		cubie_12[5] = 'x';						// Back
		
		// Edge
		cubie_13[0] = 'x';						// Top
		cubie_13[1] = scan_result_vector[23];	// Bottom
		cubie_13[2] = 'x';						// Left
		cubie_13[3] = 'x';						// Right
		cubie_13[4] = scan_result_vector[10];	// Front
		cubie_13[5] = 'x';						// Back
		
		// Corner
		cubie_14[0] = 'x';						// Top
		cubie_14[1] = scan_result_vector[24];	// Bottom
		cubie_14[2] = 'x';						// Left
		cubie_14[3] = scan_result_vector[49];	// Right
		cubie_14[4] = scan_result_vector[17];	// Front
		cubie_14[5] = 'x';						// Back

		// Edge
		cubie_15[0] = 'x';						// Top
		cubie_15[1] = 'x';						// Bottom
		cubie_15[2] = scan_result_vector[37];	// Left
		cubie_15[3] = 'x';						// Right
		cubie_15[4] = scan_result_vector[12];	// Front
		cubie_15[5] = 'x';						// Back
		
		// Edge
		cubie_16[0] = 'x';						// Top
		cubie_16[1] = 'x';						// Bottom
		cubie_16[2] = 'x';						// Left
		cubie_16[3] = scan_result_vector[50];	// Right
		cubie_16[4] = scan_result_vector[16];	// Front
		cubie_16[5] = 'x';						// Back
		
		// Corner
		cubie_17[0] = scan_result_vector[2];	// Top
		cubie_17[1] = 'x';						// Bottom
		cubie_17[2] = scan_result_vector[44];	// Left
		cubie_17[3] = 'x';						// Right
		cubie_17[4] = scan_result_vector[13];	// Front
		cubie_17[5] = 'x';						// Back
		
		// Edge
		cubie_18[0] = scan_result_vector[1];	// Top
		cubie_18[1] = 'x';						// Bottom
		cubie_18[2] = 'x';						// Left
		cubie_18[3] = 'x';						// Right
		cubie_18[4] = scan_result_vector[14];	// Front
		cubie_19[5] = 'x';						// Back
		
		// Corner
		cubie_19[0] = scan_result_vector[8];	// Top
		cubie_19[1] = 'x';						// Bottom
		cubie_19[2] = 'x';						// Left
		cubie_19[3] = scan_result_vector[51];	// Right
		cubie_19[4] = scan_result_vector[15];	// Front
		cubie_19[5] = 'x';						// Back
		
		return cube_scrambled;
	}
}
