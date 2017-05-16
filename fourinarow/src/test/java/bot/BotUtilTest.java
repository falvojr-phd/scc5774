package bot;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import bot.util.BotUtil;

/**
 * Unit tests for {@link BotUtil} class.
 * 
 * @author falvojr
 */
public class BotUtilTest {
	
	@Test
	public void checkHorizontalWin() {
		int[][] board = {
			{0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 2, 0, 0, 0},
			{0, 0, 0, 2, 0, 0, 0},
			{0, 0, 0, 2, 0, 0, 0},
			{0, 0, 0, 1, 1, 1, 1}
		};
		assertTrue("Horizontal winner should return '1'", BotUtil.checkTerminal(board) == 1);
	}
	
	@Test
	public void checkVerticalWin() {
		int[][] board = {
			{0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 1, 2, 0, 0},
			{0, 0, 1, 2, 2, 0, 0},
			{0, 0, 1, 1, 2, 0, 0},
			{0, 0, 1, 2, 2, 0, 0}
		};
		assertTrue("Vertical winner should return '2'", BotUtil.checkTerminal(board) == 2);
	}
	
	@Test
	public void checkDiagonalUpLeftWin() {	
		int[][] board = {
			{0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 1, 0, 2, 1},
			{0, 0, 0, 1, 1, 2, 2},
			{0, 0, 0, 2, 2, 1, 2},
			{0, 0, 2, 1, 1, 2, 1}
		};
		assertTrue("Diagonal (up right) winner should return '1'", BotUtil.checkTerminal(board) == 1);
	}
	
	@Test
	public void checkDiagonalUpRightWin() {	
		int[][] board = {
			{0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 2, 0, 2, 0},
			{0, 0, 0, 1, 2, 2, 0},
			{0, 0, 0, 2, 1, 1, 2},
			{0, 0, 2, 1, 1, 2, 1}
		};
		assertTrue("Diagonal (up left) winner should return '2'", BotUtil.checkTerminal(board) == 2);
	}

}
