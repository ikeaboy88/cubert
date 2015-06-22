package com.pc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.PriorityQueue;

// Theory: 				http://www.policyalmanac.org/games/aStarTutorial_de.html	
// Example project: 	https://code.google.com/p/jianwikis/wiki/AStarAlgorithmForPathPlanning

public class Solver {

	// Closed list - Contains all visited nodes (Key: cube state as hash, value: Node object)
	public Hashtable<Integer, Node> closed_list = new Hashtable<Integer, Node>();
	
	// Open list - Contains all nodes we know, but not have visited yet (Key: cube state as hash, value: Node object)
	public Hashtable<Integer, Node> open_list = new Hashtable<Integer, Node>();
	
	// Nodes from open_list in a sorted order (cheapest on top)
	public PriorityQueue<Node> priority_queue = new PriorityQueue<Node>(new NodeComparator());
	
	// Constructor
	public Solver() {
		
	}
	
	public List<Character> calculateSolvingSequence(int start_hash, int solved_hash) {

		List<Character> solving_sequence = new ArrayList<Character>();
		
		// Initialize start node with start_hash

		// Initialize solved node with null
		
		
		// Add start state to the open list
		
		// Add start state to priority queue
		
		// loop - As long as open list contains nodes
		
			// Get first element of the priority queue (cheapest)
		
			// Remove the according node from the open list
		
			// IF node is the solved state, break loop
		
			// ELSE node is not the solved state, ...
				// Add node to closed list
		
				// Expand node - Create nodes for every possible subsequent state
		
				// loop - For every new node (neighbours)
					
					// IF node is not in closed list?
		
						// Calculate cost g from node to neighbour
		
						// IF node is NOT in opened list
							// create new node (hash, g, ,...)
							// set predecessor_node (to expanding node)
							// put node to opened list
							// put node in priority queue
						// ELSE IF (in open list - have already been here XD ) check if current path costs < node costs
							// set cheaper predecessor (to that node)
							// set g cost
							// calc new h cost to target
							
		
		return solving_sequence;
	}
}
