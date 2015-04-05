package com.nxt;

import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.TachoMotorPort;

public class NXTRegulatedMotor_State extends NXTRegulatedMotor {

	// States of the table
	public enum Table {
		MOVING, RESTING;
	}
	
	// Initial state for the table
	private Enum<Table> table_state = Table.RESTING;
		
	// Super constructors
	public NXTRegulatedMotor_State(TachoMotorPort port) {
		super(port);
		// TODO Auto-generated constructor stub
	}

	// Setter
	public Enum<Table> setTableState(Table t) {
		return this.table_state = t;
	}
	
	// Getter
	public Enum<Table> getTableState() {
		return this.table_state;
	}
}
