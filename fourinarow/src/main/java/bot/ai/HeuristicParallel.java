package bot.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import bot.BotParser;
import bot.Field;

/**
 * Heuristic (utility funtion) parallel implementation (poor performance).
 * 
 * @author Venilton FalvoJr <falvojr@gmail.com>
 */
@Deprecated
public class HeuristicParallel {
	
	/**
	 * Win score.
	 */
	public static final short WIN = Short.MAX_VALUE;	
	/**
	 * Loss score.
	 */
	public static final short LOSS = Short.MIN_VALUE;
	
	private final ExecutorService executor = Executors.newFixedThreadPool(4);
	
	/**
	 * Score function with following checks:<br>
	 * <br>
	 * 1. Vertical points. Check each column for vertical score. Possible situations:<br>
     * <br>
     *  0  1  2  3  4  5  6   <br>
     * [x][ ][ ][ ][ ][ ][ ] 0<br>
     * [x][x][ ][ ][ ][ ][ ] 1<br>
     * [x][x][x][ ][ ][ ][ ] 2<br>
     * [x][x][x][ ][ ][ ][ ] 3<br>
     * [ ][x][x][ ][ ][ ][ ] 4<br>
     * [ ][ ][x][ ][ ][ ][ ] 5<br>
     * <br>
     * <br>
     * 2. Horizontal points. Check each row's score. Possible situations:<br>
     * <br>
     *  0  1  2  3  4  5  6   <br>
     * [x][x][x][x][ ][ ][ ] 0<br>
     * [ ][x][x][x][x][ ][ ] 1<br>
     * [ ][ ][x][x][x][x][ ] 2<br>
     * [ ][ ][ ][x][x][x][x] 3<br>
     * [ ][ ][ ][ ][ ][ ][ ] 4<br>
     * [ ][ ][ ][ ][ ][ ][ ] 5<br>
     * <br>
     * <br>
     * 3. Diagonal points 1 (left-bottom). Possible situation:<br>
     * <br>
     *  0  1  2  3  4  5  6   <br>
     * [x][ ][ ][ ][ ][ ][ ] 0<br>
     * [ ][x][ ][ ][ ][ ][ ] 1<br>
     * [ ][ ][x][ ][ ][ ][ ] 2<br>
     * [ ][ ][ ][x][ ][ ][ ] 3<br>
     * [ ][ ][ ][ ][ ][ ][ ] 4<br>
     * [ ][ ][ ][ ][ ][ ][ ] 5<br>
     * <br>
     * <br>
     * 4. Diagonal points 2 (right-bottom). Possible situation:<br>
     * <br>
     *  0  1  2  3  4  5  6   <br>
     * [ ][ ][ ][x][ ][ ][ ] 0<br>
     * [ ][ ][x][ ][ ][ ][ ] 1<br>
     * [ ][x][ ][ ][ ][ ][ ] 2<br>
     * [x][ ][ ][ ][ ][ ][ ] 3<br>
     * [ ][ ][ ][ ][ ][ ][ ] 4<br>
     * [ ][ ][ ][ ][ ][ ][ ] 5<br>
	 */
	public int score(final Field field) {
		final List<FutureTask<Integer>> tasks = new ArrayList<>();
		
		final FutureTask<Integer> verticalTask = new FutureTask<>(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				int scoreVertical = 0;
				for (int row = 0; row < field.getRows() - 3; row++) {
					for (int col = 0; col < field.getCols(); col++) {
						final int score = scorePosition(field, row, col, 1, 0);
						if (isTerminal(score)) {
							return score;
						}
						scoreVertical += score;
					}
				}
				return scoreVertical;
			}
		});
		tasks.add(verticalTask);
		executor.execute(verticalTask);

		final FutureTask<Integer> horizontalTask = new FutureTask<>(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				int scoreHorizontal = 0;
				for (int row = 0; row < field.getRows(); row++) {
					for (int col = 0; col < field.getCols() - 3; col++) {
						final int score = scorePosition(field, row, col, 0, 1);
						if (isTerminal(score)) {
							return score;
						}
						scoreHorizontal += score;
					}
				}
				return scoreHorizontal;
			}
		});
		tasks.add(horizontalTask);
		executor.execute(horizontalTask);
		
		final FutureTask<Integer> diagonalLeftBottomTask = new FutureTask<>(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				int scoreDiagonalLeftBottom = 0;
				for (int row = 0; row < field.getRows() - 3; row++) {
					for (int col = 0; col < field.getCols() - 3; col++) {
						final int score = scorePosition(field, row, col, 1, 1);
						if (isTerminal(score)) {
							return score;
						}
						scoreDiagonalLeftBottom += score;
					}
				}
				return scoreDiagonalLeftBottom;
			}
		});
		tasks.add(diagonalLeftBottomTask);
		executor.execute(diagonalLeftBottomTask);
		
		final FutureTask<Integer> diagonalRightBottomTask = new FutureTask<>(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				int scoreDiagonalRightBottom = 0;
				for (int row = 3; row < field.getRows(); row++) {
					for (int col = 0; col < field.getCols() - 3; col++) {
						final int score = scorePosition(field, row, col, -1, +1);
						if (isTerminal(score)) {
							return score;
						}
						scoreDiagonalRightBottom += score;
					}
				}
				return scoreDiagonalRightBottom;
			}
		});
		tasks.add(diagonalRightBottomTask);
		executor.execute(diagonalRightBottomTask);

		int totalScore = 0;
		for (int i = 0; i < tasks.size(); i++) {
			try {
				final Integer score = tasks.get(i).get();
				if (isTerminal(score)) {
					return score;
				}
				totalScore += score;
			} catch (InterruptedException | ExecutionException e) {
				return new Random().nextInt(7);
			}
		}
		
		return totalScore;
	}
	
	private int scorePosition(final Field field, int row, int col, final int deltaY, final int deltaX) {
		int enemyPoints = 0;
		int botPoints = 0;
		for (int i = 0; i < 4; i++) {
			if (field.getBoard()[row][col] == BotParser.getEnemyId()) {
				enemyPoints++;
			} else if (field.getBoard()[row][col] == BotParser.getBotId()) {
				botPoints++;
			}
			row += deltaY;
			col += deltaX;
		}
		if (enemyPoints == 4) {
			return LOSS;
		} else if (botPoints == 4) {
			return WIN;
		} else {
			return botPoints;
		}
	}
	
	private boolean isTerminal(final int score) {
		return score == WIN || score == LOSS;
	}
	
	@SuppressWarnings("unused")
	private int increaseScore(Field field, int score) {
		return field.getPlayer() == BotParser.getBotId() ? (int) Math.round(score * 1.25) : score;
	}
	
	/**
	 * Private constructor for Singleton Pattern.
	 */
	private HeuristicParallel() {
		super();
	}

	private static class HeuristicHolder {
		public static final HeuristicParallel INSTANCE = new HeuristicParallel();
	}

	public static HeuristicParallel getInstance() {
		return HeuristicHolder.INSTANCE;
	}
}
