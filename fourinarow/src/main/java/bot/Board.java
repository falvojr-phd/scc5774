package bot;

import static bot.BotConfig.*;

/**
 * Board 4 in a row abstraction.
 * 
 * @author Venilton FalvoJr <falvojr@gmail.com>, 
 */
public class Board {

	private int[][] field;
	private int cols;
	private int rows;
	private int player;

	public Board(int[][] field, int player) {
		this.field = field;
		this.rows = field.length;
		this.cols = field[0].length;
		this.player = player;
	}
	
	public Board(int rows, int cols) {
		this.field = new int[rows][cols];
		this.rows = rows;
		this.cols = cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
		this.field = new int[rows][cols];
	}

	public void setRows(int rows) {
		this.rows = rows;
		this.field = new int[rows][cols];
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public int getCols() {
		return cols;
	}

	public int getRows() {
		return rows;
	}

	public void parseFromString(String data) {
		data = data.replace(';', ',');
		String[] dataArray = data.split(",");
		int counter = 0;
		for (int row = 0; row < this.rows; row++) {
			for (int col = 0; col < this.cols; col++) {
				this.field[row][col] = Integer.parseInt(dataArray[counter]); 
				counter++;
			}
		}
	}
	
	public boolean isTerminal(int depth, int score) {
		if (depth == 0 || score == SCORE || score == -SCORE || isFull()) {
			return true;
		}
		return false;
	}
	
	public boolean isValidMove(int col) {
		return this.field[0][col] == EMPTY_SLOT;
	}
	
	public boolean addDisc(int col) {
	    if (isValidMove(col) && col >= 0 && col < this.cols) {
	        for (int row = this.rows - 1; row >= 0; row--) {
	            if (this.field[row][col] == EMPTY_SLOT) {
	                this.field[row][col] = this.player;
	                break;
	            }
	        }
	        this.player = switchRound(this.player);
	        return true;
	    } else {
	        return false;
	    }
	}
	
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
	public int score() {
		int scoreVertical = 0;
		for (int row = 0; row < this.rows - 3; row++) {
			for (int col = 0; col < this.cols; col++) {
				final int score = scorePosition(row, col, 1, 0);
				if (score == SCORE || score == -SCORE) {
					return score;
				}
				scoreVertical += score;
			}
		}
		int scoreHorizontal = 0;
		for (int row = 0; row < this.rows; row++) {
			for (int col = 0; col < this.cols - 3; col++) {
				final int score = scorePosition(row, col, 0, 1);
				if (score == SCORE || score == -SCORE) {
					return score;
				}
				scoreHorizontal += score;
			}
		}
		int scoreDiagonalLeftBottom = 0;
		for (int row = 0; row < this.rows - 3; row++) {
			for (int col = 0; col < this.cols - 3; col++) {
				final int score = scorePosition(row, col, 1, 1);
				if (score == SCORE || score == -SCORE) {
					return score;
				}
				scoreDiagonalLeftBottom += score;
			}
		}
		int scoreDiagonalRightBottom = 0;
		for (int row = 3; row < this.rows; row++) {
			for (int col = 0; col <= this.cols - 4; col++) {
				final int score = scorePosition(row, col, -1, +1);
				if (score == SCORE || score == -SCORE) {
					return score;
				}
				scoreDiagonalRightBottom += score;
			}
		}
		return scoreVertical + scoreHorizontal + scoreDiagonalLeftBottom + scoreDiagonalRightBottom;
	}
	
	public Board clone() {
		int [][] newField = new int[this.field.length][];
		for(int i = 0; i < this.field.length; i++) {
		  int[] aMatrix = this.field[i];
		  int   aLength = aMatrix.length;
		  newField[i] = new int[aLength];
		  System.arraycopy(aMatrix, 0, newField[i], 0, aLength);
		}
	    return new Board(newField, this.player);
	}

	private boolean isFull() {
		for (int col = 0; col < COLS; col++) {
			if (isValidMove(col)) {
				return false;
			}
		}
		return true;
	}
	
	private int scorePosition(int row, int col, int deltaY, int deltaX) {
		int enemyPoints = 0;
		int myPoints = 0;
		for (int i = 0; i < 4; i++) {
			if (this.field[row][col] == getEnemyId()) {
				enemyPoints++;
			} else if (this.field[row][col] == getMyId()) {
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

	private int switchRound(int round) {
		// 1 My Bot, 2 Enemy
		if (round == 1) {
			return 2;
		} else {
			return 1;
		}
	}
}
