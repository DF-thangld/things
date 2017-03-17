package sudoku;

class ProblemGroup {
	
	// sum of each number in the group (after solved)
	public static int TOTAL_SUM = 45;
	
	private ProblemCell[] cells;
	
	public ProblemCell[] getCells() {
		return this.cells;
	}
	public ProblemCell getCell(int index) throws Exception {
		if (index < 0 || index >= 9) {
			throw new Exception("Index should be between 0 and 8");
		}
		return this.cells[index];
	}
	public void setCell(ProblemCell cell, int index) {
		this.cells[index] = cell;
	}
	
	public ProblemGroup() throws Exception {
		this.cells = new ProblemCell[9];
		
		for (int i=0; i<9; i++) {
			this.cells[i] = new ProblemCell();
		}
	}
	
	public String getNumbersString() {
		StringBuilder result = new StringBuilder();
		for (int i=0; i<9; i++) {
			if (this.cells[i].getCellNumber() > 0) {
				result.append(this.cells[i].getCellNumber());
			}
		}
		return result.toString();
	}
	
	public void removePossibleNumber(int number) {
		for (int i=0; i<9; i++) {
			this.cells[i].getPossibleNumbers()[number] = false;
		}
	} 
	
	public boolean isSolved() {
		
		// check if sum equal 45
		int sum = 0;
		for (int i=0; i<9; i++) {
			int cellNumber = this.cells[i].getCellNumber();
			//if this number group has 0 or number > 9 => it hasn't solved yet
			if (cellNumber <= 0 || cellNumber > 9) {
				return false;
			}
			sum += cellNumber;
		}
		if (sum != ProblemGroup.TOTAL_SUM) {
			return false;
		}
		
		// check duplicate number in the group
		boolean[] checked_array = new boolean[9];
		for (int i=0; i<9; i++) {
			checked_array[i] = false;
		}
		for (int i=0; i<9; i++) {
			int checked_number = this.cells[i].getCellNumber();
			if (checked_array[checked_number]) {
				return false;
			}
			else {
				checked_array[checked_number] = true;
			}
		}
		
		// otherwise the group is solved
		return true;
	}
	
	//find all combinations of cells which have count of possible numbers equal number of cells 
	public ProblemCell[][] findGroupingCells(int groupSize) {
		
	}
}


