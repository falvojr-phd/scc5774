package bot.ai;

import bot.Field;

/**
 * Minimax (+Alpha-Beta) implementation.
 * 
 * @author Venilton FalvoJr <falvojr@gmail.com>
 */
public class Minimax {

	private static final int UNKNOW_COL = -1;
	
	public int[] maxValue(final Field field, final int depth, int alpha, final int beta) {
		// if terminal (state) return utility(state)
		final int score = field.score();
		if (field.isTerminal(depth, score)) {
			return new int[] { UNKNOW_COL, score };
		}
		// initialize v = -∞
		final int[] value = new int[] { UNKNOW_COL, Integer.MIN_VALUE };
		// for each successor of state:
		for (int col = 0; col < field.getCols(); col++) {
			final Field clonedField = field.clone();
			if (clonedField.addDisc(col)) {
				// v = max(v, min-value(successor, α, β))
				final int[] valueSucessor = this.minValue(clonedField, depth - 1, alpha, beta);
				if (value[0] == UNKNOW_COL || valueSucessor[1] > value[1]) {
					value[0] = col;
					value[1] = valueSucessor[1];
				}
				// if v ≥ β return v 
				if (value[1] >= beta) {
					return value;
				}
				// α = max(α, v)
				alpha = Math.max(alpha, value[1]);
			}
		}
		// return v
		return value;
	}

	public int[] minValue(final Field field, final int depth, final int alpha, int beta) {	
		// if terminal (state) return utility(state)
		final int score = field.score();
		if (field.isTerminal(depth, score)) {
			return new int[] { UNKNOW_COL, score };
		}
		// initialize v = +∞
		final int[] value = new int[] { UNKNOW_COL, Integer.MAX_VALUE };
		// for each successor of state:
		for (int col = 0; col < field.getCols(); col++) {
			final Field clonedField = field.clone();
			if (clonedField.addDisc(col)) {
				// v = min(v, max-value(successor, α, β))
				int[] valueSucessor = this.maxValue(clonedField, depth - 1, alpha, beta);
				if (value[0] == UNKNOW_COL || valueSucessor[1] < value[1]) {
					value[0] = col;
					value[1] = valueSucessor[1];
				}
				// if v ≤ α return v
				if (value[1] <= alpha) {
					return value;
				}
				// β = min(β, v)
				beta = Math.min(beta, value[1]);
			}
		}
		// return v
		return value;
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
