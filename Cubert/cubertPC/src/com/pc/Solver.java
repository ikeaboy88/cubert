package com.pc;

import java.util.Hashtable;
import java.util.PriorityQueue;

// Theory: 				http://www.policyalmanac.org/games/aStarTutorial_de.html	
// Example project: 	https://code.google.com/p/jianwikis/wiki/AStarAlgorithmForPathPlanning

public class Solver {

	// Closed list - Contains all visited nodes (Key: cube state as hash?!, value: Node object)
	public Hashtable<char[][], Node> closed_list = new Hashtable<char[][], Node>();
	
	// Open list - Contains all nodes we know, but not have visited yet (Key: cube state as hash?!, value: Node object)
	public Hashtable<char[][], Node> open_list = new Hashtable<char[][], Node>();
	
	// Nodes from open_list in a sorted order (cheapest on top)
	public PriorityQueue<Node> priority_queue = new PriorityQueue<Node>(new NodeComparator());
	
	// Constructor
	public Solver() {
		
	}
}
