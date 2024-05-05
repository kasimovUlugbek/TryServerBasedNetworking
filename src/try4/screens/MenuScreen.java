package try4.screens;

import processing.core.PImage;

import java.awt.Point;
import java.awt.Rectangle;

import try4.DrawingSurface;

public class MenuScreen extends Screen {

	private DrawingSurface surface;
	private PImage titleImage;

	private Rectangle playButton, instrButton, credButton, musicToggleButton;

	private int musicTimer = 0;// in milliseconds I think

	public MenuScreen(DrawingSurface surface) {
		super(800, 600);
		this.surface = surface;

		playButton = new Rectangle(800 / 2 - 100, 600 / 2 - 100, 200, 75);
		instrButton = new Rectangle(800 / 2 - 100, 600 / 2 - 10, 200, 75);
		credButton = new Rectangle(800 / 2 - 100, 600 / 2 + 80, 200, 75);

		musicToggleButton = new Rectangle(DRAWING_WIDTH - 50, DRAWING_HEIGHT / 8, 40, 40);

	}

	public void setup() {
		titleImage = surface.loadImage("resources\\c581177a2a4988860651d8161539b322.png");// replace this with a font

		surface.player.play("resources\\\\newmutation.mp3", 0);// uncomment when exporting/presenting project
	}

	public void draw() {
		if (surface.player.isFinished()) {
			musicTimer = 0;
			surface.player.play("resources\\\\newmutation.mp3", musicTimer);
		}
		surface.background(255, 115, 100);

		surface.image(titleImage, DRAWING_WIDTH / 8, DRAWING_HEIGHT / 8, DRAWING_WIDTH * 3 / 4, DRAWING_HEIGHT / 8);

		surface.push();
		surface.image(DrawingSurface.woodenSignBoard_img, playButton.x, playButton.y, playButton.width, playButton.height);
		surface.fill(0);
		String str = "Play";
		float w = surface.textWidth(str);
		surface.text(str, playButton.x + playButton.width / 2 - w / 2, playButton.y + playButton.height / 2);
		surface.pop();

		surface.push();
		surface.image(DrawingSurface.woodenSignBoard_img, instrButton.x, instrButton.y, instrButton.width, instrButton.height);
		surface.fill(0);
		str = "Instructions";
		w = surface.textWidth(str);
		surface.text(str, instrButton.x + instrButton.width / 2 - w / 2, instrButton.y + instrButton.height / 2);
		surface.pop();

		surface.push();
		surface.image(DrawingSurface.woodenSignBoard_img, credButton.x, credButton.y, credButton.width, credButton.height);
		surface.fill(0);
		str = "Credits";
		w = surface.textWidth(str);
		surface.text(str, credButton.x + credButton.width / 2 - w / 2, credButton.y + credButton.height / 2);
		surface.pop();

		surface.push();
		PImage chosenImage = DrawingSurface.disabledMusicSign_img;
		if (surface.player.isTurnedOn())
			chosenImage = DrawingSurface.musicSign_img;

		surface.image(chosenImage, musicToggleButton.x, musicToggleButton.y, musicToggleButton.width, musicToggleButton.height);
		surface.pop();

	}

	public void mousePressed() {
		Point p = surface.actualCoordinatesToAssumed(new Point(surface.mouseX, surface.mouseY));

		if (playButton.contains(p)) {
			surface.switchScreen(ScreenSwitcher.CLASS_SELECTION_SCREEN);
			surface.startNetworkingWindow();
			return;
		}

		if (instrButton.contains(p)) {
			surface.switchScreen(ScreenSwitcher.INSTRUCTIONS_SCREEN);
			return;
		}

		if (credButton.contains(p)) {
			surface.switchScreen(ScreenSwitcher.CREDITS_SCREEN);
			return;
		}

		if (musicToggleButton.contains(p)) {
			if (surface.player.isTurnedOn()) {
				musicTimer = surface.player.getMusicProgress();
				surface.player.stop();
			} else {
				System.out.println(musicTimer);
				surface.player.play("resources\\\\newmutation.mp3", musicTimer);
			}
			return;
		}
	}

}
