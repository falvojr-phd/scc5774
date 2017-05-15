package bot.util;

/**
 * Four in a row bot useful class.
 * 
 * @author falvojr
 */
public final class BotUtil {

	public static final int EMPTY_SLOT = 0;
	
	private BotUtil() {
		super();
	}

	/**
	 * Four in a row win verification.
	 * 
	 * @see https://codereview.stackexchange.com/a/127105/103486
	 * 
	 * @return int (disc) of the winner
	 */
	public static int checkWinner(int[][] board) {
		final int height = board.length;
		final int width = board[0].length;
		// iterate rows, bottom to top
		for (int row = 0; row < height; row++) { 		
			// iterate columns, left to right
			for (int col = 0; col < width; col++) { 	
				int player = board[row][col];
				if (player == EMPTY_SLOT) {
					// don't check empty slots
					continue; 							
				}
	            if (col + 3 < width && checkRight(board, row, col, player)) {
	                    return player;
	            }
	                if (row + 3 < height) {
	                    if (checkUp(board, row, col, player)) {
	                        return player;
	                    }
	                    if (col + 3 < width && checkUpRight(board, row, col, player)) {
	                        return player;
	                    }
	                    if (col - 3 >= 0 && checkUpLeft(board, row, col, player)) {
	                        return player;
	                    }
				}
			}
		}
		// no winner found
		return EMPTY_SLOT;
	}

	private static boolean checkUpLeft(int[][] board, int row, int col, int player) {
		return player == board[row + 1][col - 1] &&
		       player == board[row + 2][col - 2] &&
		       player == board[row + 3][col - 3];
	}

	private static boolean checkUpRight(int[][] board, int row, int col, int player) {
		return player == board[row + 1][col + 1] && 
			   player == board[row + 2][col + 2] && 
			   player == board[row + 3][col + 3];
	}

	private static boolean checkUp(int[][] board, int row, int col, int player) {
		return player == board[row + 1][col] && 
			   player == board[row + 2][col] && 
			   player == board[row + 3][col];
	}

	private static boolean checkRight(int[][] board, int row, int col, int player) {
		return player == board[row][col + 1] && 
			   player == board[row][col + 2] && 
			   player == board[row][col + 3];
	}
}
