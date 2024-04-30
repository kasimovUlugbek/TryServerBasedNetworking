package try4.screens;

import java.awt.Point;
import java.awt.Rectangle;

import try4.DrawingSurface;

public class MenuScreen extends Screen {

	private DrawingSurface surface;

	private Rectangle playButton;
	private Rectangle instrButton;

	public MenuScreen(DrawingSurface surface) {
		super(800, 600);
		this.surface = surface;

		playButton = new Rectangle(800 / 2 - 100, 600 / 2 - 50, 200, 100);
		instrButton = new Rectangle(800 / 2 - 100, 600 / 2 + 100, 200, 100);
	}

	public void draw() {

		surface.background(255, 255, 255);

		surface.push();
		surface.rect(playButton.x, playButton.y, playButton.width, playButton.height, 10, 10, 10, 10);
		surface.fill(0);
		String str = "Play";
		float w = surface.textWidth(str);
		surface.text(str, playButton.x + playButton.width / 2 - w / 2, playButton.y + playButton.height / 2);
		surface.pop();

		surface.push();
		surface.rect(instrButton.x, instrButton.y, instrButton.width, instrButton.height, 10, 10, 10, 10);
		surface.fill(0);
		str = "Instructions";
		w = surface.textWidth(str);
		surface.text(str, instrButton.x + instrButton.width / 2 - w / 2, instrButton.y + instrButton.height / 2);
		surface.pop();

	}

	public void mousePressed() {
		Point p = surface.actualCoordinatesToAssumed(new Point(surface.mouseX, surface.mouseY));

		if (playButton.contains(p)) {
			surface.switchScreen(ScreenSwitcher.GAME_SCREEN);
			surface.StartnetowkringThing();
			surface.switchScreen(ScreenSwitcher.GAME_SCREEN);
			return;
		}

		if (instrButton.contains(p)) {
			surface.switchScreen(ScreenSwitcher.INSTRUCTIONS_SCREEN);
			return;
		}
	}

}
