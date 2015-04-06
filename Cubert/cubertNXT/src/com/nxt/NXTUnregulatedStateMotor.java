package com.nxt;

import lejos.nxt.NXTMotor;
import lejos.nxt.TachoMotorPort;

/**
 * Extends the unregulated NXTMotor class by a enum of states and getter/setter methods
 */
public class NXTUnregulatedStateMotor extends NXTMotor {

	// States of the arm
	public enum Arm {
		HOLDING, RELEASED, MOVING;
	}
	
	// Initial state for the arm
	private Enum<Arm> arm_state;
	
	// Super constructors
	public NXTUnregulatedStateMotor(TachoMotorPort port) {
		super(port);
		// set initial state
		arm_state = Arm.RELEASED;
	}

	// Setter
	public Enum<Arm> setArmState(Arm a) {
		return this.arm_state = a;
	}
	
	// Getter
	public Enum<Arm> getArmState() {
		return this.arm_state;
	}
}
