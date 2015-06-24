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

	// Constructor for start node
	public Node(int state_hash) {
		this.predecessor_node = null;
		this.state_hash = state_hash;
		this.g_costs = 0;
		this.h_costs = 0.0;
		this.action = '\u0000';
	}
	
	// Constructor for further nodes
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

	public Node getPredecessor_node() {
		return predecessor_node;
	}

	public void setPredecessor_node(Node predecessor_node) {
		this.predecessor_node = predecessor_node;
	}

	public int getState_hash() {
		return state_hash;
	}

	public void setState_hash(int state_hash) {
		this.state_hash = state_hash;
	}

	public int getG_costs() {
		return g_costs;
	}

	public void setG_costs(int g_costs) {
		this.g_costs = g_costs;
	}

	public double getH_costs() {
		return h_costs;
	}

	public void setH_costs(double h_costs) {
		this.h_costs = h_costs;
	}

	public char getAction() {
		return action;
	}

	public void setAction(char action) {
		this.action = action;
	}
}
