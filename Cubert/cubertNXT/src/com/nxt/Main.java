package com.nxt;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.comm.NXTConnection;
import lejos.nxt.comm.USB;

public class Main {

	public static void main(String[] args) {

		connect();
	}

	private static void connect() {

		LCD.drawString("Right BT->USB Verbindung", 0, 0);
		NXTConnection connection = null;
		if(Button.waitForAnyPress() == Button.ID_RIGHT){
			LCD.drawString("Waiting for USB connection", 0, 1);
			connection = USB.waitForConnection();
		}
		LCD.clear();
		LCD.drawString("connected", 0, 0);
		Button.waitForAnyPress();
	}

}
