package com.nxt;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import lejos.util.Delay;

/**
 * 
 * @author Maximilian Braun Perform movement actions like turn, tilt, hold, ...
 *         the cube
 *
 */
public class Movement {
	NXTMotor mc = new NXTMotor(MotorPort.B);

	/**
	 * Rotate the table for a given angle in degrees
	 * 
	 * @param angle
	 *            The angle the table should turn
	 * @return The angle the motor did actually turn
	 */
	public int rotateTable(int angle) {
		int angle_translated;
		// Translate gear transmission (Small gear: 24 cogs, Large gear: 56
		// cogs)
		angle_translated = (int) (angle * 56 / 24);
		Motor.A.setSpeed(360);
		Motor.A.rotate(angle_translated);
		// System.out.println(angle + ", -  " + angle_translated);

		return angle_translated;
	}

	//represents the colorsensors initial (start/stop) position
	public void moveToInitial() {
		mc.resetTachoCount();
		mc.setPower(50);
		while(Math.abs(mc.getTachoCount())<90)
		{
			mc.forward();
		}
		mc.stop();
		
	}

	//moves 180° forward = above middle cubie
	public void moveAboveMiddleCubie() {
		mc.resetTachoCount();
		mc.setPower(50);
		while(Math.abs(mc.getTachoCount())<180)
		{
			mc.forward();
		}
		mc.stop();
	}
	
	//moves 180° forward = above side cubie
	public void moveAboveSideCubie() {
		mc.resetTachoCount();
		mc.setPower(50);
		while(Math.abs(mc.getTachoCount())<270)
		{
			mc.backward();
		}
		mc.stop();
	}
}
