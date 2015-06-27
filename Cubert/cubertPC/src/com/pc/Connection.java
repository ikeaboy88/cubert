package com.pc;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import lejos.pc.comm.NXTCommLogListener;
import lejos.pc.comm.NXTConnector;
import lejos.pc.comm.NXTInfo;

public class Connection {
	private DataOutputStream dos = null;
	private DataInputStream dis = null;

	// use this connector for opening multi in-/ outputstreams
	private final NXTConnector nxt_Comm = new NXTConnector();
	public NXTInfo[] nxt_Info = null;
	private BufferedReader bufferedReader = null;

	public void connectToNXT() {
		System.out.println("Trying to connect...");
		nxt_Comm.addLogListener(new NXTCommLogListener() {

			public void logEvent(String message) {
				System.out.println("USBSend Log.listener: " + message);
			}

			public void logEvent(Throwable throwable) {
				System.out.println("USBSend Log.listener - stack trace: ");
				throwable.printStackTrace();
			}
		});
		if (!nxt_Comm.connectTo("usb://")) {
			System.err.println("No NXT found using USB");
			System.exit(1);
		}
		// create input-/ and outputstreams
		dos = new DataOutputStream(nxt_Comm.getOutputStream());
		dis = new DataInputStream(nxt_Comm.getInputStream());
		bufferedReader = new BufferedReader(new InputStreamReader(dis));
	}

	public void sendSolvingSequence(char[] sequence) {
		
		//save sequence in list because we don't know how long the sequence will be
		List<Byte>solving_sequence = new ArrayList<Byte>();

		//translate character into byte values
		for(int i = 0; i < sequence.length; i++){
			switch(sequence[i]){
			case 't' :solving_sequence.add((byte) 0);
				break; 
			case 'T' :solving_sequence.add((byte) 1);
				break; 
			case 'd' :solving_sequence.add((byte) 2);
				break; 
			case 'D' :solving_sequence.add((byte) 3);
				break; 
			case 'l' :solving_sequence.add((byte) 4);
				break; 
			case 'L' :solving_sequence.add((byte) 5);
				break; 
			case 'r' :solving_sequence.add((byte) 6);
				break; 
			case 'R' :solving_sequence.add((byte) 7);
				break; 
			case 'f' :solving_sequence.add((byte) 8);
				break; 
			case 'F' :solving_sequence.add((byte) 9);
				break; 
			case 'b' :solving_sequence.add((byte) 10);
				break; 
			case 'B' :solving_sequence.add((byte) 11);
				break; 
			default  :System.out.println("unlösbar :-(");
			}
		}
		
		//stop sign
		solving_sequence.add((byte) -1);
		
		//transfer bytes from list to array for data transfer between pc and nxt
		byte[] solving_sequence_as_byte = new byte[solving_sequence.size()];
		System.out.println("solving sequence: ");
		for(int i = 0; i < solving_sequence_as_byte.length; i++){
//			for(byte b : solving_sequence){
				solving_sequence_as_byte[i] = solving_sequence.get(i);
				System.out.println(solving_sequence.get(i));
			
//			}
		}
		try {
			
			dos.write(solving_sequence_as_byte,0,solving_sequence_as_byte.length);
			dos.flush();
//					dos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** Returns an char array with the scanned colors */
	public char[] getScanResultVector() {	
		int data_available;
		int data_recieved = 0;
		//array the data inputstream will be read in
		byte[]scan_result_vector_as_byte = new byte[54];
		
		//desired format for scan result vector
		char[] scan_result_vector = new char[54];

		try {
			//ask input stream for data as long as 54 bytes are available
			do {
				data_available=	dis.read(scan_result_vector_as_byte, data_recieved, 54);
				data_recieved += data_available;
				System.out.println(data_recieved);
			}while(data_recieved < 54);

			//fill char array with translated byte values
			System.out.println("Scan result vector:");
			for (int i = 0; i < scan_result_vector_as_byte.length; i++) {
			
				switch(scan_result_vector_as_byte[i]){
				
				case 0: scan_result_vector[i] = 'R';
				break;
				case 1: scan_result_vector[i] = 'Y';
				break;
				case 2: scan_result_vector[i] = 'B';
				break;
				case 3: scan_result_vector[i] = 'G';
				break;
				case 4: scan_result_vector[i] = 'O';
				break;
				case 5: scan_result_vector[i] = 'W';
				break;
				default: System.out.println("nix da :(");
				break;
				}
			
				
				System.out.println(scan_result_vector[i]);
			}
		} catch (IOException e) {
			System.out.println("Can't communicate to NXT");
			e.printStackTrace();
		}
		return scan_result_vector;
	}

	/** Returns an array with the scanned colors */
	public byte[] getReferenceRgbValues() {
		byte[] recieved_rgb_value = new byte[18];
		try {
			// read 18 characters
			int recieved_byte = dis.read(recieved_rgb_value, 0, 54);
		} catch (IOException e) {
			System.out.println("Can't communicate to NXT");
			e.printStackTrace();
		}

		return recieved_rgb_value;
	}

	public void sendPressedCharToNXT() {
		// type on keyboard an send data char by char to NXT ;-)
		String writeToNXT = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		while (true) {
			try {
				writeToNXT = reader.readLine();
				dos.writeChars(writeToNXT);
				dos.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public byte[] getMode() {
		// mode 0 = scanning; mode 1 = solving
		int mode = 0;
		byte[] b = new byte[1];
		System.out.println("waiting for mode...");
		// dis = new DataInputStream(nxt_Comm.getInputStream());
		try {

			// tell Inputstream how many bytes it should read
			mode = dis.read(b, 0, 1);
			// mode = dis.read();
			// dis.close();
			// read fully method ??
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return b;
	}

	public void sendRGBCalibration(int[] ref_RGB_calibration) {
		// TODO Auto-generated method stub
		try {
		byte[]b = new byte[18];
		for(int i = 0; i < ref_RGB_calibration.length; i++){
			b[i] = (byte)ref_RGB_calibration[i];
			dos.write(b, 0, 18);
			dos.flush();
		}
		//write 18 bytes from byte array
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void sendSolvingSequenceLength(int sequence_length) {
		// TODO Auto-generated method stub
		byte[]length = new byte[1];
		length[0] =(byte) sequence_length;
		try {
			dos.write(length, 0, 1);
			dos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
}
