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
	public static final short DEPTH = 10;
	/**
	 * Win/loss score.
	 */
	public static final short SCORE = Short.MAX_VALUE;
	/**
	 * Empty slot identification.
	 */
	public static final short EMPTY_SLOT = 0;
	/**
	 * Alpha default value.
	 */
	public static final short ALPHA = Short.MIN_VALUE;
	/**
	 * Beta default value.
	 */
	public static final short BETA = Short.MAX_VALUE;

	private BotConfig() {
		super();
	}
}
