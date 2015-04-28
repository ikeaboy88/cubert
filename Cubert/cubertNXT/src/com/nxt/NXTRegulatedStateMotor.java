package com.nxt;

import lejos.nxt.LCD;
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
		REMOVED, CENTER, EDGE, CORNER, MOVING;
	}
	
	// Initial state for the sensor
	private Enum<Sensor> sensor_state;
	// Initial state for the table
	private Enum<Table> table_state;
	// Display the current state
	private boolean debug;
		
	// Super constructors
	public NXTRegulatedStateMotor(TachoMotorPort port) {
		super(port);
		// set initial states
		this.sensor_state = Sensor.REMOVED;
		this.table_state = Table.RESTING;
		this.debug = false;
	}
	public NXTRegulatedStateMotor(TachoMotorPort port, boolean debug) {
		super(port);
		// set initial states
		this.sensor_state = Sensor.REMOVED;
		this.table_state = Table.RESTING;
		this.debug = debug; 
	}

	// Setter
	public Enum<Sensor> setSensorState(Sensor s) {
		if (debug) {
			LCD.drawString("Sensor:        ", 0, 2);
			LCD.drawString("Sensor: " + s, 0, 2);
			LCD.refresh();
		}
		return this.sensor_state = s;
	}
	public Enum<Table> setTableState(Table t) {
		if (debug) {
			LCD.drawString("Table:        ", 0, 3);
			LCD.drawString("Table: " + t, 0, 3);
			LCD.refresh();
		}
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
