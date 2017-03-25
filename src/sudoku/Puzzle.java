package sudoku;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Puzzle implements Cloneable {
    private Group[] horizontalLines;
    private Group[] verticalLines;
    private Group[] blocks;
    private boolean solved = false;

    public Group[] getHorizontalLines() {
        return horizontalLines;
    }
    public Group[] getVerticalLines() {
        return verticalLines;
    }
    public Group[] getBlocks() {
        return blocks;
    }

    public ArrayList<Cell> getUnsolvedCells() {
        ArrayList<Cell> result = new ArrayList<Cell>();
        for (Group group: this.getVerticalLines()) {
            for (Cell cell: group.getCells()) {
                if (!cell.isSolved()) {
                    result.add(cell);
                }
            }
        }

        return result;
    }
    
    public ArrayList<Cell> getSolvedCells() {
        ArrayList<Cell> result = new ArrayList<Cell>();
        for (Group group: this.getVerticalLines()) {
            for (Cell cell: group.getCells()) {
                if (cell.isSolved()) {
                    result.add(cell);
                }
            }
        }

        return result;
    }

    public ArrayList<Group> getAllGroups() {
        ArrayList<Group> groups = new ArrayList<Group>();
        for (int i=0; i<9; i++) {
            groups.add(this.getVerticalLines()[i]);
            groups.add(this.getHorizontalLines()[i]);
            groups.add(this.getBlocks()[i]);
        }

        return groups;
    }

    protected Puzzle()  {

    }

    public static Puzzle CreateBlankPuzzle() throws Exception {

        Group[] horizontalLines = new Group[9];
        for (int i=0; i<9; i++) {
            horizontalLines[i] = new Group();
            for (int j=0; j<9; j++) {
                Cell cell = new Cell();
                horizontalLines[i].setCell(cell, i);
            }
        }

        return Puzzle.CreatePuzzle(horizontalLines);
    }

    public static Puzzle CreatePuzzle(Group[] horizontalLines) throws Exception {
        if (horizontalLines.length != 9) {
            throw new Exception("The number of horizontal lines must be exactly 9");
        }

        Puzzle puzzle = new Puzzle();

        puzzle.horizontalLines = horizontalLines;
        puzzle.verticalLines = new Group[9];
        puzzle.blocks = new Group[9];
        for (int i=0; i<9; i++) {
            puzzle.verticalLines[i] = new Group();
            puzzle.blocks[i] = new Group();
            for (int j=0; j<9; j++) {
                puzzle.horizontalLines[i].getCell(j).setHorizontalGroup(puzzle.horizontalLines[i]);
            }
        }


        // initialize vertical lines

        for (int i=0; i<9; i++) {

            for (int j=0; j<9; j++) {
                Cell cell = puzzle.horizontalLines[i].getCell(j);
                puzzle.verticalLines[j].setCell(cell, i);
                puzzle.verticalLines[j].getCell(i).setVerticalGroup(puzzle.verticalLines[j]);
            }
        }

        // initialize blocks

        for (int i=0; i<9; i++) {

            for (int j=0; j<9; j++) {
                Cell cell = puzzle.horizontalLines[Math.floorDiv(j, 3) + Math.floorDiv(i, 3)*3].getCell((i%3)*3 + j - Math.floorDiv(j, 3)*3);
                puzzle.blocks[i].setCell(cell, j);
                puzzle.blocks[i].getCell(j).setBlockGroup(puzzle.blocks[i]);
            }
        }

        // assign all cells to the the puzzle
        for (int i=0; i<9; i++) {
            for (int j=0; j<9; j++) {
                puzzle.getHorizontalLines()[i].getCell(j).setPuzzle(puzzle);
            }
        }
        return puzzle;
    }

    /* create Sudoku Puzzle from a file
     * File format:
     * 1234604...
     * with "0" or any invalid number means an undiscovered cell
    */
    public static Puzzle CreatePuzzle (String filename) throws Exception {
        String line;
        InputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        Group[] horizontalLines = new Group[9];
        int lineNumber = 0;
        try {
            fis = new FileInputStream(filename);
            isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            br = new BufferedReader(isr);
            while ((line = br.readLine()) != null && lineNumber<9) {
                horizontalLines[lineNumber] = new Group();

                if (line.length() != 9) {
                    throw new Exception("Wrong file format, please check the file again");
                }
                for (int i=0; i<9; i++) {
                    int cellNumber = 0;
                    try {
                        cellNumber = Integer.parseInt(String.valueOf(line.charAt(i)));
                    }
                    catch (Exception e) {
                        cellNumber = 0;
                    }
                    if (cellNumber < 0 || cellNumber > 9) {
                        cellNumber = 0;
                    }

                    Cell cell = new Cell(cellNumber);
                    horizontalLines[lineNumber].setCell(cell, i);
                }
                lineNumber++;
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            //close resource
            try {
                    br.close();
                    isr.close();
                    fis.close();
            }
            catch (Exception e) {
            }

        }

        //finish reading file into horizontal lines
        return Puzzle.CreatePuzzle(horizontalLines);
    }

    // a Puzzle is solved if all horizontal lines are solved (or vertical lines, or blocks)
    public boolean isSolved() {
        if (this.solved) {
            return true;
        }

        for (int i=0; i<9; i++) {
            if (!this.horizontalLines[i].isSolved()) {
                return false;
            }
        }

        this.solved = true;
        return true;
    }

    public void saveToFile(String filename) throws IOException {
        File fout = new File(filename);
        FileOutputStream fos = new FileOutputStream(fout);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        for (Group group: this.verticalLines) {
            for (Cell cell: group.getCells()) {
                bw.write(cell.getCellNumber());
            }
            bw.newLine();
            bw.flush();
        }


        bw.close();
    }

    public Puzzle clone() {
        Puzzle puzzle = null;
        try {
            puzzle = Puzzle.CreateBlankPuzzle();
            for (int i=0; i<9; i++) {
                for (int j=0; j<9; j++) {
                    Cell oldCell = this.getHorizontalLines()[i].getCell(j);
                    Cell newCell = puzzle.getHorizontalLines()[i].getCell(j);
                    newCell.setCellNumber(oldCell.getCellNumber());
                    for (int k=0; k<9; k++) {
                        newCell.getPossibleNumbers()[k] = oldCell.getPossibleNumbers()[k];
                    }
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return puzzle;
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i=0; i<9; i++) {
            result.append(this.getVerticalLines()[i].toString());
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
	
}
