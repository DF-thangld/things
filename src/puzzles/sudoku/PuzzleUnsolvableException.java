package puzzles.sudoku;

public class PuzzleUnsolvableException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PuzzleUnsolvableException() {

	}

	public PuzzleUnsolvableException(String message) {
		super(message);
	}

	public PuzzleUnsolvableException(Throwable cause) {
		super(cause);
	}

	public PuzzleUnsolvableException(String message, Throwable cause) {
		super(message, cause);
	}

	public PuzzleUnsolvableException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
