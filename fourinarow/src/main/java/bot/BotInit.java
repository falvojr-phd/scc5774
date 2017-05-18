package bot;

/**
 * Bot global configurations.
 * 
 * @author falvojr
 */
public class BotInit {
	/**
	 * Height.
	 */
	public static final int ROWS = 6;
	/**
	 * Width.
	 */
	public static final int COLS = 7;
	/**
	 * Search depth.
	 */
	public static final int DEPTH = 10;
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

	private BotInit() {
		super();
	}
}
