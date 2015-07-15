package com.test;

import java.awt.Button;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.pc.Cube;
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
		
			// randomly permutation of cube according to permutationCount
			for (int i = 0; i < permutationCount; i++) {
				Character face = permutedFace.charAt(randomIndex.nextInt(permutedFace.length()));
				scrambledCube.add(face);
				cube.permuteCube(face);
			}

			Solver magic = new Solver(cube);

			// Calculate solving sequence
			List<Character> sequence = magic.calculateSolvingSequence();

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
