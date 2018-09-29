/*
 *  Name - Allen Lam
 *  Date - 21 September, 2018
 *  CS 4200
 *  
 *  A* 8 puzzle solver that accepts user inputed 8 puzzle to solve or random puzzle is generated to solve.
 *  Also shows average search costs with 100 cases.
 */

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		menu();
	}

	private static void menu() {

		Scanner s = new Scanner(System.in);

		String initial = "";
		Tree aStar;

		boolean exit = false;

		System.out.println("8 Puzzle Solver With A*");

		while(!exit) {
			System.out.print("\n1. Generate random puzzle \n2. Input custom puzzle \n3. Average search costs \n4. Exit \n>");

			String input = s.nextLine();

			switch(input) {

			case "1":
				initial = parse(randomize());
				System.out.println("\n1. With h1 \n2. With h2");
				input = s.nextLine();
				
				System.out.println(input);
				
				if(input.equals("1")) {
					aStar = new Tree(initial, 1);
					aStar.getSolution();
					aStar.showSolution();						
				}
				else {
					aStar = new Tree(initial, 2);
					aStar.getSolution();
					aStar.showSolution();					
				}
				break;

			case "2":
				System.out.print("Follow the format 0-8 (0 1 2 3 4 5 6 7 8 or 012345678)\n>");
				initial = parse(s.nextLine());
				if(validateInput(initial)) {
					System.out.println("\n1. With h1 \n2. With h2");
					input = s.nextLine();
					
					if(input.equals("1")) {
						aStar = new Tree(initial, 1);
						aStar.getSolution();
						aStar.showSolution();						
					}
					else {
						aStar = new Tree(initial, 2);
						aStar.getSolution();
						aStar.showSolution();					
					}
				}
				break;
				
			case "3":
				System.out.println("Randomized 100 Cases Search Cost By Nodes Generated and Time to find Goal\n");
				generateSearchCosts();
				break;

			case "4":
				exit = true;
				break;

			default:
				System.out.println("Invalid input. Try again.");
				break;			
			}


		}

		s.close();
	}
	
	// Obtain a linked list states in multiple depths and randomly pick one to use
	private static String randomizeDepth(int depth) {
		Random rand = new Random();
		LinkedList<Node> list = possibleStates(depth);
		
		return list.get(rand.nextInt(list.size())).getState();
	}
	
	// Creates a A* tree which will load multiple states in a linked list and returns
	private static LinkedList<Node> possibleStates(int depth){
		Tree finder = new Tree("012345678", 2);
		return finder.getStatesDepth(depth);
	}
	
	private static void generateSearchCosts() {
		
		double h1Avg = 0, h2Avg = 0, h1Time = 0, h2Time = 0;
		DecimalFormat df = new DecimalFormat("#.##");
		int itr = 100;
		System.out.format("%-5s %-15s %-15s %-15s %-15s %-5s\n", "Depth", "h1 A* Average", "h2 A* Average", "h1 Time (ns)", "h2 Time(ns)", "Cases");
		// Creates the search costs for both heuristics.
		for(int i = 2; i <= 24; i+=2) {
			for(int j = 0; j < itr; j++) {
				String initial = parse(randomizeDepth(i));
				Tree h1 = new Tree(initial, 1);
				Tree h2 = new Tree(initial, 2);
				
				h1Time += getTime(h1);
				h2Time += getTime(h2);
				
				// Obtain total number of nodes made to find the solution
				h1Avg += h1.getFrontier().size() + h1.getExplored().size();
				h2Avg += h2.getFrontier().size() + h2.getExplored().size();	
					
			}
			
			h1Avg /= itr;
			h2Avg /= itr;
			h1Time /= itr;
			h2Time /= itr;
			System.out.format("%-5s %-15s %-15s %-15s %-15s %-5d\n", i, df.format(h1Avg), df.format(h2Avg), df.format(h1Time), df.format(h2Time), itr);

		}
		
		h1Avg = 0;
		h2Avg = 0;
		h1Time = 0;
		h2Time = 0;
		
	}
	
	// Finds time that solution took to be found
	private static double getTime(Tree tree) {
		double start, end;
		
		start = System.nanoTime();
		tree.getSolution();
		end = System.nanoTime();
		
		return end - start;
	}

	// Use to validate user input of puzzle
	private static boolean validateInput(String input) {

		try {

			int num = 0;

			if(input.length() > 17) 
				throw new Exception("Invalid length please try another input.");
			else if(input.equals("0 1 2 3 4 5 6 7 8") || input.equals("012345678") )
				throw new Exception("Puzzle is already solved input another puzzle.");

			for(int i = 0; i < input.length(); i++) {
				if(Character.isDigit(input.charAt(i)))
					num++;
				else 
					throw new Exception("Only use numbers for the puzzle.");
			}

			if(!solvable(input))
				throw new Exception("Cannot solve this puzzle please try another input.");
			else if(num == 9)
				return true;

			throw new Exception("Invalid format please try another input.");

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

	}

	private static boolean validate(String input) {
		return solvable(input);
	}

	// Provide random string(0-8) for random puzzle
	private static String randomize() {

		String str = "";

		ArrayList<Integer> list = new ArrayList<Integer>();

		for(int i = 0; i < 9; i++) {
			list.add(i);
		}

		do{
			Collections.shuffle(list);

			str = list.toString().replaceAll("[\\[\\]]", "").replaceAll(",", "").replaceAll(" ", "");

			//System.out.println(validate(str));

		}while(!validate(str));

		return str;
	}

	// Check amount of possible inversions needed to see if it is solvable 
	// If even amount it is solvable else it is not 
	private static boolean solvable(String input) {
		int total = 0; 

		for(int i = 0; i < 8; i++) {
			if(input.charAt(i) != '0') {
				for(int j = i + 1; j < 9; j++ ) {
					if(input.charAt(j) != '0' && input.charAt(i) > input.charAt(j)) {
						total++;
					}
				}
			}
		}
		return total % 2 == 0;
	}

	// Remove spaces from the input
	public static String parse(String input) {
		StringBuilder sb = new StringBuilder();

		for(int i = 0; i < input.length(); i++) {
			if(input.charAt(i) != ' ') {
				sb.append(input.charAt(i));
			}
		}

		return sb.toString();
	}

}
