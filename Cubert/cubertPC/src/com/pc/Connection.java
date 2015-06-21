package com.pc;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
		// String[] solvingSequence = new String[3];
		int i = 23;
		try {

			// for (int i = 0; i < sequence.length; i++){
			// solvingSequence[i] = Character.toString(sequence[i]);
			// dos.writeBytes(solvingSequence[i]);
			// System.out.println(solvingSequence[i]);
			// }
			dos.writeInt(i);
			// send data through stream
			dos.flush();
			// dos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** Returns an array with the scanned colors */
	public char[] getScanResultVector() {
		String recievedString = null;
		char[] scan_result_vector = new char[54];

		try {

			recievedString = bufferedReader.readLine();
			// System.out.println("Recieved String: " +recievedString);
			recievedString.toCharArray();

			for (int i = 0; i < recievedString.length(); i++) {
				scan_result_vector[i] = recievedString.charAt(i);

				// System.out.println("Recieved Data: " +scan_result_vector[i]);
			}
			// dis.close();
		} catch (IOException e) {
			System.out.println("Can't communicate to NXT");
			e.printStackTrace();
		}

		return scan_result_vector;
	}

	/** Returns an array with the scanned colors */
	public byte[] getReferenceRgbValues() {
		byte[] recieved_rgb_value = new byte[18];
		// String recieved_rgb_value = null;
		try {

			// read 18 characters
			// recieved_rgb_value += bufferedReader.readLine();
			int recieved_byte = dis.read(recieved_rgb_value, 0, 18);
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
		}
		//write 18 bytes from byte array
			dos.write(b, 0, b.length);
			dos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
