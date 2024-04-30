package try4.screens;

public interface ScreenSwitcher {
	public static final int MENU_SCREEN = 0;
//	public static final int CLASS_SELECTION_SCREEN = 1;
	public static final int GAME_SCREEN = 1;
	public static final int INSTRUCTIONS_SCREEN = 2;

	public void switchScreen(int i);
}
