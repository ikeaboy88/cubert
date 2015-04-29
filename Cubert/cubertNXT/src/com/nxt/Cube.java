package com.nxt;

import lejos.nxt.SensorPort;
import lejos.util.Delay;

public class Cube {

	public Movement move;
	public ColorDetector detect;
	
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
	
	public final char[][] cube_solved = {	cubie_00_solved,
											cubie_01_solved,
											cubie_02_solved,
											cubie_03_solved,
											cubie_04_solved,
											cubie_05_solved,
											cubie_06_solved,
											cubie_07_solved,
											cubie_08_solved,
											cubie_09_solved,
											cubie_10_solved,
											cubie_11_solved,
											cubie_12_solved,
											cubie_13_solved,
											cubie_14_solved,
											cubie_15_solved,
											cubie_16_solved,
											cubie_17_solved,
											cubie_18_solved,
											cubie_19_solved
										};
	
	public Cube() {
		move = new Movement(true);
		detect = new ColorDetector(SensorPort.S3, true);
	}
	
	public void executeCompleteScan() {
		
		// scan center
		move.moveSensorToCenter();
		Delay.msDelay(200);
		detect.detectColor(200, 5);
		
		//Scan edges
		move.moveSensorToEdge();
		Delay.msDelay(200);
		detect.detectColor(200, 5);
		//Button.waitForAnyPress();
		for (int i = 0; i < 7; i++) {
			Delay.msDelay(200);
			move.rotateTable(45);
			if (i % 2 == 1) {
				move.moveSensorToEdge();
			} else {
				move.moveSensorToCorner();
			}
			detect.detectColor(200, 5);
		}
		Delay.msDelay(200);
		move.rotateTable(45);
		move.moveSensorToEdge();
		Delay.msDelay(200);
		move.removeSensor();
		
		//Tilt cube
		move.holdCube();
		Delay.msDelay(1000);
		move.tiltCube();
		Delay.msDelay(1000);
		move.releaseCube();
		Delay.msDelay(1000);
	}
	
}
