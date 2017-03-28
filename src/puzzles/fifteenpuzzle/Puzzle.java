package puzzles.fifteenpuzzle;

import java.util.ArrayList;

public class Puzzle implements Cloneable {
	private int[][] matrix = new int[4][4];
	public int[][] getMatrix() {
		return this.matrix;
	}
	
	public Puzzle() {
		for (int i=0; i<4; i++) {
			for (int j=0; j<4; j++) {
				this.matrix[i][j] = i*4 + j + 1;
			}
		}
		this.matrix[3][3] = 0;
		
	}
	
	public int getNumberAt(int x, int y) {
		return this.matrix[x][y];
	}
	
	public void setNumberAt(int x, int y, int number) {
		this.matrix[x][y] = number;
	}
	
	public int[] findNumber(int number) throws InvalidPuzzle {
		for (int i=0; i<4; i++) {
			for (int j=0; j<4; j++) {
				if (this.getNumberAt(i, j) == number) {
					return new int[] {i, j};
				}
			}
		}
		throw new InvalidPuzzle("Moving cell not found");
	}
	
	// change state of the puzzle according to the direction
	// return true if it can move, false if cannot move
	public boolean move(Direction direction) throws InvalidPuzzle {
		int[] coordinate = this.findNumber(0);
		if (direction == Direction.Up) {
			if (coordinate[0] == 0) {
				return false;
			}
			int targetedNumber = this.getNumberAt(coordinate[0] - 1, coordinate[1]);
			this.setNumberAt(coordinate[0] - 1, coordinate[1], 0);
			this.setNumberAt(coordinate[0], coordinate[1], targetedNumber);
		}
		else if (direction == Direction.Down) {
			if (coordinate[0] == 3) {
				return false;
			}
			int targetedNumber = this.getNumberAt(coordinate[0] + 1, coordinate[1]);
			this.setNumberAt(coordinate[0] + 1, coordinate[1], 0);
			this.setNumberAt(coordinate[0], coordinate[1], targetedNumber);
		}
		
		else if (direction == Direction.Left) {
			if (coordinate[1] == 0) {
				return false;
			}
			int targetedNumber = this.getNumberAt(coordinate[0], coordinate[1] - 1);
			this.setNumberAt(coordinate[0], coordinate[1] - 1, 0);
			this.setNumberAt(coordinate[0], coordinate[1], targetedNumber);
		}
		
		else if (direction == Direction.Right) {
			if (coordinate[1] == 3) {
				return false;
			}
			int targetedNumber = this.getNumberAt(coordinate[0], coordinate[1] + 1);
			this.setNumberAt(coordinate[0], coordinate[1] + 1, 0);
			this.setNumberAt(coordinate[0], coordinate[1], targetedNumber);
		}
		
		return true;
	}
	
	public ArrayList<Direction> getPossibleMoves() throws InvalidPuzzle {
		ArrayList<Direction> result = new ArrayList<Direction>();
		int[] movingCellCoordinate = this.findNumber(0);
		if (movingCellCoordinate[1] > 0) {
			result.add(Direction.Left);
		}
		if (movingCellCoordinate[1] < 3) {
			result.add(Direction.Right);
		}
		if (movingCellCoordinate[0] > 0) {
			result.add(Direction.Up);
		}
		if (movingCellCoordinate[0] < 3) {
			result.add(Direction.Down);
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (int i=0; i<4; i++) {
			for (int j=0; j<4; j++) {
				int value = this.matrix[i][j]; 
				if (value < 10) {
					result.append("0");
				}
				result.append(String.valueOf(value));
				result.append("|");
			}
			result.append(System.lineSeparator());
		}
		
		return result.toString();
	}
	
	@Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
       if (!(obj instanceof Puzzle))
            return false;
        if (obj == this)
            return true;

        Puzzle rhs = (Puzzle) obj;
        return (this.toString().equals(rhs.toString()));
    }
    
    public Puzzle clone() {
        Puzzle puzzle = new Puzzle();
        for (int i=0; i<4; i++) {
        	for (int j=0; j<4; j++) {
        		puzzle.matrix[i][j] = this.matrix[i][j];
            }
        }

        return puzzle;
    }
}
