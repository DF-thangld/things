package sudoku;

import java.util.ArrayList;

public class Solver {
	
	public static int MAX_LOOP_TIME = 10000;
	
	/* Solution of a naive puzzle (puzzles don't need guess work):
	 * Loop through horizontal, vertical and block groups
	 * 	 	Look for 2, 3, 4 cells which have merged possible numbers length equal to number of cell =>
	 * 			Remove these merged numbers from possible numbers in other cells of the group 
	 * 		Find for numbers which only appear once in the group
	 * 			Assign that number
	 * 		If a cell only has 1 possible number => 
	 * 			Assign that number
	 */
	public Puzzle naiveSolve(Puzzle problem) throws Exception {
		
		//first loop => assign possible numbers to cell
		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				Cell cell = problem.getHorizontalLines()[i].getCell(j);
				if (cell.getCellNumber() != 0) {
					cell.getHorizontalGroup().removePossibleNumber(cell.getCellNumber());
					cell.getVerticalGroup().removePossibleNumber(cell.getCellNumber());
					cell.getBlockGroup().removePossibleNumber(cell.getCellNumber());
				}
			}
		}
		
		// add all groups in the problem to a variable
		ArrayList<Group> groups = new ArrayList<Group>();
		for (int i=0; i<9; i++) {
			groups.add(problem.getVerticalLines()[i]);
			groups.add(problem.getHorizontalLines()[i]);
			groups.add(problem.getBlocks()[i]);
		}
		
		// keep looping until result found
		while (!problem.isSolved()) {
			//check if anything has been done during a solve loop;
			boolean doSomething = false;
			// look for 2, 3, 4, 5, 6, 7, 8 cells with combined possible 
			for (int groupingSize=2; groupingSize<=8; groupingSize++) {
				for (Group group: groups) {
					ArrayList<ArrayList<Cell>> groupingCells = group.findGroupingCells(groupingSize);
					for (ArrayList<Cell> cells: groupingCells) {
						StringBuilder possibleNumbers = new StringBuilder();
						for (Cell cell: cells) {
							possibleNumbers.append(cell.getPossibleNumbersString());
						}
						
						String compressedPossibleNumbers = Utils.removeDuplicateCharacterInString(possibleNumbers.toString());
						for (int i=0; i<9; i++) {
							Cell cell = group.getCell(i);
							if (!cells.contains(cell)) {
								for (char number: compressedPossibleNumbers.toCharArray()) {
									if (cell.getPossibleNumbers()[Integer.parseInt(String.valueOf(number))]) {
										cell.getPossibleNumbers()[Integer.parseInt(String.valueOf(number))] = false;
										doSomething = true;
									}
								}
							}
						}
					}
				}
			}
			
			// look for cells with only 1 possible number
			for (Group group: groups) {
				for (Cell cell: group.getCells()) {
					String possibleNumbers = cell.getPossibleNumbersString();
					if (!cell.isSolved() && possibleNumbers.length() == 1) {
						cell.setCellNumber(Integer.parseInt(possibleNumbers));
						doSomething = true;
					}
					else if (!cell.isSolved() && possibleNumbers.length() == 0) {
						throw new UnassignableCellException("Can not assign number to this cell");
					}
				}
			}
			
			// if during a loop, this algorithm doesn't do anything => it need guess work
			if (!doSomething) {
				throw new NotANaivePuzzleException("This is not a naive Sudoku puzzle");
			}
		}
		
		return problem;
	}
	
	/*
	 * First solution of a sudoku puzzle (may need guess work)
	 * Algorithm:
	 * 	Loop until problem is solved or exception because cannot assign number:
	 * 		- Solve the puzzle using naive algorithm until it is solved or cannot go anymore
	 * 		- Choose a random unsolved cell, assign a random possible number, remove that number from possible numbers list for that cell and remember state
	 * 		- If exception because cannot assign number to any cell, go back a state and try another number
	 * 		- TODO: after 10000 loop => declare the puzzle unsolvable
	 */
	public Puzzle solve(Puzzle problem) throws PuzzleUnsolvableException {
		Puzzle result = problem.clone();
		int loopTime = 0;
		
		while (!result.isSolved()) {
			
			try {
				result = this.naiveSolve(result);
			} 
			catch (UnassignableCellException e) {
				// cannot assign cell => go back 1 state
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// loop times more than threshold => declare the puzzle unsolvable
			loopTime++;
			if (loopTime > Solver.MAX_LOOP_TIME) {
				throw new PuzzleUnsolvableException("This Sudoku puzzle can not be solved");
			}
		}
		
		return result;
	}
	
}
