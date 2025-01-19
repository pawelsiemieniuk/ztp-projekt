package game.keyHandler;

import com.googlecode.lanterna.input.KeyStroke;

import game.IGameController;
import maze.Side;
import view.MenuSelect;

public class KeyHandler implements IKeyHandler {
	private IGameController gameController;
	
	private int menuOption = 0;
	private MenuSelect[] menuSelect = MenuSelect.values();
	
	public KeyHandler(IGameController gameController) {
		this.gameController = gameController;
	}

	public void Menu(KeyStroke key) {
		switch(key.getKeyType()) {
			case Enter:
				EnterMenuOption();
				DrawMenu();
				return;
			case EOF:
				Exit();
				return;
			case ArrowUp:
				switchMenuOption(MenuDirection.UP);
				SelectMenuOption();
				return;
			case ArrowDown:
				switchMenuOption(MenuDirection.DOWN);
				SelectMenuOption();
				return;
			case Character:
				break;
			default:
				return;
		}
		switch(key.getCharacter()) {
			case 'q':
				if(key.isCtrlDown()) {
					Exit();
				}
		}
	}


	public void Game(KeyStroke key) {
		switch(key.getKeyType()) {
			case EOF:
				Exit();
				return;
			case Escape: // Pause
				UpdateHeader(true);
				PauseGame();
				return;
			case Character:
				break;
			default:
				return;
		}
		if(key.getCharacter() == 'q' && key.isCtrlDown()) {
			Exit();
		}
	}


	public void Pause(KeyStroke key) {
		switch(key.getKeyType()) {
			case EOF:
				Exit();
				return;
			case Escape: // Resume
				UpdateHeader(false);
				ResumeGame();
				return;
			case Character:
				break;
			default:
				return;
			}
			switch(key.getCharacter()) {
				case 'q':
					if(key.isCtrlDown()) {
						Exit();
					}
					break;
			}
	}


	public void Pacman(KeyStroke key) {
		switch(key.getKeyType()) {
			case ArrowUp:
				Move(Side.UP);
				return;
			case ArrowDown:
				Move(Side.DOWN);
				return;
			case ArrowLeft:
				Move(Side.LEFT);
				return;
			case ArrowRight:
				Move(Side.RIGHT);
				return;
			case Character:
				break;
			default:
				return;
		}
		switch(key.getCharacter()) {
			case 'w':
				Move(Side.UP);
				break;
			case 's':
				Move(Side.DOWN);
				break;
			case 'a': 
				Move(Side.LEFT);
				break;
			case 'd': 
				Move(Side.RIGHT);
				break;
		}
	}


	public MenuSelect getMenuOption() {
		return menuSelect[menuOption];
	}


	public void switchMenuOption(MenuDirection direction) {
		switch(direction) {
			case UP:
				menuOption -= 1;
				break;
			case DOWN:
				menuOption += 1;
				break;
		}
		
		int optionsNumber = menuSelect.length;
		if(menuOption < 0) {
			menuOption = optionsNumber - 1;
		} else if(menuOption > optionsNumber - 1) {
			menuOption = 0;
		}
	}

	public void resetMenuOption() {
		menuOption = 0;		
	}
	
	
	private void DrawMenu() {
		gameController.DrawMenu();
	}
	private void UpdateHeader(Boolean paused) {
		gameController.UpdateHeader(paused);
	}
	private void PauseGame() {
		gameController.PauseGame();
	}
	private void ResumeGame() {
		gameController.ResumeGame();
	}
	private void Exit() {
		gameController.Exit();
	}
	private void SelectMenuOption() {
		gameController.SelectMenuOption();
	}
	private void EnterMenuOption() {
		gameController.EnterMenuOption();
	}
	private void Move(Side side) {
		gameController.Move(side);
	}
}
