package bot;

/**
 * Bot global configurations.
 * 
 * @author Venilton FalvoJr <falvojr@gmail.com>
 */
public class BotConfig {
	/**
	 * Search depth.
	 */
	public static final int DEPTH = 8;
	/**
	 * Win/loss score.
	 */
	public static final int SCORE = 100000;
	/**
	 * Empty slot identification.
	 */
	public static final int EMPTY_SLOT = 0;
	/**
	 * Alpha default value.
	 */
	public static final int ALPHA = Integer.MIN_VALUE;
	/**
	 * Beta default value.
	 */
	public static final int BETA = Integer.MAX_VALUE;

	private BotConfig() {
		super();
	}
}
