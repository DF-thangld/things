package sudoku;

import java.util.ArrayList;

public class Tester {
    public static void main(String args[]) throws Exception {
        
        Generator generator = new Generator();
        for (int i=0; i<100; i++) {
            Puzzle puzzle = generator.generate();
            System.out.println(puzzle.getSolvedCells().size());
        }
    	
    	
        
        
    }
}
