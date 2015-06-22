package com.pc;

public class Node {

	// Node we came from
	private Node predecessor_node;
	
	// Cost since start
	private int g_costs;
	
	// Estimation of cost to solved state
	private double h_costs;
	
	// Action we performed at the predecessor node to get to this current node
	private char action;

	public double getTotalCosts() {
		
		return g_costs + h_costs;
	}
}
