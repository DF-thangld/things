package sudoku;

import java.util.ArrayList;
import java.util.Random;

public class Generator {
    
    public Puzzle generate() throws Exception {
        Solver solver = new Solver();
        Random random = new Random();
        
        //Create a random finished Sudoku
        Puzzle result = Puzzle.CreateBlankPuzzle();
        result = solver.solve(result);
        
        ArrayList<Cell> solvedCells = new ArrayList<Cell>();
        for (int i=0; i<9; i++) {
            for (int j=0; j<9; j++) {
                solvedCells.add(result.getVerticalLines()[i].getCell(j));
            }
        }
        
        while (true) {
            // remove random number from puzzle until it cannot be solved by naive algorithm anymore
            int randomCellIndex = random.nextInt(solvedCells.size());
            Cell randomCell = solvedCells.get(randomCellIndex);
            int cellNumber = randomCell.getCellNumber();
            randomCell.setCellNumber(0);
            
            for (int i=0; i<9; i++) {
                for (int j=0; j<9; j++) {
                    Cell cell = result.getHorizontalLines()[i].getCell(j);
                    if (!cell.isSolved()) {
                        for (int k=0; k<9; k++) {
                            cell.getPossibleNumbers()[k] = true;
                        }
                    }
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
    
    protected Puzzle generate(Puzzle seed, ArrayList<Puzzle> states) throws Exception {
        
        Solver solver = new Solver();
        Random random = new Random();
        
        ArrayList<Cell> solvedCells = new ArrayList<Cell>();
        for (int i=0; i<9; i++) {
            for (int j=0; j<9; j++) {
                solvedCells.add(seed.getVerticalLines()[i].getCell(j));
            }
        }
        
        while (true) {
            // remove random number from puzzle until it cannot be solved by naive algorithm anymore
            int randomCellIndex = random.nextInt(solvedCells.size());
            Cell randomCell = solvedCells.get(randomCellIndex);
            randomCell.setCellNumber(0);
            
            for (int i=0; i<9; i++) {
                for (int j=0; j<9; j++) {
                    Cell cell = seed.getHorizontalLines()[i].getCell(j);
                    if (!cell.isSolved()) {
                        for (int k=0; k<9; k++) {
                            cell.getPossibleNumbers()[k] = true;
                        }
                    }
                }
            }
            
            states.add(seed.clone());
            try {
                solver.naiveSolve(seed);
            }
            catch (NotANaivePuzzleException | PuzzleUnsolvableException exc) {
                System.out.println(seed.toString());
                Puzzle lastState = states.remove(states.size() - 1);
                if (states.size() == 0) {
                    return lastState;
                }
                else {
                    return this.generate(lastState, states);
                }
            }
        }
        
        
    }
}
