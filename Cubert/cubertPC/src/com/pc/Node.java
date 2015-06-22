package com.pc;

public class Node {

	// Node we came from
	private Node predecessor_node;
	
	// Hash value of the cube's current state
	private int state_hash;
	
	// Cost since start
	private int g_costs;
	
	// Estimation of cost to solved state
	private double h_costs;
	
	// Action we performed at the predecessor node to get to this current node
	private char action;

	public Node(int state_hash) {
		this.predecessor_node = null;
		this.state_hash = state_hash;
		this.g_costs = 0;
		this.h_costs = 0.0;
		this.action = '\u0000';
	}
	
	public Node(Node predecessor_node, int state_hash, int g_costs, double h_costs, char action) {
		this.predecessor_node = predecessor_node;
		this.state_hash = state_hash;
		this.g_costs = g_costs;
		this.h_costs = h_costs;
		this.action = action;
	}
	
	public double getTotalCosts() {
		
		return g_costs + h_costs;
	}
}
