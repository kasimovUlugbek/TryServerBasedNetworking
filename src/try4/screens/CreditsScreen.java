package try4.screens;

import processing.core.PApplet;
import try4.DrawingSurface;

public class CreditsScreen extends Screen {

	private DrawingSurface surface;

	public CreditsScreen(DrawingSurface surface) {
		super(800, 600);
		this.surface = surface;
	}
	
	public void draw() {
		surface.background(255);
		
		surface.fill(0);
		String str = "Bloodbath Brigade™, made by Evan Wescott.";
		float w = surface.textWidth(str);
		surface.text(str, (surface.width - w) / 2, surface.height / 2);
	}
	
	public void keyPressed() {
		if (surface.key == PApplet.ESC) {
			surface.switchScreen(ScreenSwitcher.MENU_SCREEN);
			surface.key = 0;
		}
	}

}
