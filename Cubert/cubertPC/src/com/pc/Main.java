package com.pc;

public class Main {

	public static void main(String[] args) 
	{
		Connection connect_PC = new Connection();
		connect_PC.connectToNXT();
		Cube cube = new Cube(connect_PC.getScanResultVector());
		if (cube.cube_orientation == null) {
			System.out.println("SCAN ERROR");
		}
		
		int count = 0;
		for (char[] cube_solved_vector: cube.cube_solved) {
			System.out.println();
			System.out.println();
			System.out.println("Cubie : " + count + " solved: ");
			for (char color: cube_solved_vector) {
				System.out.print(" " + color);
			}
			System.out.println();
			System.out.println("Cubie : " + count + " scrambled: ");
			for (char color: cube.cube_scrambled[count]) {
				System.out.print(" " + color);
			}
			count ++;
		}
	}
}
