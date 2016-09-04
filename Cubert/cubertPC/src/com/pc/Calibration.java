package com.pc;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;

public class Calibration {

	Connection con;
	public Calibration(Connection con){
		this.con = con;
	}
		
	public void calibrate() {

		byte [] reference_rgb = con.getReferenceRgbValues();
		File calibration = new File("src/calibration/reference_RGB_values.txt");
		if (!calibration.exists()) {

			try {
				calibration.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//when file already exist, write in file 
		try {
			//empty current calibration file 
			Writer deleter = new FileWriter(calibration);
			deleter.write("");
			deleter.close();
			
			//fill calibration file with new calibration values
			Writer file_writer = new FileWriter(calibration);
			String sequence;
			//go through byte buffer, read received reference rgb values and write them in reference_RGB_values.txt
			for (int i = 0 ; i < reference_rgb.length ; i++ ){
			
				//is there is a byte overflow, convert the byte into int
				if(reference_rgb[i]<0){
					//get positive value of overflow
					int temp_rgb = -reference_rgb[i];
					//bring overflow value into range of 32bit Integer
					int rgb = 255-temp_rgb;
					//add value to sequence written in .txt file
					sequence = rgb+"\n";
				}
				else{					
					sequence = reference_rgb[i]+"\n";
				}
				file_writer.write(sequence);			
			}
			file_writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public int[] readCalibrationFromFile() {
		// TODO Auto-generated method stub
		int[] rgb_reference_vector = new int[18];
			//initialize file reader which reads .txt file
			BufferedReader file_reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("../../calibration/reference_RGB_values.txt")));
			
			try {
				//reads whole line in .txt file
				String line = file_reader.readLine();
				//read until empty line
				//while(line!=null){
					//for(int[]rgb_values :rgb_reference){
						for(int i = 0 ; i < rgb_reference_vector.length; i++){
							//parse readed line into intger and put it into array
							rgb_reference_vector[i] = Integer.parseInt(line);
							//read next line
							System.out.println("line: "+line);
							System.out.println("Array: "+rgb_reference_vector[i]);
							line = file_reader.readLine();
						}
					//}
							//}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return rgb_reference_vector;
		
		
	}
}
