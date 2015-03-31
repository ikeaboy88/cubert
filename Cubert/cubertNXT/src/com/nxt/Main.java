package com.nxt;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.comm.NXTConnection;
import lejos.nxt.comm.USB;

public class Main {

	public static void main(String[] args) {

		// set up variabel which temporarily stores input from pc
		int recievedInt = 0, sendInt = 5678;
		char recievedString = 0;
		
		LCD.drawString("Right BT->USB Verbindung", 0, 0);
		NXTConnection connection = null;
		
		if (Button.waitForAnyPress() == Button.ID_RIGHT) 
		{
			LCD.drawString("Waiting for USB connection", 0, 1);
			connection = USB.waitForConnection();
		}
		LCD.clear();
		LCD.drawString("connected", 0, 0);
		
		// open Streams for sending/recieving data
		DataOutputStream dos = connection.openDataOutputStream();
		DataInputStream dis = connection.openDataInputStream();
		
		Button.waitForAnyPress();
		LCD.clear();

		// stop programm by pressing left button
		//while (!Button.LEFT.isDown()) 
	//	{

			// send data to pc
			try 
			{
				LCD.drawInt(sendInt, 1, 2);
				LCD.drawString("sending data to pc...", 1,3);
				dos.writeInt(sendInt);
				dos.flush();
				LCD.drawString("press BT", 1, 4);
				Button.waitForAnyPress();
			} catch (IOException e1) 
			{
				e1.printStackTrace();
			}
			// check whether data is available to read
			if (connection.available() > 0) 
			{
				try 
				{
					LCD.clear();
					recievedInt = dis.readInt();
					LCD.drawString("Recieved Data:", 0,0);
					LCD.drawInt(recievedInt, 0, 1);

					// recieve keyboard input ;-)
					/*
					  recievedString = dis.readChar(); LCD.clear();
					  LCD.drawChar(recievedString, 1, 1); LCD.refresh();
					 */
				} catch (IOException e) 
				{
					// close connection when error
					connection.close();
					e.printStackTrace();
					System.exit(0);
				}
			}
//		}
			Button.waitForAnyPress();
			try {
				dos.close();
				dis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
}
