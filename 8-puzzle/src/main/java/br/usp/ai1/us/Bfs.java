package br.usp.ai1.us;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Breadth-First Search (Uninformed Search Methods).
 * 
 * @author falvojr
 */
public class Bfs extends EightPuzzle {

	private State initialState;

	public Bfs(State initialState) {
		this.initialState = initialState;
	}

	public boolean solve() {
		final Queue<State> q = new LinkedList<>();
		State currentState;
		final State nextState = null;

		q.add(initialState);
		while (!q.isEmpty())
		{
			currentState = q.poll();
			if (super.isSolution(currentState)) {
				return true;
			}
			while (super.nextAction(currentState, nextState))
			{
				q.add(nextState);
			}
		}
		return false;
	}

}
