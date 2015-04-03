package com.nxt;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.comm.NXTConnection;
import lejos.nxt.comm.USB;
import lejos.nxt.comm.USBConnection;

public class Main {

	public static void main(String[] args) {
		Connection connect_NXT = new Connection();
		connect_NXT.connectToPC();
		connect_NXT.sendDatatoPC(connect_NXT.getSendInt());
		connect_NXT.recieveDatafromPC();
		connect_NXT.closeStreams();
	}

	
}
