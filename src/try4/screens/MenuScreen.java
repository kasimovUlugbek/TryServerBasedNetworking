package try4.screens;

import processing.core.PImage;

import java.awt.Point;
import java.awt.Rectangle;
import try4.DrawingSurface;
import try4.MP3Player;

public class MenuScreen extends Screen {

	private DrawingSurface surface;
	private PImage titleImage;

	private Rectangle playButton, instrButton, credButton;
	
	public MenuScreen(DrawingSurface surface) {
		super(800, 600);
		this.surface = surface;

		playButton = new Rectangle(800 / 2 - 100, 600 / 2 - 100, 200, 75);
		instrButton = new Rectangle(800 / 2 - 100, 600 / 2, 200, 75);
		credButton = new Rectangle(800 / 2 - 100, 600 / 2 + 100, 200, 75);
		
	}
	
	public void setup() {
		titleImage = surface.loadImage("resources\\c581177a2a4988860651d8161539b322.png");
		//MP3Player.play("resources\\newmutation.mp3");
		surface.player.play("resources\\newmutation.mp3");
	}

	public void draw() {

		surface.background(255, 255, 255);

		surface.image(titleImage, DRAWING_WIDTH/8, DRAWING_HEIGHT/8, DRAWING_WIDTH*3/4, DRAWING_HEIGHT/8);
		
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

		surface.push();
		surface.rect(credButton.x, credButton.y, credButton.width, credButton.height, 10, 10, 10, 10);
		surface.fill(0);
		str = "Credits";
		w = surface.textWidth(str);
		surface.text(str, credButton.x + credButton.width / 2 - w / 2, credButton.y + credButton.height / 2);
		surface.pop();
		
	}

	public void mousePressed() {
		Point p = surface.actualCoordinatesToAssumed(new Point(surface.mouseX, surface.mouseY));

		if (playButton.contains(p)) {
			surface.switchScreen(ScreenSwitcher.CLASS_SELECTION_SCREEN);
			surface.StartnetowkringThing();
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
	}

}
