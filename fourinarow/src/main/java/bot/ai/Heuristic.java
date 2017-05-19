package bot.ai;

import static bot.BotConfig.SCORE;

import bot.Field;
import bot.BotParser;

/**
 * Heuristic (utility funtion) implementation.
 * 
 * @author Venilton FalvoJr <falvojr@gmail.com>
 */
public class Heuristic {

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
	public int score(final Field field) {
		int scoreVertical = 0;
		for (int row = 0; row < field.getRows() - 3; row++) {
			for (int col = 0; col < field.getCols(); col++) {
				final int score = scorePosition(field, row, col, 1, 0);
				if (score == SCORE || score == -SCORE) {
					return score;
				}
				scoreVertical += score;
			}
		}
		int scoreHorizontal = 0;
		for (int row = 0; row < field.getRows(); row++) {
			for (int col = 0; col < field.getCols() - 3; col++) {
				final int score = scorePosition(field, row, col, 0, 1);
				if (score == SCORE || score == -SCORE) {
					return score;
				}
				scoreHorizontal += score;
			}
		}
		int scoreDiagonalLeftBottom = 0;
		for (int row = 0; row < field.getRows() - 3; row++) {
			for (int col = 0; col < field.getCols() - 3; col++) {
				final int score = scorePosition(field, row, col, 1, 1);
				if (score == SCORE || score == -SCORE) {
					return score;
				}
				scoreDiagonalLeftBottom += score;
			}
		}
		int scoreDiagonalRightBottom = 0;
		for (int row = 3; row < field.getRows(); row++) {
			for (int col = 0; col <= field.getCols() - 4; col++) {
				final int score = scorePosition(field, row, col, -1, +1);
				if (score == SCORE || score == -SCORE) {
					return score;
				}
				scoreDiagonalRightBottom += score;
			}
		}
		return scoreVertical + scoreHorizontal + scoreDiagonalLeftBottom + scoreDiagonalRightBottom;
	}
	
	private int scorePosition(final Field field, int row, int col, final int deltaY, final int deltaX) {
		int enemyPoints = 0;
		int myPoints = 0;
		for (int i = 0; i < 4; i++) {
			if (field.getBoard()[row][col] == getEnemyId()) {
				enemyPoints++;
			} else if (field.getBoard()[row][col] == getMyId()) {
				myPoints++;
			}
			row += deltaY;
			col += deltaX;
		}
		if (enemyPoints == 4) {
			return -SCORE;
		} else if (myPoints == 4) {
			return SCORE;
		} else {
			return myPoints;
		}
	}
	
	private int getMyId() {
		return BotParser.mBotId;
	}

	private int getEnemyId() {
		return this.getMyId() == 1 ? 2 : 1;
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
