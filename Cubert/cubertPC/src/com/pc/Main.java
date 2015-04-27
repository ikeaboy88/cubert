package com.pc;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTCommLogListener;
import lejos.pc.comm.NXTConnector;
import lejos.pc.comm.NXTInfo;

public class Main {

	public static void main(String[] args) 
	{
		Connection connect_PC= new Connection();
		connect_PC.connectToNXT();
		connect_PC.sendDataToNXT(connect_PC.send_Int);
		connect_PC.recieveDataFromNXT();
		connect_PC.closeStreams();
	}
}
