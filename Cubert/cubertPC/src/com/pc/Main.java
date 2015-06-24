package com.pc;

import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) 
	{
		//Regular code
//		Connection connect_PC = new Connection();
//		connect_PC.connectToNXT();
//		Cube cube = new Cube(connect_PC.getScanResultVector());
		
		// Code for debugging the permutations
		Cube cube = new Cube(null);
		Solver magic = new Solver(cube);
		
		if (cube.cube_orientation == null) {
			System.out.println("SCAN ERROR");
		}

		// Random scramble sequence
		cube.permuteCube('L');
		cube.permuteCube('T');
		cube.permuteCube('l');
		cube.permuteCube('D');
		cube.permuteCube('B');
		cube.permuteCube('d'); // 6 permutations:  1 s
		cube.permuteCube('R'); // 7 permutations:  4 s
		cube.permuteCube('t'); // 8 permutations: 31 s
		cube.permuteCube('f'); // 9 permutations: Cancelled after 10 minutes...


		int count = 0;
		for (char[] cube_solved_cubie: cube.cube_solved) {
			System.out.println();
			System.out.println();
			System.out.println("Cubie : " + count + " solved: ");
			for (char color: cube_solved_cubie) {
				System.out.print(" " + color);
			}
			System.out.println();
			System.out.println("Cubie : " + count + " scrambled: ");
			for (char color: cube.cube_scrambled[count]) {
				System.out.print(" " + color);
			}

			System.out.println();
			if (Arrays.equals(cube.cube_scrambled[count], cube_solved_cubie)) {
				System.out.println("IDENTICAL");
			} else {
				System.out.println("DIFFERENT");
			}
			count ++;
		}
		
		System.out.println();
		if (Arrays.deepEquals(cube.cube_solved, cube.cube_scrambled)) {
			System.out.println("CUBE IS SOLVED");
		} else {
			System.out.println("CUBE IS SCRAMBLED");
		}
		
		List<Character> solving_sequence = magic.calculateSolvingSequence();
		for (Character character : solving_sequence) {
			System.out.println(character);
		}
		
		System.out.println("END");
	}
}
