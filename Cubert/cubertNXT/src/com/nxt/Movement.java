package com.nxt;

import lejos.nxt.Motor;

/**
 * 
 * @author Maximilian Braun Perform movement actions like turn, tilt, hold, ...
 *         the cube
 *
 */
public class Movement {

	/**
	 * Rotate the table for a given angle in degrees
	 * @param angle The angle the table should turn
	 * @return The angle the motor did actually turn
	 */
	public int rotateTable(int angle) {
		int angle_translated;
		
		// Translate gear transmission (Small gear: 24 cogs, Large gear: 56 cogs)
		angle_translated = (int) (angle * 56 / 24);

		Motor.A.setSpeed(360);
		Motor.A.rotate(angle_translated);
//		System.out.println(angle + ", -  " + angle_translated);

		return angle_translated;
	}

}
