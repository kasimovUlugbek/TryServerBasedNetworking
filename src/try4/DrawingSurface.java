package try4;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import libraryStuff.MP3Player;
import networking.frontend.NetworkManagementPanel;
import processing.core.PApplet;
import processing.core.PImage;
import try4.screens.ClassSelectionScreen;
import try4.screens.CreditsScreen;
import try4.screens.GameScreen;
import try4.screens.InstructionsScreen;
import try4.screens.MenuScreen;
import try4.screens.Screen;
import try4.screens.ScreenSwitcher;

public class DrawingSurface extends PApplet implements ScreenSwitcher {

	// Window stuff
	public float ratioX, ratioY;
	private static final int DRAWING_WIDTH = 1024, DRAWING_HEIGHT = 768;

	private Screen activeScreen;
	private ArrayList<Screen> screens;
	private NetworkManagementPanel nmp;

	public static PImage knightClass_img, healerClass_img, rangedClass_img, unchosenClass_img;
	public static PImage playerArrowPointer_img;
	public static PImage dirtTile_img, grassTile_img, dirtyGrassTile_img, lightGrassTile_img;
	public static PImage woodenSignBoard_img, escSign_img, musicSign_img, disabledMusicSign_img;
	public int selectedClass = -1;

	public MP3Player player = new MP3Player();

	public DrawingSurface() {
		screens = new ArrayList<Screen>();

		MenuScreen menuScreen = new MenuScreen(this);
		screens.add(menuScreen);

		ClassSelectionScreen classSelectionScreen = new ClassSelectionScreen(this);
		screens.add(classSelectionScreen);

		GameScreen gameScreen = new GameScreen(this);
		screens.add(gameScreen);

		InstructionsScreen instructionsScreen = new InstructionsScreen(this);
		screens.add(instructionsScreen);

		CreditsScreen creditsScreen = new CreditsScreen(this);
		screens.add(creditsScreen);

		switchScreen(MENU_SCREEN);
	}

	public void settings() {
		setSize(DRAWING_WIDTH, DRAWING_HEIGHT);
		noSmooth();// stops anti-aliasing
	}

	public void setup() {
		// The Player uses the PApplet in its constructor, so we're initializing a bunch
		// of stuff in setup() instead of the constructor
		// so the PApplet is definitely ready to go.

		knightClass_img = loadImage("img/knight.png");
		rangedClass_img = loadImage("img/ranger.png");
		healerClass_img = loadImage("img/healer.png");
		unchosenClass_img = loadImage("img/unchosen.png");
		escSign_img = loadImage("img/ESCsign.png");
		playerArrowPointer_img = loadImage("img/playerArrowPointer.png");
		dirtTile_img = loadImage("img/dirtTile.png");
		grassTile_img = loadImage("img/grassTile.png");
		dirtyGrassTile_img = loadImage("img/dirtyGrass.png");
		lightGrassTile_img = loadImage("img/lighterGrass.png");
		woodenSignBoard_img = loadImage("img/woodenSignBoard.png");
		musicSign_img = loadImage("img/musicSign.png");
		disabledMusicSign_img = loadImage("img/disabledMusicSign.png");

		String username = "";
		do {
			try {
				username = JOptionPane.showInputDialog("Give yourself a username:").trim();
			} catch (Exception e) {
				// forces you to type a user-name. can't click on the x
			}
		} while (username.length() <= 2);

		((GameScreen) screens.get(ScreenSwitcher.GAME_SCREEN)).setUsername(username);

		for (Screen s : screens)
			s.setup();
		this.windowMove(255, 10);
	}

	public void draw() {

		ratioX = (float) width / activeScreen.DRAWING_WIDTH;
		ratioY = (float) height / activeScreen.DRAWING_HEIGHT;

		push();
		scale(ratioX, ratioY);

		activeScreen.draw();

		pop();

	}

	public void keyPressed() {
		activeScreen.keyPressed();
	}

	@Override
	public void keyReleased() {
		activeScreen.keyReleased();
	}

	public void mousePressed() {
		activeScreen.mousePressed();
	}

	public void mouseMoved() {
		activeScreen.mouseMoved();
	}

	public void mouseDragged() {
		activeScreen.mouseDragged();
	}

	public void mouseReleased() {
		activeScreen.mouseReleased();
	}

	public Point assumedCoordinatesToActual(Point assumed) {
		return new Point((int) (assumed.getX() * ratioX), (int) (assumed.getY() * ratioY));
	}

	public Point actualCoordinatesToAssumed(Point actual) {
		return new Point((int) (actual.getX() / ratioX), (int) (actual.getY() / ratioY));
	}

	@Override
	public void switchScreen(int i) {
		activeScreen = screens.get(i);
		activeScreen.onSwitchedTo();
	}

	public void startNetworkingWindow() {
		if (nmp == null)
			nmp = new NetworkManagementPanel("ProcessingAction", 6, (GameScreen) screens.get(ScreenSwitcher.GAME_SCREEN));
	}

	public void hideNetworkingWindow() {
		if (nmp == null)
			return;
		nmp.setVisible(false);
		nmp = null;
	}

}
