package try4.worldGen;

import processing.core.PApplet;
import processing.core.PImage;
import try4.DrawingSurface;

public class Chunk {

	public int positionX, positionY;// position of top left corner
	public static final int TILE_PER_CHUNK = 8;
	public static final int TILE_SIZE = 40;
	private float noiseScale = 0.1f;
	private int[][] tiles;// I feel like using shorts will not make much difference

	public Chunk(PApplet surface, int positionX, int positionY) {
		this.positionX = positionX;
		this.positionY = positionY;

		tiles = new int[TILE_PER_CHUNK][TILE_PER_CHUNK];

		for (int j = 0; j < tiles.length; j++) {
			for (int k = 0; k < tiles[j].length; k++) {
				tiles[j][k] = (int) (surface.noise((positionX + k * TILE_SIZE) * noiseScale, (positionY + j * TILE_SIZE) * noiseScale) * 100);
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
