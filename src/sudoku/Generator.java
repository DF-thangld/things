package sudoku;

import java.util.ArrayList;
import java.util.Random;

public class Generator {
    
	/*
	 * Generate a random suduko solution then create the hardest puzzle from that solution
	 * Hardest puzzle means the puzzle with least number of solved cells
	 * Solution: use brute force to get all puzzles from a sudoku solution, then return the hardest puzzle
	 */
	public Puzzle generate() throws Exception {
        Solver solver = new Solver();
        
        //Create a random finished Sudoku
        Puzzle seed = Puzzle.CreateBlankPuzzle();
        while (!seed.isSolved()) {
        	try {
        		seed = solver.solve(seed);
            }
        	catch (Exception e) {}
        }
        
        Puzzle result = this.generate(seed);
        while (result.getSolvedCells().size() > 27) {
        	result = this.generate(seed);
        }
        
        return result;
    }
	
	public Puzzle generate (Puzzle seed) throws Exception {
		Puzzle result = seed.clone();
		Random random = new Random();
		Solver solver = new Solver();
		ArrayList<Cell> solvedCells = result.getSolvedCells();
		
		while (true) {
            // remove random number from puzzle until it cannot be solved by naive algorithm anymore
            int randomCellIndex = random.nextInt(solvedCells.size());
            Cell randomCell = solvedCells.get(randomCellIndex);
            int cellNumber = randomCell.getCellNumber();
            randomCell.setCellNumber(0);
            
            for (Cell cell: result.getUnsolvedCells()) {
            	for (int i=0; i<9; i++) {
            		cell.getPossibleNumbers()[i] = true;
            	}
            }
            
            try {
                solver.naiveSolve(result);
            }
            catch (NotANaivePuzzleException | PuzzleUnsolvableException e) {
                randomCell.setCellNumber(cellNumber);
                break;
            }
        }
        
        return result;
	}
}
