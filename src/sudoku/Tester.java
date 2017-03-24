package sudoku;

import java.util.ArrayList;

public class Tester {
    public static void main(String args[]) throws Exception {
        
        Generator generator = new Generator();
        for (int i=0; i<100; i++) {
            Puzzle puzzle = generator.generate();
        
            int solvedCellCount = 0;
            for (Group group: puzzle.getVerticalLines()) {
                for (Cell cell: group.getCells()) {
                    if (cell.isSolved()) {
                        solvedCellCount++;
                    }
                }
            }
            System.out.println(solvedCellCount);
        }
        
        
        
        
    }
}
