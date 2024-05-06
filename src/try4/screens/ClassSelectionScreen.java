package try4.screens;

import java.awt.Point;
import java.awt.Rectangle;

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
		surface.background(255, 115, 100);

		surface.push();
		surface.textSize(30);
		surface.textAlign(PApplet.LEFT, PApplet.TOP);
		surface.noStroke();

		surface.fill(100, 100);
		surface.rect(healerButton.x, healerButton.y, healerButton.width, healerButton.height, 10, 10, 10, 10);
		surface.fill(0);
		surface.text("Healer", healerButton.x + healerW * 0.5f, healerButton.y);

		surface.fill(100, 100);
		surface.rect(rangerButton.x, rangerButton.y, rangerButton.width, rangerButton.height, 10, 10, 10, 10);
		surface.fill(0);
		surface.text("Ranger", rangerButton.x + rangerW * 0.5f, rangerButton.y);

		surface.fill(100, 100);
		surface.rect(knightButton.x, knightButton.y, knightButton.width, knightButton.height, 10, 10, 10, 10);
		surface.fill(0);
		surface.text("Knight", knightButton.x + knightW * 0.5f, knightButton.y);

		surface.fill(30, 50);
		surface.rect(selectButton.x, selectButton.y, selectButton.width, selectButton.height, 10, 10, 10, 10);
		surface.fill(0);
		surface.text("select", selectButton.x + selectW * 0.5f, selectButton.y);

		PImage tempClassImage = DrawingSurface.unchosenClass_img;
		String description = "choose a class";
		if (surface.selectedClass == 0) {
			tempClassImage = DrawingSurface.healerClass_img;
			description = "Guy who heals\nPASSIVE: kills self-heal a small amount\nM1: fire a damaging projectile toward the cursor\nQ: ";// can maybe get strings from reading text files. if we'l need to
		} else if (surface.selectedClass == 1) {
			tempClassImage = DrawingSurface.rangedClass_img;
			description = "Guy who exterminates\nM1: fire a volley of three damaging arrows toward the cursor\nQ: fire a large arrow that deals heavy damage and pierces enemies\nE: summon a storm of arrows in an area around the cursor, damaging enemies over time";
		} else if (surface.selectedClass == 2) {
			tempClassImage = DrawingSurface.knightClass_img;
			description = "Guy who protects\nPASSIVE: a shield points toward the cursor, blocking projectiles";
		}
		surface.fill(80, 80);
		surface.rect(portraitSquare.x-5, portraitSquare.y + portraitSquare.height-5, portraitSquare.width+10,
				portraitSquare.height+10, 10, 10, 10, 10);
		surface.fill(0);
		surface.textSize(15);
		surface.text(description, portraitSquare.x, portraitSquare.y + portraitSquare.height, portraitSquare.width,
				portraitSquare.height);
		surface.image(tempClassImage, portraitSquare.x, portraitSquare.y, portraitSquare.width, portraitSquare.height);
		surface.pop();

//		surface.push();
	}

	public void mousePressed() {
		Point p = surface.actualCoordinatesToAssumed(new Point(surface.mouseX, surface.mouseY));

		if (selectButton.contains(p) && surface.selectedClass != -1) {
			surface.switchScreen(ScreenSwitcher.GAME_SCREEN);
			surface.player.stop();
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
