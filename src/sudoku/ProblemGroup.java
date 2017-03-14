package sudoku;

class ProblemGroup {
	
	// sum of each number in the group (after solved)
	public static int TOTAL_SUM = 45;
	
	private ProblemCell[] numbers;
	
	public ProblemCell[] getNumbers() {
		return this.numbers;
	}
	public ProblemCell getNumber(int index) throws Exception {
		if (index < 0 || index >= 9) {
			throw new Exception("Index should be between 0 and 8");
		}
		return this.numbers[index];
	}
	
	public ProblemGroup() {
		this.numbers = new ProblemCell[9];
		
		for (int i=0; i<9; i++) {
			this.numbers[i] = new ProblemCell();
		}
	}
	
	public boolean isSolved() {
		int sum = 0;
		for (int i=0; i<9; i++) {
			int cellNumber = this.numbers[i].getCellNumber();
			//if this number group has 0 => it hasn't solved yet
			if (cellNumber == 0) {
				return false;
			}
			sum += cellNumber;
		}
		
		// check if sum equal 45
		if (sum != ProblemGroup.TOTAL_SUM) {
			return false;
		}
		
		// if this number group has duplicate number => it hasn't solved yet
		
		return true;
	}
}


