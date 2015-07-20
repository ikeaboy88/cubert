package com.pc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

// Theory: 					http://www.policyalmanac.org/games/aStarTutorial_de.html	
// Example project: 		https://code.google.com/p/jianwikis/wiki/AStarAlgorithmForPathPlanning

// Ideas for improvements	https://www.cs.princeton.edu/courses/archive/fall06/cos402/papers/korfrubik.pdf

// - Count number of created nodes for comparison
// - Clone cube object in constructor and use it's initial state instead of revertCubePermutations() in every iteration
// - Implement actions for 180 degree turn of each face
// - Eliminate all subsequent actions that would result in the predecessing state (no L after an l turn, ...)
// - Test variations of the heuristic
// - Implement IDA*
// - Use pattern databases

public class Solver {

	// Closed list - Contains all visited nodes (Key: cube state as hash, value: Node object)
	public Hashtable<Integer, Node> closed_list = new Hashtable<Integer, Node>();
	
	// Open list - Contains all nodes we know, but not have visited yet (Key: cube state as hash, value: Node object)
	public Hashtable<Integer, Node> open_list = new Hashtable<Integer, Node>();
	
	// Nodes from open_list in a sorted order (cheapest on top)
	public PriorityQueue<Node> priority_queue = new PriorityQueue<Node>(new NodeComparator());
	
	public Cube cube;
	
	char[][] predecessor_state;
	public int state_hash;
	
	private char[] actions = new char[]{'t', 'd', 'l', 'r', 'f', 'b', 'T', 'D', 'L', 'R', 'F', 'B','0','1','2','3','4','5'};
	
	public Stack<Node> solution_nodes_stack;
	public Stack<Node> path_nodes_stack;
	
	public Node solved_node;
	public Node predecessor_node;
	public Node current_node;
	
	List<Node> neighbours = new ArrayList<Node>();
	
	public long counter_nodes = 0;
	public long counter_improved_nodes = 0;
	public int counter_tree_depth = 0;
	
	// Constructor
	public Solver(Cube cube) {
		this.cube = cube;
	}
	
	public List<Character> calculateSolvingSequence() {

		List<Character> solving_sequence = new ArrayList<Character>();
		
		// Get hash value of the cube's solved state
		int cube_solved_hash = cube.hashCubeState(cube.cube_solved);

		// Initialize start node with cube's initial state
		char[][] cube_start_state = cube.cube_scrambled;
		int cube_start_hash = cube.hashCubeState(cube_start_state);
		Node start_node = new Node(cube_start_hash);
		
		/** 1 */
		// Add start node to the open list and to the priority queue
		System.out.println("Number of nodes created: " + counter_nodes++);
		open_list.put(cube_start_hash, start_node);
		priority_queue.add(start_node);
		
		// Initialize solved node with null
		solved_node = null;
		
		/** 2 */
		// loop - As long as open list contains nodes
		while (open_list.size() > 0)
		{
			/** 2a */
			// Get first (cheapest) node from priority queue and remove it (from open list AND priority queue)
			current_node = priority_queue.poll();
			open_list.remove(current_node.getState_hash());
			
			/** DEBUG - get predecessor node from current node */
			state_hash = current_node.getState_hash();
			predecessor_node = current_node.getPredecessor_node();
			
			permuteCubeFromStartToCurrentNode(cube_start_state, current_node);
			
			// IF current node holds the solved state: Set current node as solved node and break the loop
			if (current_node.getState_hash() == cube_solved_hash)
			{
				solved_node = current_node;
				closed_list.put(cube_solved_hash, solved_node);
				break;
			} 
			// ELSE node is not the solved state, ...
			else
			{
				// Add current node to closed list
				closed_list.put(current_node.getState_hash(), current_node);
				
				// Bring cube state up to date with current node
				//cube.permuteCube(current_node.getAction());
				//char[][] cached_state = cube.getCube_scrambled();
				
				// Expand node - Create nodes for every subsequent state of the cube's current state
				List<Node> neighbour_nodes = this.getNeighbourNodes(current_node, cube, actions);
		
				// loop - For every new node (neighbours)
				for (Node neighbour_node : neighbour_nodes)
				{
					// IF neighbour is not in closed list?
					Node visited_node = closed_list.get(neighbour_node.getState_hash());
					if (visited_node == null)
					{
						// UNCLEAR if needed: Calculate cost g from node to neighbour
						
						// IF neighbour is NOT in opened list
						Node known_node = open_list.get(neighbour_node.getState_hash());
						if (known_node == null)
						{
							// UNCLEAR: already done in getNeighbourNodes (not efficient)
							// create new node (hash, g, ,...)
							// set predecessor_node (to expanding node)
							
							// Add node to opened list and priority queue
							open_list.put(neighbour_node.getState_hash(), neighbour_node);
							priority_queue.add(neighbour_node);
							System.out.println("neue unbekannte node in open list aufgenommen - Count: " + counter_nodes++);
						}
						// ELSE IF (neighbour is already in open list, check if new path is better)
						else if (neighbour_node.getG_costs() < known_node.getG_costs())
						{
							// set the new neighbour as predecessor (because path is better)
							known_node.setPredecessor_node(current_node);
							// set g cost (because cheaper)
							known_node.setG_costs(neighbour_node.getG_costs());
							// calc new h cost to target
							known_node.setH_costs(neighbour_node.getH_costs());
							known_node.setAction(neighbour_node.getAction());
							// update the modified node inside the priority queue
							priority_queue.remove(known_node);
							priority_queue.add(known_node);
							
							System.out.println("!! Node bereits bekannt - Weg dort hin verbessert !! - Count improved: " + counter_improved_nodes++);
						}
						
					}
				}
			}
			revertCubePermutations(cube, current_node);			
		}
		// Open list empty or solved node found
		
		// Get solving sequence
		if(solved_node != null)
		{
			System.out.println();
			System.out.println("Number of nodes: " + counter_nodes);
			System.out.println("Improved nodes: " + counter_improved_nodes);
			System.out.println("Tree depth: " + counter_tree_depth);
			//get solved_node and fill array from top to bottom
			solution_nodes_stack = new Stack<Node>();
			List<Node> solution_nodes_list = new ArrayList<Node>();
			Node parent_node = solved_node;

			// As long as a node has an predecessor
			while (parent_node != null)
			{
				//.. fill the stack with those nodes
				solution_nodes_stack.push(parent_node);
				parent_node = parent_node.getPredecessor_node();
			}
			while (solution_nodes_stack.size() > 0 )
			{
				solution_nodes_list.add(solution_nodes_stack.pop());
			}
			for (Node node : solution_nodes_list) {
				solving_sequence.add(node.getAction());
			}
		}
		return solving_sequence;
	}
	
