package bot.ai;

import bot.Board;

/**
 * Minimax (+Alpha-Beta) Implementation.
 * 
 * @author falvojr
 */
public class Minimax {

	private static final int UNKNOW_COL = -1;
	
	public int[] maxValue(Board board, int depth, int alpha, int beta) {
		int score = board.score();
		if (board.isFinished(depth, score)) {
			return new int[] { UNKNOW_COL, score };
		}
		int[] max = new int[] { UNKNOW_COL, Integer.MIN_VALUE };
		for (int col = 0; col < board.getCols(); col++) {
			Board clonedBoard = board.copy();
			if (clonedBoard.place(col)) {
				int[] nextMove = this.minValue(clonedBoard, depth - 1, alpha, beta);
				if (max[0] == UNKNOW_COL || nextMove[1] > max[1]) {
					max[0] = col;
					max[1] = nextMove[1];
					alpha = nextMove[1];
				}
				if (alpha >= beta) {
					return max;
				}
			}
		}
		return max;
	}

	public int[] minValue(Board board, int depth, int alpha, int beta) {
		int score = board.score();
		if (board.isFinished(depth, score)) {
			return new int[] { UNKNOW_COL, score };
		}
		int[] min = new int[] { UNKNOW_COL, Integer.MAX_VALUE };
		for (int col = 0; col < board.getCols(); col++) {
			final Board clonedBoard = board.copy();
			if (clonedBoard.place(col)) {
				int[] nextMove = this.maxValue(clonedBoard, depth - 1, alpha, beta);
				if (min[0] == UNKNOW_COL || nextMove[1] < min[1]) {
					min[0] = col;
					min[1] = nextMove[1];
					beta = nextMove[1];
				}
				if (alpha >= beta) {
					return min;
				}
			}
		}
		return min;
	}
	
	/**
	 * Private constructor for Singleton Pattern.
	 */
	private Minimax() {
		super();
	}

	private static class MinimaxHolder {
		public static final Minimax INSTANCE = new Minimax();
	}

	public static Minimax getInstance() {
		return MinimaxHolder.INSTANCE;
	}
}
