package sudoku;

public class Solver {
	
	
	/* Solution (only implement for puzzles that don't need guess work):
	 * Loop through horizontal, vertical and block groups
	 * 	 	Look for 2, 3, 4 cells which have merged possible numbers length equal to number of cell =>
	 * 			Remove these merged numbers from possible numbers in other cells of the group 
	 * 		Find for numbers which only appear once in the group
	 * 			Assign that number
	 * 		If a cell only has 1 possible number => 
	 * 			Assign that number
	 */
	public Problem solve(Problem problem) throws Exception {
		
		// TODO: should create new cells instead of using old ones
		Problem result = Problem.CreateProblem(problem.getHorizontalLines());
		
		//first loop => assign possible numbers to cell
		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				ProblemCell cell = problem.getHorizontalLines()[i].getCell(j);
				if (cell.getCellNumber() != 0) {
					cell.getHorizontalGroup().removePossibleNumber(cell.getCellNumber());
					cell.getVerticalGroup().removePossibleNumber(cell.getCellNumber());
					cell.getBlockGroup().removePossibleNumber(cell.getCellNumber());
				}
			}
		}
		
		// keep looping until result found
		while (!problem.isSolved()) {
			
			
			
		}
		
		return result;
	}
}
