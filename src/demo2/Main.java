package demo2;

import networking.frontend.NetworkManagementPanel;
import processing.core.PApplet;

public class Main {
	public static void main(String args[]) {

		DrawingSurface drawing = new DrawingSurface();
		PApplet.runSketch(new String[]{""}, drawing);
		drawing.windowResizable(true);

		NetworkManagementPanel nmp = new NetworkManagementPanel("ProcessingDrawing", 6, drawing);

	}
}
