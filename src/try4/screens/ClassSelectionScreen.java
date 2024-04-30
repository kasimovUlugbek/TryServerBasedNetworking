package try4.screens;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TextArea;

import processing.core.PImage;
import try4.DrawingSurface;

public class ClassSelectionScreen extends Screen {

	private DrawingSurface surface;

	private Rectangle healerButton;
	private Rectangle rangerButton;
	private Rectangle knightButton;

	private Rectangle portraitSquare;
	private TextArea textArea;

	public ClassSelectionScreen(DrawingSurface surface) {
		super(800, 600);
		this.surface = surface;

		int blockWidth = ((DRAWING_WIDTH - 200) / 3);
		int blockHeight = ((DRAWING_HEIGHT - 200) / 3);
		healerButton = new Rectangle(100, 50, blockWidth, blockHeight);
		rangerButton = new Rectangle(100 + blockWidth, 50, blockWidth, blockHeight);
		knightButton = new Rectangle(100 + blockWidth * 2, 50, blockWidth, blockHeight);
	}

	@Override
	public void draw() {
		surface.background(255, 255, 255);

		surface.rect(healerButton.x, healerButton.y, healerButton.width, healerButton.height, 10, 10, 10, 10);
		surface.fill(0);
		String str = "Play";
		float w = surface.textWidth(str);
		surface.text(str, healerButton.x + healerButton.width / 2 - w / 2, healerButton.y + healerButton.height / 2);

		surface.push();
		surface.rect(knightButton.x, knightButton.y, knightButton.width, knightButton.height, 10, 10, 10, 10);
		surface.fill(0);
		str = "Instructions";
		w = surface.textWidth(str);
		surface.text(str, knightButton.x + knightButton.width / 2 - w / 2, knightButton.y + knightButton.height / 2);
		surface.pop();
	}

	public void mousePressed() {
		Point p = surface.actualCoordinatesToAssumed(new Point(surface.mouseX, surface.mouseY));

		if (healerButton.contains(p) && surface.selectedClass != -1) {
			surface.switchScreen(ScreenSwitcher.GAME_SCREEN);
			return;
		}

		if (knightButton.contains(p)) {

			return;
		}
	}

}
