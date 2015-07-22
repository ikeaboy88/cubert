package com.test;

import java.awt.Button;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import com.pc.Cube;
import com.pc.Node;
import com.pc.Solver;

public class RandomCubeStateGenerator {

	public static void main(String[] args) {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		do{
			System.out.println("Generating randomly scrambled cube...");

			Cube cube = new Cube(null);

			Random randomIndex = new Random();
			List<Character>scrambledCube = new ArrayList<Character>();
			String permutedFace = "TDFBLRtdfblr";
			int permutationCount = 0;
			
			
			try {
		    
		    System.out.println("wie oft soll der cube permutiert werden?");
				String eingabe = br.readLine();
				permutationCount = Integer.parseInt(eingabe);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
			if (permutationCount == 0)
			{
				cube.permuteCube('T');
				cube.permuteCube('d');
				cube.permuteCube('l');
				/**
				cube.permuteCube('d');
				cube.permuteCube('B');
				
				cube.permuteCube('r');
				cube.permuteCube('f');
				cube.permuteCube('B');
				cube.permuteCube('R');
				cube.permuteCube('F');

				cube.permuteCube('r');
				cube.permuteCube('f');
				cube.permuteCube('B');
				cube.permuteCube('R');
				cube.permuteCube('F');

				cube.permuteCube('r');
				cube.permuteCube('f');
				cube.permuteCube('B');
				cube.permuteCube('R');
				cube.permuteCube('F');
				 */ 
				 
			}
			else if (permutationCount > 0)
			{
				// randomly permutation of cube according to permutationCount
				for (int i = 0; i < permutationCount; i++) {
					Character face = permutedFace.charAt(randomIndex.nextInt(permutedFace.length()));
					scrambledCube.add(face);
					cube.permuteCube(face);
				}
			}
			else
			{
				System.out.println("bad input - shutting down");
				return;
			}
			
			Solver magic = new Solver(cube);

			// Calculate solving sequence
			//List<Character> sequence = magic.calculateSolvingSequence();
			List<Character> sequence = new ArrayList<Character>();
			Node solved = magic.calculateSolvingSequenceIDA();
			if (solved != null) {
				System.out.println(solved.getTotalCosts());
				Stack<Node> solution_nodes_stack = new Stack<Node>();
				List<Node> solution_nodes_list = new ArrayList<Node>();
				Node parent_node = solved;

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
					sequence.add(node.getAction());
				}
			} else {
				System.out.println("Nothing found");
			}
			System.out.println("best path: ");
			
			// fill char array with values from List
			System.out.println("best path: ");

			for (int a = 0; a < sequence.size(); a++) {
				System.out.println(sequence.get(a));
			}

			System.out.println("permuted faces: ");
			
			for (int b = 0; b < scrambledCube.size(); b++) {
				System.out.println(scrambledCube.get(b));
			}
		}while(true);
	}
}
