package com.pc;

public class Cube {

	// 3D-Coordinates for every cubie (to calculate Manhattan distance)
	private int[][] cubie_coordinates = null;
	
	// Initial orientation of the cube itself - which centers are facing in which direction
	public char[] cube_orientation = new char[6];
	public char[][] cube_scrambled = null;
	public char[][] cube_solved = null;
	
	public Cube(char[] scan_result_vector) {
		if (scan_result_vector != null) {
			// Initial orientation - validated - should not be changed
			cube_orientation = getCubeOrientation(scan_result_vector);
			if (cube_orientation != null) {
				cube_solved = createSolvedState(cube_orientation);
				cube_scrambled = createScrambledState(scan_result_vector);
			}
		} else {
			// In DEBUG case if there is no scan result from the NXT
			cube_solved = createSolvedState(new char [] {'W', 'Y', 'O', 'R', 'G', 'B'});
			cube_scrambled = this.getDummySolvedState();
		}
		cubie_coordinates = this.getCubieCoordinates();
	}
	
	/**
	 * Finds the solved index/position of a given cubie signature
	 * @param cubie as char[] of its signature
	 * @return index of the solved position of the passed cubie
	 */
	public int findCubieSolvedIndex(char[] cubie) {
		
		boolean match = true;
		
		// Compare signature of cubie with all solved cubie signatures
		for (int i = 0; i < cube_solved.length; i++) {

			int x_count = 0;
			int x_count_solved = 0;
			
			// Go through cubie's signature
			for (int j = 0; j < cubie.length; j++) {
				
				// skip entries with x
				if (cubie[j] != 'x') {
					
					// Skip current cubie if one color is not contained in the current checked solved cubie
					if (! (new String(cube_solved[i]).contains("" + cubie[j]))) {
						match = false;
						break;
					}
				} else {
					x_count++;
				}
				
				if (cube_solved[i][j] == 'x') {
					x_count_solved++;
				}
			}

			// Return the solved index of the matching cubie
			if (match) {
				if (x_count_solved == x_count) {
					return i;
				}
			} else {
				match = true;
			}
		}
		// No match found
		return -1;
	}
	
	/**
	 * Get the average distance (3D-MHD & Color-Distance) over all cubies of a turned face
	 * @param face that has been turned (t, d, l, r, f, b)
	 * @return distance of the face to it's solved state
	 */
	public double calculateFaceDistance(char face) {
		
		double distance = 0.0;
		
		int[] moved_cubies = getAllCubiesByFace(face);
		
		for (int i = 0; i < moved_cubies.length; i++) {
			distance += calculateManhattanDistance(moved_cubies[i]) + calculateColorDistance(moved_cubies[i]);
		}
		return distance / 8.0;
	}
	
	/**
	 * Get indices of all cubies from a given face
	 * @param face (t, d, l, r, f, b)
	 * @return integer array with cubie indices of the given face
	 */
	private int[] getAllCubiesByFace(char face) {
		
		int[] cubies_of_face;
		
		switch (face) {
			case 't':
				cubies_of_face = new int[] {5, 6, 7, 10, 11, 17, 18, 19}; 
				break;
	
			case 'd':
				cubies_of_face = new int[] {0, 1, 2, 8, 9, 12, 13, 14}; 
				break;
			
			case 'l':
				cubies_of_face = new int[] {0, 3, 5, 8, 10, 12, 15, 17}; 
				break;
			
			case 'r':
				cubies_of_face = new int[] {2, 4, 7, 9, 11, 14, 16, 19}; 
				break;
			
			case 'f':
				cubies_of_face = new int[] {12, 13, 14, 15, 16, 17, 18, 19}; 
				break;
			
			case 'b':
				cubies_of_face = new int[] {0, 1, 2, 3, 4, 5, 6, 7}; 
				break;
			
			default:
				return null;
		}
		return cubies_of_face;
	}
	
	/**
	 * Calculates the 3D-Manhattan-Distance between a given cubie and it's solved position
	 * @param cubie_index 	Index of cubie to check
	 * @return 3D-Manhattan distance of both positions
	 */
	public int calculateManhattanDistance(int cubie_index) {
		
		int manhattan_distance = 0;
		
		for (int i = 0; i < 3; i++) {
			manhattan_distance += Math.abs( cubie_coordinates[cubie_index][i] - cubie_coordinates[this.findCubieSolvedIndex(cube_scrambled[cubie_index])][i] );
		}
		
		return manhattan_distance;
	}
	
