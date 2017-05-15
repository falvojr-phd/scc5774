package bot;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import bot.util.BotUtil;

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
		assertTrue("Horizontal winner should return '1'", BotUtil.checkWinner(board) == 1);
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
		assertTrue("Vertical winner should return '2'", BotUtil.checkWinner(board) == 2);
	}
	
	@Test
	public void checkDiagonalWin() {
		int[][] board = {
			{0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 2, 0, 2, 0},
			{0, 0, 0, 1, 2, 2, 0},
			{0, 0, 0, 2, 1, 1, 2},
			{0, 0, 2, 1, 1, 2, 1}
		};
		assertTrue("Diagonal winner should return '2'", BotUtil.checkWinner(board) == 2);
	}

}
