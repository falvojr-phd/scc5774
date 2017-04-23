package br.usp.ai1;

import br.usp.ai1.us.Bfs;
import br.usp.ai1.us.State;

public class Main {

	public static void main(String[] args) {
		State initial = new State();

		initial.intBoard = 123405867;
		initial.currentAction = 0;

		if (new Bfs(initial).solve()) {
			System.out.println("Sucesso!\n");
		} else {
			System.out.println("Falha!\n");
		}
	}

}
