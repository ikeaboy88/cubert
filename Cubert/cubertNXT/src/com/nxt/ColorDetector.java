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
	private int redValue;
	private int greenValue;
	private int blueValue;

	private int[] red_rgb_ref = { 33, 0, 0 };
	private int[] green_rgb_ref = { 1, 0, 10 };
	private int[] blue_rgb_ref = { 0, 0, 17 };
	private int[] white_rgb_ref = { 53, 44, 54 };
	private int[] yellow_rgb_ref = { 56, 73, 0 };
	private int[] orange_rgb_ref = { 53, 13, 0 };

	private int[][] rgb_ref = { red_rgb_ref, green_rgb_ref, blue_rgb_ref,
			white_rgb_ref, yellow_rgb_ref, orange_rgb_ref };

	ColorHTSensor colorSensor;
	final static int INTERVAL = 200; // milliseconds

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

	public void detectColor() {
		String r = "R";
		String g = "G";
		String b = "B";
		LCD.clear();
		
		LCD.drawString(r, 0, 5);
		LCD.drawInt(colorSensor.getRGBNormalized(Color.RED), 1, 5);
		
		LCD.drawString(g, 5, 5);
		LCD.drawInt(colorSensor.getRGBNormalized(Color.GREEN), 6, 5);
		
		LCD.drawString(b, 10, 5);
		LCD.drawInt(colorSensor.getRGBNormalized(Color.BLUE), 11, 5);

		redValue = colorSensor.getRGBNormalized(Color.RED);
		greenValue = colorSensor.getRGBNormalized(Color.GREEN);
		blueValue = colorSensor.getRGBNormalized(Color.BLUE);

		int[] comparison = { redValue, greenValue, blueValue };
		// old distance
		double oldDistance = 1000;
		// new distance
		double distance = 0;
		int index = 99;
		
		
		for (int i = 0; i < rgb_ref.length; i++) {

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
		case 0:
			LCD.drawString("Rot", 3, 3);
			break;
		case 1:
			LCD.drawString("Gruen", 3, 3);
			break;
		case 2:
			LCD.drawString("Blau", 3, 3);
			break;
		case 3:
			LCD.drawString("Weiﬂ", 3, 3);
			break;
		case 4:
			LCD.drawString("Gelb", 3, 3);
			break;
		case 5:
			LCD.drawString("Orange", 3, 3);
			break;
		default:
			LCD.drawString("None", 3, 3);
			break;
		}

		LCD.refresh();
		Delay.msDelay(1000);

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
}