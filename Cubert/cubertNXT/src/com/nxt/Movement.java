package com.nxt;

import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.util.Delay;

/**
 * Perform movement actions like turn, tilt, hold, ... the cube
 */
public class Movement {
	
	// Regulated motor for the table
	private NXTRegulatedMotor ma;
	// Unregulated motor for the sensor
	private NXTMotor mb;
	// Unregulated motor for the arm
	private NXTMotor mc; 

	public Movement() {
		ma = new NXTRegulatedMotor(MotorPort.A);
		mb = new NXTMotor(MotorPort.B);
		mc = new NXTMotor(MotorPort.C);
	}

	/**
	 * Rotate the table for a given angle in degrees
	 * 
	 * @param angle The angle the table should turn
	 * @return The angle the motor did actually turn
	 */
	public int rotateTable(int angle) {
		int angle_translated;
		// Translate gear transmission (Small gear: 24 cogs, Large gear: 56
		// cogs)
		angle_translated = (int) (angle * 56 / 24);

		ma.setSpeed(360);
		ma.rotate(angle_translated);

		return angle_translated;
	}
	
	public void tiltCube() {
		mc.setPower(100);

		// tilt consists of 2 moves: pull and push
		for (int i = 1; i <= 2; i++) {
			mc.resetTachoCount();

			// move 90 degrees
			while (Math.abs(mc.getTachoCount()) < 95) {
				// first move: pull cube
				if (i == 1) {
					mc.backward();
				}
				// second move: push cube
				if (i == 2) {
					// push cube
					mc.forward();
				}
			}
			mc.stop();
			Delay.msDelay(200);
		}
	}
	
	public void releaseCube() {
		mc.resetTachoCount();
		mc.setPower(60);
		
		// move 110 degrees
		while (Math.abs(mc.getTachoCount()) < 110) {
			mc.forward();
		}
		mc.stop();
	}

	public void holdCube() {
		mc.resetTachoCount();
		mc.setPower(60);
		
		// move 110 degrees
		while (Math.abs(mc.getTachoCount()) < 110) {
			mc.backward();
		}
		mc.stop();
	}

	//represents the colorsensors initial (start/stop) position
	public void removeSensor() {
		mb.resetTachoCount();
		mb.setPower(50);

		while (Math.abs(mb.getTachoCount()) < 90) {
			mb.forward();
		}
		mb.stop();
	}

	//moves 180 degrees forward = above middle cubie
	public void moveSensorToCenter() {
		mb.resetTachoCount();
		mb.setPower(50);

		while (Math.abs(mb.getTachoCount()) < 180)	{
			mb.forward();
		}
		mb.stop();
	}
	
	//moves 180 degrees forward = above side cubie
	public void moveSensorToEdge() {
		mb.resetTachoCount();
		mb.setPower(50);

		while (Math.abs(mb.getTachoCount()) < 270) {
			mb.backward();
		}
		mb.stop();
	}
}
