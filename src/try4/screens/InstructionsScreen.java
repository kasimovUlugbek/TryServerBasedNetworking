package try4.screens;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import processing.core.PApplet;
import try4.DrawingSurface;

public class InstructionsScreen extends Screen {

	private DrawingSurface surface;

	public InstructionsScreen(DrawingSurface surface) {
		super(800, 600);
		this.surface = surface;
	}

	public void draw() {

		surface.background(255, 255, 255);

		surface.push();
		surface.fill(0);
		String str = "uh, yeah, play, one hosts, then the others connect to the ip";
		float w = surface.textWidth(str);
		surface.text(str, (surface.width - w) / 2, surface.height / 2);
		surface.pop();

	}

	@Override
	public void keyPressed() {// in the future. show the player that shows that ESC is the way to go back.
		if (surface.key == PApplet.ESC) {
			surface.switchScreen(ScreenSwitcher.MENU_SCREEN);
			surface.key = 0;// This prevents a processing program from closing on escape key
		}
	}

}
