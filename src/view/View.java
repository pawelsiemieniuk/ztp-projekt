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
	
	
    private static int  BUTTON_WIDTH			 = 15,
    					MENU_ENTRIES 			 = 3;
    					
    private static String BUTTON_NEWGAME_TEXT 	  = "NEW GAME      ",
			  			  BUTTON_LOADGAME_TEXT 	  = "LOAD GAME     ",
    					  BUTTON_EXIT_TEXT 		  = "EXIT          ",
    					  FOOTER_TEXT 			  = "Q: EXIT | ESC: PAUSE";


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
    	return OpenWindow(null, 20);
    }
    
    public Screen OpenWindow(TerminalSize terminalSize, int terminalFontSize) {
        try {
			screen = new DefaultTerminalFactory()
					.setInitialTerminalSize(terminalSize)
					.setTerminalEmulatorFontConfiguration(SwingTerminalFontConfiguration.getDefaultOfSize(terminalFontSize))
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
    	int gPosX 	= (int)((screenWidth  - gWidth) / 2);
    	//int gPosY 	 = 1;
    	int gPosY 	= (int)((screenHeight - gHeight) / 2);
    	
    	gameView.SetupGameView(gPosX, gPosY, gWidth, gHeight);
    	
    	screen.clear();
    }

    public void DrawMenu() throws IOException {
    	screen.clear();
    	
    	int menuWidth  = BUTTON_WIDTH;
    	int menuHeight = MENU_ENTRIES * 2;
    	int menuPosX   = (int)((screenWidth  - menuWidth)  / 2);
    	int menuPosY   = (int)((screenHeight - menuHeight) / 2);
    	int ngPosY = menuPosY, 
    		lgPosY = menuPosY + 2, 
    		exPosY = menuPosY + 4;
    	menuScreen
    		.setBackgroundColor(fgColor).setForegroundColor(bgColor)
    		.drawLine(menuPosX, ngPosY, menuPosX + menuWidth, ngPosY, ' ')
    		.drawLine(menuPosX, lgPosY, menuPosX + menuWidth, lgPosY, ' ')
    		.drawLine(menuPosX, exPosY, menuPosX + menuWidth, exPosY, ' ')
    		.putCSIStyledString(menuPosX, ngPosY, BUTTON_NEWGAME_TEXT + Symbols.TRIANGLE_LEFT_POINTING_BLACK)
    		.putCSIStyledString(menuPosX, lgPosY, BUTTON_LOADGAME_TEXT)
    		.putCSIStyledString(menuPosX, exPosY, BUTTON_EXIT_TEXT);
    	
    	DrawFooter();
    	DrawHeader("MENU");
    	
    	screen.refresh();
    }
    
    public void DrawGame(Field[] fields) throws Exception {
    	screen.clear();
    	
    	DrawFooter();
    	DrawHeader("GAME");
    	if(gameView == null) {
    		throw new Exception("'gameView' not initialized, use SetupGameView()");
    	}
		gameView.DrawGame(fields);
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
    
    public void UpdateHeader(Boolean paused) {
    	int pausePosX = screenWidth - 7;
    	int iconPosX = screenWidth / 2 - 1;
    	if(paused) {
    		menuScreen
    		.setBackgroundColor(fgColor).setForegroundColor(bgColor)
    		.putCSIStyledString(iconPosX, 0, "||")
    		.putCSIStyledString(pausePosX, 0, "PAUSED");
    	} else {
    		menuScreen
    		.setBackgroundColor(fgColor).setForegroundColor(bgColor)
    		.drawLine(iconPosX, 0, screenWidth, 0, ' ');
    	}
    	try {
			screen.refresh();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void UpdateMenu(MenuSelect option) throws IOException {
    	int menuWidth  = BUTTON_WIDTH;
    	int menuHeight = MENU_ENTRIES * 2;
    	int menuPosX   = (int)((screenWidth  - menuWidth)  / 2);
    	int menuPosY   = (int)((screenHeight - menuHeight) / 2);
    	int ngPosY = menuPosY, 
    		lgPosY = menuPosY + 2, 
    		exPosY = menuPosY + 4;
    	
    	String buttonStartText 		 = BUTTON_NEWGAME_TEXT;
    	String buttonLoadText 		 = BUTTON_LOADGAME_TEXT;
    	String buttonExitText 		 = BUTTON_EXIT_TEXT;
    	
    	switch(option) {
    		case START:
    			buttonStartText += Symbols.TRIANGLE_LEFT_POINTING_BLACK;
    			break;
    		case LOAD:
    			buttonLoadText += Symbols.TRIANGLE_LEFT_POINTING_BLACK;
    			break;
    		case EXIT:
    			buttonExitText += Symbols.TRIANGLE_LEFT_POINTING_BLACK;
    			break;
    	}
    	menuScreen
    		.setBackgroundColor(fgColor).setForegroundColor(bgColor)
    		.drawLine(menuPosX, ngPosY, menuPosX + menuWidth, ngPosY, ' ')
    		.drawLine(menuPosX, lgPosY, menuPosX + menuWidth, lgPosY, ' ')
    		.drawLine(menuPosX, exPosY, menuPosX + menuWidth, exPosY, ' ')
    		.putCSIStyledString(menuPosX, ngPosY, buttonStartText)
    		.putCSIStyledString(menuPosX, lgPosY, buttonLoadText)
    		.putCSIStyledString(menuPosX, exPosY, buttonExitText);
    	
    	screen.refresh();
    }
    
    public void UpdateGameView(ArrayList<Field> fieldsToUpdate) throws IOException {
    	gameView.Update(fieldsToUpdate);
    	screen.refresh();
    }
    
    public void Exit() {
    	CloseWindow();
    }
    
    private void CloseWindow() {
    	try {
			screen.stopScreen();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public KeyStroke GetLastKey() throws IOException {
    	return screen.pollInput();
    }
    public KeyStroke WaitForKey() throws IOException {
    	return screen.readInput();
    }
}

