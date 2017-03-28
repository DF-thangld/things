package puzzles.fifteenpuzzle;

import java.util.ArrayList;
import java.util.Random;

public class Generator {
	
	// randomly move the moving cell 100 times 
	public Puzzle generate() throws InvalidPuzzle {
		Puzzle puzzle = new Puzzle();
		Random random = new Random();
		Direction lastDirection = Direction.Down;
		for (int i=0; i<100; i++) {
			
			ArrayList<Direction> possibleMoves = puzzle.getPossibleMoves();
			possibleMoves.remove(lastDirection);
			Direction direction = possibleMoves.get(random.nextInt(possibleMoves.size()));
			puzzle.move(direction);
			if (direction == Direction.Up) {
				lastDirection = Direction.Down;
			}
			else if (direction == Direction.Down) {
				lastDirection = Direction.Up;
			}
			else if (direction == Direction.Left) {
				lastDirection = Direction.Right;
			}
			else if (direction == Direction.Right) {
				lastDirection = Direction.Left;
			}
		}
		
		return puzzle;
	}
}
