package puzzles;

import puzzles.fifteenpuzzle.Direction;
import puzzles.fifteenpuzzle.Generator;
import puzzles.fifteenpuzzle.Puzzle;

public class Tester {
    public static void main(String args[]) throws Exception {
        
        Generator generator = new Generator();
        Puzzle puzzle = generator.generate();
        System.out.println(puzzle.toString());
    	
    	
    }
}
