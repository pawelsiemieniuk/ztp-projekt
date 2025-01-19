package main;

import game.Game;
import game.IGameController;

public class Main {

	public static void main(String[] args) {
		IGameController gameController = new Game();

		gameController.Init();
		gameController.Menu();
	}

}
