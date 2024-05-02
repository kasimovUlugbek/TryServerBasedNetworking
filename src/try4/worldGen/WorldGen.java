package try4.worldGen;

import java.util.ArrayList;

import processing.core.PApplet;

public class WorldGen {
	private PApplet surface;
	private int renderDist = 2;// chunks in each direction
	private ArrayList<Chunk> chunks;

	public WorldGen(PApplet surface, int seed) {
		this.surface = surface;
		chunks = new ArrayList<Chunk>();
		surface.noiseSeed(seed);
	}

	// plan:
	// make array of booleans that keeps track of if the cell was checked and
	// confirmed
	// check that we have all the spots filled.
	// those that were checked with booleans are deleted
	// draw
	public void draw(double playerPosX, double playerPosY) {
		boolean[] checkedChunks = new boolean[chunks.size()];

		int keepingChunks = 0;
		for (int i = 0; i < chunks.size(); i++) {// checking and confirming chunks that should stay
			if (chunks.get(i).positionX <= renderDist * Chunk.TILE_SIZE * Chunk.TILE_PER_CHUNK
					&& chunks.get(i).positionY <= renderDist * Chunk.TILE_SIZE * Chunk.TILE_PER_CHUNK) {
				keepingChunks++;
				checkedChunks[i] = true;
			}
		}
//		System.out.println(keepingChunks);

		boolean foundTheChunk = false;
		for (int j = -renderDist; j < renderDist; j++) {
			for (int k = -renderDist; k < renderDist; k++) {
				for (int i = 0; i < chunks.size(); i++) {
					if (chunks.get(i).positionX == k * Chunk.TILE_SIZE * Chunk.TILE_PER_CHUNK && chunks.get(i).positionY == j * Chunk.TILE_SIZE * Chunk.TILE_PER_CHUNK) {
						foundTheChunk = true;
					}
				}
				if (!foundTheChunk) {
					// create a new chunk in this location
					chunks.add(new Chunk(surface, (int) (k * Chunk.TILE_SIZE * Chunk.TILE_PER_CHUNK),
							(int) (j * Chunk.TILE_SIZE * Chunk.TILE_PER_CHUNK)));
					System.out.println("creating a chunk");
				}
				foundTheChunk = false;
			}
		}
		for (int i = 0; i < checkedChunks.length; i++) {// remove chunks that were not confirmed
			if (!checkedChunks[i])
				chunks.remove(i);
		}

		for (Chunk chunk : chunks) {
			chunk.draw(surface);
		}
	}
}
