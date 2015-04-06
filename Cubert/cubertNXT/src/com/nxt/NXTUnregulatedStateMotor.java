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
	// Display the current state
	private boolean debug;
	
	// Super constructors
	public NXTUnregulatedStateMotor(TachoMotorPort port) {
		super(port);
		// set initial state
		this.arm_state = Arm.RELEASED;
		this.debug = false;
	}
	public NXTUnregulatedStateMotor(TachoMotorPort port, boolean debug) {
		super(port);
		// set initial state
		this.arm_state = Arm.RELEASED;
		this.debug = true;
	}

	// Setter
	public Enum<Arm> setArmState(Arm a) {
		if (debug) {
			System.out.println("Arm: " + a);
		}
		return this.arm_state = a;
	}
	
	// Getter
	public Enum<Arm> getArmState() {
		return this.arm_state;
	}
}
