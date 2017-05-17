package bot;

import bot.ai.Minimax;

/**
 * BotStarter class
 * 
 * Magic happens here. You should edit this file, or more specifically
 * the makeTurn() method to make your bot do more than random moves.
 * 
 * @author Jim van Eeden <jim@starapple.nl>, Joost de Meij <joost@starapple.nl>
 */

public class BotStarter {	

     /**
      * Makes a turn. Edit this method to make your bot smarter.
      * 
     * @param field current {@link Field}.
      *
      * @return The column where the turn was made.
      */
     public int makeTurn(Field field) {
         int[] move = new Minimax().maxValue(field, 8, 0, 0);     
         return move[0];
     }
     
 	public static void main(String[] args) {
 		BotParser parser = new BotParser(new BotStarter());
 		parser.run();
 	}
 	
 }
