package sudoku;

public class ProblemCell {
	private int cellNumber = 0;
	private ProblemGroup horizontalGroup = null;
	private ProblemGroup verticalGroup = null;
	private ProblemGroup blockGroup = null;
	private int positionInHorizontalGroup = 0;
	private int positionInVerticalGroup = 0;
	private int positionInBlockGroup = 0;
	private String id;
	
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

	public ProblemGroup getHorizontalGroup() {
		return horizontalGroup;
	}

	public void setHorizontalGroup(ProblemGroup horizontalGroup) {
		this.horizontalGroup = horizontalGroup;
	}

	public ProblemGroup getVerticalGroup() {
		return verticalGroup;
	}

	public void setVerticalGroup(ProblemGroup verticalGroup) {
		this.verticalGroup = verticalGroup;
	}

	public ProblemGroup getBlockGroup() {
		return blockGroup;
	}

	public void setBlockGroup(ProblemGroup blockGroup) {
		this.blockGroup = blockGroup;
	}

	// When set a number to the cell, all possible numbers become null
	// Also, remove that number from possible numbers in all cells of its horizontal, vertical and block group
	public void setCellNumber(int cellNumber) throws Exception {
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
	}
	
	public int getCellNumber() {
		return this.cellNumber;
	}
	
	private boolean[] possibleNumbers = new boolean[9];
	public boolean[] getPossibleNumbers() {
		return this.possibleNumbers;
	}
	
	public ProblemCell() throws Exception {
		this.setCellNumber(0);
		this.id = Utils.generateRandomString(20);
		for (int i=0; i<9; i++) {
			this.possibleNumbers[i] = true;
		}
	}
	
	public ProblemCell(int cellNumber) throws Exception {
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
}
