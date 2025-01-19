package view;

import java.util.ArrayList;

import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import cookie.BasicCookie;
import cookie.FruitCookie;
import cookie.PowerCookie;
import entities.ghost.RedGhost;
import maze.Field;

public class GameView {

	private static int MIN_MAZE_WIDTH  =  1,
					   MIN_MAZE_HEIGHT = 11;
	
	private int mazePosX, mazePosY;
	private int mazeWidth, mazeHeight;
	
	private int mazeWidthInBlocks, mazeHeightInBlocks; // Block = 2 horizontal cells
	
	
	private Screen screen;
	private TextGraphics mazeScreen,
						 scoreScreen;

	private TextColor bgColor, fgColor;
	
	
	GameView(Screen screen) {
		this.screen = screen;
	}
	

	public void SetupGameView(int gamePosX, int gamePosY, int gameWidth, int gameHeight) throws Exception {
		if(gameWidth < MIN_MAZE_WIDTH) {
			throw new Exception("Game box width is to small. Minimum size is " + MIN_MAZE_WIDTH + ".");
		}
		if(gameHeight < MIN_MAZE_HEIGHT) {
			throw new Exception("Game box height is to small. Minimum size is " + MIN_MAZE_HEIGHT + ".");
		}
		
		this.mazePosX   = gamePosX;
		this.mazePosY   = gamePosY;
		this.mazeWidth  = gameWidth;
		this.mazeHeight = gameHeight;
		//this.mazeWidth  = Integer.divideUnsigned(boxWidth,  2) * 2;
		//this.mazeHeight = Integer.divideUnsigned(boxHeight, 2) * 2;
		
		//this.mazeWidthInBlocks  = Integer.divideUnsigned(this.mazeWidth, 2);
		//this.mazeHeightInBlocks = this.mazeHeight - 1;
		
		
		this.bgColor  = TextColor.ANSI.BLACK;
		this.fgColor  = TextColor.ANSI.WHITE_BRIGHT;
		
		this.mazeScreen  = screen.newTextGraphics();
		this.scoreScreen = screen.newTextGraphics();
	}
	
	public void DrawGame(Field[] fields) {
		DrawMaze(fields);
	}
	
	private void DrawMaze(Field[] fields) {
		DrawMazeBackground();
		DrawFields(fields);
	}
	
	private void DrawMazeBackground() {
		TerminalPosition leftUpperCorner = new TerminalPosition(0, 1);
		TerminalSize terminalSize = screen.getTerminalSize();
		TerminalSize backgroundSize = new TerminalSize(terminalSize.getColumns(), terminalSize.getRows() - 2);
		mazeScreen
		.setBackgroundColor(bgColor).setForegroundColor(TextColor.ANSI.BLACK_BRIGHT)
		.fillRectangle(leftUpperCorner, backgroundSize, Symbols.BLOCK_DENSE);
	}
	
	private void DrawFields(Field[] fields) {
		for(Field field : fields) {
			DrawField(field);
		}
	}
	
	private void DrawField(Field field) {
		//Position fieldBlockPos = translatePositionToBlockPosition(new Position(field.getX(), field.getY()));
		int fieldPosX = mazePosX + field.getX();//fieldBlockPos.x();
		int fieldPosY = mazePosY + field.getY();//fieldBlockPos.y();
		
		char fieldSymbol = ' ';
		TextColor bgFieldColor = TextColor.ANSI.BLACK;
		TextColor fieldColor   = TextColor.ANSI.BLACK;
		
		if(field.isWall()) 
		{
			fieldSymbol = Symbols.BLOCK_SOLID;
			bgFieldColor = TextColor.ANSI.BLUE;//WHITE_BRIGHT;
			fieldColor = TextColor.ANSI.BLUE;//WHITE_BRIGHT;
		} else if(field.hasPacman()) {
			fieldSymbol = field.getPacman().getCharacter();//Symbols.FACE_BLACK;
			fieldColor = TextColor.ANSI.YELLOW_BRIGHT;
			DrawScore(field.getPacman().getScore());
			DrawLives(field.getPacman().getLives());
		} else if(field.hasGhost()) {
			fieldSymbol = Symbols.FACE_BLACK;
			fieldColor = field.getGhost().getColor();
			/*switch(field.getGhost().getColor()) {
				case TextColor.ANSI.RED:
					fieldColor = TextColor.ANSI.RED;
					break;
				case TextColor.ANSI.MAGENTA_BRIGHT:
					fieldColor = TextColor.ANSI.MAGENTA;
					break;
				case TextColor.ANSI.BLUE:
					fieldColor = TextColor.ANSI.BLUE;
					break;
				case TextColor.ANSI.YELLOW:
					fieldColor = TextColor.ANSI.YELLOW;
					break;
			default:
				break;
			}*/
		} else if(field.hasCookie()) {
			Class cookieClass = field.getCookie().getClass();
			if(cookieClass.equals(BasicCookie.class)) {
				fieldColor = TextColor.ANSI.WHITE_BRIGHT;
				fieldSymbol = Symbols.BULLET;
			} else if(cookieClass.equals(FruitCookie.class)) {
				fieldColor = TextColor.ANSI.RED_BRIGHT;
				fieldSymbol = Symbols.CLUB;
			} else if(cookieClass.equals(PowerCookie.class)) {
				fieldColor = TextColor.ANSI.YELLOW_BRIGHT;
				fieldSymbol = Symbols.DIAMOND;
			}
		}
		
		
		mazeScreen
		.setBackgroundColor(bgFieldColor).setForegroundColor(fieldColor)
		.setCharacter(fieldPosX, fieldPosY, fieldSymbol);
		//.setCharacter(fieldPosX + 1, fieldPosY, fieldSymbol);
	}
	
	private void DrawLives(int lives) {
		int livesPosX = mazePosX;
		int livesPosY = mazePosY + mazeHeight;
		
		mazeScreen
		.setBackgroundColor(bgColor).setForegroundColor(TextColor.ANSI.RED_BRIGHT);
		for(int i = 0; i < lives; i++) {
			mazeScreen
			.setCharacter(livesPosX + i, livesPosY, Symbols.HEART);
		}
	}
	
	private void DrawScore(int score) {
		int livesPosX = mazePosX;
		int livesPosY = mazePosY + mazeHeight;
		
		String scoreString = String.valueOf(score);
		
		mazeScreen
		.setBackgroundColor(bgColor).setForegroundColor(fgColor)
		.drawLine(livesPosX, livesPosY, livesPosX + mazeWidth - 1, livesPosY, ' ')
		.putCSIStyledString(livesPosX + mazeWidth - scoreString.length(), livesPosY, scoreString);
	}
	
	public void Update(ArrayList<Field> fieldsToUpdate) {
		for(Field field : fieldsToUpdate) {
			DrawField(field);
		}
	}
	
	
	private Position translatePositionToBlockPosition(Position position) {
		return new Position(position.x(), position.y());
		//return new Position(position.x() * 2, position.y());
	}

	public int CalcGameWidth(int gameSpaceColumns) {
		int gameWidth = gameSpaceColumns;
		//int gameWidth = gameSpaceColumns * 2;
		return gameWidth;
	}
}

class Position {
	private int x, y;
	Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int x() {
		return this.x;
	}
	public int y() {
		return this.y;
	}
}

