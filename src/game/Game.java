package game;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.stream.Stream;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;

import entities.Pacman;
import game.keyHandler.*;
import maze.Field;
import maze.Maze;
import maze.Side;
import view.IViewController;
import view.View;

public class Game implements IEventListener, IGameController {
	private static int TERMINAL_WIDTH 	  = 46,
					   TERMINAL_HEIGHT 	  = 40,
					   TERMINAL_FONT_SIZE = 20;

	private static Boolean CONTINUOUS_MOVE_MODE = true;
	
	private static int FRAME_TIME_ms = 10; 	 	// ms
	private static int GHOST_MOVE_FRAMES  = 60,
					   PACMAN_MOVE_FRAMES = 20; // Tylko gdy CONTINUOUS_MOVE_MODE = true
	
	private static int PACMAN_LIVES = 4;
	
	private IViewController viewController;
	
	
	private Maze maze;
	
	private IKeyHandler keyHandler;
	
	private int gameWidth  = 21,
				gameHeight = 20;
	
	
	private Boolean gameOver   	  = false;
	private Boolean gamePaused 	  = false;
	private Boolean pacmanDied 	  = false;
	private Boolean pacmanPowerOn = false;
	
	private ArrayList<Field> fieldsToUpdate = new ArrayList<Field>();
	
	public EventManager events;
	
	
	public Game() {
	}
	
	public void Init() {
		InitViewController();
		InitWindow();
		InitKeyHandler();
	}
	
	private void InitViewController() {
		viewController = View.getView();
	}
	
	private void InitWindow() {
		viewController.OpenWindow(new TerminalSize(TERMINAL_WIDTH, TERMINAL_HEIGHT), TERMINAL_FONT_SIZE);
		viewController.SetupWindow();
		try {
			viewController.SetupGameView(gameWidth, gameHeight);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void InitKeyHandler() {
		keyHandler = new KeyHandler(this);
	}
	
	private void InitMaze() {
		maze = new Maze();
		maze.GenerateMaze(gameWidth, gameHeight);
	}
	
	private void Setup() {
		gameOver   	  = false;
		gamePaused 	  = false;
		pacmanDied 	  = false;
		pacmanPowerOn = false;
		Pacman.getPacman().setLives(PACMAN_LIVES);
		ResetPacmanGhostsPosition();
		try {
			viewController.UpdateGameView(fieldsToUpdate);
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public void Menu() {
		DrawMenu();
		try {
			MenuLoop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void DrawMenu() {
		try {
			viewController.DrawMenu();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void Exit() {
		viewController.Exit();
    	System.exit(0);
		System.err.println("App exit did not succeed.");
	}
	
	public void StartGame() {
		InitMaze();
		DrawGame();
		Setup();
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
	
	public void PauseGame() {
		gamePaused = true;
	}
	
	public void ResumeGame() {
		gamePaused = false;
	}
	
	public void DrawGame() {
		Field[] fieldsToDraw = (Field[]) Stream.of(maze.getFields()).flatMap(Stream::of).toArray(Field[]::new);
		try {
			viewController.DrawGame(fieldsToDraw);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void UpdateHeader(Boolean paused) {
		viewController.UpdateHeader(paused);
	}

	private void MenuLoop() {
		KeyStroke key = null;
        while(true) {
        	try {
				key = viewController.WaitForKey();
			} catch (IOException e) {
				e.printStackTrace();
			}
        	if(key != null) {
        		try {
					keyHandler.Menu(key);
				} catch (Exception e) {
					e.printStackTrace();
				}
        	}
        }
	}
	
	private void GameLoop() {
		KeyStroke key = null;
		KeyStroke lastKey = null;

		int ghostFrameCounter  = 0,
			pacmanFrameCounter = 0;
		Duration deltaTime = Duration.ZERO;
		Instant beginFrameTime = Instant.now();
		while(!gameOver) {
			try {
				key = viewController.GetLastKey();
				if(CONTINUOUS_MOVE_MODE && key != null) { 
					lastKey = key; 
				}
			} catch (IOException e) { e.printStackTrace(); }

			if(CONTINUOUS_MOVE_MODE) {
				if(lastKey != null) {
					if(gamePaused && key != null) {
						keyHandler.Pause(key);
						lastKey = null;
					} else if(!gamePaused) {
						keyHandler.Game(lastKey);
						if(pacmanFrameCounter >= PACMAN_MOVE_FRAMES) {
							keyHandler.Pacman(lastKey);
							pacmanFrameCounter = 0;
						}
					}
				}
			} else if(key != null) {
				if(gamePaused) {
					keyHandler.Pause(key);
				} else {
					keyHandler.Game(key);
					keyHandler.Pacman(key);
				}
			}
			// Sprawdzenie przed ruchem duchów żeby niepotrzebnie nie aktualizować widoku
			if(pacmanDied) {
				// PacmanDeadAnimation();
				ResetPacmanGhostsPosition();
				key = lastKey = null;
				pacmanDied = false;
				try {
					viewController.UpdateGameView(fieldsToUpdate);
				} catch (IOException e) {
					e.printStackTrace();
				}
				continue;
			}

			deltaTime = Duration.between(beginFrameTime, Instant.now());
			if(!gamePaused && deltaTime.toMillis() >= FRAME_TIME_ms) {
				if(ghostFrameCounter >= GHOST_MOVE_FRAMES) {
					MoveGhosts();
					ghostFrameCounter = 0;
				}
				ghostFrameCounter++;
				if(CONTINUOUS_MOVE_MODE) { pacmanFrameCounter++; }

				try {
					viewController.UpdateGameView(fieldsToUpdate);
				} catch (IOException e) { e.printStackTrace(); }
				beginFrameTime = Instant.now();
			}
			// Sprawdzenie pod koniec
			if(pacmanDied) {
				// PacmanDeadAnimation();
				ResetPacmanGhostsPosition();
				key = lastKey = null;
				pacmanDied = false;
				try {
					viewController.UpdateGameView(fieldsToUpdate);
				} catch (IOException e) {
					e.printStackTrace();
				}
				continue;
			}
		}
	}

	public void SelectMenuOption() {
		try {
			viewController.UpdateMenu(keyHandler.getMenuOption());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void EnterMenuOption() {
		switch(keyHandler.getMenuOption()) {
			case START:
				StartGame(true);
				break;
			case LOAD:
				StartGame(false);
				break;
			case EXIT:
				Exit();
				break;
		}
		keyHandler.resetMenuOption();
	}

	private void ResetPacmanGhostsPosition() {
		for(Field field : maze.ResetGhostsPosition()) {
			fieldsToUpdate.add(field);
		}
		for(Field field : maze.ResetPacmanPosition()) {
			fieldsToUpdate.add(field);
		}
	}
	
	public void Move(Side side) {
		for(Field field: maze.MovePacman(side)) {
			fieldsToUpdate.add(field);
			if(field.hasGhost() && field.hasPacman()) {
				if(pacmanPowerOn) {
					field.getGhost().Kill();
				} else {
					field.getPacman().loseLife();
					if(field.getPacman().getLives() <= 0) {
						gameOver = true;
					}
					pacmanDied = true;
				}
			}
		}
	}
	
	private void MoveGhosts() {
		for(Field field : maze.MoveGhosts()) {
			fieldsToUpdate.add(field);
			if(field.hasGhost() && field.hasPacman()) {
				field.getPacman().loseLife();
				if(field.getPacman().getLives() <= 0) {
					gameOver = true;
				}
				pacmanDied = true;
			}
		}
	}

	@Override
	public void update(String data) {
		// TODO Auto-generated method stub
		
	}
}
