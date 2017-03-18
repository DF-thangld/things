package sudoku;

import java.util.ArrayList;

class ProblemGroup {
	
	// sum of each number in the group (after solved)
	public static int TOTAL_SUM = 45;
	
	private ArrayList<ProblemCell> cells;
	
	public ArrayList<ProblemCell> getCells() {
		return this.cells;
	}
	public ProblemCell getCell(int index) throws Exception {
		if (index < 0 || index >= 9) {
			throw new Exception("Index should be between 0 and 8");
		}
		return this.cells.get(index);
	}
	public void setCell(ProblemCell cell, int index) {
		this.cells.set(index, cell);
	}
	
	public ProblemGroup() throws Exception {
		this.cells = new ArrayList<ProblemCell>();
		
		for (int i=0; i<9; i++) {
			this.cells.add(new ProblemCell());
		}
	}
	
	public String getNumbersString() {
		StringBuilder result = new StringBuilder();
		for (int i=0; i<9; i++) {
			if (this.cells.get(i).getCellNumber() > 0) {
				result.append(this.cells.get(i).getCellNumber());
			}
		}
		return result.toString();
	}
	
	public void removePossibleNumber(int number) {
		for (int i=0; i<9; i++) {
			this.cells.get(i).getPossibleNumbers()[number] = false;
		}
	} 
	
	public boolean isSolved() {
		
		// check if sum equal 45
		int sum = 0;
		for (int i=0; i<9; i++) {
			int cellNumber = this.cells.get(i).getCellNumber();
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
			int checked_number = this.cells.get(i).getCellNumber();
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
	
	private StringBuilder output = new StringBuilder(); 
	private void combine(String stringToSearch, int lengthToFind, int start, ArrayList<String> result){
        for( int i = start; i < stringToSearch.length(); ++i ){
            output.append( stringToSearch.charAt(i) );
            if (output.length() == lengthToFind) {
            	result.add(output.toString());
            }
            if ( i < stringToSearch.length() ) {
            	this.combine(stringToSearch, lengthToFind, i + 1, result);
            }
            output.setLength( output.length() - 1 );
        }
    }
	
	//find all combinations of cells which have count of possible numbers equal number of cells 
	public ArrayList<ArrayList<ProblemCell>> findGroupingCells(int groupSize) throws NumberFormatException, Exception {
		ArrayList<ArrayList<ProblemCell>> result = new ArrayList<ArrayList<ProblemCell>>();
		
		// find all unsolved cells in format of string
		StringBuilder unsolvedCells = new StringBuilder();
		for (int i=0; i<9; i++) {
			if (this.cells.get(i).getCellNumber() != 0) {
				unsolvedCells.append(i);
			}
		}
		
		// find all combinations of unsolved cells with size equal to parameter
		ArrayList<String> combinations = new ArrayList<String>();
		this.combine(unsolvedCells.toString(), groupSize, 0, combinations);
		for (String combination: combinations) {
			StringBuilder possibleNumbers = new StringBuilder();
			ArrayList<ProblemCell> cells = new ArrayList<ProblemCell>();
			for (int i=0; i<groupSize; i++) {
				int indexOfCellInGroup = Integer.parseInt(combination.substring(i, i+1));
				// this shouldn't throw exception
				ProblemCell cell = this.getCell(indexOfCellInGroup);
				String possibleNumber = cell.getPossibleNumbersString(); 
				possibleNumbers.append(possibleNumber);
				
				// add cell to cells array
				cells.add(cell);
			}
			
			String compressedPossibleNumbers = Utils.removeDuplicateCharacterInString(possibleNumbers.toString());
			if (compressedPossibleNumbers.length() == groupSize) { // found new match
				result.add(cells);
			}
		}
		
		return result;
	}
}


