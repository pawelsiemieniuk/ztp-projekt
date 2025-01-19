package view;

import java.io.IOException;
import java.util.ArrayList;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import maze.Field;

public interface IViewController {
	public Screen OpenWindow();
	public Screen OpenWindow(TerminalSize terminalSize, int terminalFontSize);
	
	public void SetupWindow();
	public void SetupGameView(int gameColumns, int gameRows) throws Exception;
	
	public void DrawMenu() throws IOException;
	public void DrawGame(Field[] fields) throws Exception;
	
	public void UpdateMenu(MenuSelect option) throws IOException;
	public void UpdateHeader(Boolean paused);
	public void UpdateGameView(ArrayList<Field> fieldsToUpdate) throws IOException;
	
	public void Exit();
	
	public KeyStroke GetLastKey() throws IOException;
	public KeyStroke WaitForKey() throws IOException;
	
}
