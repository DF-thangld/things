package puzzles.fifteenpuzzle;

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
	
	public int[] getMovingCellCoordinate() throws InvalidPuzzle {
		for (int i=0; i<4; i++) {
			for (int j=0; j<4; j++) {
				if (this.getNumberAt(i, j) == 0) {
					return new int[] {i, j};
				}
			}
		}
		throw new InvalidPuzzle("Moving cell not found");
	}
	
	// change state of the puzzle according to the direction
	// return true if it can move, false if cannot move
	public boolean move(Direction direction) throws InvalidPuzzle {
		int[] coordinate = this.getMovingCellCoordinate();
		if (direction == Direction.Up) {
			if (coordinate[1] == 0) {
				return false;
			}
			int targetedNumber = this.getNumberAt(coordinate[0], coordinate[1] - 1);
			this.setNumberAt(coordinate[0], coordinate[1] - 1, 0);
			this.setNumberAt(coordinate[0], coordinate[1], targetedNumber);
		}
		else if (direction == Direction.Down) {
			if (coordinate[1] == 3) {
				return false;
			}
			int targetedNumber = this.getNumberAt(coordinate[0], coordinate[1] + 1);
			this.setNumberAt(coordinate[0], coordinate[1] + 1, 0);
			this.setNumberAt(coordinate[0], coordinate[1], targetedNumber);
		}
		
		else if (direction == Direction.Left) {
			if (coordinate[0] == 0) {
				return false;
			}
			int targetedNumber = this.getNumberAt(coordinate[0] - 1, coordinate[1]);
			this.setNumberAt(coordinate[0] - 1, coordinate[1], 0);
			this.setNumberAt(coordinate[0], coordinate[1], targetedNumber);
		}
		
		else if (direction == Direction.Right) {
			if (coordinate[0] == 3) {
				return false;
			}
			int targetedNumber = this.getNumberAt(coordinate[0] + 1, coordinate[1]);
			this.setNumberAt(coordinate[0] + 1, coordinate[1], 0);
			this.setNumberAt(coordinate[0], coordinate[1], targetedNumber);
		}
		
		return true;
	}
}
