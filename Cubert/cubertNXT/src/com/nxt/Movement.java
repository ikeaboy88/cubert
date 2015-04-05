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
//	Automaton automation = new Automaton();
	int tacho_count_current = 0;
	
	NXTMotor motor_b = new NXTMotor(MotorPort.B);

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
		motor_b.resetTachoCount();
		motor_b.setPower(50);
		while(Math.abs(motor_b.getTachoCount())<90)
		{
			motor_b.forward();
		}
		motor_b.stop();
//		automation.setState(State.REMOVED);
		
	}

	//moves 180° forward = above middle cubie
	public void moveAboveMiddleCubie() {
		motor_b.resetTachoCount();
		motor_b.setPower(50);
		while(Math.abs(motor_b.getTachoCount())<180)
		{
			motor_b.forward();
		}
		motor_b.stop();
//		automation.setState(State.MIDDLE);
	}
	
	//moves 180° forward = above side cubie
	public void moveAboveSideCubie() {
		motor_b.resetTachoCount();
		motor_b.setPower(50);
		while(Math.abs(motor_b.getTachoCount())<270)
		{
			motor_b.backward();
		}
		motor_b.stop();
//		automation.setState(State.SIDE);
	}
}
