package com.pc;

public class Main {

	public static void main(String[] args) 
	{
		Connection connect_PC = new Connection();
		connect_PC.connectToNXT();
		connect_PC.getScanResultVector();
	}
}
