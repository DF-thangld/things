package sudoku;

public class ProblemCell {
	private int cellNumber = 0;
	
	// when set a number to the cell, all possible numbers become null
	public void setCellNumber(int cellNumber) {
		this.cellNumber = cellNumber;
		for (int i=0; i<9; i++) {
			this.getPossibleNumbers()[i] = false;
		}
	}
	
	public int getCellNumber() {
		return this.cellNumber;
	}
	
	private boolean[] possibleNumbers = new boolean[9];
	public boolean[] getPossibleNumbers() {
		return this.possibleNumbers;
	}
	
	public ProblemCell() {
		this.setCellNumber(0);
	}
	
	public ProblemCell(int cellNumber) {
		this();
		this.setCellNumber(cellNumber);
	}
	
}
