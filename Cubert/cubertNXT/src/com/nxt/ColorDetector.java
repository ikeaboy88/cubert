package com.nxt;


import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorHTSensor;
import lejos.robotics.Color;
import lejos.util.Delay;

/**
 * For testing the HiTechnic color sensor (see lejos.nxt.addon.ColorHTSensor).
 * @author BB
 */
public class ColorDetector {

	public enum CUBIECOLOR{
		NONE, RED, ORANGE, YELLOW, WHITE, GREEN, BLUE, BLACK;
	}
	
	// Initial state for the sensor
	private CUBIECOLOR color_state;
	// Display the current state
	private boolean debug;
	private int redValue;
	private int greenValue;
	private int blueValue;
	private int sumColorValue;
		
	ColorHTSensor colorSensor;
	final static int INTERVAL = 200; // milliseconds

	public ColorDetector(){
		//set initial colorstate
		colorSensor = new ColorHTSensor(SensorPort.S3);
		this.color_state = CUBIECOLOR.NONE;
		this.debug = false;
	}
	
	public ColorDetector(Boolean debug){
		//set initial colorstate
		colorSensor = new ColorHTSensor(SensorPort.S3);
		this.color_state = CUBIECOLOR.NONE;
		this.debug = debug;
	}
	
	public void detectColor(){
		String color = "Color";
		String r = "R";
		String g = "G";
		String b = "B";
		
		String[] colorNames = {"Red", "Green", "Blue", "Yellow", "Magenta", "Orange",
				             "White", "Black", "Pink", "Gray", "Light gray", "Dark Gray", "Cyan"			
		};
		
		
		//scan until colorID remains unchanged...
	/*	while(_colorID != colorSensor.getColorID()) {
			
			switch(colorSensor.getColorID()){
			case 0: setColor_state(CUBIECOLOR.RED);
					break;
			case 1: setColor_state(CUBIECOLOR.GREEN);
					break;
			case 2: setColor_state(CUBIECOLOR.BLUE);
					break;
			case 3: setColor_state(CUBIECOLOR.YELLOW);
					break;
			case 4: setColor_state(CUBIECOLOR.BLUE);
					break;
			case 5: setColor_state(CUBIECOLOR.ORANGE);
					break; 
			case 6: setColor_state(CUBIECOLOR.WHITE);
					break;
			case 7: setColor_state(CUBIECOLOR.BLACK);
					break;
			default:setColor_state(CUBIECOLOR.NONE);
					break;
			}
	
			*/
			
		//green B:10-25 blue B:26-50
		//scan again if sum of rgb values spread in a range of ten around the sum of the current rgb values
		while(sumColorValue <= ( colorSensor.getRGBComponent(Color.RED)+colorSensor.getRGBComponent(Color.GREEN)+colorSensor.getRGBComponent(Color.BLUE)) - 10 ||sumColorValue >= ( colorSensor.getRGBComponent(Color.RED)+colorSensor.getRGBComponent(Color.GREEN)+colorSensor.getRGBComponent(Color.BLUE)) + 10) {
			LCD.clear();
		

			LCD.drawString(color, 0, 3);
			LCD.drawInt(colorSensor.getColorID(),7,3);
			
			LCD.drawString(colorNames[colorSensor.getColorID()], 0, 4);
			LCD.drawString(r, 0, 5);
			LCD.drawInt(colorSensor.getRGBRaw(Color.RED),1,5);
			LCD.drawString(g, 5, 5);
			LCD.drawInt(colorSensor.getRGBRaw(Color.GREEN),6,5);
			LCD.drawString(b, 10, 5);
			LCD.drawInt(colorSensor.getRGBRaw(Color.BLUE),11,5);
			
			LCD.refresh();
			redValue = colorSensor.getRGBComponent(Color.RED);
			greenValue = colorSensor.getRGBComponent(Color.GREEN);
			blueValue = colorSensor.getRGBComponent(Color.BLUE);
			//get the sum of all r, g an b values
			sumColorValue = redValue + greenValue + blueValue;
			Delay.msDelay(1000);
			
		}
	}
	
	//getter
	public Enum<CUBIECOLOR> getColor_state() {
		return color_state;
	}

	//setter
	public Enum<CUBIECOLOR> setColor_state(CUBIECOLOR color_state) {
		if (debug) {
			LCD.drawString("Color: " + color_state, 1,1);
		}
		return this.color_state = color_state;
	}
	
	public void calibrate(){
	LCD.drawString("calibrate colorsensor", 0, 1);
	LCD.drawString("white", 0, 2);
	Button.waitForAnyPress();
	LCD.clear();
	// weiﬂ
	colorSensor.initWhiteBalance();
	//colorsensor.calibrateHigh();
	LCD.drawString("black", 0, 3);
	Button.waitForAnyPress();
	colorSensor.initBlackLevel();
	LCD.clear();
	//colorsensor.calibrateLow();
	}
}