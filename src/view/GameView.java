package view;

import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import cookie.BasicCookie;
import cookie.FruitCookie;
import cookie.PowerCookie;
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
		//DrawMazeBorder();
		DrawFields(fields);
	}
	
	private void DrawMazeBorder() {
		int borderStartX = mazePosX;
		int borderEndX   = mazePosX + mazeWidth + 1;
		int borderStartY = mazePosY;
		int borderEndY   = mazePosY + mazeHeight + 1;
		
		mazeScreen
			.setBackgroundColor(bgColor).setForegroundColor(fgColor)
			.drawLine( // GÓRA
					borderStartX, borderStartY,
					borderEndX,   borderStartY,
					Symbols.BLOCK_SOLID)
			.drawLine(	// DÓŁ
					borderStartX, borderEndY, 
					borderEndX,   borderEndY, 
					Symbols.BLOCK_SOLID)
			.drawLine( // LEWO
					borderStartX, borderStartY + 1, 
					borderStartX, borderEndY - 1, 
					Symbols.BLOCK_SOLID)
			/*.drawLine( // LEWO
					borderStartX - 1, borderStartY, 
					borderStartX - 1, borderEndY, 
					Symbols.BLOCK_SOLID)*/
			.drawLine( // PRAWO
					borderEndX, borderStartY + 1, 
					borderEndX, borderEndY - 1, 
					Symbols.BLOCK_SOLID)
			/*.drawLine( // PRAWO
					borderEndX + 1, borderStartY, 
					borderEndX + 1, borderEndY, 
					Symbols.BLOCK_SOLID)*/;
	}
	
	private void DrawFields(Field[] fields) {
		System.out.println(fields[0].toString());
		for(Field field : fields) {
			DrawField(field);
		}
	}
	
	private void DrawField(Field field) {
		Position fieldBlockPos = translatePositionToBlockPosition(new Position(field.getX(), field.getY()));
		int fieldPosX = mazePosX + fieldBlockPos.x();
		int fieldPosY = mazePosY + fieldBlockPos.y();
		
		char fieldSymbol = ' ';
		TextColor bgFieldColor = TextColor.ANSI.BLACK_BRIGHT;
		TextColor fieldColor   = TextColor.ANSI.BLACK;
		
		if(field.isWall()) 
		{
			fieldSymbol = Symbols.BLOCK_SOLID;
			bgFieldColor = TextColor.ANSI.WHITE;
			fieldColor = TextColor.ANSI.WHITE;
		} else if(field.hasPacman()) {
			fieldSymbol = Symbols.FACE_BLACK;
			fieldColor = TextColor.ANSI.YELLOW_BRIGHT;
			DrawLives(field.getPacman().getLives());
		} else if(field.hasGhost()) {
			fieldSymbol = Symbols.FACE_BLACK;
			switch(field.getGhost().getColor()) {
				case RED:
					fieldColor = TextColor.ANSI.RED;
					break;
				case PINK:
					fieldColor = TextColor.ANSI.MAGENTA;
					break;
				case BLUE:
					fieldColor = TextColor.ANSI.BLUE;
					break;
				case ORANGE:
					fieldColor = TextColor.ANSI.YELLOW;
					break;
			}
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

