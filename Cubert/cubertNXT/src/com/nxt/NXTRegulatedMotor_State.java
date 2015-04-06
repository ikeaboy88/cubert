package com.nxt;

import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.TachoMotorPort;

public class NXTRegulatedMotor_State extends NXTRegulatedMotor {

	// States of the table
	public enum Table {
		MOVING, RESTING;
	}
	// States of the sensor
	public enum Sensor {
		REMOVED, CENTER, EDGE, MOVING;
	}
	
	// Initial state for the sensor
	private Enum<Sensor> sensor_state = Sensor.REMOVED;
	// Initial state for the table
	private Enum<Table> table_state = Table.RESTING;
		
	// Super constructors
	public NXTRegulatedMotor_State(TachoMotorPort port) {
		super(port);
		// TODO Auto-generated constructor stub
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
