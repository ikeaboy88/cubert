package com.nxt;

import lejos.nxt.LCD;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.util.Delay;

/**
 * Perform movement actions like turn, tilt, hold, ... the cube
 */
public class Movement {
	//dummy state
	int state;
	
	// Regulated motor for the table
	private NXTRegulatedMotor ma;
	// Unregulated motor for the sensor
	private NXTMotor mb;
	// Unregulated motor for the arm
	private NXTMotor mc; 
	
//	private Statemachine statemachine = new Statemachine();

	public Movement() {
		ma = new NXTRegulatedMotor(MotorPort.A);
		mb = new NXTMotor(MotorPort.B);
		mc = new NXTMotor(MotorPort.C);
		initialPosition();
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
	
	//move cube to initial position with most distance to cube 
	public void initialPosition(){
		mb.resetTachoCount();
		mb.setPower(70);
		
		
		while (Math.abs(mb.getTachoCount()) <45 ) {
			mb.forward();
		}
		mb.stop();
		state = 0;
	}

	public void removeSensor() {
		mb.resetTachoCount();
		mb.setPower(70);

		//when sensor is located in center
		if(state == 2){
			while (Math.abs(mb.getTachoCount()) <180 ) {
				mb.backward();
			}
		}
		//when sensor is located on edge 
		else if(state == 3){
			while (Math.abs(mb.getTachoCount()) <120 ) {
				mb.backward();
			}
		}else LCD.drawString("kein remove state", 0, 0);
		

		mb.stop();
		state=1;
//		statemachine.setState(State.ColorsensorREMOVED);
	}

	//moves 180 degrees forward = above middle cubie
	public void moveSensorToCenter() {
		mb.resetTachoCount();
		mb.setPower(70);

		//when sensor is removed 
		if(state == 0){
			
			while (Math.abs(mb.getTachoCount()) < 180)	{
				mb.forward();
			}
			
		}else if( state == 1){
			
			while (Math.abs(mb.getTachoCount()) < 120)	{
				mb.forward();
			}
		}
		//when sensor is on edge 
			else if(state == 3 ){
			
			while (Math.abs(mb.getTachoCount()) < 60)	{
				mb.forward();
			}
		}else LCD.drawString("kein center state", 0, 1);
		
		mb.stop();
		state=2;
	}
	
	//moves 180 degrees forward = above side cubie
	public void moveSensorToEdge() {
		mb.resetTachoCount();
		mb.setPower(70);

		if(state == 0){
			
			while (Math.abs(mb.getTachoCount()) < 120) {
				mb.forward();
			}
		}else if(state == 2){
			
			while (Math.abs(mb.getTachoCount()) < 60) {
				mb.backward();
			}
		}else LCD.drawString("kein edge state", 0, 2);
		
		mb.stop();
		state=3;
	}
}
