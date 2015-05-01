package com.nxt;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.comm.USB;
import lejos.nxt.comm.USBConnection;
import lejos.util.Delay;

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
	}
	
	/** send an String array character by character to the PC*/
	public void sendColorSequence(char[] scan_result_vector) {
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
		LCD.drawString("press BT", 1, 4);
		Button.waitForAnyPress();
	}

	// return type should be int ,void only for testing
	public List<Character> getSolvingSequence() {
		String recievedString = null;
		char recievedChar;
		List <Character> scannedCubeState = new ArrayList<Character>();
		
		try {
			dis = connection.openDataInputStream();
			bufferedReader = new BufferedReader(new InputStreamReader(dis));
			
//			 check whether data is available to read
			if (dis.available() > 0) {
				LCD.clear();
				recievedString = bufferedReader.readLine();
//				recievedString = reader.readLine();
				LCD.drawString("Recieved Data:", 0, 0);
				LCD.drawString(recievedString, 0, 1);
//				LCD.drawString("Press to End", 0, 2);
//				Button.waitForAnyPress();
				
				for(int i = 0; i < recievedString.length(); i++)
				{
						recievedChar=recievedString.charAt(i);
						scannedCubeState.add(recievedChar);
						
						System.out.println("Recieved Data: " +scannedCubeState.get(i));
				}
			} else {
				System.out.println("nothing to read");
			}
				
			Button.waitForAnyPress();
			dis.close();
		} catch (IOException e1) {
			System.out.println("Can't communicate");
			e1.printStackTrace();
		}
		return scannedCubeState;
	}

	/*
	 * recieve keyboardinput just in time String recievedString = null;
	 * recievedString = dis.readChar(); LCD.clear();
	 * LCD.drawChar(recievedString, 1, 1); LCD.refresh();
	 */
}
