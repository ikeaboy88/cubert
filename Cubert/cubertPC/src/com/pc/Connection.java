package com.pc;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.List;

import lejos.pc.comm.NXTCommLogListener;
import lejos.pc.comm.NXTConnector;
import lejos.pc.comm.NXTInfo;

public class Connection {
	//use this connector for opening multi in-/ outputstreams
	NXTConnector nxt_Comm = new NXTConnector();
	public NXTInfo[] nxt_Info = null;
	DataOutputStream dos = null;
	DataInputStream dis = null;
	BufferedReader bufferedReader = null;
//	CharBuffer charBuffer;

	public void connectToNXT() 
	{
		System.out.println("Trying to connect...");
		nxt_Comm.addLogListener(new NXTCommLogListener(){

			public void logEvent(String message) {
				System.out.println("USBSend Log.listener: "+message);
			}
			public void logEvent(Throwable throwable) {
				System.out.println("USBSend Log.listener - stack trace: ");
				 throwable.printStackTrace();
			}
		} 
		);
		if (!nxt_Comm.connectTo("usb://")){
			System.err.println("No NXT found using USB");
			System.exit(1);
		}
	}

	public void sendDataToNXT() 
	{
		try 
		{
			dos = new DataOutputStream(nxt_Comm.getOutputStream());
			dos.writeChars("B");			
			// send data through stream
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//return type should be int, void only for testing
	public void recieveDataFromNXT() 
	{
		String recievedString = null;
		try 
		{
			dis = new DataInputStream(nxt_Comm.getInputStream());
//			dis.read();
			bufferedReader = new BufferedReader(new InputStreamReader(dis));
//			if(bufferedReader.ready())
//			{
				recievedString = bufferedReader.readLine();
//				bufferedReader.read(recievedString);
				System.out.println("Recieved Data: "+recievedString);
			
		} catch (IOException e) {
			System.out.println("Can't communicate to NXT");
			e.printStackTrace();
		}
	}

	public void closeStreams() 
	{
		try 
		{
			dis.close();
			dos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendPressedCharToNXT() 
	{
		// type on keyboard an send data char by char to NXT ;-)
		String writeToNXT = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) 
		{
			try 
			{
				writeToNXT = reader.readLine();
				dos.writeChars(writeToNXT);
				dos.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

