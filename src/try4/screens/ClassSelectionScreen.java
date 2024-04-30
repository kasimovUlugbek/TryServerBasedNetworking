package try4.screens;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TextArea;

import processing.core.PApplet;
import processing.core.PImage;
import try4.DrawingSurface;

public class ClassSelectionScreen extends Screen {

	private DrawingSurface surface;

	private Rectangle healerButton;
	private Rectangle rangerButton;
	private Rectangle knightButton;

	private Rectangle selectButton;

	private Rectangle portraitSquare;
	private TextArea textArea;

	public ClassSelectionScreen(DrawingSurface surface) {
		super(800, 600);
		this.surface = surface;
	}

	int healerW, rangerW, knightW, selectW;

	@Override
	public void setup() {
		int blockX = DRAWING_WIDTH - DRAWING_WIDTH / 5;
		int blockMid = DRAWING_HEIGHT / 2;

		surface.push();
		surface.textSize(30);
		healerW = (int) surface.textWidth("healer");
		healerButton = new Rectangle(blockX - healerW, blockMid - 75, healerW * 2, 50);
		rangerW = (int) surface.textWidth("ranger");
		rangerButton = new Rectangle(blockX - rangerW, blockMid, rangerW * 2, 50);
		knightW = (int) surface.textWidth("knight");
		knightButton = new Rectangle(blockX - knightW, blockMid + 75, knightW * 2, 50);

		selectW = (int) surface.textWidth("select");
		selectButton = new Rectangle(DRAWING_WIDTH - selectW - 50, DRAWING_HEIGHT - 50, (int) (selectW * 1.5), 50);

		portraitSquare = new Rectangle(50, 50, (int) (DRAWING_WIDTH / 2.5), (int) (DRAWING_WIDTH / 2.5));

		surface.pop();
	}

	@Override
	public void draw() {
		surface.background(255, 255, 255);

		surface.push();
		surface.textSize(30);
		surface.textAlign(PApplet.LEFT, PApplet.TOP);
		surface.noStroke();

		surface.fill(50, 50);
		surface.rect(healerButton.x, healerButton.y, healerButton.width, healerButton.height, 10, 10, 10, 10);
		surface.fill(0);
		surface.text("Healer", healerButton.x + healerW * 0.5f, healerButton.y);

		surface.fill(50, 50);
		surface.rect(rangerButton.x, rangerButton.y, rangerButton.width, rangerButton.height, 10, 10, 10, 10);
		surface.fill(0);
		surface.text("Ranger", rangerButton.x + rangerW * 0.5f, rangerButton.y);

		surface.fill(50, 50);
		surface.rect(knightButton.x, knightButton.y, knightButton.width, knightButton.height, 10, 10, 10, 10);
		surface.fill(0);
		surface.text("Knight", knightButton.x + knightW * 0.5f, knightButton.y);

		surface.fill(30, 50);
		surface.rect(selectButton.x, selectButton.y, selectButton.width, selectButton.height, 10, 10, 10, 10);
		surface.fill(0);
		surface.text("select", selectButton.x + selectW * 0.5f, selectButton.y);

		if (surface.selectedClass == 0)
			surface.image(DrawingSurface.healerClass_img, portraitSquare.x, portraitSquare.y);
		else if (surface.selectedClass == 1)
			surface.image(DrawingSurface.rangedClass_img, portraitSquare.x, portraitSquare.y);
		else if (surface.selectedClass == 2)
			surface.image(DrawingSurface.knightClass_img, portraitSquare.x, portraitSquare.y);
		surface.pop();

//		surface.push();
	}

	public void mousePressed() {
		Point p = surface.actualCoordinatesToAssumed(new Point(surface.mouseX, surface.mouseY));

		if (selectButton.contains(p) && surface.selectedClass != -1) {
			surface.switchScreen(ScreenSwitcher.GAME_SCREEN);
			return;
		}

		if (healerButton.contains(p)) {
			surface.selectedClass = 0;
			return;
		} else if (rangerButton.contains(p)) {
			surface.selectedClass = 1;
			return;
		} else if (knightButton.contains(p)) {
			surface.selectedClass = 2;
			return;
		}
	}

}
