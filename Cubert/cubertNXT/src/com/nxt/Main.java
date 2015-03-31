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
		int recievedInt = 0;
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
		DataInputStream dis = connection.openDataInputStream();
		DataOutputStream dos = connection.openDataOutputStream();
		
		Button.waitForAnyPress();
		LCD.clear();
		LCD.drawString("trying to recieve data...", 0, 0);

		// stop programm by pressing left button
		while (!Button.LEFT.isDown()) 
		{

			// send data to pc
			try 
			{
				dos.write(56789);
				dos.flush();
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
					LCD.drawInt(recievedInt, 0, 0);

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
		}
	}
}
