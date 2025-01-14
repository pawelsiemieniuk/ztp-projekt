package game;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.stream.Stream;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;

import maze.Field;
import maze.Maze;
import maze.Side;
import view.IViewController;
import view.MenuSelect;
import view.View;

public class Game implements EventListener, GameController {
	private static int TERMINAL_WIDTH 	  = 120,
					   TERMINAL_HEIGHT 	  = 40,
					   TERMINAL_FONT_SIZE = 20;

	private static int FRAME_TIME_ms = 10; // ms
	private static int GHOST_MOVE_FRAMES = 60;
	
	private IViewController viewController;

	private MenuSelect[] menuSelect = MenuSelect.values();
	private int currentMenuOption = 0;
	
	
	private Maze maze;
	
	private int gameWidth  = 32,
				gameHeight = 32;
	
	private int score;
	private int level;
	
	private Boolean gameEnded  = false;
	private Boolean gamePaused = false;
	
	private ArrayList<Field> fieldsToUpdate = new ArrayList<Field>();
	
	public EventManager events;
	
	
	public Game() {
	}
	
	public void InitViewController() {
		viewController = View.getView();
	}
	
	public void InitWindow() {
		viewController.OpenWindow(new TerminalSize(TERMINAL_WIDTH, TERMINAL_HEIGHT), TERMINAL_FONT_SIZE);
		viewController.SetupWindow();
		try {
			viewController.SetupGameView(gameWidth, gameHeight);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void InitMaze() {
		maze = new Maze();
		maze.GenerateMaze(gameWidth, gameHeight);
	}
	
	public void Menu() {
		try {
			viewController.DrawMenu();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			MenuLoop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void StartGame() {
		InitMaze();
		DrawGame();
		GameLoop();
	}
	
	private void StartGame(Boolean isGameNew) {
		if(isGameNew) {
			StartGame();
		} else {
			// LoadGame();
			StartGame();
		}
	}
	
	private void PauseGame() {
		gamePaused = true;
	}
	
	private void ResumeGame() {
		gamePaused = false;
	}
	
	private void DrawGame() {
		Field[] fieldsToDraw = (Field[]) Stream.of(maze.getFields()).flatMap(Stream::of).toArray(Field[]::new);
		try {
			viewController.DrawGame(fieldsToDraw);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void MenuLoop() throws Exception {
		KeyStroke key = null;
        while(true) {
        	key = viewController.WaitForKey();
        	if(key != null) {
        		MenuKeyHandler(key);
        	}
        }
	}
	
	private void GameLoop() {
		KeyStroke key = null;

		int frameCounter = 0;
		Duration deltaTime = Duration.ZERO;
		Instant beginFrameTime = Instant.now();
		while(!gameEnded) {
			try {
				key = viewController.GetLastKey();
			} catch (IOException e) { e.printStackTrace(); }

			if(key != null) {
				if(gamePaused) {
					PauseKeyHandler(key);
				} else {
					GameKeyHandler(key);
				}
			}

			deltaTime = Duration.between(beginFrameTime, Instant.now());
			if(!gamePaused && deltaTime.toMillis() >= FRAME_TIME_ms) {
				if(frameCounter >= GHOST_MOVE_FRAMES) {
					//MoveGhosts();
					System.out.println("Ghosts move");
					frameCounter = 0;
				}
				frameCounter++;

				try {
					viewController.UpdateGameView(fieldsToUpdate);
				} catch (IOException e) { e.printStackTrace(); }
				beginFrameTime = Instant.now();
			}
		}
	}

	private void SelectMenuOption() {
		if(currentMenuOption < 0) {
			currentMenuOption = menuSelect.length + currentMenuOption;
		} else if(currentMenuOption > 2) {
			currentMenuOption %= menuSelect.length;
		}
		try {
			viewController.UpdateMenu(menuSelect[currentMenuOption]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void EnterMenuOption() throws Exception {
		if(currentMenuOption < 0) {
			currentMenuOption = menuSelect.length + currentMenuOption;
		} else if(currentMenuOption > 2) {
			currentMenuOption %= menuSelect.length;
		}
		switch(menuSelect[currentMenuOption]) {
			case START:
				StartGame(true);
				break;
			case LOAD:
				StartGame(false);
				break;
			case EXIT:
				viewController.Exit();
				break;
		}
		currentMenuOption = 0;
	}

	private void MenuKeyHandler(KeyStroke key) throws Exception {
		switch(key.getKeyType()) {
			case Enter:
				EnterMenuOption();
				viewController.DrawMenu();
				return;
			case EOF:
				viewController.Exit();
				return;
			case ArrowUp:
				currentMenuOption = currentMenuOption - 1;
				SelectMenuOption();
				return;
			case ArrowDown:
				currentMenuOption = currentMenuOption + 1;
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
					viewController.Exit();
				}
		}
	}

	public void GameKeyHandler(KeyStroke key) {
		switch(key.getKeyType()) {
			case EOF:
				try {
					viewController.Exit();
				} catch (Exception e) { e.printStackTrace(); }
				return;
			case Escape: // Pause
				viewController.UpdateHeader(true);
				PauseGame();
				//tetrisController.clrscr();
				return;
			case ArrowUp: // Clockwise turn
				Move(Side.UP);
				return;
			case ArrowDown: // Soft drop
				Move(Side.DOWN);
				return;
			case ArrowLeft: // Move left
				Move(Side.LEFT);
				return;
			case ArrowRight: // Move right
				Move(Side.RIGHT);
				return;
			case Character:
				break;
			default:
				return;
		}
		switch(key.getCharacter()) {
			case 'q':
				if(key.isCtrlDown()) {
					//SaveGame();
					try {
						viewController.Exit();
					} catch (Exception e) { e.printStackTrace(); }
				}
				break;
			case 'w': // Hard drop
				Move(Side.UP);
				break;
			case 's': // Counterclockwise turn
				Move(Side.DOWN);
				break;
			case 'a': // Clockwise turn
				Move(Side.LEFT);
				break;
			case 'd': // Cache Shape
				Move(Side.RIGHT);
				break;
			/*case 's':
				if(key.isCtrlDown()) {
					gameInstance.SaveGame();
				}
				break;*/
		}
	}

	public void PauseKeyHandler(KeyStroke key) {
		switch(key.getKeyType()) {
		case EOF:
			try {
				viewController.Exit();
			} catch (Exception e) { e.printStackTrace(); }
			return;
		case Escape: // Resume
			viewController.UpdateHeader(false);
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
					try {
						viewController.Exit();
					} catch (Exception e) { e.printStackTrace(); }
				}
				break;
		}
	}
	
	
	private void Move(Side side) {
		for(Field field: maze.MovePacman(side)) {
			fieldsToUpdate.add(field);
		}
	}

	@Override
	public void update(String data) {
		// TODO Auto-generated method stub
		
	}
}
