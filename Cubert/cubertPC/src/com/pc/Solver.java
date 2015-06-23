package com.pc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

// Theory: 				http://www.policyalmanac.org/games/aStarTutorial_de.html	
// Example project: 	https://code.google.com/p/jianwikis/wiki/AStarAlgorithmForPathPlanning

public class Solver {

	// Closed list - Contains all visited nodes (Key: cube state as hash, value: Node object)
	public Hashtable<Integer, Node> closed_list = new Hashtable<Integer, Node>();
	
	// Open list - Contains all nodes we know, but not have visited yet (Key: cube state as hash, value: Node object)
	public Hashtable<Integer, Node> open_list = new Hashtable<Integer, Node>();
	
	// Nodes from open_list in a sorted order (cheapest on top)
	public PriorityQueue<Node> priority_queue = new PriorityQueue<Node>(new NodeComparator());
	
	public Cube cube;
	
	private char[] actions = new char[]{'t', 'd', 'l', 'r', 'f', 'b'};
	
	public Stack<Node> solution_nodes_stack;
	
	public Node solved_node;
	
	public long counter = 0;
	
	// Constructor
	public Solver(Cube cube) {
		this.cube = cube;
	}
	
	public List<Character> calculateSolvingSequence() {

		List<Character> solving_sequence = new ArrayList<Character>();
		
		// Get hash value of the cube's solved state
		int cube_solved_hash = cube.hashCubeState(cube.cube_solved);

		// Initialize start node with cube's initial state
		int cube_start_hash = cube.hashCubeState(cube.cube_scrambled);
		Node start_node = new Node(cube_start_hash);
		
		// Initialize solved node with null
		solved_node = null;
		
		// Add start node to the open list and to the priority queue
		open_list.put(cube_start_hash, start_node);
		priority_queue.add(start_node);
		
		// loop - As long as open list contains nodes
		while (open_list.size() > 0)
		{
			System.out.println(counter++);
			// Get first (cheapest) node from priority queue and remove it (from open list AND priority queue)
			Node current_node = priority_queue.poll();
			open_list.remove(current_node.getState_hash());
			
			// IF current node holds the solved state: Set current node as solved node and break the loop
			if (current_node.getState_hash() == cube_solved_hash)
			{
				solved_node = new Node(current_node.getPredecessor_node(), current_node.getState_hash(), current_node.getG_costs(), current_node.getH_costs(), current_node.getAction());
				break;
			} 
			// ELSE node is not the solved state, ...
			else
			{
				// Add current node to closed list
				closed_list.put(current_node.getState_hash(), current_node);
				
				//permute cube?
				cube.permuteCube(current_node.getAction());
				
				// Expand node - Create nodes for every subsequent state of the cube's current state
				List<Node> neighbour_nodes = this.getNeighbourNodes(current_node, cube, actions);
		
				// loop - For every new node (neighbours)
				for (Node neighbour_node : neighbour_nodes)
				{
					// IF node is not in closed list?
					Node visited = closed_list.get(neighbour_node.getState_hash());
					if (visited == null)
					{
						// UNCLEAR if needed: Calculate cost g from node to neighbour
						
						// IF neighbour is NOT in opened list
						Node neighbour = open_list.get(neighbour_node.getState_hash());
						if (neighbour == null)
						{
							// UNCLEAR: already done in getNeighbourNodes
							//create new node (hash, g, ,...)
							// set predecessor_node (to expanding node)
							
							// Add node to opened list and priority queue
							open_list.put(neighbour_node.getState_hash(), neighbour_node);
							priority_queue.add(neighbour_node);
						}
						// ELSE IF (in open list - have already been here XD ) check if current path costs < node costs
						else if (neighbour_node.getG_costs() < neighbour.getG_costs())
						{
							// set cheaper predecessor (to that node)
							neighbour.setPredecessor_node(current_node);
							// set g cost
							neighbour.setG_costs(neighbour_node.getG_costs());
							// calc new h cost to target
							neighbour.setH_costs(neighbour_node.getH_costs());
							neighbour.setAction(neighbour_node.getAction());
							//set scrambled state to nodes predecessor node
						}
						
					}
				}
			}
							
		}
		// Open list empty or solved node found
		
		// Get solving sequence
		if(solved_node != null)
		{
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
	
	private List<Node> getNeighbourNodes(Node current_node, Cube cube, char[] actions) {
		
		List<Node> neighbours = new ArrayList<Node>();
		
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
}
