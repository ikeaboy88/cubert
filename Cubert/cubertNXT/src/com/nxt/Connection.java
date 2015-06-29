package com.nxt;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.comm.USB;
import lejos.nxt.comm.USBConnection;

public class Connection {
	private USBConnection connection = null;
	private DataOutputStream dos = null;
	private DataInputStream dis = null;
	private BufferedReader bufferedReader = null; 	
	
	public void connectToPC() {
		LCD.drawString("Right BT->USB Verbindung", 0, 0);

//		if (Button.waitForAnyPress() == Button.ID_RIGHT) {
		LCD.drawString("Waiting for USB connection", 0, 1);
		connection = USB.waitForConnection();
//		}
		LCD.clear();
		LCD.drawString("connected", 0, 0);
//		Button.waitForAnyPress();
		dos = connection.openDataOutputStream();
		dis = getConnection().openDataInputStream();
		bufferedReader = new BufferedReader(new InputStreamReader(dis));
	}
	
	public USBConnection getConnection() {
		return connection;
	}
	
	public void disconnectFromPC() {
		try {
			connection.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/** send an String array character by character to the PC*/
	public void sendScanResultVector(char[] scan_result_vector) {
		LCD.clear();
		LCD.drawString("filling array..", 0, 0);

//		List<Integer> scan_result_vector_as_byte = new ArrayList<Integer>();
		byte[]scan_result = new byte[54];
		
		for(int i = 0; i < scan_result_vector.length; i++){
				switch(scan_result_vector[i]){

				case 'R' : //scan_result_vector_as_byte.add(0);
							scan_result[0] = 0;
					try {
						dos.write(scan_result, 0, 1);
						dos.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
							LCD.drawString("R", 0, 1);
					break; 
				case 'Y' : //scan_result_vector_as_byte.add(1);
							scan_result[0] = 1;
					try {
						dos.write(scan_result, 0, 1);
						dos.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
							LCD.drawString("Y", 0, 1);
					break; 
				case 'B' : //scan_result_vector_as_byte.add(2);
							scan_result[0] = 2;
					try {
						dos.write(scan_result, 0, 1);
						dos.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
							LCD.drawString("B", 0, 1);
					break; 
				case 'G' : //scan_result_vector_as_byte.add(3);
							scan_result[0] = 3;
					try {
						dos.write(scan_result, 0, 1);
						dos.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
							LCD.drawString("G", 0, 1);
					break; 
				case 'O' : //scan_result_vector_as_byte.add(4);
							scan_result[0] = 4;
					try {
						dos.write(scan_result, 0, 1);
						dos.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
							LCD.drawString("O", 0, 1);
					break; 
				case 'W' : //scan_result_vector_as_byte.add(5);
							scan_result[0] = 5;
					try {
						dos.write(scan_result, 0, 1);
						dos.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
							LCD.drawString("W", 0, 1);
					break; 				
				}
			
		}
		

		LCD.drawString("sending scan result to pc...", 1, 3);
		/*
		try {
			byte[]scan_result = new byte[scan_result_vector_as_byte.size()];
			for(int i = 0; i < scan_result_vector_as_byte.size(); i++){
				int ref = scan_result_vector_as_byte.get(i);
				scan_result[i] = (byte) ref;
			}
			dos.write(scan_result, 0, scan_result_vector_as_byte.size());
			dos.flush();
		} catch (IOException e) {
			LCD.drawString("Can't send data to PC", 1, 4);
			e.printStackTrace();
		}
		*/
	}

	/*
	//Because we can only send byte through stream, translate characters into bytes
	private List<Integer> translateScanResultVectorIntoByte(char[] char_sequence) {
		// TODO Auto-generated method stub
			for(Character color : char_sequence){
				switch(color){
				case 'R' : byte_sequence.add(0);
					break; 
				case 'Y' : byte_sequence.add(1);
					break; 
				case 'B' : byte_sequence.add(2);
					break; 
				case 'G' : byte_sequence.add(3);
					break; 
				case 'O' : byte_sequence.add(4);
					break; 
				case 'W' : byte_sequence.add(5);
					break; 				
				}
			
		}
		return byte_sequence;
	}*/

	public char[] getSolvingSequence(int solving_sequence_length) {
		LCD.clear();
		
		//dynamisches array?!
		byte[]solving_sequence_as_byte = new byte[solving_sequence_length];
		int data_available=0; 
		int data_recieved = 0;
		
		//when stop sign accurs, stop reading input stream!
//		for(int i = 0; i < solving_sequence_as_byte.length; i++){
			do{
				try {
					
					data_available = dis.read(solving_sequence_as_byte, data_recieved, solving_sequence_length);
					data_recieved += data_available;
					LCD.drawString("recieved:"+data_recieved, 0, 0);
				} catch (IOException e) {
					LCD.drawString("nix da", 0, 1);
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}while(data_recieved < solving_sequence_length);
			Button.waitForAnyPress();
//		}
		
		char[] solving_sequence = new char[solving_sequence_as_byte.length-1];
		
		//translate bytes into chars 
		//therefore ignore the last int, representing the stop sign. length-1
		for(int i = 0; i < solving_sequence_as_byte.length-1; i++){
//			for(Byte b: solving_sequence_as_byte){
				switch(solving_sequence_as_byte[i]){
				case 0: solving_sequence[i] = 't';
					break; 
				case 1: solving_sequence[i] = 'T';
					break; 
				case 2: solving_sequence[i] = 'd';
					break;
				case 3: solving_sequence[i] = 'D';
					break; 
				case 4: solving_sequence[i] = 'l';
					break; 
				case 5: solving_sequence[i] = 'L';
					break; 
				case 6: solving_sequence[i] = 'r';
					break; 
				case 7: solving_sequence[i] = 'R';
					break; 
				case 8: solving_sequence[i] = 'f';
					break; 
				case 9: solving_sequence[i] = 'F';
					break; 
				case 10: solving_sequence[i] = 'b';
					break; 
				case 11: solving_sequence[i] = 'B';
					break; 
				case -1: LCD.drawString("stop sign", 0, 1);
				}
//			}
		}
		return solving_sequence;
	}

	public void sendRGBCalibration(List<Integer> reference) {
		// TODO Auto-generated method stub
		LCD.clear();
		LCD.drawString("sending data to pc...", 1, 3);
		try {
			byte[]rgb_calibration_value = new byte[18];
			for(int i = 0; i < reference.size(); i++){
				int ref = reference.get(i);
				rgb_calibration_value[i] = (byte) ref;
			}
			dos.write(rgb_calibration_value, 0, reference.size());
			dos.flush();
		} catch (IOException e) {
			LCD.drawString("Can't send data to PC", 1, 4);
			e.printStackTrace();
		}
		
	}
		
	public void sendMode(int mode){
		try {
			byte[]b = new byte[1];
			b[0] = (byte) mode;
			dos.write(b, 0, 1);
			dos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int[][] safeReferenceRGBValues() {
		// TODO Auto-generated method stub
		LCD.clear();
		
		byte[] recieved_rgb_value = new byte[3];
		int[][]rgb_reference = new int[6][3];
		try {

			int rgb;
			int tmp_rgb;
			int data_available=0; 
			int data_recieved = 0;
			for(int [] j : rgb_reference){
				for (int i =0; i < 3; i++){
					
					 data_recieved = 0;
					do{
						data_available = dis.read(recieved_rgb_value, data_recieved, 3);
						data_recieved += data_available;
						LCD.drawString("data: "+data_recieved, 0, 4);
					}while(data_recieved < 3);
					if(recieved_rgb_value[i] < 0){
						tmp_rgb = -recieved_rgb_value[i];
						rgb = 255 - tmp_rgb;
					}else{
						rgb = recieved_rgb_value[i];
					}
					j[i] = rgb;
					
				}
			}
			//format values
			
//			int index = 0;
//				for (int i = 0; i < 6; i++){
//					
//					for(int j = i*3; j < 3+(i*3); j++){
//						index = j;
//						//is there is a byte overflow, convert the byte into int
//						if(recieved_rgb_value[j]<0){
//							//get positive value of overflow
//							int temp_rgb = -recieved_rgb_value[j];
//							//bring overflow value into range of 32bit Integer
//							rgb = 255-temp_rgb;
//						}
//						else{					
//							rgb = recieved_rgb_value[j];
//						}
//						
//						// writing values in two dimensional array range: [6][3]
//						// therefore modulo operation on j, so that range is between 0 and 2
//						if(j>=3){
//							index %= 3;
//						}
//						rgb_reference[i][index] = rgb;
//					}
//				}
		} catch (IOException e) {
			LCD.clear();
			LCD.drawString("ERROR while safing reference values!", 0, 1);
			System.out.println("Can't communicate to NXT");
			e.printStackTrace();
		}
		return rgb_reference;

	}

	public int getSolvingSequenceLength() {
		// TODO Auto-generated method stub
		byte[]b = new byte[1];
		try {
			dis.read(b, 0, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int sequence_length = b[0];
		return sequence_length;
	}

	public int getMode() {
		// TODO Auto-generated method stub
		byte[]b = new byte[1];
		try {
			dis.read(b, 0, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return b[0];
	}

	/*
	 * recieve keyboardinput just in time String recievedString = null;
	 * recievedString = dis.readChar(); LCD.clear();
	 * LCD.drawChar(recievedString, 1, 1); LCD.refresh();
	 */
}
