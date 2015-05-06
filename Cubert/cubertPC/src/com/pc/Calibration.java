package com.pc;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class Calibration {

	public static void main(String[] args) {

		Connection con = new Connection();
		con.connectToNXT();

		String reference_rgb = con.getReferenceRgbValues();
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
			Writer file_writer = new FileWriter(calibration);
//			for (int i = 0 ; i < reference_rgb.length ; i++ ){
				file_writer.write(reference_rgb);
				file_writer.close();
//			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
