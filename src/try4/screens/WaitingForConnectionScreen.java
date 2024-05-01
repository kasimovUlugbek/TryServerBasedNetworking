package try4.screens;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TextArea;

import processing.core.PApplet;
import processing.core.PImage;
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