	private void revertCubePermutations(Cube cube, Node current_node) {

		Node current = current_node;
		
		do {
			cube.permuteCube(current.getAction());
			cube.permuteCube(current.getAction());
			cube.permuteCube(current.getAction());
			
			current = current.getPredecessor_node();
			
		} while (current != null); 
	}

	private List<Node> getNeighbourNodes(Node current_node, Cube cube, char[] actions) {
		
		neighbours.clear();
		
		for (int i = 0; i < actions.length; i++) {
			
			// Simulate a permutation of the cube
			cube.permuteCube(actions[i]);
			int state_hash = cube.hashCubeState(cube.cube_scrambled);
			int g_costs = current_node.getG_costs() + 1;
			double h_costs = cube.calculateFaceDistance(actions[i]);
			
			// Add the resulting state as a neighbour
			neighbours.add(i, new Node(current_node, state_hash, g_costs, h_costs, actions[i]));

			// Reset permutation
			cube.permuteCube(actions[i]);
			cube.permuteCube(actions[i]);
			cube.permuteCube(actions[i]);
		}
		
		return neighbours;
	}
	
	private void permuteCubeFromStartToCurrentNode(char[][] cube_start_state, Node current_node) {
		
		//get solved_node and fill array from top to bottom
		path_nodes_stack = new Stack<Node>();
		List<Node> path_nodes_list = new ArrayList<Node>();
		Node parent_node = current_node;

		int local_depth_count = 0;
		// As long as a node has an predecessor
		while (parent_node != null)
		{
			//.. fill the stack with those nodes
			path_nodes_stack.push(parent_node);
			parent_node = parent_node.getPredecessor_node();
			
			// Debug tree depth
			if (local_depth_count > counter_tree_depth)
			{
				counter_tree_depth++;
			}
			local_depth_count++;
		}
		while (path_nodes_stack.size() > 0 )
		{
			path_nodes_list.add(path_nodes_stack.pop());
		}
		cube.cube_scrambled = cube_start_state;
//		System.out.println("Current best sequence:");
		for (Node node : path_nodes_list) {
			cube.permuteCube(node.getAction());
//			System.out.println(node.getAction());
		}
	}
}
