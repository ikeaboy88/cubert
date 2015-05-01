package com.nxt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.comm.USB;
import lejos.nxt.comm.USBConnection;

public class Connection {
	USBConnection connection = null;
	DataOutputStream dos = null;
	DataInputStream dis = null;

	public void connectToPC() {
		LCD.drawString("Right BT->USB Verbindung", 0, 0);

		if (Button.waitForAnyPress() == Button.ID_RIGHT) {
			LCD.drawString("Waiting for USB connection", 0, 1);
			connection = USB.waitForConnection();
		}
		LCD.clear();
		LCD.drawString("connected", 0, 0);
	}

	public void sendDatatoPC() {
		LCD.clear();
		LCD.drawString("sending data to pc...", 1, 3);
		try {
			dos = connection.openDataOutputStream();
			dos.writeChars("A");
			dos.flush();
		} catch (IOException e) {
			LCD.drawString("Can't send data to PC", 1, 4);
			e.printStackTrace();
		}
		LCD.drawString("press BT", 1, 4);
		Button.waitForAnyPress();
	}

	// return type should be int ,void only for testing
	public void recieveDatafromPC() {
		char recievedString;
		try {
			dis = connection.openDataInputStream();
			// check whether data is available to read
			if (connection.available() > 0) {
				LCD.clear();
				recievedString = dis.readChar();
//				recievedString = reader.readLine();
				LCD.drawString("Recieved Data:", 0, 0);
				LCD.drawChar(recievedString, 0, 1);
				LCD.drawString("Press to End", 0, 2);
				Button.waitForAnyPress();
			} else {
				System.out.println("nothing to read");
			}
		} catch (IOException e1) {
			System.out.println("Can't communicate");
			e1.printStackTrace();
		}

		/*
		 * recieve keyboardinput just in time String recievedString = null;
		 * recievedString = dis.readChar(); LCD.clear();
		 * LCD.drawChar(recievedString, 1, 1); LCD.refresh();
		 */
	}

	public void closeStreams() {
		try {
			dis.close();
			dos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
