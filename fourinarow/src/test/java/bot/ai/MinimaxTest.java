package bot.ai;

import static bot.BotConfig.*;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import bot.Board;
import bot.BotParser;
import bot.ai.Minimax;

/**
 * Unit tests for {@link BotUtil} class.
 * 
 * @author falvojr
 */
public class MinimaxTest {
	
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
		final int col = runMinimax(board)[0];
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
		final int col = runMinimax(board)[0];
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
		final int col = runMinimax(board)[0];
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
		final int col = runMinimax(board)[0];
		assertTrue("Diagonal left-bottom win possibility.", col == 3);
	}
	
	private int[] runMinimax(int[][] field) {
		final int player = 1;
		BotParser.mBotId = player;
		final Board board = new Board(field, player);
		final int[] state = Minimax.getInstance().maxValue(board, DEPTH, ALPHA, BETA);
		return state;
	}

}
