import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;

public class Tree{

	/*
	 * The A* Tree 
	 * Will calculate the distance from goal state
	 */

	private static final String GOAL = "012345678";

	//explored for explored nodes already associate with string to pull solution back out
	private HashMap<String, Node> explored;

	//solution stack to store path
	private Stack<Node> solution;

	//Usage of frontier comparator to check for optimal path to take
	PriorityQueue<Node> frontier = new PriorityQueue<Node> (new Comparator<Node>() {

		@Override
		public int compare(Node x, Node y) {

			if(x.gethSum() == y.gethSum()) {
				return Integer.compare(y.getDepth(), x.getDepth());
			}

			return Integer.compare(x.gethSum(), y.gethSum());
		}

	});

	public Tree(String state) {
		explored = new HashMap<>();
		solution = new Stack<>();

		frontier.add(new Node(state,null,0,0,h2Val(state),h2Val(state)));
	}

	public Stack<Node> getSol(){
		return solution;
	}

	// |1 2 3 4 0 5 6 7 8|
	// Explore all possible state of the string from where 0 by swapping tile with empty tile
	public void explore() {

		//Add all possible states it can have
		while(!checkGoalState(frontier.peek())) {
			Node top = frontier.peek();
			String currentState = top.getState();
			String newState; 

			int zeroPos = currentState.indexOf('0');
			int leftRight = zeroPos % 3;
			int upDown = zeroPos / 3;
			
			int tileLeft = zeroPos - 1, tileRight = zeroPos + 1, tileUp = zeroPos - 3, tileDown = zeroPos + 3;

			//System.out.println("THE PEEK " + frontier.peek().getState());
			//Add all possible states within explore/frontier?????

			Node nextState;

			if(leftRight != 0) {
				//Swap left tile with 0
				newState = swapTile(currentState, zeroPos, tileLeft);
				nextState = new Node(newState, currentState, top.getDepth() + 1, h1Val(newState), h2Val(newState)  ,  top.getDepth() + 1 + h2Val(newState));
				newFrontier(nextState);

			}

			if(leftRight  != 2) {
				//Swap right tile with 0
				newState = swapTile(currentState, zeroPos, tileRight);
				nextState = new Node(newState, currentState, top.getDepth() + 1, h1Val(newState), h2Val(newState)  ,  top.getDepth() + 1 + h2Val(newState));
				newFrontier(nextState);				
			}

			if(upDown != 0) {
				//Swap top tile with 0
				newState = swapTile(currentState, zeroPos, tileUp);
				nextState = new Node(newState, currentState, top.getDepth() + 1, h1Val(newState), h2Val(newState)  ,  top.getDepth() + 1 + h2Val(newState));
				newFrontier(nextState);
			}

			if(upDown != 2) {
				//Swap bottom tile with 0
				newState = swapTile(currentState, zeroPos, tileDown);
				nextState = new Node(newState, currentState, top.getDepth() + 1, h1Val(newState), h2Val(newState)  ,  top.getDepth() + 1 + h2Val(newState));
				newFrontier(nextState);
			}

			// Add state to explored set and remove from frontier
			explored.put(currentState, top);
			frontier.remove(top);

		}	

	}

	public boolean getSolution() {
		while(!checkGoalState(frontier.peek())){
			explore();
			if(checkGoalState(frontier.peek()))
				solution.push(frontier.peek());
		}

		return true;
	}

	public void showSolution() {

		while(solution.peek().getParentState() != null) {

			String parentState = solution.peek().getParentState();

			solution.push(explored.get(parentState));
		}

		System.out.format("%-10s%-10s%-10s%-10s%-10s\n", "Depth", "Puzzle","h1", "h2", "h Total");

		while(!solution.isEmpty()) {
			Node path = solution.pop();
			printSolution(path);
		}

	}

	public void printSolution(Node state) {
		
		String input = state.getState();		
		
		System.out.format("%-10s%-10s%-10s%-10s%-10s\n", state.getDepth(), input.charAt(0) +""+ input.charAt(1) +""+ input.charAt(2),state.getH1(), state.getH2(), state.gethSum() );
		System.out.format("%-10s%-10s\n", "",input.charAt(3) +""+ input.charAt(4) +""+ input.charAt(5));
		System.out.format("%-10s%-10s\n", "",input.charAt(6) +""+ input.charAt(7) +""+ input.charAt(8));
		System.out.println("\n");

	}

	// Return current number of misplaced tiles so far
	// If tile is not at its current place add to h1 value of that state
	public int h1Val(String state) {
		int val = 0;

		for(int i = 0; i < state.length(); i++) {
			int tile =  Character.getNumericValue(state.charAt(i));
			if(i != tile && tile != 0) {
				val++;
			}
		}

		return val;
	}

	//Hamming distance
	public int h2Val(String state) {
		int val = 0;

		for(int i = 0; i < state.length(); i++) {
			int tile =  Character.getNumericValue(state.charAt(i));
			if(tile != 0) {
				val += Math.abs((i % 3) - (tile % 3));
				val += Math.abs((i / 3) - (tile / 3));
			}
		}

		return val;
	}

	// Check node if it has reached the goal state;
	public boolean checkGoalState(Node state) {
		return state.getState().equals(GOAL);
	}

	// Swap tile with empty space 
	public String swapTile(String state, int emptyPos, int filledPos) {

		StringBuilder sb = new StringBuilder(state);

		sb.setCharAt(emptyPos, state.charAt(filledPos));
		sb.setCharAt(filledPos, state.charAt(emptyPos));

		return sb.toString();

	}

	public boolean isExplored(String state) {
		return explored.containsKey(state);
	}

	public void newFrontier(Node state) {
		if(!isExplored(state.getState())) {
			frontier.add(state);
		}
	}

}
