package ztp_project;

import game.Game;
import game.GameController;

public class Main {

	public static void main(String[] args) {
		GameController gameController = new Game();
		// Jakaś wstępna inicjalizacja/config itp
		gameController.InitViewController();
		gameController.InitWindow();
		
		gameController.Menu();
	}

}
