package pc;

import lejos.nxt.Motor;
import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

public class Main {
	
public static void main(String[]args){
	
	connect();
}

//connect to NXT via USB
private static void connect() {
	NXTComm nxtComm; 
	NXTInfo[] nxtInfo = null;
	try {
		System.out.println("Trying to connect...");
		nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.USB);
		nxtInfo =nxtComm.search(null);
		nxtComm.open(nxtInfo[0]);
	} catch (NXTCommException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("Connected to " + nxtInfo[0].name);
}
}
