package bot.ai;

import bot.BotParser;
import bot.Field;

/**
 * Heuristic (utility funtion) implementation.
 * 
 * @author Venilton FalvoJr <falvojr@gmail.com>
 */
public class Heuristic {

	/**
	 * Win score.
	 */
	public static final short WIN = Short.MAX_VALUE;	
	/**
	 * Loss score.
	 */
	public static final short LOSS = Short.MIN_VALUE;
	
	/**
	 * Score function with following checks:<br>
	 * <br>
	 * 1. Vertical points. Check each column for vertical score. Possible situations:<br>
     * <br>
     *  0  1  2  3  4  5  6   <br>
     * [x][ ][ ][ ][ ][ ][ ] 0<br>
     * [x][x][ ][ ][ ][ ][ ] 1<br>
     * [x][x][x][ ][ ][ ][ ] 2<br>
     * [x][x][x][ ][ ][ ][ ] 3<br>
     * [ ][x][x][ ][ ][ ][ ] 4<br>
     * [ ][ ][x][ ][ ][ ][ ] 5<br>
     * <br>
     * <br>
     * 2. Horizontal points. Check each row's score. Possible situations:<br>
     * <br>
     *  0  1  2  3  4  5  6   <br>
     * [x][x][x][x][ ][ ][ ] 0<br>
     * [ ][x][x][x][x][ ][ ] 1<br>
     * [ ][ ][x][x][x][x][ ] 2<br>
     * [ ][ ][ ][x][x][x][x] 3<br>
     * [ ][ ][ ][ ][ ][ ][ ] 4<br>
     * [ ][ ][ ][ ][ ][ ][ ] 5<br>
     * <br>
     * <br>
     * 3. Diagonal points 1 (left-bottom). Possible situation:<br>
     * <br>
     *  0  1  2  3  4  5  6   <br>
     * [x][ ][ ][ ][ ][ ][ ] 0<br>
     * [ ][x][ ][ ][ ][ ][ ] 1<br>
     * [ ][ ][x][ ][ ][ ][ ] 2<br>
     * [ ][ ][ ][x][ ][ ][ ] 3<br>
     * [ ][ ][ ][ ][ ][ ][ ] 4<br>
     * [ ][ ][ ][ ][ ][ ][ ] 5<br>
     * <br>
     * <br>
     * 4. Diagonal points 2 (right-bottom). Possible situation:<br>
     * <br>
     *  0  1  2  3  4  5  6   <br>
     * [ ][ ][ ][x][ ][ ][ ] 0<br>
     * [ ][ ][x][ ][ ][ ][ ] 1<br>
     * [ ][x][ ][ ][ ][ ][ ] 2<br>
     * [x][ ][ ][ ][ ][ ][ ] 3<br>
     * [ ][ ][ ][ ][ ][ ][ ] 4<br>
     * [ ][ ][ ][ ][ ][ ][ ] 5<br>
	 */
	public short score(final Field field) {
		final short[][] board = field.getBoard();
		short scoreVertical = 0;
		for (short row = 0; row < field.getRows() - 3; row++) {
			for (short col = 0; col < field.getCols(); col++) {
				final short score = scorePosition(board, row, col, 1, 0);
				if (isTerminal(score)) {
					return score;
				}
				scoreVertical += score;
			}
		}
		
		short scoreHorizontal = 0;
		for (short row = 0; row < field.getRows(); row++) {
			for (short col = 0; col < field.getCols() - 3; col++) {
				final short score = scorePosition(board, row, col, 0, 1);
				if (isTerminal(score)) {
					return score;
				}
				scoreHorizontal += score;
			}
		}
		
		short scoreDiagonalLeftBottom = 0;
		for (short row = 0; row < field.getRows() - 3; row++) {
			for (short col = 0; col < field.getCols() - 3; col++) {
				final short score = scorePosition(board, row, col, 1, 1);
				if (isTerminal(score)) {
					return score;
				}
				scoreDiagonalLeftBottom += score;
			}
		}
		
		short scoreDiagonalRightBottom = 0;
		for (short row = 3; row < field.getRows(); row++) {
			for (short col = 0; col < field.getCols() - 3; col++) {
				final short score = scorePosition(board, row, col, -1, +1);
				if (isTerminal(score)) {
					return score;
				}
				scoreDiagonalRightBottom += score;
			}
		}
		
		return (short) (scoreVertical + scoreHorizontal + scoreDiagonalLeftBottom + scoreDiagonalRightBottom);
	}
	
	private short scorePosition(final short[][] board, short row, short col, final int deltaY, final int deltaX) {
		short enemyPoints = 0;
		short botPoints = 0;
		for (short i = 0; i < 4; i++) {
			final short disc = board[row][col];
			if (disc == BotParser.getEnemyId()) {
				enemyPoints++;
			} else if (disc == BotParser.getBotId()) {
				botPoints++;
			}
			row += deltaY;
			col += deltaX;
		}
		if (enemyPoints == 4) {
			return LOSS;
		} else if (botPoints == 4) {
			return WIN;
		} else {
			return botPoints;
		}
	}
	
	private boolean isTerminal(final short score) {
		return score == WIN || score == LOSS;
	}
	
	/**
	 * Private constructor for Singleton Pattern.
	 */
	private Heuristic() {
		super();
	}

	private static class HeuristicHolder {
		public static final Heuristic INSTANCE = new Heuristic();
	}

	public static Heuristic getInstance() {
		return HeuristicHolder.INSTANCE;
	}
}
