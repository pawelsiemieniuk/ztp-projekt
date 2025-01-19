package game;

import maze.Side;

public interface IGameController {
	public void Init();
	public void Menu();
	public void Exit();
	public void StartGame();
	
	public void DrawMenu();
	public void UpdateHeader(Boolean paused);
	public void PauseGame();
	public void ResumeGame();
	public void SelectMenuOption();
	public void EnterMenuOption();
	public void Move(Side side);
}
