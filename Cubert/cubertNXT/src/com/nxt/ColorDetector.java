package com.nxt;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorHTSensor;
import lejos.robotics.Color;
import lejos.util.Delay;

/**
 * For testing the HiTechnic color sensor (see lejos.nxt.addon.ColorHTSensor).
 * 
 * @author BB
 */
public class ColorDetector {

	public enum CUBIECOLOR {
		NONE, RED, ORANGE, YELLOW, WHITE, GREEN, BLUE, BLACK;
	}
	// Initial state for the sensor
	private CUBIECOLOR color_state;
	// Display the current state
	private boolean debug;

	private int[] red_rgb_ref = { 255, 0, 0 };
	private int[] green_rgb_ref = { 110, 190, 255 };
	private int[] blue_rgb_ref = { 40, 15, 255 };
	private int[] white_rgb_ref = { 255, 255, 255 };
	private int[] yellow_rgb_ref = { 160, 255, 0 };
	private int[] orange_rgb_ref = { 255, 135, 0 };

	private int[][] rgb_ref = { red_rgb_ref, green_rgb_ref, blue_rgb_ref,
			white_rgb_ref, yellow_rgb_ref, orange_rgb_ref };

	ColorHTSensor colorSensor;

	public ColorDetector() {
		// set initial colorstate
		colorSensor = new ColorHTSensor(SensorPort.S3);
		this.color_state = CUBIECOLOR.NONE;
		this.debug = false;
	}

	public ColorDetector(Boolean debug) {
		// set initial colorstate
		colorSensor = new ColorHTSensor(SensorPort.S3);
		this.color_state = CUBIECOLOR.NONE;
		this.debug = debug;
	}

	public int detectColor() {
		LCD.clear();

		int[] rgb_vector = this.calculateAverageRgbVector(5, 200);
		
		LCD.drawString("R", 0, 5);
		LCD.drawInt(rgb_vector[0], 1, 5);
		
		LCD.drawString("G", 5, 5);
		LCD.drawInt(rgb_vector[1], 6, 5);
		
		LCD.drawString("B", 10, 5);
		LCD.drawInt(rgb_vector[2], 11, 5);


		int[] comparison = { rgb_vector[0], rgb_vector[1], rgb_vector[2] };
		// old distance
		double oldDistance = 1000;
		// new distance
		double distance = 0;
		int index = -1;
		
		
		for (int i = 1; i <= rgb_ref.length; i++) {

			// get distance to actual color value
			distance = calculateEuklidianDistance(rgb_ref[i], comparison);
			// when the calculated distance to new color is shorter than to
			// further measured distances: keep it!
			if (distance < oldDistance) {
				oldDistance = distance;
				index = i;
			}
		}
		switch (index) {
		case 1:
			LCD.drawString("Rot", 3, 3);
			break;
		case 2:
			LCD.drawString("Gruen", 3, 3);
			break;
		case 3:
			LCD.drawString("Blau", 3, 3);
			break;
		case 4:
			LCD.drawString("Weiﬂ", 3, 3);
			break;
		case 5:
			LCD.drawString("Gelb", 3, 3);
			break;
		case 6:
			LCD.drawString("Orange", 3, 3);
			break;
		default:
			LCD.drawString("None", 3, 3);
			break;
		}
		LCD.refresh();
		
		return index;
	}

	// getter
	public Enum<CUBIECOLOR> getColor_state() {
		return color_state;
	}

	// setter
	public Enum<CUBIECOLOR> setColor_state(CUBIECOLOR color_state) {
		if (debug) {
			LCD.drawString("Color: " + color_state, 1, 1);
		}
		return this.color_state = color_state;
	}

	public void calibrate() {
		LCD.drawString("calibrate colorsensor", 0, 1);
		LCD.drawString("white", 0, 2);
		Button.waitForAnyPress();
		LCD.clear();
		// weiﬂ
		colorSensor.initWhiteBalance();
		LCD.drawString("black", 0, 3);
		Button.waitForAnyPress();
		colorSensor.initBlackLevel();
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
	
	private int[] calculateAverageRgbVector(int iterations, long duration) {
		
		int r = 0;
		int g = 0;
		int b = 0;
		
		int[] rgb_vector = {r, g, b};
		
		for (int i = 0; i < iterations; i++) {
			r += colorSensor.getRGBNormalized(Color.RED);
			g += colorSensor.getRGBNormalized(Color.GREEN);
			b += colorSensor.getRGBNormalized(Color.BLUE);
			Delay.msDelay(duration/iterations);
		}
		
		rgb_vector[0] = r/iterations;
		rgb_vector[1] = g/iterations;
		rgb_vector[2] = b/iterations;
		
		return rgb_vector;
	}
}