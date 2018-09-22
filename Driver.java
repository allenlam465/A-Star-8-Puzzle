import java.util.ArrayList;
import java.util.Collections;
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
			System.out.print("\n1. Generate random puzzle \n2. Input custom puzzle \n3. Exit \n>");

			String input = s.nextLine();

			switch(input) {

			case "1":
				initial = parse(randomize());
				aStar = new Tree(initial);
				aStar.getSolution();
				aStar.showSolution();
				break;

			case "2":
				System.out.print("Follow the format 0-8 (0 1 2 3 4 5 6 7 8)\n>");
				initial = parse(s.nextLine());
				if(validateInput(initial)) {
					aStar = new Tree(initial);
					aStar.getSolution();
					aStar.showSolution();
					
				}
				break;

			case "3":
				exit = true;
				break;

			default:
				System.out.println("Invalid input. Try again.");
				break;			
			}


		}

		s.close();
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
