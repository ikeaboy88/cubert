package com.nxt;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.comm.NXTConnection;
import lejos.nxt.comm.USB;
import lejos.nxt.comm.USBConnection;

public class Main {

	public static void main(String[] args) {
		// Create object to execute movements on Cubert
		Movement move = new Movement();
		
		//***********************************
		// "Runtime loop"
		while (true){
			// After pressing a button... 
			Button.waitForAnyPress();
			// Exit loop when escape button was pressed
			if (Button.ESCAPE.isDown()){
				break;
			}
			// ...execute code below
			move.rotateTable(90);
		}
		//***********************************
		
		/* Connection between NXT and PC */
		//Connection connect_NXT = new Connection();
		//connect_NXT.connectToPC();
		//connect_NXT.sendDatatoPC(connect_NXT.getSendInt());
		//connect_NXT.recieveDatafromPC();
		//connect_NXT.closeStreams();
	}

	
}
