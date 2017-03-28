package puzzles.sudoku;

import java.util.ArrayList;

import puzzles.Utils;

class Group {
	
    // sum of each number in the group (after solved)
    public static int TOTAL_SUM = 45;

    private ArrayList<Cell> cells;

    public ArrayList<Cell> getCells() {
            return this.cells;
    }
    public Cell getCell(int index) {
            if (index < 0 || index >= 9) {
                    return null;
            }
            return this.cells.get(index);
    }
    public void setCell(Cell cell, int index) {
            this.cells.set(index, cell);
    }

    public Group() throws Exception {
            this.cells = new ArrayList<Cell>();

            for (int i=0; i<9; i++) {
                    this.cells.add(new Cell());
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
            this.cells.get(i).getPossibleNumbers()[number-1] = false;
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
            if (sum != Group.TOTAL_SUM) {
                return false;
            }

            // check duplicate number in the group
            boolean[] checked_array = new boolean[9];
            for (int i=0; i<9; i++) {
                checked_array[i] = false;
            }
            for (int i=0; i<9; i++) {
                int checked_number = this.cells.get(i).getCellNumber();
                if (checked_array[checked_number - 1]) {
                    return false;
                }
                else {
                    checked_array[checked_number - 1] = true;
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
    public ArrayList<ArrayList<Cell>> findGroupingCells(int groupSize) throws NumberFormatException, Exception {
        ArrayList<ArrayList<Cell>> result = new ArrayList<ArrayList<Cell>>();

        // find all unsolved cells in format of string
        StringBuilder unsolvedCells = new StringBuilder();
        for (int i=0; i<9; i++) {
            if (this.cells.get(i).getCellNumber() == 0) {
                unsolvedCells.append(i);
            }
        }
        
        //return blank array if unsolved cells smaller or equal to group size to find
        if (unsolvedCells.length() <= groupSize) {
            return result;
        }

        // find all combinations of unsolved cells with size equal to parameter
        ArrayList<String> combinations = new ArrayList<String>();
        this.combine(unsolvedCells.toString(), groupSize, 0, combinations);
        for (String combination: combinations) {
                StringBuilder possibleNumbers = new StringBuilder();
                ArrayList<Cell> cells = new ArrayList<Cell>();
                for (int i=0; i<groupSize; i++) {
                        int indexOfCellInGroup = Integer.parseInt(combination.substring(i, i+1));
                        // this shouldn't throw exception
                        Cell cell = this.getCell(indexOfCellInGroup);
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
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i=0; i<9; i++) {
            result.append(this.getCell(i).getCellNumber());
        }
        
        return result.toString();
    }
}


