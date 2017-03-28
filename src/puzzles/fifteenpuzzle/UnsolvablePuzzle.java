package puzzles.fifteenpuzzle;

public class UnsolvablePuzzle extends Exception {
	private static final long serialVersionUID = 1L;
	
	public UnsolvablePuzzle(String message) {
		super(message);
	}
}
