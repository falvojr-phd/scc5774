package bot.ai;

import bot.Field;

/**
 * Minimax (+Alpha-Beta) implementation.
 * 
 * @author Venilton FalvoJr <falvojr@gmail.com>
 */
public class Minimax {
	
	/**
	 * Alpha default value.
	 */
	public static final short ALPHA = Short.MIN_VALUE;
	/**
	 * Beta default value.
	 */
	public static final short BETA = Short.MAX_VALUE;
	/**
	 * Unknown column.
	 */
	private static final int UNKNOWN_COL = -1;
	
	public short[] maxValue(final Field field, final short depth, short alpha, final short beta) {
		// if terminal (state) return utility(state)
		final short score = field.score();
		if (field.isTerminal(depth, score)) {
			return new short[] { UNKNOWN_COL, score };
		}
		// initialize v = -∞
		final short[] value = new short[] { UNKNOWN_COL, Short.MIN_VALUE };
		// for each successor of state:
		for (short col = 0; col < field.getCols(); col++) {
			final Field clonedField = field.clone();
			if (clonedField.addDisc(col)) {
				// v = max(v, min-value(successor, α, β))
				final short[] valueSucessor = this.minValue(clonedField, (short) (depth - 1), alpha, beta);
				if (value[0] == UNKNOWN_COL || valueSucessor[1] > value[1]) {
					value[0] = col;
					value[1] = valueSucessor[1];
				}
				// if v ≥ β return v (or without equal)
				if (value[1] > beta) {
					return value;
				}
				// α = max(α, v)
				alpha = (short) Math.max(alpha, value[1]);
			}
		}
		// return v
		return value;
	}

	public short[] minValue(final Field field, final short depth, final short alpha, short beta) {	
		// if terminal (state) return utility(state)
		final short score = field.score();
		if (field.isTerminal(depth, score)) {
			return new short[] { UNKNOWN_COL, score };
		}
		// initialize v = +∞
		final short[] value = new short[] { UNKNOWN_COL, Short.MAX_VALUE };
		// for each successor of state:
		for (short col = 0; col < field.getCols(); col++) {
			final Field clonedField = field.clone();
			if (clonedField.addDisc(col)) {
				// v = min(v, max-value(successor, α, β))
				short[] valueSucessor = this.maxValue(clonedField, (short) (depth - 1), alpha, beta);
				if (value[0] == UNKNOWN_COL || valueSucessor[1] < value[1]) {
					value[0] = col;
					value[1] = valueSucessor[1];
				}
				// if v ≤ α return v (or without equal)
				if (value[1] <= alpha) {
					return value;
				}
				// β = min(β, v)
				beta = (short) Math.min(beta, value[1]);
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
