/*
 * Node for the A* tree.	
 */
public class Node {

	// hSum = f(n) = g(n) + h(n)
	// h1 = g(n) = number of of misplaced tiles from goal state calculate during each state in tree
	// h2 = h(n) = sum of distance of tiles from current state to their goal state (Vertical Move + Horizontal Moves to goal state)

	//Each node will hold the sum of distance from the goal state
	//h1 will be added to the fSum during A* alg

	private int hVal;
	private int hSum;
	private int depth;

	// Current state and parentState stores puzzle string
	private String state;
	private String parentState; 


	public Node(String state, String parentState, int depth, int hVal, int hSum) {
		this.state = state;
		this.parentState = parentState;
		this.setDepth(depth);
		this.sethVal(hVal);
		this.sethSum(hSum);
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getParentState() {
		return parentState;
	}
	
	public void setParentState(String parentState) {
		this.parentState = parentState;
	}
	
	public int getDepth() {
		return depth;
	}
	
	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int gethSum() {
		return hSum;
	}

	public void sethSum(int hSum) {
		this.hSum = hSum;
	}
	
	public void sethVal(int hVal) {
		this.hVal = hVal;
	}
	
	public int gethVal() {
		return hVal;
	}



}
