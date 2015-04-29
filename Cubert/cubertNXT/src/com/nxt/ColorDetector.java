package com.nxt;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorHTSensor;
import lejos.robotics.Color;
import lejos.util.Delay;

/**
 * Detection of the cube's 6 different colors with the HiTechnic Color Sensor V1
 */
public class ColorDetector extends ColorHTSensor {

	// colors of the cube surface
	public enum Colors {
		RED, GREEN, BLUE, WHITE, YELLOW, ORANGE, NONE;
	}
	
	// state for the color
	private Enum<Colors> color_state;
	
	// show or hide output on the NXT display
	private boolean debug;

	// reference values for each color (with light from top, Dayan II Guhong speed cube)
	private final int[] red_rgb_ref = { 255, 0, 0 };
	private final int[] green_rgb_ref = { 110, 190, 255 };
	private final int[] blue_rgb_ref = { 40, 15, 255 };
	private final int[] white_rgb_ref = { 255, 255, 255 };
	private final int[] yellow_rgb_ref = { 160, 255, 0 };
	private final int[] orange_rgb_ref = { 255, 135, 0 };
	
	// nested array with all reference array from above
	private final int[][] rgb_ref = { red_rgb_ref, green_rgb_ref, blue_rgb_ref,
			white_rgb_ref, yellow_rgb_ref, orange_rgb_ref };


	/**
	 * Default constructor without debug
	 */
	public ColorDetector(SensorPort port) {
		super(port);
		this.color_state = Colors.NONE;
		this.debug = false;
	}

	/**
	 * Constructor with debug (output on NXT display) option
	 * @param debug If true debug is activated
	 */
	public ColorDetector(SensorPort port, Boolean debug) {
		super(port);
		this.color_state = Colors.NONE;
		this.debug = debug;
	}

	/**
	 * Detects the color of the surface the color sensor is currently above.
	 * Uses the average of multiple readings and then returns the reference color with the shortest euklidian distance.
	 * @param duration How long the the complete measuring process should take
	 * @param iterations How many measurements will be executed within that duration
	 * @return Detected color as char (r, g, b, w, y, o, x)
	 */
	public char detectColor(long duration, int iterations) {
		
		// initialize detected color with no color
		char detected_color = 'x';
		// initialize RGB array with 0 values
		int[] rgb_vector =  {0, 0, 0};
		// fill vector with averaged RGB values from a measurement
		rgb_vector = this.getAverageRgbVector(duration, iterations);

		// initialize high value for the previous euklidian distance
		double previous_distance = 1000;
		// initialize low value for the current euklidian distance
		double distance = 0;
		int color_index = -1;
		
		// calculate euklidian distance between the measured RGB vector and all the reference RGB vectors
		for (int i = 1; i <= rgb_ref.length; i++) {

			// get euklidian distance for the current two RGB vectors
			distance = calculateEuklidianDistance(rgb_ref[i-1], rgb_vector);
			// when new distance is shorter than the previous one..
			if (distance < previous_distance) {
				// cache distance and index
				previous_distance = distance;
				color_index = i;
			}
		}
		
		// return detected color and set the color state
		switch (color_index) {
		case 1:
			this.setColorState(Colors.RED);
			detected_color = 'r';
			break;
		case 2:
			this.setColorState(Colors.GREEN);
			detected_color = 'g';
			break;
		case 3:
			this.setColorState(Colors.BLUE);
			detected_color = 'b';
			break;
		case 4:
			this.setColorState(Colors.WHITE);
			detected_color = 'w';
			break;
		case 5:
			this.setColorState(Colors.YELLOW);
			detected_color = 'y';
			break;
		case 6:
			this.setColorState(Colors.ORANGE);
			detected_color = 'o';
			break;
		default:
			this.setColorState(Colors.NONE);
			detected_color = 'x';
			break;
		}
		
		// show detected colors and RGB values on NXT display
		if (this.debug) {
			try {
				LCD.drawString("----------------", 0, 5);
				LCD.drawString("Color:", 0, 6);
				LCD.drawString("      ", 7, 6);
				LCD.drawString(this.getColorState().toString(), 7, 6);
				LCD.drawString("r", 0, 7);
				LCD.drawString("   ", 1, 7);
				LCD.drawInt(rgb_vector[0], 1, 7);
				LCD.drawString("g", 5, 7);
				LCD.drawString("   ", 6, 7);
				LCD.drawInt(rgb_vector[1], 6, 7);
				LCD.drawString("b", 10, 7);
				LCD.drawString("   ", 11, 7);
				LCD.drawInt(rgb_vector[2], 11, 7);
				LCD.refresh();
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("BAD ARRAY INDEX - " + e.getMessage());
			}
		}
		return detected_color;
	}

	/**
	 * Calibrates the white and black values of the color sensor and stores it on the sensor persistently.
	 */
	public void calibrate() {

		LCD.drawString("calibrate colorsensor", 0, 1);
		// Calibrate white value
		LCD.drawString("white", 0, 2);
		Button.waitForAnyPress();
		this.initWhiteBalance();
		LCD.clear();
		
		// Calibrate black value
		LCD.drawString("black", 0, 3);
		Button.waitForAnyPress();
		this.initBlackLevel();
		LCD.clear();
	}

	/**
	 * Calculates the euklidian distance of two input vectors
	 * 
	 * @param reference
	 *            Vector with integer values of a reference point
	 * @param comparison
	 *            A second vector with integer values of the same length
	 * @return Euklidian distance between both vectors / 0 If vectors don't have
	 *         the same length
	 */
	private static double calculateEuklidianDistance(int[] reference, int[] comparison) {
		double distance = 0;
		if (reference.length == comparison.length) {
			for (int i = 0; i < reference.length; i++) {
				distance += Math.pow(reference[i] - comparison[i], 2);
			}
		} else {
			throw new IllegalArgumentException();
		}
		return Math.sqrt(distance);
	}
	
	/**
	 * Executes a row of normalized RGB measurements and returns an array with the averaged RGB values
	 * @param duration How long the the complete measuring process should take
	 * @param iterations How many measurements will be executed within that duration
	 * @return 3-dimensional array of the averaged integer RGB values (0 - 255)
	 */
	private int[] getAverageRgbVector(long duration, int iterations) {
		
		// initialize RGB array with 0 values
		int[] rgb_vector = {0, 0, 0};
		
		// only if parameters are positive
		if (duration > 0 && iterations > 0) {

			for (int i = 0; i < iterations; i++) {

				// read normalized RGB values and cumulate them in the vector slots
				rgb_vector[0] += this.getRGBNormalized(Color.RED);
				rgb_vector[1] += this.getRGBNormalized(Color.GREEN);
				rgb_vector[2] += this.getRGBNormalized(Color.BLUE);
				
				// wait until next reading
				Delay.msDelay(duration/iterations);
			}
			
			// calculate average in every vector slot
			rgb_vector[0] = rgb_vector[0]/iterations;
			rgb_vector[1] = rgb_vector[1]/iterations;
			rgb_vector[2] = rgb_vector[2]/iterations;
		}
		return rgb_vector;
	}
	
	// Setter
	public void setColorState(Colors c) {
		this.color_state = c;
	}
	
	// Getter
	public Enum<Colors> getColorState() {
		return this.color_state;
	}
}