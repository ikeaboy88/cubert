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
	private int sendInt = 5678;

	public void connectToPC() {
		LCD.drawString("Right BT->USB Verbindung", 0, 0);

		if (Button.waitForAnyPress() == Button.ID_RIGHT) {
			LCD.drawString("Waiting for USB connection", 0, 1);
			connection = USB.waitForConnection();
		}
		LCD.clear();
		LCD.drawString("connected", 0, 0);
	}

	public void sendDatatoPC(int sendInt) {
		LCD.clear();
		LCD.drawInt(sendInt, 1, 2);
		LCD.drawString("sending data to pc...", 1, 3);
		try {
			dos = connection.openDataOutputStream();
			dos.writeInt(sendInt);
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
		int recievedInt = 0;
		try {
			dis = connection.openDataInputStream();
			// check whether data is available to read
			if (connection.available() > 0) {
				LCD.clear();
				recievedInt = dis.readInt();
				LCD.drawString("Recieved Data:", 0, 0);
				LCD.drawInt(recievedInt, 0, 1);
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

	public int getSendInt() {
		return sendInt;
	}

	public void setSendInt(int sendInt) {
		this.sendInt = sendInt;
	}
}
