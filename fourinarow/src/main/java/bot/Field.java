package bot;

import static bot.BotConfig.EMPTY_SLOT;
import static bot.BotConfig.SCORE;

import bot.ai.Heuristic;

/**
 * Board 4 in a row abstraction.
 * 
 * @author Venilton FalvoJr <falvojr@gmail.com>
 */
public class Field {

	private short[][] board;
	private short cols;
	private short rows;
	private short player;

	public Field(final short[][] board, final short player) {
		this.board = board;
		this.rows = (short) board.length;
		this.cols = (short) board[0].length;
		this.player = player;
	}

	public Field(final short rows, final short cols) {
		this.board = new short[rows][cols];
		this.rows = rows;
		this.cols = cols;
	}

	public short[][] getBoard() {
		return board;
	}

	public void setCols(final short cols) {
		this.cols = cols;
		this.board = new short[rows][cols];
	}

	public void setRows(final short rows) {
		this.rows = rows;
		this.board = new short[rows][cols];
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(final short player) {
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
				this.board[row][col] = Short.parseShort(dataArray[counter]);
				counter++;
			}
		}
	}
	
	public boolean isEmpty() {
		for (short col = 0; col < this.cols; col++) {
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

	public boolean addDisc(final short col) {
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
		final short[][] newBoard = new short[this.board.length][];
		for (short i = 0; i < this.board.length; i++) {
			final short[] aMatrix = this.board[i];
			final short aLength = (short) aMatrix.length;
			newBoard[i] = new short[aLength];
			System.arraycopy(aMatrix, 0, newBoard[i], 0, aLength);
		}
		return new Field(newBoard, this.player);
	}

	public short score() {
		return Heuristic.getInstance().score(this);
	}

	private boolean isFull() {
		for (short col = 0; col < this.cols; col++) {
			if (isNonFullColumn(col)) {
				return false;
			}
		}
		return true;
	}

	private boolean isNonFullColumn(final short col) {
		return this.board[0][col] == EMPTY_SLOT;
	}
	
	private boolean isEmptyColumn(final short col) {
		return this.board[this.rows - 1][col] == EMPTY_SLOT;
	}
	
	private short switchRound(short round) {
		if (round == 1) {
			return 2;
		} else {
			return 1;
		}
	}

}