	/**
	 * Calculates the number of incorrect oriented colors of a given cubie compared to it's solved orientation
	 * @param cubie_index Index of the cubie to check
	 * @return Color distance of cubie to it's solved orientation
	 */
	public int calculateColorDistance(int cubie_index) {
		
		int color_distance = 0;
		
		for (int i = 0; i < 6; i++) {

			if ( cube_scrambled[cubie_index][i] != cube_solved[this.findCubieSolvedIndex(cube_scrambled[cubie_index])][i] ) {

				if ( cube_scrambled[cubie_index][i] != 'x' ) {
					
					color_distance++;
				}
			}
		}
		return color_distance;
	}
	
	private int[][] getCubieCoordinates() {
		
		// 3D coordinates of each cubie by it's index
		// (see presentation)		  x, y, z
		int[] cubie_00_coordinates = {0, 0, 0};
		int[] cubie_01_coordinates = {1, 0, 0};
		int[] cubie_02_coordinates = {2, 0, 0};
		int[] cubie_03_coordinates = {0, 1, 0};
		int[] cubie_04_coordinates = {2, 1, 0};
		int[] cubie_05_coordinates = {0, 2, 0};
		int[] cubie_06_coordinates = {1, 2, 0};
		int[] cubie_07_coordinates = {2, 2, 0};
		int[] cubie_08_coordinates = {0, 0, 1};
		int[] cubie_09_coordinates = {2, 0, 1};
		int[] cubie_10_coordinates = {0, 2, 1};
		int[] cubie_11_coordinates = {2, 2, 1};
		int[] cubie_12_coordinates = {0, 0, 2};
		int[] cubie_13_coordinates = {1, 0, 2};
		int[] cubie_14_coordinates = {2, 0, 2};
		int[] cubie_15_coordinates = {0, 1, 2};
		int[] cubie_16_coordinates = {2, 1, 2};
		int[] cubie_17_coordinates = {0, 2, 2};
		int[] cubie_18_coordinates = {1, 2, 2};
		int[] cubie_19_coordinates = {2, 2, 2};
		
		int[][] cubie_coordinates = {
							cubie_00_coordinates, cubie_01_coordinates, cubie_02_coordinates, cubie_03_coordinates, cubie_04_coordinates,
							cubie_05_coordinates, cubie_06_coordinates, cubie_07_coordinates, cubie_08_coordinates, cubie_09_coordinates,
							cubie_10_coordinates, cubie_11_coordinates, cubie_12_coordinates, cubie_13_coordinates, cubie_14_coordinates,
							cubie_15_coordinates, cubie_16_coordinates, cubie_17_coordinates, cubie_18_coordinates, cubie_19_coordinates
						};
		
		return cubie_coordinates;
	}
	
