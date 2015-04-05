package com.nxt;

import lejos.nxt.Motor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import lejos.nxt.TachoMotorPort;
import lejos.util.Delay;

/**
 * 
 * @author Maximilian Braun Perform movement actions like turn, tilt, hold, ...
 *         the cube
 *
 */
public class Movement {
	
	NXTMotor motor_a;
	NXTMotor motor_c; 

	public Movement(){
		motor_a = new NXTMotor(MotorPort.A);
		motor_c = new NXTMotor(MotorPort.C);
	}

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
	
	public void tiltCube() {
		motor_c.setPower(60);

		for (int i = 1; i <= 2; i++){
			motor_c.resetTachoCount();

			while (Math.abs(motor_c.getTachoCount()) < 90){
				if (i == 1){
					motor_c.backward();
				}
				if (i == 2){
					motor_c.forward();
				}
			}
			motor_c.stop();
			Delay.msDelay(200);
		}
	}
	
	public void releaseCube() {
		motor_c.resetTachoCount();
		motor_c.setPower(60);
		
		while (Math.abs(motor_c.getTachoCount()) < 110){
			motor_c.forward();
		}
		motor_c.stop();
	}

	public void holdCube() {
		motor_c.resetTachoCount();
		motor_c.setPower(60);
		
		while (Math.abs(motor_c.getTachoCount()) < 110){
			motor_c.backward();
		}
		motor_c.stop();
	}
}
