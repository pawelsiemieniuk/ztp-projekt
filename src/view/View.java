package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;

import maze.Field;


public class View implements IViewController {
	private static volatile View view;
	
	private static int TERMINAL_FONT_SIZE = 20;
	
    private static int  BUTTON_WIDTH			 = 15,
    					BUTTON_POS_X			 = 31,
    					BUTTON_NEWGAME_POS_Y 	 = 9,
    					BUTTON_LOADGAME_POS_Y 	 = 11,
    					BUTTON_LEADERBOARD_POS_Y = 13,
    					BUTTON_EXIT_POS_Y 		 = 15;
    					
    private static String BUTTON_NEWGAME_TEXT 	  = "NEW GAME      ",
			  			  BUTTON_LOADGAME_TEXT 	  = "LOAD GAME     ",
    					  BUTTON_LEADERBOARD_TEXT = "LEADERBOARD   ",
    					  BUTTON_EXIT_TEXT 		  = "EXIT          ",
    					  FOOTER_TEXT 			  = "Ctrl+Q: EXIT | ESC: PAUSE | Ctrl+S: SAVE";
    private String 		  HEADER_TEXT 			  = "TETRIS";

    private Screen screen;
    private int screenWidth, screenHeight;
    
    private TextGraphics menuScreen;
    
    private TextColor fgColor, bgColor;
    
    
    private GameView gameView;

    private View() {
    }
    
    public static View getView() {
        if (view == null) {
            synchronized(View.class) {
                if (view == null) {
                    view = new View();
                }
            }
        }
        return view;
    }
    
    public Screen OpenWindow() {
    	return OpenWindow(null);
    }
    
    public Screen OpenWindow(TerminalSize terminalSize) {
        try {
			screen = new DefaultTerminalFactory()
					.setInitialTerminalSize(terminalSize)
					.setTerminalEmulatorFontConfiguration(SwingTerminalFontConfiguration.getDefaultOfSize(TERMINAL_FONT_SIZE))
					.createScreen();
			screen.startScreen();
		} catch (IOException e) {
			System.err.println("If on windows, try starting app with javaw.");
			e.printStackTrace();
		}
        return screen;
    }
    
    public void SetupWindow() {
		screenWidth  = screen.getTerminalSize().getColumns();
		screenHeight = screen.getTerminalSize().getRows() - 1;
		
		bgColor = TextColor.ANSI.BLACK;
		fgColor = TextColor.ANSI.WHITE;
		
		screen.setCursorPosition(null);
		screen.clear();
		
		menuScreen = screen.newTextGraphics();
    }
    
    public void SetupGameView(int gameColumns, int gameRows) throws Exception {
    	gameView = new GameView(screen);
    	
    	int gWidth  = gameView.CalcGameWidth(gameColumns);
    	int gHeight = gameRows;
    	int gPosX 	= (int)((screenWidth - gWidth) / 2);
    	//int gPosY 	 = 1;
    	int gPosY 	= (int)((screenHeight - gHeight) / 2);
    	
    	gameView.SetupGameView(gPosX, gPosY, gWidth, gHeight);
    	
    	screen.clear();
        /*gameBoxView = new GameBoxView(screen);
        
    	int GBWidth  = gameBoxView.CalcGameBoxWidth(gameColumns);
    	int GBHeight = gameRows;
    	int GBPosX 	 = (int)((screenWidth - GBWidth) / 2) - 1;
    	int GBPosY 	 = 1;
        gameBoxView.SetupGameBox(GBPosX, GBPosY, GBWidth, GBHeight);
        
        screen.clear();*/
    }

    public void DrawMenu() throws IOException {
    	screen.clear();
    	
    	DrawFooter();
    	DrawHeader("MENU");
    	
    	menuScreen
    		.setBackgroundColor(fgColor).setForegroundColor(bgColor)
    		.drawLine(BUTTON_POS_X, BUTTON_NEWGAME_POS_Y, 	  BUTTON_POS_X + BUTTON_WIDTH, BUTTON_NEWGAME_POS_Y, ' ')
    		.drawLine(BUTTON_POS_X, BUTTON_LOADGAME_POS_Y, 	  BUTTON_POS_X + BUTTON_WIDTH, BUTTON_LOADGAME_POS_Y, ' ')
    		.drawLine(BUTTON_POS_X, BUTTON_LEADERBOARD_POS_Y, BUTTON_POS_X + BUTTON_WIDTH, BUTTON_LEADERBOARD_POS_Y, ' ')
    		.drawLine(BUTTON_POS_X, BUTTON_EXIT_POS_Y, 		  BUTTON_POS_X + BUTTON_WIDTH, BUTTON_EXIT_POS_Y, ' ')
    		.putCSIStyledString(BUTTON_POS_X, BUTTON_NEWGAME_POS_Y, 	BUTTON_NEWGAME_TEXT + Symbols.TRIANGLE_LEFT_POINTING_BLACK)
    		.putCSIStyledString(BUTTON_POS_X, BUTTON_LOADGAME_POS_Y, 	BUTTON_LOADGAME_TEXT)
    		.putCSIStyledString(BUTTON_POS_X, BUTTON_LEADERBOARD_POS_Y, BUTTON_LEADERBOARD_TEXT)
    		.putCSIStyledString(BUTTON_POS_X, BUTTON_EXIT_POS_Y, 		BUTTON_EXIT_TEXT);
    	
    	screen.refresh();
    }
    
