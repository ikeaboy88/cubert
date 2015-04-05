package com.nxt;

import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.util.Delay;

/**
 * Perform movement actions like turn, tilt, hold, ... the cube
 */
public class Movement {
	// dummy state
	// 1=sensor is removed
	// 2=sensor is centered
	// 2=sensor is edged
	int state;

	// Regulated motor for the table
	private NXTRegulatedMotor ma;
	// Unregulated motor for the sensor
	private NXTRegulatedMotor mb;
	// Unregulated motor for the arm
	private NXTMotor mc;

	public Movement() {
		state = 0;
		ma = new NXTRegulatedMotor(MotorPort.A);
		mb = new NXTRegulatedMotor(MotorPort.B);
		mc = new NXTMotor(MotorPort.C);
		removeSensor();
	}

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

	// Max degree = 200
	// Start degree = 0
	public void removeSensor() {
		mb.resetTachoCount();

		// when sensor is centered
		if (state == 2) {
			mb.rotateTo(-200);
			mb.stop();
			System.out.println(mb.getTachoCount());
		}
		// when sensor is edged
		else  if (state == 3) {
			mb.rotateTo(-140);
			mb.stop();
			System.out.println(mb.getTachoCount());

		}
		// when sensor is initial
		 else  if (state == 0) {
			mb.rotateTo(30);
			mb.stop();
			System.out.println(mb.getTachoCount());
		}

		else{
			LCD.drawString("kein remove state", 0, 0);
		}

		state = 1;
	}

	public void moveSensorToCenter() {
		mb.resetTachoCount();

		// when sensor is removed
		if (state == 1) {
			mb.rotateTo(200);
			mb.stop();
			System.out.println(mb.getTachoCount());
		}
		// when sensor is on edge
		else if (state == 3) {
			mb.rotateTo(60);
			mb.stop();
			System.out.println(mb.getTachoCount());
		} else{ 
			LCD.drawString("kein center state", 0, 1);

		}
		state = 2;
	}

	public void moveSensorToEdge() {
		mb.resetTachoCount();

		// when sensor is removed
		if (state == 1) {
			mb.rotateTo(140);
			mb.stop();
			System.out.println(mb.getTachoCount());
		}
		// when sensor is centered
		else if (state == 2) {
			mb.rotateTo(-45);
			mb.stop();
			System.out.println(mb.getTachoCount());
		} else{
			LCD.drawString("kein edge state", 0, 2);

		}
		state = 3;
	}
	
	public void clear() {
		LCD.clear();
		
	}
}
