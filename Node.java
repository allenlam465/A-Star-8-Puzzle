/*
 * Node for the A* tree.	
 */
public class Node {

	// hSum = f(n) = g(n) + h(n)
	// h1 = g(n) = number of of misplaced tiles from goal state calculate during each state in tree
	// h2 = h(n) = sum of distance of tiles from current state to their goal state (Vertical Move + Horizontal Moves to goal state)

	//Each node will hold the sum of distance from the goal state
	//h1 will be added to the fSum during A* alg

	private int h1;
	private int h2;
	private int hSum;
	private int depth;

	// Current state and parentState stores puzzle string
	private String state;
	private String parentState; 


	public Node(String state, String parentState, int depth, int h1, int h2, int hSum) {
		this.state = state;
		this.parentState = parentState;
		this.setDepth(depth);
		this.setH1(h1);
		this.setH2(h2);
		this.sethSum(hSum);
	}

	public int gethSum() {
		return hSum;
	}

	public void sethSum(int hSum) {
		this.hSum = hSum;
	}

	public int getH2() {
		return h2;
	}

	public void setH2(int h2) {
		this.h2 = h2;
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

	public int getH1() {
		return h1;
	}

	public void setH1(int h1) {
		this.h1 = h1;
	}

}
