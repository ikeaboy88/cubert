package com.pc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

public class Main {

	public static void main(String[] args) {

		int numberToSend = 1234;
		int recievedInt = 0;
		NXTComm nxtComm;
		NXTInfo[] nxtInfo = null;

		try {
			System.out.println("Trying to connect...");
			nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.USB);
			nxtInfo = nxtComm.search(null);
			nxtComm.open(nxtInfo[0]);
			System.out.println("Connected to " + nxtInfo[0].name);

			// Get Streams for sending/recieving data
			DataOutputStream dos = new DataOutputStream(nxtComm.getOutputStream());
			DataInputStream dis = new DataInputStream(nxtComm.getInputStream());
			try 
			{
				dos.writeInt(numberToSend);
				
				// send data through stream
				dos.flush();
				
				//checking for available data to read
					if (nxtComm.available() > 0) 
					{
						recievedInt = dis.readInt();
						System.out.println("" + recievedInt);
					}

				// type on keyboard an send data char by char to NXT ;-)
				/*
				 * // BufferedReader reader = new BufferedReader(new
				 * InputStreamReader(System.in)); String writeToNXT = null;
				 * while(true){ writeToNXT = reader.readLine();
				 * dos.writeChars(writeToNXT); dos.flush(); }
				 */
			} catch (IOException e)
			{
				System.out.println("can't communicate");
				e.printStackTrace();
			}

		} catch (NXTCommException e) 
		{
			e.printStackTrace();
		}
	}
}
