package com.nxt;

import com.nxt.NXTRegulatedStateMotor.Table;
import com.nxt.NXTUnregulatedStateMotor.Arm;
import com.nxt.NXTRegulatedStateMotor.Sensor;

import lejos.nxt.MotorPort;
import lejos.util.Delay;

/**
 * Perform movement actions like turn, tilt, hold, ... the cube
 */
public class Movement {
	
	// Regulated motor for the table
	private NXTRegulatedStateMotor ma;
	// Unregulated motor for the sensor
	private NXTRegulatedStateMotor mb;
	// Unregulated motor for the arm
	private NXTUnregulatedStateMotor mc;

	public Movement() {
		ma = new NXTRegulatedStateMotor(MotorPort.A, true);
		mb = new NXTRegulatedStateMotor(MotorPort.B, true);
		mc = new NXTUnregulatedStateMotor(MotorPort.C, true);
	}

	/**
	 * Rotate the table for a given angle in degrees
	 * 
	 * @param angle The angle the table should turn
	 * @return Enum<Table> Current state of the table
	 */
	public Enum<Table> rotateTable(int angle) {
		int angle_translated;

		// only when in resting state
		if (ma.getTableState() == Table.RESTING) {
			
			ma.setTableState(Table.MOVING);
			// Translate gear transmission (Small gear: 24 cogs, Large gear: 56 cogs)
			angle_translated = (int) (angle * 56 / 24);
	
			ma.setSpeed(360);
			ma.rotate(angle_translated);
			ma.setTableState(Table.RESTING);
		}
		return ma.getTableState();
	}
	
	/**
	 * Tilt the cube by pulling and pushing the arm back and forward again
	 * 
	 * @return Enum<Arm> Current state of the arm
	 */
	public Enum<Arm> tiltCube() {
		//only when in holding state
		if (mc.getArmState() == Arm.HOLDING) {
			
			mc.setArmState(Arm.MOVING);
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
			mc.setArmState(Arm.HOLDING);
		}
		return mc.getArmState();
	}
	
	/**
	 * Release cube by elevating the arm
	 * 
	 * @return Enum<Arm> Current state of the arm
	 */
	public Enum<Arm> releaseCube() {
		// only when in holding state
		if (mc.getArmState() == Arm.HOLDING) {
			
			mc.setArmState(Arm.MOVING);
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

	/**
	 * Hold cube by lowering the arm
	 * 
	 * @return Enum<Arm> Current state of the arm
	 */
	public Enum<Arm> holdCube() {
		// only when in released state
		if (mc.getArmState() == Arm.RELEASED) {
			
			mc.setArmState(Arm.MOVING);
			mc.resetTachoCount();
			mc.setPower(60);
			
			// move 110 degrees
			while (Math.abs(mc.getTachoCount()) < 110) {
				mc.backward();
			}
			mc.stop();
			mc.setArmState(Arm.HOLDING);
		}
		return mc.getArmState();
	}

	/**
	 * Move color sensor to the furthest position after scanning the edges
	 * 
	 * @return Enum<Sensor> Current state of the sensor
	 */
	public Enum<Sensor> removeSensor() {
		// only when in edge state
		if (mb.getSensorState() == Sensor.EDGE) {
				
			mb.setSensorState(Sensor.MOVING);
			mb.resetTachoCount();
			mb.rotateTo(-135);
			mb.stop();
			mb.setSensorState(Sensor.REMOVED);
		}
		return mb.getSensorState();
	}

	/**
	 * Move color sensor from the furthest position to above the center of the cube
	 * 
	 * @return Enum<Sensor> Current state of the sensor
	 */
	public Enum<Sensor> moveSensorToCenter() {
		// only when in removed state
		if (mb.getSensorState() == Sensor.REMOVED) {
	
			mb.setSensorState(Sensor.MOVING);
			mb.resetTachoCount();
			mb.rotateTo(195);
			mb.stop();
			mb.setSensorState(Sensor.CENTER);
		}
		return mb.getSensorState();
	}
	
	/**
	 * Move the color sensor above the edge of the cube after scanning the center
	 * 
	 * @return Enum<Sensor> Current state of the sensor
	 */
	public Enum<Sensor> moveSensorToEdge() {
		// only when in center state
		if (mb.getSensorState() == Sensor.CENTER) {

			mb.setSensorState(Sensor.MOVING);
			mb.resetTachoCount();
			mb.rotateTo(-60);
			mb.stop();
			mb.setSensorState(Sensor.EDGE);
		}
		return mb.getSensorState();
	}
	
	public Enum<Sensor> nodSensor() {
		// only when in center or edge state (sensor above cubie)
		if (mb.getSensorState() == Sensor.EDGE || mb.getSensorState() == Sensor.CENTER) {

			mb.setSensorState(Sensor.NODDING);
			mb.resetTachoCount();
			int nod = 0;
			int time = 0;
			//as long as color isn't reliable detected sensor should nod
			do{
				mb.rotateTo(nod-2);
				mb.rotateTo(nod+2);
				Delay.msDelay(500);
				time += 1;
				
			}while(time<5 && nod!=0);
			
			mb.stop();
			mb.setSensorState(Sensor.EDGE);
		}
		return mb.getSensorState();
	}
}
