package view;

import java.io.IOException;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;

import maze.Field;

public interface IViewController {
	public Screen OpenWindow();
	public Screen OpenWindow(TerminalSize terminalSize);
	public void SetupWindow();
	public void SetupGameView(int gameColumns, int gameRows) throws Exception;
	public void DrawMenu() throws IOException;
	public void DrawGame(Field[] fields) throws Exception;
	public void CloseWindow();
	
	public void refreshScreen() throws IOException; // TMP
}
