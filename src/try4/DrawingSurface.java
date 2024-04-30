package try4;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import networking.frontend.NetworkManagementPanel;
import processing.core.PApplet;
import processing.core.PImage;
import try4.screens.ClassSelectionScreen;
import try4.screens.GameScreen;
import try4.screens.Screen;
import try4.screens.ScreenSwitcher;

public class DrawingSurface extends PApplet implements ScreenSwitcher {

	// Window stuff
	public float ratioX, ratioY;
	private static final int DRAWING_WIDTH = 800, DRAWING_HEIGHT = 600;

	private Screen activeScreen;
	private ArrayList<Screen> screens;
	private NetworkManagementPanel nmp;

	private PImage knightClass_img, healerClass_img, rangedClass_img;

	public DrawingSurface() {
		screens = new ArrayList<Screen>();

		ClassSelectionScreen gameClassSelectionScreen = new ClassSelectionScreen(this);
		screens.add(gameClassSelectionScreen);

		GameScreen gameScreen = new GameScreen(this);
		screens.add(gameScreen);

		activeScreen = screens.get(0);
	}

	public void settings() {
		setSize(DRAWING_WIDTH, DRAWING_HEIGHT);
	}

	public void setup() {
		// The Player uses the PApplet in its constructor, so we're initializing a bunch
		// of stuff in setup() instead of the constructor
		// so the PApplet is definitely ready to go.

		knightClass_img = loadImage("img/knight.png");
		rangedClass_img = loadImage("img/ranger.png");
		healerClass_img = loadImage("img/healer.png");

		String username = JOptionPane.showInputDialog("Give yourself a username:");

		for (Screen s : screens)
			s.setup();

	}

	public void draw() {

		ratioX = (float) width / activeScreen.DRAWING_WIDTH;
		ratioY = (float) height / activeScreen.DRAWING_HEIGHT;

		push();

		scale(ratioX, ratioY);

		activeScreen.draw();

		pop();

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
		if (screens.get(i) instanceof GameScreen)
			nmp = new NetworkManagementPanel("ProcessingAction", 6, (GameScreen) screens.get(i));
	}

}