	// Quarter turn clockwise (when look at the face directly)
	public void permuteCube(char face) {
		
		char new_top, new_bottom, new_left, new_right, new_front, new_back;
		char[] 	cubie_00_temp, cubie_01_temp, cubie_02_temp, cubie_03_temp, cubie_04_temp, cubie_05_temp, 
				cubie_06_temp, cubie_07_temp, cubie_08_temp, cubie_09_temp, cubie_10_temp, cubie_11_temp, cubie_12_temp,
				cubie_13_temp, cubie_14_temp, cubie_15_temp, cubie_17_temp;    
		
		// Explanation: For a given face..
		//	.. change the orientations ONLY of all the involved cubies like they will be after the turn,
		//  .. then switch the newly oriented cubies' positions like they will be after the turn
		
		// Rotate top face
		if (face == 't') {
		
			// Cubie 5
			new_right = cube_scrambled[5][5];
			new_back = cube_scrambled[5][2];
			cube_scrambled[5][5] = 'x';
			cube_scrambled[5][2] = 'x';
			cube_scrambled[5][3] = new_right;
			cube_scrambled[5][5] = new_back;
	
			// Cubie 6
			new_right = cube_scrambled[6][5];
			cube_scrambled[6][5] = 'x';
			cube_scrambled[6][3] = new_right;
	
			// Cubie 7
			new_right = cube_scrambled[7][5];
			new_front = cube_scrambled[7][3];
			cube_scrambled[7][5] = 'x';
			cube_scrambled[7][3] = 'x';
			cube_scrambled[7][3] = new_right;
			cube_scrambled[7][4] = new_front;
			
			// Cubie 10
			new_back = cube_scrambled[10][2];
			cube_scrambled[10][2] = 'x';
			cube_scrambled[10][5] = new_back;
			
			// Cubie 11
			new_front = cube_scrambled[11][3];
			cube_scrambled[11][3] = 'x';
			cube_scrambled[11][4] = new_front;
			
			// Cubie 17
			new_left = cube_scrambled[17][4];
			new_back = cube_scrambled[17][2];
			cube_scrambled[17][4] = 'x';
			cube_scrambled[17][2] = 'x';
			cube_scrambled[17][2] = new_left;
			cube_scrambled[17][5] = new_back;
			
			// Cubie 18
			new_left = cube_scrambled[18][4];
			cube_scrambled[18][4] = 'x';
			cube_scrambled[18][2] = new_left;
			
			// Cubie 19
			new_left = cube_scrambled[19][4];
			new_front = cube_scrambled[19][3];
			cube_scrambled[19][4] = 'x';
			cube_scrambled[19][3] = 'x';
			cube_scrambled[19][2] = new_left;
			cube_scrambled[19][4] = new_front;
			
			// Cubie 05 is replaced by Cubie 17 
			cubie_05_temp = cube_scrambled[5];
			cube_scrambled[5] = cube_scrambled[17];
			
			// Cubie 06 is replaced by Cubie 10
			cubie_06_temp = cube_scrambled[6];
			cube_scrambled[6] = cube_scrambled[10];
			
			// Cubie 07 is replaced by Cubie 5
			cubie_07_temp = cube_scrambled[7];
			cube_scrambled[7] = cubie_05_temp;
			
			// Cubie 10 is replaced by Cubie 18 
			cube_scrambled[10] = cube_scrambled[18];
			
			// Cubie 11 is replaced by Cubie 6 
			cubie_11_temp = cube_scrambled[11];
			cube_scrambled[11] = cubie_06_temp;
			
			// Cubie 17 is replaced by Cubie 19 
			cube_scrambled[17] = cube_scrambled[19];
			
			// Cubie 18 is replaced by Cubie 11 
			cube_scrambled[18] = cubie_11_temp;
			
			// Cubie 19 is replaced by Cubie 7 
			cube_scrambled[19] = cubie_07_temp;
		}
		
		// Rotate down/bottom face
		if (face == 'd') {
		
			// Cubie 0
			new_front = cube_scrambled[0][2];
			new_left = cube_scrambled[0][5];
			cube_scrambled[0][2] = 'x';
			cube_scrambled[0][5] = 'x';
			cube_scrambled[0][4] = new_front;
			cube_scrambled[0][2] = new_left;
	
			// Cubie 1
			new_left = cube_scrambled[1][5];
			cube_scrambled[1][5] = 'x';
			cube_scrambled[1][2] = new_left;
	
			// Cubie 2
			new_left = cube_scrambled[2][5];
			new_back = cube_scrambled[2][3];
			cube_scrambled[2][5] = 'x';
			cube_scrambled[2][3] = 'x';
			cube_scrambled[2][2] = new_left;
			cube_scrambled[2][5] = new_back;
			
			// Cubie 8
			new_front = cube_scrambled[8][2];
			cube_scrambled[8][2] = 'x';
			cube_scrambled[8][4] = new_front;
			
			// Cubie 9
			new_back = cube_scrambled[9][3];
			cube_scrambled[9][3] = 'x';
			cube_scrambled[9][5] = new_back;
			
			// Cubie 12
			new_front = cube_scrambled[12][2];
			new_right = cube_scrambled[12][4];
			cube_scrambled[12][2] = 'x';
			cube_scrambled[12][4] = 'x';
			cube_scrambled[12][4] = new_front;
			cube_scrambled[12][3] = new_right;
			
			// Cubie 13
			new_right = cube_scrambled[13][4];
			cube_scrambled[13][4] = 'x';
			cube_scrambled[13][3] = new_right;
			
			// Cubie 14
			new_right = cube_scrambled[14][4];
			new_back = cube_scrambled[14][3];
			cube_scrambled[14][4] = 'x';
			cube_scrambled[14][3] = 'x';
			cube_scrambled[14][3] = new_right;
			cube_scrambled[14][5] = new_back;
			
			// Cubie 00 is replaced by Cubie 02 
			cubie_00_temp = cube_scrambled[0];
			cube_scrambled[0] = cube_scrambled[2];
			
			// Cubie 01 is replaced by Cubie 09 
			cubie_01_temp = cube_scrambled[1];
			cube_scrambled[1] = cube_scrambled[9];
			
			// Cubie 02 is replaced by Cubie 14 
			cube_scrambled[2] = cube_scrambled[14];
			
			// Cubie 08 is replaced by Cubie 01 
			cubie_08_temp = cube_scrambled[8];
			cube_scrambled[8] = cubie_01_temp;
			
			// Cubie 09 is replaced by Cubie 13 
			cube_scrambled[9] = cube_scrambled[13];
			
			// Cubie 12 is replaced by Cubie 00 
			cubie_12_temp = cube_scrambled[12];
			cube_scrambled[12] = cubie_00_temp;
			
			// Cubie 13 is replaced by Cubie 08 
			cube_scrambled[13] = cubie_08_temp;
			
			// Cubie 14 is replaced by Cubie 12 
			cube_scrambled[14] = cubie_12_temp;
		}
		
		// Rotate left face
		if (face == 'l') {
			
			// Cubie 0
			new_top = cube_scrambled[0][5];
			new_back = cube_scrambled[0][1];
			cube_scrambled[0][5] = 'x';
			cube_scrambled[0][1] = 'x';
			cube_scrambled[0][0] = new_top;
			cube_scrambled[0][5] = new_back;
			
			// Cubie 3
			new_top = cube_scrambled[3][5];
			cube_scrambled[3][5] = 'x';
			cube_scrambled[3][0] = new_top;
	
			// Cubie 5
			new_top = cube_scrambled[5][5];
			new_front = cube_scrambled[5][0];
			cube_scrambled[5][5] = 'x';
			cube_scrambled[5][0] = 'x';
			cube_scrambled[5][0] = new_top;
			cube_scrambled[5][4] = new_front;
			
			// Cubie 8
			new_back = cube_scrambled[8][1];
			cube_scrambled[8][1] = 'x';
			cube_scrambled[8][5] = new_back;
			
			// Cubie 10
			new_front = cube_scrambled[10][0];
			cube_scrambled[10][0] = 'x';
			cube_scrambled[10][4] = new_front;
			
			// Cubie 12
			new_bottom = cube_scrambled[12][4];
			new_back = cube_scrambled[12][1];
			cube_scrambled[12][4] = 'x';
			cube_scrambled[12][1] = 'x';
			cube_scrambled[12][1] = new_bottom;
			cube_scrambled[12][5] = new_back;
			
			// Cubie 15
			new_bottom = cube_scrambled[15][4];
			cube_scrambled[15][4] = 'x';
			cube_scrambled[15][1] = new_bottom;
			
			// Cubie 17
			new_bottom = cube_scrambled[17][4];
			new_front = cube_scrambled[17][0];
			cube_scrambled[17][4] = 'x';
			cube_scrambled[17][0] = 'x';
			cube_scrambled[17][1] = new_bottom;
			cube_scrambled[17][4] = new_front;
			
			// Cubie 0 is replaced by Cubie 12 
			cubie_00_temp = cube_scrambled[0];
			cube_scrambled[0] = cube_scrambled[12];
			
			// Cubie 3 is replaced by Cubie 8
			cubie_03_temp = cube_scrambled[3];
			cube_scrambled[3] = cube_scrambled[8];
			
			// Cubie 5 is replaced by Cubie 0
			cubie_05_temp = cube_scrambled[5];
			cube_scrambled[5] = cubie_00_temp;
			
			// Cubie 8 is replaced by Cubie 15 
			cube_scrambled[8] = cube_scrambled[15];

			// Cubie 10 is replaced by Cubie 3 
			cubie_10_temp = cube_scrambled[10];
			cube_scrambled[10] = cubie_03_temp;
			
			// Cubie 12 is replaced by Cubie 17 
			cube_scrambled[12] = cube_scrambled[17];
			
			// Cubie 15 is replaced by Cubie 10 
			cube_scrambled[15] = cubie_10_temp;
			
			// Cubie 17 is replaced by Cubie 5 
			cube_scrambled[17] = cubie_05_temp;
		}
		
		// Rotate right face
		if (face == 'r') {
		
			// Cubie 2
			new_bottom = cube_scrambled[2][5];
			new_front = cube_scrambled[2][1];
			cube_scrambled[2][5] = 'x';
			cube_scrambled[2][1] = 'x';
			cube_scrambled[2][1] = new_bottom;
			cube_scrambled[2][4] = new_front;
	
			// Cubie 4
			new_bottom = cube_scrambled[4][5];
			cube_scrambled[4][5] = 'x';
			cube_scrambled[4][1] = new_bottom;
	
			// Cubie 7
			new_bottom = cube_scrambled[7][5];
			new_back = cube_scrambled[7][0];
			cube_scrambled[7][5] = 'x';
			cube_scrambled[7][0] = 'x';
			cube_scrambled[7][1] = new_bottom;
			cube_scrambled[7][5] = new_back;
			
			// Cubie 9
			new_front = cube_scrambled[9][1];
			cube_scrambled[9][1] = 'x';
			cube_scrambled[9][4] = new_front;
			
			// Cubie 11
			new_back = cube_scrambled[11][0];
			cube_scrambled[11][0] = 'x';
			cube_scrambled[11][5] = new_back;
			
			// Cubie 14
			new_top = cube_scrambled[14][4];
			new_front = cube_scrambled[14][1];
			cube_scrambled[14][4] = 'x';
			cube_scrambled[14][1] = 'x';
			cube_scrambled[14][0] = new_top;
			cube_scrambled[14][4] = new_front;
			
			// Cubie 16
			new_top = cube_scrambled[16][4];
			cube_scrambled[16][4] = 'x';
			cube_scrambled[16][0] = new_top;
			
			// Cubie 19
			new_top = cube_scrambled[19][4];
			new_back = cube_scrambled[19][0];
			cube_scrambled[19][4] = 'x';
			cube_scrambled[19][0] = 'x';
			cube_scrambled[19][0] = new_top;
			cube_scrambled[19][5] = new_back;
			
			// Cubie 02 is replaced by Cubie 7 
			cubie_02_temp = cube_scrambled[2];
			cube_scrambled[2] = cube_scrambled[7];
			
			// Cubie 04 is replaced by Cubie 11
			cubie_04_temp = cube_scrambled[4];
			cube_scrambled[4] = cube_scrambled[11];
			
			// Cubie 07 is replaced by Cubie 19
			cubie_07_temp = cube_scrambled[7];
			cube_scrambled[7] = cube_scrambled[19];
			
			// Cubie 09 is replaced by Cubie 4 
			cubie_09_temp = cube_scrambled[9];
			cube_scrambled[9] = cubie_04_temp;
			
			// Cubie 11 is replaced by Cubie 16 
			cubie_11_temp = cube_scrambled[11];
			cube_scrambled[11] = cube_scrambled[16];
			
			// Cubie 14 is replaced by Cubie 2 
			cubie_14_temp = cube_scrambled[14];
			cube_scrambled[14] = cubie_02_temp;
			
			// Cubie 16 is replaced by Cubie 9 
			cube_scrambled[16] = cubie_09_temp;
			
			// Cubie 19 is replaced by Cubie 14 
			cube_scrambled[19] = cubie_14_temp;
		}
		
		// Rotate front face
		if (face == 'f') {
		
			// Cubie 12
			new_top = cube_scrambled[12][2];
			new_left = cube_scrambled[12][1];
			cube_scrambled[12][2] = 'x';
			cube_scrambled[12][1] = 'x';
			cube_scrambled[12][0] = new_top;
			cube_scrambled[12][2] = new_left;
	
			// Cubie 13
			new_left = cube_scrambled[13][1];
			cube_scrambled[13][1] = 'x';
			cube_scrambled[13][2] = new_left;
	
			// Cubie 14
			new_bottom = cube_scrambled[14][3];
			new_left = cube_scrambled[14][1];
			cube_scrambled[14][3] = 'x';
			cube_scrambled[14][1] = 'x';
			cube_scrambled[14][1] = new_bottom;
			cube_scrambled[14][2] = new_left;
			
			// Cubie 15
			new_top = cube_scrambled[15][2];
			cube_scrambled[15][2] = 'x';
			cube_scrambled[15][0] = new_top;
			
			// Cubie 16
			new_bottom = cube_scrambled[16][3];
			cube_scrambled[16][3] = 'x';
			cube_scrambled[16][1] = new_bottom;
			
			// Cubie 17
			new_top = cube_scrambled[17][2];
			new_right = cube_scrambled[17][0];
			cube_scrambled[17][2] = 'x';
			cube_scrambled[17][0] = 'x';
			cube_scrambled[17][0] = new_top;
			cube_scrambled[17][3] = new_right;
			
			// Cubie 18
			new_right = cube_scrambled[18][0];
			cube_scrambled[18][0] = 'x';
			cube_scrambled[18][3] = new_right;
			
			// Cubie 19
			new_bottom = cube_scrambled[19][3];
			new_right = cube_scrambled[19][0];
			cube_scrambled[19][3] = 'x';
			cube_scrambled[19][0] = 'x';
			cube_scrambled[19][1] = new_bottom;
			cube_scrambled[19][3] = new_right;
			
			// Cubie 12 is replaced by Cubie 14 
			cubie_12_temp = cube_scrambled[12];
			cube_scrambled[12] = cube_scrambled[14];
			
			// Cubie 13 is replaced by Cubie 16
			cubie_13_temp = cube_scrambled[13];
			cube_scrambled[13] = cube_scrambled[16];
			
			// Cubie 14 is replaced by Cubie 19
			cube_scrambled[14] = cube_scrambled[19];
			
			// Cubie 15 is replaced by Cubie 13 
			cubie_15_temp = cube_scrambled[15];
			cube_scrambled[15] = cubie_13_temp;
			
			// Cubie 16 is replaced by Cubie 18 
			cube_scrambled[16] = cube_scrambled[18];
			
			// Cubie 17 is replaced by Cubie 12 
			cubie_17_temp = cube_scrambled[17];
			cube_scrambled[17] = cubie_12_temp;
			
			// Cubie 18 is replaced by Cubie 15 
			cube_scrambled[18] = cubie_15_temp;
			
			// Cubie 19 is replaced by Cubie 17 
			cube_scrambled[19] = cubie_17_temp;
		}
		
		// Rotate back face
		if (face == 'b') {
			
			// Cubie 0
			new_bottom = cube_scrambled[0][2];
			new_right = cube_scrambled[0][1];
			cube_scrambled[0][2] = 'x';
			cube_scrambled[0][1] = 'x';
			cube_scrambled[0][1] = new_bottom;
			cube_scrambled[0][3] = new_right;
	
			// Cubie 1
			new_right = cube_scrambled[1][1];
			cube_scrambled[1][1] = 'x';
			cube_scrambled[1][3] = new_right;
	
			// Cubie 2
			new_top = cube_scrambled[2][3];
			new_right = cube_scrambled[2][1];
			cube_scrambled[2][3] = 'x';
			cube_scrambled[2][1] = 'x';
			cube_scrambled[2][0] = new_top;
			cube_scrambled[2][3] = new_right;
			
			// Cubie 3
			new_bottom = cube_scrambled[3][2];
			cube_scrambled[3][2] = 'x';
			cube_scrambled[3][1] = new_bottom;
			
			// Cubie 4
			new_top = cube_scrambled[4][3];
			cube_scrambled[4][3] = 'x';
			cube_scrambled[4][0] = new_top;
			
			// Cubie 5
			new_bottom = cube_scrambled[5][2];
			new_left = cube_scrambled[5][0];
			cube_scrambled[5][2] = 'x';
			cube_scrambled[5][0] = 'x';
			cube_scrambled[5][1] = new_bottom;
			cube_scrambled[5][2] = new_left;
			
			// Cubie 6
			new_left = cube_scrambled[6][0];
			cube_scrambled[6][0] = 'x';
			cube_scrambled[6][2] = new_left;
			
			// Cubie 7
			new_top = cube_scrambled[7][3];
			new_left = cube_scrambled[7][0];
			cube_scrambled[7][3] = 'x';
			cube_scrambled[7][0] = 'x';
			cube_scrambled[7][0] = new_top;
			cube_scrambled[7][2] = new_left;
			
			// Cubie 0 is replaced by Cubie 5 
			cubie_00_temp = cube_scrambled[0];
			cube_scrambled[0] = cube_scrambled[5];
			
			// Cubie 1 is replaced by Cubie 3 
			cubie_01_temp = cube_scrambled[1];
			cube_scrambled[1] = cube_scrambled[3];
			
			// Cubie 2 is replaced by Cubie 0 
			cubie_02_temp = cube_scrambled[2];
			cube_scrambled[2] = cubie_00_temp;
			
			// Cubie 3 is replaced by Cubie 6 
			cube_scrambled[3] = cube_scrambled[6];
			
			// Cubie 4 is replaced by Cubie 1 
			cubie_04_temp = cube_scrambled[4];
			cube_scrambled[4] = cubie_01_temp;
			
			// Cubie 5 is replaced by Cubie 7 
			cube_scrambled[5] = cube_scrambled[7];
			
			// Cubie 6 is replaced by Cubie 4 
			cube_scrambled[6] = cubie_04_temp;
			
			// Cubie 7 is replaced by Cubie 2 
			cube_scrambled[7] = cubie_02_temp;
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
	
	public char[][] getDummySolvedState() {
				
		// FOR DEBUGGING ONLY
		
		// Solved state for each cubie (White side of the cube showing up, green side facing front)
		// 		values in the array: x - no color (facing inside), r - red, g - green, b - blue, w - white, y - yellow, o - orange)
		// 		positions in the array: Top, bottom, left, right, front, back)
		char[] cubie_01 = {'x', 'Y', 'O', 'x', 'x', 'B'}; // Corner: 	Yellow	, orange, blue
		char[] cubie_02 = {'x', 'Y', 'x', 'x', 'x', 'B'}; // Edge:		Yellow	, blue
		char[] cubie_03 = {'x', 'Y', 'x', 'R', 'x', 'B'}; // Corner: 	Yellow	, red	, blue
		char[] cubie_04 = {'x', 'x', 'O', 'x', 'x', 'B'}; // Edge:		Orange	, blue
		char[] cubie_05 = {'x', 'x', 'x', 'R', 'x', 'B'}; // Edge:		Red		, blue
		char[] cubie_06 = {'W', 'x', 'O', 'x', 'x', 'B'}; // Corner: 	White	, orange, blue
		char[] cubie_07 = {'W', 'x', 'x', 'x', 'x', 'B'}; // Edge:		White	, blue
		char[] cubie_08 = {'W', 'x', 'x', 'R', 'x', 'B'}; // Corner: 	White	, red	, blue
		char[] cubie_09 = {'x', 'Y', 'O', 'x', 'x', 'x'}; // Edge:		Yellow	, orange
		char[] cubie_10 = {'x', 'Y', 'x', 'R', 'x', 'x'}; // Edge:		Yellow	, red
		char[] cubie_11 = {'W', 'x', 'O', 'x', 'x', 'x'}; // Edge:		White	, orange
		char[] cubie_12 = {'W', 'x', 'x', 'R', 'x', 'x'}; // Edge:		White	, red
		char[] cubie_13 = {'x', 'Y', 'O', 'x', 'G', 'x'}; // Corner: 	Yellow	, orange, green
		char[] cubie_14 = {'x', 'Y', 'x', 'x', 'G', 'x'}; // Edge:		Yellow	, green
		char[] cubie_15 = {'x', 'Y', 'x', 'R', 'G', 'x'}; // Corner: 	Yellow	, red	, green
		char[] cubie_16 = {'x', 'x', 'O', 'x', 'G', 'x'}; // Edge:		Orange	, green
		char[] cubie_17 = {'x', 'x', 'x', 'R', 'G', 'x'}; // Edge:		Red		, green
		char[] cubie_18 = {'W', 'x', 'O', 'x', 'G', 'x'}; // Corner: 	White	, orange, green
		char[] cubie_19 = {'W', 'x', 'x', 'x', 'G', 'x'}; // Edge:		White	, green
		char[] cubie_20 = {'W', 'x', 'x', 'R', 'G', 'x'}; // Corner: 	White	, red	, green
		
		char[][] dummy_solved_state = { cubie_01, cubie_02, cubie_03, cubie_04,
									cubie_05 ,cubie_06, cubie_07, cubie_08,	cubie_09,
									cubie_10, cubie_11, cubie_12, cubie_13, cubie_14,
									cubie_15, cubie_16, cubie_17, cubie_18, cubie_19, cubie_20
								};
		
		return dummy_solved_state;
	}
}
