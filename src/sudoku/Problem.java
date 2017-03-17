package sudoku;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class Problem {
	private ProblemGroup[] horizontalLines;
	private ProblemGroup[] verticalLines;
	private ProblemGroup[] blocks;
	
	public ProblemGroup[] getHorizontalLines() {
		return horizontalLines;
	}
	public ProblemGroup[] getVerticalLines() {
		return verticalLines;
	}
	public ProblemGroup[] getBlocks() {
		return blocks;
	}
	
	protected Problem()  {
		
	}
	
	public static Problem CreateProblem(ProblemGroup[] horizontalLines) throws Exception {
		if (horizontalLines.length != 9) {
			throw new Exception("The number of horizontal lines must be exactly 9");
		}
		
		Problem problem = new Problem();
		
		problem.horizontalLines = horizontalLines;
		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				problem.horizontalLines[i].getCell(j).setHorizontalGroup(problem.horizontalLines[i]);
			}
		}
		
		// initialize vertical lines
		problem.verticalLines = new ProblemGroup[9];
		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				ProblemCell cell = problem.horizontalLines[i].getCell(j);
				problem.verticalLines[j].setCell(cell, i);
				problem.verticalLines[j].getCell(i).setVerticalGroup(problem.verticalLines[j]);
			}
		}
		
		// initialize blocks
		problem.blocks = new ProblemGroup[9];
		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				ProblemCell cell = problem.horizontalLines[Math.floorDiv(j, 3) + Math.floorDiv(i, 3)*3].getCell((i%3)*3 + j - Math.floorDiv(j, 3)*3);
				problem.blocks[i].setCell(cell, j);
				problem.blocks[i].getCell(j).setBlockGroup(problem.blocks[i]);
			}
		}
		
		return problem;
	}
	
	/* create Sudoku problem from a file
	 * File format:
	 * 1234604...
	 * with "0" or any invalid number means an undiscovered cell
	*/
	public static Problem CreateProblem (String filename) throws Exception {
		String line;
		InputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		ProblemGroup[] horizontalLines = new ProblemGroup[9];
		int lineNumber = 0;
		try {
		    fis = new FileInputStream(filename);
		    isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
		    br = new BufferedReader(isr);
			while ((line = br.readLine()) != null && lineNumber<9) {
				horizontalLines[lineNumber] = new ProblemGroup();
				lineNumber++;
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
					
					ProblemCell cell = new ProblemCell(cellNumber);
					horizontalLines[lineNumber].setCell(cell, i);
				}
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
		return Problem.CreateProblem(horizontalLines);
	}
	
	// a problem is solved if all horizontal lines are solved (or vertical lines, or blocks)
	public boolean isSolved() {
		for (int i=0; i<9; i++) {
			if (!this.horizontalLines[i].isSolved()) {
				return false;
			}
		}
		
		return true;
	}
	
}
