package sudoku;

public class Cell {
	private int cellNumber = 0;
	private Group horizontalGroup = null;
	private Group verticalGroup = null;
	private Group blockGroup = null;
	private int positionInHorizontalGroup = 0;
	private int positionInVerticalGroup = 0;
	private int positionInBlockGroup = 0;
	private String id;
	private boolean solved = false;
	private Puzzle puzzle;
	
	public String getId() {
		return id;
	}
	public int getPositionInHorizontalGroup() {
		return positionInHorizontalGroup;
	}

	public void setPositionInHorizontalGroup(int positionInHorizontalGroup) {
		this.positionInHorizontalGroup = positionInHorizontalGroup;
	}

	public int getPositionInVerticalGroup() {
		return positionInVerticalGroup;
	}

	public void setPositionInVerticalGroup(int positionInVerticalGroup) {
		this.positionInVerticalGroup = positionInVerticalGroup;
	}

	public int getPositionInBlockGroup() {
		return positionInBlockGroup;
	}

	public void setPositionInBlockGroup(int positionInBlockGroup) {
		this.positionInBlockGroup = positionInBlockGroup;
	}

	public Group getHorizontalGroup() {
		return horizontalGroup;
	}

	public void setHorizontalGroup(Group horizontalGroup) {
		this.horizontalGroup = horizontalGroup;
	}

	public Group getVerticalGroup() {
		return verticalGroup;
	}

	public void setVerticalGroup(Group verticalGroup) {
		this.verticalGroup = verticalGroup;
	}

	public Group getBlockGroup() {
		return blockGroup;
	}

	public void setBlockGroup(Group blockGroup) {
		this.blockGroup = blockGroup;
	}

	// When set a number to the cell, all possible numbers become null
	// Also, remove that number from possible numbers in all cells of its horizontal, vertical and block group
	public boolean setCellNumber(int cellNumber) {
		if (cellNumber < 0 || cellNumber > 9) {
			return false;
		}
			
		this.cellNumber = cellNumber;
		if (cellNumber != 0) {
			for (int i=0; i<9; i++) {
				this.getPossibleNumbers()[i] = false;
				if (this.horizontalGroup != null) {
					this.horizontalGroup.getCell(i).getPossibleNumbers()[cellNumber - 1] = false;
				}
				if (this.verticalGroup != null) {
					this.verticalGroup.getCell(i).getPossibleNumbers()[cellNumber - 1] = false;
				}
				if (this.blockGroup != null) {
					this.blockGroup.getCell(i).getPossibleNumbers()[cellNumber - 1] = false;
				}
			}
		}
		return true;
	}
	
	public int getCellNumber() {
		return this.cellNumber;
	}
	
	private boolean[] possibleNumbers = new boolean[9];
	public boolean[] getPossibleNumbers() {
		return this.possibleNumbers;
	}
	
	public Cell() {
		this.setCellNumber(0);
		this.id = Utils.generateRandomString(20);
		for (int i=0; i<9; i++) {
			this.possibleNumbers[i] = true;
		}
	}
	
	public Cell(int cellNumber) {
		this();
		if (cellNumber != 0) {
			this.setCellNumber(cellNumber);
		}
		
	}
	
	public String getPossibleNumbersString() {
		StringBuilder builder = new StringBuilder();
		for (int i=0; i<9; i++) {
			if (this.possibleNumbers[i]) {
				builder.append(String.valueOf(i+1));
			}	
		}
		
		return builder.toString();
	}
	
	// check if the cell is solved
	public boolean isSolved() {
		if (this.solved) {
			return this.solved;
		}
		
		if (this.getCellNumber() != 0) {
			this.solved = true;
			return this.solved;
		}
		return false;
	}
	public Puzzle getPuzzle() {
		return puzzle;
	}
	public void setPuzzle(Puzzle puzzle) {
		this.puzzle = puzzle;
	}
}
