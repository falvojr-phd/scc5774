package bot;

import static bot.BotConfig.*;

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
		final short[] state = Minimax.getInstance().maxValue(field, DEPTH, ALPHA, BETA);
		final short col = state[0];
		return col;
	}

	public static void main(String[] args) {
		final BotParser parser = new BotParser(new BotStarter());
		parser.run();
	}

}
