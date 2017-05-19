package bot;

import static bot.BotConfig.*;

import bot.ai.Heuristic;

/**
 * Board 4 in a row abstraction.
 * 
 * @author Venilton FalvoJr <falvojr@gmail.com>
 */
public class Board {

	private int[][] field;
	private int cols;
	private int rows;
	private int player;

	public Board(final int[][] field, final int player) {
		this.field = field;
		this.rows = field.length;
		this.cols = field[0].length;
		this.player = player;
	}

	public Board(final int rows, final int cols) {
		this.field = new int[rows][cols];
		this.rows = rows;
		this.cols = cols;
	}

	public int[][] getField() {
		return field;
	}

	public void setCols(final int cols) {
		this.cols = cols;
		this.field = new int[rows][cols];
	}

	public void setRows(final int rows) {
		this.rows = rows;
		this.field = new int[rows][cols];
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
				this.field[row][col] = Integer.parseInt(dataArray[counter]);
				counter++;
			}
		}
	}

	public boolean isTerminal(final int depth, final int score) {
		if (depth == 0 || score == SCORE || score == -SCORE || isFull()) {
			return true;
		}
		return false;
	}

	public boolean isValidMove(final int col) {
		return this.field[0][col] == EMPTY_SLOT;
	}

	public boolean addDisc(final int col) {
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

	public Board clone() {
		final int[][] newField = new int[this.field.length][];
		for (int i = 0; i < this.field.length; i++) {
			final int[] aMatrix = this.field[i];
			final int aLength = aMatrix.length;
			newField[i] = new int[aLength];
			System.arraycopy(aMatrix, 0, newField[i], 0, aLength);
		}
		return new Board(newField, this.player);
	}

	public int score() {
		return Heuristic.getInstance().score(this);
	}

	private boolean isFull() {
		for (int col = 0; col < COLS; col++) {
			if (isValidMove(col)) {
				return false;
			}
		}
		return true;
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
