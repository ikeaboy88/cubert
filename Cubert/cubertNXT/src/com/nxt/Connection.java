package com.nxt;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.comm.USB;
import lejos.nxt.comm.USBConnection;

public class Connection {
	private USBConnection connection = null;
	private DataOutputStream dos = null;
	private DataInputStream dis = null;
	private BufferedReader bufferedReader = null; 
	
	static int[][]ref_rgb = new int[6][3];
	
	public void connectToPC() {
		LCD.drawString("Right BT->USB Verbindung", 0, 0);

		if (Button.waitForAnyPress() == Button.ID_RIGHT) {
			LCD.drawString("Waiting for USB connection", 0, 1);
			connection = USB.waitForConnection();
		}
		LCD.clear();
		LCD.drawString("connected", 0, 0);
		Button.waitForAnyPress();
		dos = connection.openDataOutputStream();
		dis = getConnection().openDataInputStream();
		bufferedReader = new BufferedReader(new InputStreamReader(dis));
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
			for (int i = 0; i < scan_result_vector.length; i++){
			scanResult[i] = Character.toString(scan_result_vector[i]);
			dos.writeBytes(scanResult[i]);
			}
			dos.flush();
//			dos.close();
		} catch (IOException e) {
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

	public void sendRGBCalibration(List<Integer> reference) {
		// TODO Auto-generated method stub
		LCD.clear();
//		char[]scan_result_vector = {'A','B','C','D'};
		LCD.drawString("sending data to pc...", 1, 3);
		try {
			byte[]rgb_calibration_value = new byte[18];
			for(int i = 0; i < reference.size(); i++){
				int ref = reference.get(i);
				rgb_calibration_value[i] = (byte) ref;
			}
			dos.write(rgb_calibration_value, 0, reference.size());
			//dos.writeBytes(reference);
			
			dos.flush();
		} catch (IOException e) {
			LCD.drawString("Can't send data to PC", 1, 4);
			e.printStackTrace();
		}
		
	}
	
	public void sendMode(int mode){
		try {
			byte[]b = new byte[1];
			b[0] = (byte) mode;
			dos.write(b, 0, 1);
			dos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int[][] safeReferenceRGBValues() {
		// TODO Auto-generated method stub
		LCD.clear();
		
		byte[] recieved_rgb_value = new byte[18];
		int[][]rgb_reference = new int[6][3];
		try {

			// read 18 characters in byte array
			//int recieved_byte = dis.read(recieved_rgb_value, 0, 18);
			LCD.drawString("reading...", 0, 0);
			dis.read(recieved_rgb_value, 0, 18);
			//format values
			int rgb;
			int index = 0;
//			if(recieved_rgb_value[0]!=0){
				for (int i = 0; i < 6; i++){
					
					for(int j = i*3; j < 3+(i*3); j++){
						index = j;
						//is there is a byte overflow, convert the byte into int
						if(recieved_rgb_value[j]<0){
							//get positive value of overflow
							int temp_rgb = -recieved_rgb_value[j];
							//bring overflow value into range of 32bit Integer
							rgb = 255-temp_rgb;
						}
						else{					
							rgb = recieved_rgb_value[j];
						}
						
						// writing values in two dimensional array range: [6][3]
						// therefore modulo operation on j, so that range is between 0 and 2
						if(j>=3){
							index %= 3;
						}
						rgb_reference[i][index] = rgb;
//						ref_rgb[i][index]=rgb;
						LCD.drawString("ref: "+rgb_reference[i][index], 0, 3);
						LCD.drawString("i: "+i, 0, 4);
						LCD.drawString("j: "+j, 0, 5);
						LCD.drawString("index: "+index, 0, 6);
					
						
					}
					LCD.drawString("1 for", 0, 2);
				}
//			}
//			ref_rgb = rgb_reference;
			LCD.drawString("ref: "+ref_rgb[0][1], 0, 3);
		} catch (IOException e) {
			LCD.clear();
			LCD.drawString("ERROR while safing reference values!", 0, 1);
			System.out.println("Can't communicate to NXT");
			e.printStackTrace();
		}
		return rgb_reference;

	}

	/*
	 * recieve keyboardinput just in time String recievedString = null;
	 * recievedString = dis.readChar(); LCD.clear();
	 * LCD.drawChar(recievedString, 1, 1); LCD.refresh();
	 */
}
