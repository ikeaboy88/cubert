package com.pc;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

import lejos.pc.comm.NXTCommLogListener;
import lejos.pc.comm.NXTConnector;
import lejos.pc.comm.NXTInfo;

public class Connection {
	private DataOutputStream dos = null;
	private DataInputStream dis = null;
	//use this connector for opening multi in-/ outputstreams
	private final NXTConnector nxt_Comm = new NXTConnector();
	public NXTInfo[] nxt_Info = null;
	private BufferedReader bufferedReader = null;
	
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
	
	public void sendSolvingSequence(char[] sequence) 
	{
//		String[] solvingSequence = new String[3];
		int i = 23;
		try 
		{
			dos = new DataOutputStream(nxt_Comm.getOutputStream());
			
//			for (int i = 0; i < sequence.length; i++){
//			solvingSequence[i] = Character.toString(sequence[i]);
//			dos.writeBytes(solvingSequence[i]);			
//			System.out.println(solvingSequence[i]);
//			}
			dos.writeInt(i);
			// send data through stream
			dos.flush();
			dos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**Returns an array with the scanned colors */
	public List<Character> getColorSequence() 
	{
		String recievedString = null;
		char recievedChar; 
		List <Character> scannedCubeState = new ArrayList<Character>();

		try 
		{
			dis = new DataInputStream(nxt_Comm.getInputStream());
			bufferedReader = new BufferedReader(new InputStreamReader(dis));
	
			recievedString = bufferedReader.readLine();
			System.out.println("Recieved String: " +recievedString);
			recievedString.toCharArray();
			
			for(int i = 0; i < recievedString.length(); i++)
			{
					recievedChar = recievedString.charAt(i);
					scannedCubeState.add(recievedChar);
					
					System.out.println("Recieved Data: " +scannedCubeState.get(i));
			}
			dis.close();
		} catch (IOException e) {
			System.out.println("Can't communicate to NXT");
			e.printStackTrace();
		}
		
		return scannedCubeState;
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

