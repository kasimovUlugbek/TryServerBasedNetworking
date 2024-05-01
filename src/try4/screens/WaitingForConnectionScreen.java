package try4.screens;


import processing.core.PApplet;
import try4.DrawingSurface;

public class WaitingForConnectionScreen extends Screen {

	private DrawingSurface surface;

	public WaitingForConnectionScreen(DrawingSurface surface) {
		super(800, 600);
		this.surface = surface;
	}

	@Override
	public void setup() {
		
	}

	@Override
	public void draw() {
		surface.background(255, 255, 255);

		surface.push();
		surface.textSize(30);
		surface.textAlign(PApplet.LEFT, PApplet.TOP);
		surface.noStroke();

		surface.pop();
	}

}
