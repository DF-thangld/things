package sudoku;

import java.util.ArrayList;

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
		
		// add all groups in the problem to a variable
		ArrayList<ProblemGroup> groups = new ArrayList<ProblemGroup>();
		for (int i=0; i<9; i++) {
			groups.add(problem.getVerticalLines()[i]);
			groups.add(problem.getHorizontalLines()[i]);
			groups.add(problem.getBlocks()[i]);
		}
		
		// keep looping until result found
		while (!problem.isSolved()) {
			// look for 2, 3, 4, 5, 6, 7, 8 cells with combined possible 
			for (int groupingSize=2; groupingSize<=8; groupingSize++) {
				for (ProblemGroup group: groups) {
					ArrayList<ArrayList<ProblemCell>> groupingCells = group.findGroupingCells(groupingSize);
					for (ArrayList<ProblemCell> cells: groupingCells) {
						StringBuilder possibleNumbers = new StringBuilder();
						for (ProblemCell cell: cells) {
							possibleNumbers.append(cell.getPossibleNumbersString());
						}
						
						String compressedPossibleNumbers = Utils.removeDuplicateCharacterInString(possibleNumbers.toString());
						for (int i=0; i<9; i++) {
							ProblemCell cell = group.getCell(i);
							if (!cells.contains(cell)) {
								for (char number: compressedPossibleNumbers.toCharArray()) {
									cell.getPossibleNumbers()[Integer.parseInt(String.valueOf(number))] = false;
								}
							}
						}
					}
				}
			}
			
			// look for cells with only 1 possible number
			for (ProblemGroup group: groups) {
				for (ProblemCell cell: group.getCells()) {
					String possibleNumbers = cell.getPossibleNumbersString();
					if (possibleNumbers.length() == 1) {
						cell.setCellNumber(Integer.parseInt(possibleNumbers));
					}
				}
			}
		}
		
		return problem;
	}
}
