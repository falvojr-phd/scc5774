package bot.ai;

import com.rits.cloning.Cloner;

import bot.Field;

/**
 * Minimax algorithm.
 * 
 * @author falvojr
 */
public class Minimax {

	public int[] maxValue(Field board, int depth, int alpha, int beta) {

		int score = board.score();

		if (board.isFinished(depth, score)) return new int[] { -1, score };

		int[] max = new int[] { -1, Integer.MIN_VALUE };

		for (int column = 0; column < board.getCols(); column++) {
			Field clonedField = Cloner.standard().deepClone(board);

			if (!clonedField.isColumnFull(column)) {

				int[] next_move = this.minValue(clonedField, depth - 1, alpha, beta);

				if (max[0] == -1 || next_move[1] > max[1]) {
					max[0] = column;
					max[1] = next_move[1];
					alpha = next_move[1];
				}

				if (alpha >= beta) return max;
			}
		}

		return max;
	}
	
	public int[] minValue(Field board, int depth, int alpha, int beta) {

		int score = board.score();

		if (board.isFinished(depth, score)) return new int[] { -1, score };

		int[] min = new int[] { -1, Integer.MAX_VALUE };

		for (int column = 0; column < board.getCols(); column++) {
			Field clonedField = Cloner.standard().deepClone(board);

			if (!clonedField.isColumnFull(column)) {

				int[] next_move = this.maxValue(clonedField, depth - 1, alpha, beta);

				if (min[0] == -1 || next_move[1] > min[1]) {
					min[0] = column;
					min[1] = next_move[1];
					beta = next_move[1];
				}

				if (alpha >= beta) return min;
			}
		}

		return min;
	}
}
