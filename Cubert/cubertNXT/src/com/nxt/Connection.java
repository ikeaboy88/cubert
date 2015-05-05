package com.nxt;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.comm.USB;
import lejos.nxt.comm.USBConnection;

public class Connection {
	private USBConnection connection = null;
	private DataOutputStream dos = null;
	private DataInputStream dis = null;
	private BufferedReader bufferedReader = null; 

	public void connectToPC() {
		LCD.drawString("Right BT->USB Verbindung", 0, 0);

		if (Button.waitForAnyPress() == Button.ID_RIGHT) {
			LCD.drawString("Waiting for USB connection", 0, 1);
			connection = USB.waitForConnection();
		}
		LCD.clear();
		LCD.drawString("connected", 0, 0);
		Button.waitForAnyPress();
	}
	
	public USBConnection getConnection() {
		return connection;
	}
	
	public void disconnectFromPC() {
		try {
			connection.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/** send an String array character by character to the PC*/
	public void sendScanResultVector(char[] scan_result_vector) {
		LCD.clear();
//		char[]scan_result_vector = {'A','B','C','D'};
		LCD.drawString("sending data to pc...", 1, 3);
		String[] scanResult = new String[54];
		try {
			dos = connection.openDataOutputStream();
			for (int i = 0; i < scan_result_vector.length; i++){
			scanResult[i] = Character.toString(scan_result_vector[i]);
			dos.writeBytes(scanResult[i]);
			}
			dos.flush();
			dos.close();
		} catch (IOException e) {
			try {
				dos.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			LCD.drawString("Can't send data to PC", 1, 4);
			e.printStackTrace();
		}
//		LCD.drawString("press BT", 1, 4);
//		Button.waitForAnyPress();
	}

	// return type should be int ,void only for testing
	public void getSolvingSequence() {
		LCD.clear();
//		String s = "";
		int s;
		LCD.drawString("recieve..", 0, 0);
		dis = getConnection().openDataInputStream();
		bufferedReader = new BufferedReader(new InputStreamReader(dis));

		while(true){
			
			try {
				s = bufferedReader.read();
				LCD.drawString("Data: "+s, 0, 2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		LCD.drawString("Data??? ", 0, 1);
	
	}

	/*
	 * recieve keyboardinput just in time String recievedString = null;
	 * recievedString = dis.readChar(); LCD.clear();
	 * LCD.drawChar(recievedString, 1, 1); LCD.refresh();
	 */
}
