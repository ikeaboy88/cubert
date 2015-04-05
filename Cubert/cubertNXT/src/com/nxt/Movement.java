package com.nxt;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.MotorPort;
import lejos.util.Delay;

/**
 * 
 * @author Maximilian Braun Perform movement actions like turn, tilt, hold, ...
 *         the cube
 *
 */
public class Movement {
	Boolean is_scanning = false;
	int tacho_count_current = 0;

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

	// rotate forward to remove sensor from cube

	public void removeSensorFromCube() {
		MotorPort.B.resetTachoCount();
		tacho_count_current = -1;
		LCD.drawString("removing", 0, 0);
//		MotorPort.B.controlMotor(70, 2);
		Motor.B.backward();
		while (true) {
//			Delay.msDelay(700);
			if ( Math.abs(Motor.B.getTachoCount()) > tacho_count_current) {
				// Cache Tachocount
				tacho_count_current = Math.abs(Motor.B.getTachoCount());
				LCD.drawInt(Motor.B.getTachoCount(), 0, 3);

			} else {
				Motor.B.stop();
				Motor.B.resetTachoCount();
				LCD.drawString("finished", 0, 1);
				break;
			}
		}
		LCD.drawString("totally finished", 0, 2);
	}

	// When motor is in initial position move towards cube and start scanning
	// rotate backwards to scan cube
	// return value should be int for tracking purpose
	public void scanMiddleCubi() {
		MotorPort.B.resetTachoCount();
		tacho_count_current = -1;
			LCD.drawString("scanningMID!", 0, 0);
			MotorPort.B.controlMotor(70, 1);

			
			while (Motor.B.isMoving()) {
				if (Motor.B.getTachoCount() > tacho_count_current) {
					// Cache Tachocount
					tacho_count_current = Motor.B.getTachoCount();
					LCD.drawInt(MotorPort.B.getTachoCount(), 0, 3);
				//	Delay.msDelay(300);
				} else {
					MotorPort.B.controlMotor(60, 3);
					Motor.B.resetTachoCount();
					LCD.drawString("finished", 0, 1);
					break;
				}
			}
			LCD.drawString("totally finished", 0, 2);
	}
	
	public void scanSideCubi() {
		MotorPort.B.resetTachoCount();
		tacho_count_current = -1;
		LCD.drawString("SIDscann", 0, 0);
		MotorPort.B.controlMotor(70, 2);
		
		while (Motor.B.isMoving()) {
			if ( MotorPort.B.getTachoCount() > tacho_count_current) {
				// Cache Tachocount
				tacho_count_current = MotorPort.B.getTachoCount();
				LCD.drawInt(MotorPort.B.getTachoCount(), 0, 3);
				//Button.waitForAnyPress();
				//Delay.msDelay(1000);
			} else {
				MotorPort.B.controlMotor(60, 3);
				MotorPort.B.resetTachoCount();
				LCD.drawString("finished", 0, 1);
				break;
			}
		}
		LCD.drawString("totally finished", 0, 2);
	}
}
