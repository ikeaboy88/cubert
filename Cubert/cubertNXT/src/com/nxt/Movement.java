package com.nxt;

import com.nxt.NXTRegulatedMotor_State.Table;
import com.nxt.NXTUnregulatedMotor_State.Arm;
import com.nxt.NXTRegulatedMotor_State.Sensor;

import lejos.nxt.MotorPort;
import lejos.util.Delay;

/**
 * Perform movement actions like turn, tilt, hold, ... the cube
 */
public class Movement {
	
	// Regulated motor for the table
	private NXTRegulatedMotor_State ma;
	// Unregulated motor for the sensor
	private NXTRegulatedMotor_State mb;
	// Unregulated motor for the arm
	private NXTUnregulatedMotor_State mc;

	public Movement() {
		ma = new NXTRegulatedMotor_State(MotorPort.A);
		mb = new NXTRegulatedMotor_State(MotorPort.B);
		mc = new NXTUnregulatedMotor_State(MotorPort.C);
	}

	/**
	 * Rotate the table for a given angle in degrees
	 * 
	 * @param angle The angle the table should turn
	 * @return The angle the motor did actually turn
	 */
	public Enum<Table> rotateTable(int angle) {
		if (ma.getTableState() == Table.RESTING) {
			int angle_translated;
			System.out.println(ma.setTableState(Table.MOVING));
			// Translate gear transmission (Small gear: 24 cogs, Large gear: 56
			// cogs)
			angle_translated = (int) (angle * 56 / 24);
	
			ma.setSpeed(360);
			ma.rotate(angle_translated);
			ma.setTableState(Table.RESTING);
		}
		return ma.getTableState();
	}
	
	public Enum<Arm> tiltCube() {
		//only when in holding state
		if (mc.getArmState() == Arm.HOLDING) {
			System.out.println(mc.setArmState(Arm.MOVING));
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
				mc.setArmState(Arm.HOLDING);
			}
		}
		return mc.getArmState();
	}
	
	public Enum<Arm> releaseCube() {
		// only when in holding state
		if (mc.getArmState() == Arm.HOLDING) {
			System.out.println(mc.setArmState(Arm.MOVING));
			mc.resetTachoCount();
			mc.setPower(60);
			
			// move 110 degrees
			while (Math.abs(mc.getTachoCount()) < 110) {
				mc.forward();
			}
			mc.stop();
			mc.setArmState(Arm.RELEASED);
		}
		return mc.getArmState();
	}

	public Enum<Arm> holdCube() {
		// only when in released state
		if (mc.getArmState() == Arm.RELEASED) {
			System.out.println(mc.setArmState(Arm.MOVING));
			mc.resetTachoCount();
			mc.setPower(60);
			
			// move 110 degrees
			while (Math.abs(mc.getTachoCount()) < 110) {
				mc.backward();
			}
			mc.stop();
			mc.setArmState(Arm.HOLDING);
		}
		//TODO return set
		return mc.getArmState();
	}

	//represents the colorsensors initial (start/stop) position
	public Enum<Sensor> removeSensor() {
		if (mb.getSensorState() != Sensor.MOVING && mb.getSensorState() != Sensor.REMOVED) {
				
			mb.resetTachoCount();
	
			if (mb.getSensorState() == Sensor.EDGE) {
				mb.rotateTo(-135);
			}
			System.out.println(mb.setSensorState(Sensor.MOVING));
			mb.stop();
		}
		return mb.setSensorState(Sensor.REMOVED);
	}

	//moves 180 degrees forward = above middle cubie
	public Enum<Sensor> moveSensorToCenter() {
		if (mb.getSensorState() != Sensor.MOVING && mb.getSensorState() != Sensor.CENTER) {
	
			mb.resetTachoCount();
	
			if (mb.getSensorState() == Sensor.REMOVED) {
				mb.rotateTo(195);
			}
			System.out.println(mb.setSensorState(Sensor.MOVING));
			mb.stop();
		}
		return mb.setSensorState(Sensor.CENTER);
	}
	
	//moves 180 degrees forward = above side cubie
	public Enum<Sensor> moveSensorToEdge() {
		if (mb.getSensorState() != Sensor.MOVING && mb.getSensorState() != Sensor.EDGE) {
	
			mb.resetTachoCount();
	
			if (mb.getSensorState() == Sensor.CENTER) {
				mb.rotateTo(-60);
			}
			System.out.println(mb.setSensorState(Sensor.MOVING));
			mb.stop();
		}
		return mb.setSensorState(Sensor.EDGE);
	}
}
