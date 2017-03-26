package sudoku;

import java.util.ArrayList;
import java.util.Random;

public class Solver {
	
	// Higher the number, the better chance to get a puzzle, but some time it doesn't worth it
    public static int MAX_LOOP_TIME = 1000;
    private int recursionTime = 0;

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
        
        problem = problem.clone();
        //first loop => assign possible numbers to cell
        for (Group group: problem.getVerticalLines()) {
            for (Cell cell: group.getCells()) {
                if (cell.getCellNumber() != 0) {
                    cell.getHorizontalGroup().removePossibleNumber(cell.getCellNumber());
                    cell.getVerticalGroup().removePossibleNumber(cell.getCellNumber());
                    cell.getBlockGroup().removePossibleNumber(cell.getCellNumber());
                }
            }
        }

        // add all groups in the problem to a variable
        ArrayList<Group> groups = problem.getAllGroups();

        // keep looping until result found
        while (true) {
            //check if anything has been done during a solve loop;
            boolean doSomething = false;
            
            for (Group group: groups) {
                if (group.isSolved()) {
                    continue;
                }
                
                // look for 2, 3, 4, 5, 6, 7, 8 cells with combined possible 
                for (int groupingSize=2; groupingSize<=8; groupingSize++) {
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
                                    if (cell.getPossibleNumbers()[Integer.parseInt(String.valueOf(number)) - 1]) {
                                        cell.getPossibleNumbers()[Integer.parseInt(String.valueOf(number)) - 1] = false;
                                        doSomething = true;
                                    }
                                }
                            }
                        }
                    }
                }
                
                // look for cells with only 1 possible number
                for (Cell cell: group.getCells()) {
                    String possibleNumbers = cell.getPossibleNumbersString();
                    if (!cell.isSolved() && possibleNumbers.length() == 1) {
                        cell.setCellNumber(Integer.parseInt(possibleNumbers));
                        doSomething = true;
                    }
                    else if (!cell.isSolved() && possibleNumbers.length() == 0) {
                        throw new PuzzleUnsolvableException("Invalid Suduko puzzle");
                    }
                }
            }
            
            boolean solved = problem.isSolved();
            // if during a loop, this algorithm doesn't do anything => it need guess work
            if (!solved && !doSomething) {
                throw new NotANaivePuzzleException("This is not a naive Sudoku puzzle");
            }
            else if (solved) { // problem is solved
                break;
            }
        }

        return problem;
    }

    public Puzzle solve(Puzzle puzzle) throws Exception {
    	this.recursionTime = 0;
        return this.solve(puzzle, new ArrayList<Puzzle>());
    }

    /*
     * First solution of a sudoku puzzle (may need guess work)
     * Algorithm:
     * 	Loop until problem is solved or exception because cannot assign number:
     * 		- Solve the puzzle using naive algorithm until it is solved or cannot go anymore
     * 		- Choose a random unsolved cell, assign a random possible number, remove that number from possible numbers list for that cell and remember state
     * 		- If exception because cannot assign number to any cell, go back a state and try another number
     * 		- After 10000 loop => declare the puzzle unsolvable
     */
    protected Puzzle solve(Puzzle problem, ArrayList<Puzzle> states) throws PuzzleUnsolvableException {

    	this.recursionTime++;
        Puzzle result = problem.clone();

        while (true) {

        	// loop times more than threshold => declare the puzzle unsolvable
            boolean solved = result.isSolved();
            if (!solved && this.recursionTime > Solver.MAX_LOOP_TIME) {
                throw new PuzzleUnsolvableException("This Sudoku puzzle can not be solved");
            }
            else if (solved) {
                break;
            }
        	
            try {
                result = this.naiveSolve(result);
            } 
            catch (PuzzleUnsolvableException e) {
            	// check if there is no state left => puzzle unsolvable
            	if (states.size() == 0) {
            		throw new PuzzleUnsolvableException("Invalid Sudoku puzzle");
            	}
            	
                // cannot assign cell => go back 1 state
                Puzzle lastState = states.remove(states.size() - 1);
                return this.solve(lastState, states);
            }
            catch (NotANaivePuzzleException e) { // this puzzle is not naive => need guess work
                Random random = new Random();

                // choose a random unsolved cell
                ArrayList<Cell> unsolvedCells = result.getUnsolvedCells();
                Cell randomCell = unsolvedCells.get(random.nextInt(unsolvedCells.size() - 1));

                // choose a random number in that cell's possible numbers
                String possibleNumbers = randomCell.getPossibleNumbersString();
                if (possibleNumbers.length() - 1 > 0) {
                    int randomIndex = random.nextInt(possibleNumbers.length() - 1);
                    int randomNumber = Integer.parseInt(possibleNumbers.substring(randomIndex, randomIndex+1));
                    randomCell.getPossibleNumbers()[randomNumber] = false;
                    states.add(result.clone());
                    randomCell.setCellNumber(randomNumber);
                    return this.solve(result, states);
                }
                else {
                	// check if there is no state left => puzzle unsolvable
                	if (states.size() == 0) {
                		throw new PuzzleUnsolvableException("Invalid Sudoku puzzle");
                	}
                	
                    Puzzle lastState = states.remove(states.size() - 1);
                    return this.solve(lastState, states);
                }
                
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            
        }

        return result;
    }
	
}
