package com.nxt;


public enum State implements Transitions{

	/*
	 * Enum type for states
	 * Initial State of Colorsensor = REMOVED
	 */
	
	ColorsensorREMOVED
	{
		@Override
		public void read(Statemachine a) {
			a.setState(ColorsensorREMOVED);
		}
		
	},
	
	ColorsensorMIDDLE
	{

		@Override
		public void read(Statemachine a) {
			a.setState(ColorsensorMIDDLE);
		}

	},
	
	ColorsensorSIDE
	{

		@Override
		public void read(Statemachine a) {
			a.setState(ColorsensorSIDE);
			
		}

	}
	
}
