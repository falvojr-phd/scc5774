package bot.ai;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import bot.BotParser;
import bot.BotStarter;
import bot.Field;

/**
 * Unit tests for {@link BotUtil} class.
 * 
 * @author falvojr
 */
public class MinimaxTest {
	
	@Test
	public void testEmpty() {
		int[][] board = {
			{0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0}
		};
		final int col = runMinimax(board);
		assertTrue("Empty.", col == 3);
	}
	
	@Test
	public void testHorizontalWin() {
		int[][] board = {
			{0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 2, 0, 0, 0},
			{0, 0, 0, 2, 0, 0, 0},
			{0, 0, 0, 2, 0, 0, 0},
			{0, 0, 0, 1, 1, 1, 0}
		};
		final int col = runMinimax(board);
		assertTrue("Horizontal win possibility.", col == 2 || col == 6);
	}
	
	@Test
	public void testVerticalWin() {
		int[][] board = {
			{0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 1, 0, 0, 0},
			{0, 0, 0, 1, 0, 0, 0},
			{0, 0, 0, 1, 2, 2, 2}
		};
		final int col = runMinimax(board);
		assertTrue("Vertical win possibility.", col == 3);
	}

	@Test
	public void testDiagonalRightBottom() {
		int[][] board = {
			{0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 2, 0, 0, 0},
			{0, 0, 2, 1, 2, 0, 0},
			{0, 2, 1, 2, 2, 1, 0},
			{1, 1, 2, 1, 1, 1, 2}
		};
		final int col = runMinimax(board);
		assertTrue("Diagonal right-bottom win possibility.", col == 4);
	}
	
	@Test
	public void testDiagonalLeftBottom() {
		int[][] board = {
			{0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0},
			{0, 0, 2, 2, 1, 0, 0},
			{2, 1, 2, 1, 2, 1, 0},
			{1, 2, 2, 1, 2, 1, 1}
		};
		final int col = runMinimax(board);
		assertTrue("Diagonal left-bottom win possibility.", col == 3);
	}
	
	private int runMinimax(int[][] field) {
		final int player = 1;
		BotParser.mBotId = player;
		BotStarter botStarter = new BotStarter();
		return botStarter.makeTurn(new Field(field, player));
	}

}
