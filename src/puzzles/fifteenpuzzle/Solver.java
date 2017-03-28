package puzzles.fifteenpuzzle;

import java.util.ArrayList;

public class Solver {
	
	/*
	 * Currently there's no algorithm to solve this puzzle (known to me)
	 * so I will try to solve this using search algorithm: depth first search, breath first search, a* search
	 */
	public ArrayList<Direction> solve(Puzzle puzzle, SolveStrategy strategy) {
		ArrayList<Direction> result = new ArrayList<Direction>();
		
		if (strategy == SolveStrategy.DepthFirst) { 
			return solveUsingDepthFirstSearch(puzzle);
		}
		return result;
	}
	
	public ArrayList<Direction> solveUsingDepthFirstSearch(Puzzle puzzle) {
		ArrayList<Direction> result = new ArrayList<Direction>();
		
		return result;
	}
	
	public ArrayList<Direction> solveUsingDepthFirstSearch(Puzzle puzzle, ArrayList<Direction> moves, ArrayList<Puzzle> checkedStates) {
		ArrayList<Direction> result = new ArrayList<Direction>();
		
		return result;
	}
}
