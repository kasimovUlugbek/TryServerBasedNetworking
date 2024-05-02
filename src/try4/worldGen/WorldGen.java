package try4.worldGen;

import java.util.ArrayList;

import processing.core.PApplet;

public class WorldGen {
	private PApplet surface;
	private final int renderChunkRadius = 2;// chunks in each direction
	private final int renderRadius = renderChunkRadius * (int) Chunk.TILE_SIZE * Chunk.TILE_PER_CHUNK;// chunks in each direction
	private ArrayList<Chunk> chunks;
	private ArrayList<Integer> chunksToDestroy;

	public WorldGen(PApplet surface, int seed) {
		this.surface = surface;
		chunks = new ArrayList<Chunk>();
		chunksToDestroy = new ArrayList<Integer>();
		surface.noiseSeed(seed);
	}

	// plan:
	// confirm the chunks within render distance
	// fill in the possible new missing spots
	// deletes all the chunks that were not confirmed
	// draw
	boolean foundTheChunk;
	public void draw(double playerPosX, double playerPosY) {
		boolean[] checkedChunks = new boolean[chunks.size()];
		int signX = (int) (playerPosX / Math.abs(playerPosX));// negative or positive
		int signY = (int) (playerPosY / Math.abs(playerPosY));
		int playerChunkX = (int) (playerPosX / (Chunk.TILE_SIZE * Chunk.TILE_PER_CHUNK) + 0.5 * signX);
		int playerChunkY = (int) (playerPosY / (Chunk.TILE_SIZE * Chunk.TILE_PER_CHUNK) + 0.5 * signY);

		int keepingChunks = 0;
		for (int i = 0; i < chunks.size(); i++) {// checking and confirming chunks that should stay
//			if ((areInRange(chunks.get(i).positionX, playerPosX + renderRadius, renderChunkRadius)
//					|| areInRange(chunks.get(i).positionX, playerPosX - renderRadius, renderChunkRadius))
//					&& (areInRange(chunks.get(i).positionY, playerPosY + renderRadius, renderChunkRadius)
//							|| areInRange(chunks.get(i).positionY, playerPosY - renderRadius, renderChunkRadius))) {
			if (chunks.get(i).positionX <= playerPosX + renderRadius && chunks.get(i).positionY <= playerPosY + renderRadius) {
				keepingChunks++;
				checkedChunks[i] = true;
			}
		}
		System.out.println("keeping " + keepingChunks + " chunks");

		foundTheChunk = false;
		for (int j = playerChunkX + -renderChunkRadius; j < playerChunkX + renderChunkRadius; j++) {
			for (int k = playerChunkY + -renderChunkRadius; k < playerChunkY + renderChunkRadius; k++) {
				for (int i = 0; i < chunks.size(); i++) {
					if (chunks.get(i).positionX == k * Chunk.TILE_SIZE * Chunk.TILE_PER_CHUNK && chunks.get(i).positionY == j * Chunk.TILE_SIZE * Chunk.TILE_PER_CHUNK) {
						foundTheChunk = true;
					}
				}
				if (!foundTheChunk) {
					// create a new chunk in this location
					chunks.add(new Chunk(surface, (int) (k * Chunk.TILE_SIZE * Chunk.TILE_PER_CHUNK), (int) (j * Chunk.TILE_SIZE * Chunk.TILE_PER_CHUNK)));
					System.out.println("creating a chunk");
				}
				foundTheChunk = false;
			}
		}

		
		// remove chunks that were not confirmed
		for (int i = 0; i < checkedChunks.length; i++) {
			if (!checkedChunks[i]) {
				chunksToDestroy.add(i);
			}
		}

		int removed = 0;
		for (int chunkInt : chunksToDestroy) {
			chunks.remove(chunkInt - removed);
			removed++;
		}

		for (Chunk chunk : chunks) {
			chunk.draw(surface);
		}
	}

	private boolean areInRange(double a, double b, double range) {
		return Math.abs(a - b) <= range;
	}
}
