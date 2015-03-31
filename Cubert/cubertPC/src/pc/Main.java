package pc;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

public class Main {
	public NXTComm nxtComm = null;
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
		try 
		{
			nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.USB);
			nxtInfo = nxtComm.search(null);
			nxtComm.open(nxtInfo[0]);
			System.out.println("Connected to " + nxtInfo[0].name);
		} catch (NXTCommException e) {
			System.out.println("can't connect to NXT");
			e.printStackTrace();
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
			try 
			{
				Thread.currentThread().sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dis = new DataInputStream(nxtComm.getInputStream());
			
			if (nxtComm.available() > 0) 
			{
				recievedInt = dis.readInt();
				System.out.println("" + recievedInt);
			} else 
			{
				System.out.println("nothing to read");
			}
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
