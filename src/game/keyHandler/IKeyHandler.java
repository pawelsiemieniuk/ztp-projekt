package game.keyHandler;

import com.googlecode.lanterna.input.KeyStroke;

import view.MenuSelect;

// IKEA(ndler)
public interface IKeyHandler {
	public void Menu(KeyStroke key);
	public void Game(KeyStroke key);
	public void Pause(KeyStroke key);
	public void Pacman(KeyStroke key);
	
	public MenuSelect getMenuOption();
	public void switchMenuOption(MenuDirection direction);
	public void resetMenuOption();
}
