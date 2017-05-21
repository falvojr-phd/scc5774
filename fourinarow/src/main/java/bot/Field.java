package bot;

import static bot.BotConfig.*;

import bot.ai.Heuristic;

/**
 * Board 4 in a row abstraction.
 * 
 * @author Venilton FalvoJr <falvojr@gmail.com>
 */
public class Field {

	private int[][] board;
	private int cols;
	private int rows;
	private int player;

	public Field(final int[][] board, final int player) {
		this.board = board;
		this.rows = board.length;
		this.cols = board[0].length;
		this.player = player;
	}

	public Field(final int rows, final int cols) {
		this.board = new int[rows][cols];
		this.rows = rows;
		this.cols = cols;
	}

	public int[][] getBoard() {
		return board;
	}

	public void setCols(final int cols) {
		this.cols = cols;
		this.board = new int[rows][cols];
	}

	public void setRows(final int rows) {
		this.rows = rows;
		this.board = new int[rows][cols];
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(final int player) {
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
		final String[] dataArray = data.split(",");
		int counter = 0;
		for (int row = 0; row < this.rows; row++) {
			for (int col = 0; col < this.cols; col++) {
				this.board[row][col] = Integer.parseInt(dataArray[counter]);
				counter++;
			}
		}
	}
	
	public boolean isEmpty() {
		for (int col = 0; col < this.cols; col++) {
			if (!isEmptyColumn(col)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isTerminal(final int depth, final int score) {
		if (depth == 0 || score == SCORE || score == -SCORE || isFull()) {
			return true;
		}
		return false;
	}

	public boolean addDisc(final int col) {
		if (isNonFullColumn(col) && col >= 0 && col < this.cols) {
			for (int row = this.rows - 1; row >= 0; row--) {
				if (this.board[row][col] == EMPTY_SLOT) {
					this.board[row][col] = this.player;
					break;
				}
			}
			this.player = switchRound(this.player);
			return true;
		} else {
			return false;
		}
	}

	public Field clone() {
		final int[][] newBoard = new int[this.board.length][];
		for (int i = 0; i < this.board.length; i++) {
			final int[] aMatrix = this.board[i];
			final int aLength = aMatrix.length;
			newBoard[i] = new int[aLength];
			System.arraycopy(aMatrix, 0, newBoard[i], 0, aLength);
		}
		return new Field(newBoard, this.player);
	}

	public int score() {
		return Heuristic.getInstance().score(this);
	}

	private boolean isFull() {
		for (int col = 0; col < this.cols; col++) {
			if (isNonFullColumn(col)) {
				return false;
			}
		}
		return true;
	}

	private boolean isNonFullColumn(final int col) {
		return this.board[0][col] == EMPTY_SLOT;
	}
	
	private boolean isEmptyColumn(final int col) {
		return this.board[this.rows - 1][col] == EMPTY_SLOT;
	}
	
	private int switchRound(int round) {
		if (round == 1) {
			return 2;
		} else {
			return 1;
		}
	}

}
