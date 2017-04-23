package br.usp.ai1.us;

import org.apache.commons.lang.mutable.MutableInt;

public class EightPuzzle {

	private static final int BOARD_SIZE = 3;

	protected boolean isSolution(State currentState) {
		return currentState.intBoard == 123405678;
	}

	protected boolean nextAction(State currentState, State nextState) {
		char[][] board = new char[BOARD_SIZE][BOARD_SIZE];
		final MutableInt blankX = new MutableInt();
		final MutableInt blankY = new MutableInt();

		this.int2board(currentState.intBoard, board, blankX, blankY);
		nextState.currentAction = 0;
		for (; currentState.currentAction < 4; currentState.currentAction++) {
			switch (currentState.currentAction) {
			case 0:
				if (blankX.intValue() > 0) {
					this.swap(board, blankX.intValue(), blankY.intValue(), blankX.intValue() - 1, blankY.intValue());
					currentState.currentAction++;
					nextState.intBoard = this.board2int(board);
					return true;
				}
				break;
			case 1:
				if (blankX.intValue() < BOARD_SIZE - 1) {
					this.swap(board, blankX.intValue(), blankY.intValue(), blankX.intValue() + 1, blankY.intValue());
					currentState.currentAction++;
					nextState.intBoard = this.board2int(board);
					return true;
				}
				break;
			case 2:
				if (blankY.intValue() > 0) {
					this.swap(board, blankX.intValue(), blankY.intValue(), blankX.intValue(), blankY.intValue() - 1);
					currentState.currentAction++;
					nextState.intBoard = this.board2int(board);
					return true;
				}
				break;
			case 3:
				if (blankY.intValue() < BOARD_SIZE - 1) {
					this.swap(board, blankX.intValue(), blankY.intValue(), blankX.intValue(), blankY.intValue() + 1);
					currentState.currentAction++;
					nextState.intBoard = this.board2int(board);
					return true;
				}
				break;
			}
		}
		return false;
	}

	private void swap(char board[][], int bx, int by, int x, int y) {
		board[bx][by] = board[x][y];
		board[x][y] = 0;
	}

	private int board2int(char board[][]) {
		int intboard = 0;
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				intboard = intboard * 10 + board[i][j];
			}
		}
		return intboard;
	}

	private void int2board(int intBoard, char board[][], MutableInt bx, MutableInt by) {
		for (int i = BOARD_SIZE - 1; i >= 0; i--) {
			for (int j = BOARD_SIZE - 1; j >= 0; j--) {
				board[i][j] = (char) (intBoard % 10);
				intBoard /= 10;
				if (board[i][j] == 0) {
					bx.setValue(i);
					by.setValue(j);
				}
			}
		}
	}
}
