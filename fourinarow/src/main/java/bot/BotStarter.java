package bot;

import static bot.BotConfig.DEPTH;
import static bot.ai.Minimax.ALPHA;
import static bot.ai.Minimax.BETA;

import bot.ai.Minimax;

/**
 * BotStarter class
 * 
 * Magic happens here. You should edit this file, or more specifically the
 * makeTurn() method to make your bot do more than random moves.
 * 
 * @author Venilton FalvoJr <falvojr@gmail.com>, Jim van Eeden <jim@starapple.nl>, Joost de Meij <joost@starapple.nl>
 */

public class BotStarter {

	/**
	 * Makes a turn. Edit this method to make your bot smarter.
	 * 
	 * @param field current {@link Field}.
	 *
	 * @return The column where the turn was made.
	 */
	public int makeTurn(final Field field) {
		if (field.isEmpty()) {
			return  3;
		}
		final short[] state = Minimax.getInstance().maxValue(field, DEPTH, ALPHA, BETA);
		final short column = state[0];
		return column;
	}

	public static void main(String[] args) {
		final BotParser parser = new BotParser(new BotStarter());
		parser.run();
	}

}
