package game;

import java.util.stream.Stream;

import com.googlecode.lanterna.TerminalSize;

import maze.Field;
import maze.Maze;
import view.IViewController;
import view.View;

public class Game implements EventListener, GameController {
	private IViewController viewController;
	private Maze maze;
	
	private int gameWidth  = 32,
				gameHeight = 32;
	
	private int score;
	private int level;
	
	
	public EventManager events;
	
	
	public Game() {
		viewController = View.getView();
		maze = new Maze();
		maze.GenerateMaze(gameWidth, gameHeight);
	}
	
	public void StartGame() {
		viewController.OpenWindow(new TerminalSize(46, 40));
		viewController.SetupWindow();
		try {
			viewController.SetupGameView(gameWidth, gameHeight);
		} catch (Exception e) {
			e.printStackTrace();
		}
		DrawGame();
		
		Loop();
	}
	
	private void DrawGame() {
		Field[] fieldsToDraw = (Field[]) Stream.of(maze.getFields()).flatMap(Stream::of).toArray(Field[]::new);
		try {
			viewController.DrawGame(fieldsToDraw);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void Loop() {
		while(true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(String data) {
		// TODO Auto-generated method stub
		
	}
}
