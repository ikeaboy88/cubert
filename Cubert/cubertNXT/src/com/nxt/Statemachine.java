package com.nxt;

public class Statemachine {

	
	//initial state
	private State state = State.ColorsensorREMOVED;
	
	public void setState(State s)
	{
		this.state = s;
	}
	
	public void read()
	{
		//Delegate...
		state.read(this);
	}
	
	public int getState(){
		switch(state){
		case ColorsensorREMOVED: 
			return 1;
		case ColorsensorSIDE: 
			return 2;
		case ColorsensorMIDDLE:
			return 3;
		default: return 0;
		}
	}
}
