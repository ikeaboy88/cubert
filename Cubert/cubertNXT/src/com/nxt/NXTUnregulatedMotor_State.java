package com.nxt;

import lejos.nxt.NXTMotor;
import lejos.nxt.TachoMotorPort;

public class NXTUnregulatedMotor_State extends NXTMotor {

	// States of the sensor
	public enum Sensor {
		REMOVED, CENTER, EDGE, MOVING;
	}
	// States of the arm
	public enum Arm {
		HOLDING, RELEASED, MOVING;
	}
	
	// Initial state for the arm
	private Enum<Arm> arm_state = Arm.RELEASED;
	// Initial state for the sensor
	private Enum<Sensor> sensor_state = Sensor.REMOVED;
	
	// Super constructors
	public NXTUnregulatedMotor_State(TachoMotorPort port) {
		super(port);
		// TODO Auto-generated constructor stub
	}
	public NXTUnregulatedMotor_State(TachoMotorPort port, int PWMMode) {
		super(port, PWMMode);
		// TODO Auto-generated constructor stub
	}

	// Setter
	public Enum<Arm> setArmState(Arm a) {
		return this.arm_state = a;
	}
	public Enum<Sensor> setSensorState(Sensor s) {
		return this.sensor_state = s;
	}
	
	// Getter
	public Enum<Arm> getArmState() {
		return this.arm_state;
	}
	public Enum<Sensor> getSensorState() {
		return this.sensor_state;
	}
}
