package com.nxt;

import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.TachoMotorPort;

/**
 * Extends the regulated NXTRegulatedMotor class by enums of states and getter/setter methods
 */
public class NXTRegulatedStateMotor extends NXTRegulatedMotor {

	// States of the table
	public enum Table {
		MOVING, RESTING;
	}
	// States of the sensor
	public enum Sensor {
		REMOVED, CENTER, EDGE, MOVING;
	}
	
	// Initial state for the sensor
	private Enum<Sensor> sensor_state;
	// Initial state for the table
	private Enum<Table> table_state;
		
	// Super constructors
	public NXTRegulatedStateMotor(TachoMotorPort port) {
		super(port);
		// set initial states
		sensor_state = Sensor.REMOVED;
		table_state = Table.RESTING;
	}

	// Setter
	public Enum<Sensor> setSensorState(Sensor s) {
		return this.sensor_state = s;
	}
	public Enum<Table> setTableState(Table t) {
		return this.table_state = t;
	}
	
	// Getter
	public Enum<Sensor> getSensorState() {
		return this.sensor_state;
	}
	public Enum<Table> getTableState() {
		return this.table_state;
	}
}
