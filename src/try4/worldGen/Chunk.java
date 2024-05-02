package try4.worldGen;

import processing.core.PApplet;
import processing.core.PImage;
import try4.DrawingSurface;

public class Chunk {

	public int positionX, positionY;// position of top left corner
	public static final int TILE_PER_CHUNK = 8;
	public static final float TILE_SIZE = 20;
	private int[][] tiles;// I feel like using shorts will not make much difference

	public Chunk(PApplet surface, int positionX, int positionY) {
		this.positionX = positionX;
		this.positionY = positionY;

		tiles = new int[TILE_PER_CHUNK][TILE_PER_CHUNK];

		for (int j = 0; j < tiles.length; j++) {
			for (int i = 0; i < tiles[j].length; i++) {
				tiles[j][i] = (int) (surface.noise(i * 0.1f, j * 0.1f) * 100);
			}
		}
	}

	public void draw(PApplet surface) {
		PImage chosenImg;
		int val;
		for (int j = 0; j < tiles.length; j++) {
			for (int k = 0; k < tiles.length; k++) {
				val = tiles[j][k];
				if (60 > val) {
					chosenImg = DrawingSurface.dirtTile_img;
				} else {
					chosenImg = DrawingSurface.grassTile_img;
				}
				surface.image(chosenImg, k * TILE_SIZE + positionX, j * TILE_SIZE + positionY, TILE_SIZE, TILE_SIZE);
			}
		}
	}

}
