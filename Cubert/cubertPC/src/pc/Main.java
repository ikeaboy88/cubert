package pc;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTCommLogListener;
import lejos.pc.comm.NXTConnector;
import lejos.pc.comm.NXTInfo;

public class Main {
	//use this connector for opening multi in-/ outputstreams
	NXTConnector nxtComm = new NXTConnector();
	public NXTInfo[] nxtInfo = null;
	DataOutputStream dos = null;
	DataInputStream dis = null;
	int sendInt = 1234;

	public static void main(String[] args) 
	{
		Main main = new Main();
		main.connectToNXT();
		main.sendDataToNXT(main.sendInt);
		main.recieveDataFromNXT();
		main.closeStreams();
	}
	
	public void connectToNXT() 
	{
		System.out.println("Trying to connect...");
		nxtComm.addLogListener(new NXTCommLogListener(){

			public void logEvent(String message) {
				System.out.println("USBSend Log.listener: "+message);
			}
			public void logEvent(Throwable throwable) {
				System.out.println("USBSend Log.listener - stack trace: ");
				 throwable.printStackTrace();
			}
		} 
		);
		if (!nxtComm.connectTo("usb://")){
			System.err.println("No NXT found using USB");
			System.exit(1);
		}
	}

	public void sendDataToNXT(int sendInt) 
	{
		try 
		{
			dos = new DataOutputStream(nxtComm.getOutputStream());
			dos.writeInt(sendInt);
			// send data through stream
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//return type should be int, void only for testing
	public void recieveDataFromNXT() 
	{
		int recievedInt = 0;
		try 
		{
			dis = new DataInputStream(nxtComm.getInputStream());
				recievedInt = dis.readInt();
				System.out.println("Recieved Data: "+recievedInt);
		} catch (IOException e) {
			System.out.println("Can't communicate to NXT");
			e.printStackTrace();
		}
	}

	public void closeStreams() 
	{
		try 
		{
			dis.close();
			dos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendPressedCharToNXT() 
	{
		// type on keyboard an send data char by char to NXT ;-)
		String writeToNXT = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) 
		{
			try 
			{
				writeToNXT = reader.readLine();
				dos.writeChars(writeToNXT);
				dos.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