    private void DrawHeader(String headerText) {
    	menuScreen
		.setBackgroundColor(fgColor).setForegroundColor(bgColor)
		.drawLine(0, 0, screenWidth, 0, ' ')
		.putCSIStyledString(1, 0, headerText);
    }
    
    private void DrawFooter() {
    	menuScreen
    		.setBackgroundColor(fgColor).setForegroundColor(bgColor)
    		.drawLine(0, screenHeight, screenWidth, screenHeight, ' ')
		.putCSIStyledString(1, screenHeight, FOOTER_TEXT);
    }
    
    public void DrawLeaderboard() {
    	DrawFooter();
    	DrawHeader("LEADERBOARD");
    }
    
    public void DrawGame(Field[] fields) throws Exception {
    	DrawFooter();
    	DrawHeader("GAME");
    	if(gameView == null) {
    		throw new Exception("'gameView' not initialized, use SetupGameView()");
    	}
		gameView.DrawGame(fields);
		screen.refresh();
    }
    /*public void DrawGameBox() throws Exception {
    	DrawFooter();
    	DrawHeader("GAME");
    	if(gameBoxView == null) {
    		throw new Exception("'gameBoxView' not initialized, use SetupGameBoxView()");
    	}
		gameBoxView.DrawGameBox();
		screen.refresh();
    }
    
    public void DrawGameFrame(HashMap<Integer[], TextColor> blocksToDraw) throws IOException {
    	gameBoxView.UpdateGameSpace(blocksToDraw);
		screen.refresh();
    }
    
    public void UpdateCachedShape(Shape shape) throws IOException {
    	gameBoxView.UpdateCachedShape(shape);
    	screen.refresh();
    }
    
    public void UpdateComingShapes(ArrayList<Shape> shapes) throws IOException {
    	gameBoxView.UpdateComingShapes(shapes);
    	screen.refresh();
    }
    
    public void UpdateScore(int score) throws IOException {
    	gameBoxView.UpdateScore(score);
    	screen.refresh();
    }
    */
    public void UpdateHeader(Boolean paused) {
    	int pausePosX = screenWidth - 7;
    	int iconPosX = screenWidth / 2 - 1;
    	if(paused) {
    		menuScreen
    		.setBackgroundColor(bgColor).setForegroundColor(fgColor)
    		.putCSIStyledString(iconPosX, 0, "||")
    		.putCSIStyledString(pausePosX, 0, "PAUSED");
    	} else {
    		menuScreen
    		.setBackgroundColor(fgColor).setForegroundColor(bgColor)
    		.drawLine(iconPosX, 0, screenWidth, 0, ' ');
    	}
    	
    }
    /*
    public void UpdateMenu(MenuSelect option) throws IOException {
    	String buttonStartText 		 = BUTTON_NEWGAME_TEXT;
    	String buttonLoadText 		 = BUTTON_LOADGAME_TEXT;
    	String buttonLeaderboardText = BUTTON_LEADERBOARD_TEXT;
    	String buttonExitText 		 = BUTTON_EXIT_TEXT;
    	
    	switch(option) {
    		case START:
    			buttonStartText += Symbols.TRIANGLE_LEFT_POINTING_BLACK;
    			break;
    		case LOAD:
    			buttonLoadText += Symbols.TRIANGLE_LEFT_POINTING_BLACK;
    			break;
    		case LEADERBOARD:
    			buttonLeaderboardText += Symbols.TRIANGLE_LEFT_POINTING_BLACK;
    			break;
    		case EXIT:
    			buttonExitText += Symbols.TRIANGLE_LEFT_POINTING_BLACK;
    			break;
    	}
    	menuScreen
		.setBackgroundColor(fgColor).setForegroundColor(bgColor)
		.drawLine(BUTTON_POS_X, BUTTON_NEWGAME_POS_Y, 	  BUTTON_POS_X + BUTTON_WIDTH, BUTTON_NEWGAME_POS_Y, ' ')
		.drawLine(BUTTON_POS_X, BUTTON_LOADGAME_POS_Y, 	  BUTTON_POS_X + BUTTON_WIDTH, BUTTON_LOADGAME_POS_Y, ' ')
		.drawLine(BUTTON_POS_X, BUTTON_LEADERBOARD_POS_Y, BUTTON_POS_X + BUTTON_WIDTH, BUTTON_LEADERBOARD_POS_Y, ' ')
		.drawLine(BUTTON_POS_X, BUTTON_EXIT_POS_Y, 		  BUTTON_POS_X + BUTTON_WIDTH, BUTTON_EXIT_POS_Y, ' ')
		.putCSIStyledString(BUTTON_POS_X, BUTTON_NEWGAME_POS_Y, buttonStartText)
		.putCSIStyledString(BUTTON_POS_X, BUTTON_LOADGAME_POS_Y, buttonLoadText)
		.putCSIStyledString(BUTTON_POS_X, BUTTON_LEADERBOARD_POS_Y, buttonLeaderboardText)
		.putCSIStyledString(BUTTON_POS_X, BUTTON_EXIT_POS_Y, buttonExitText);
    	
    	screen.refresh();
    }*/
    
    public KeyStroke GetLastKey() throws IOException {
    	return screen.pollInput();
    }
    public KeyStroke WaitForKey() throws IOException {
    	return screen.readInput();
    }
    
    public void refreshScreen() throws IOException {
    	screen.refresh();
    }
    
    public void CloseWindow() {
    	try {
			screen.stopScreen();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}

