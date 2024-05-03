package try4.worldGen;

import java.awt.Color;
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
	int keepingChunks;
	int removed;
	boolean foundChunk;

	public void draw(double playerPosX, double playerPosY) {
		int signX = (int) (playerPosX / Math.abs(playerPosX));// negative or positive
		int signY = (int) (playerPosY / Math.abs(playerPosY));
		int chunkWidth = Chunk.TILE_SIZE * Chunk.TILE_PER_CHUNK;
		int playerChunkX = (int) (playerPosX / (chunkWidth) + 0.5 * signX);
		int playerChunkY = (int) (playerPosY / (chunkWidth) + 0.5 * signY);

		keepingChunks = 0;
		boolean[] checkedChunks = new boolean[chunks.size()];
		for (int i = 0; i < chunks.size(); i++) {// checking and confirming chunks that should stay
			if (isWithinRenderDist(playerChunkX, playerChunkY, chunks.get(i).positionX / chunkWidth, chunks.get(i).positionY / chunkWidth)) {
				checkedChunks[i] = true;
				keepingChunks++;
			}
		}
		System.out.println("keeping " + keepingChunks + " chunks");

		foundChunk = false;
		for (int j = playerChunkY - renderChunkRadius; j < playerChunkY + renderChunkRadius; j++) {
			for (int k = playerChunkX - renderChunkRadius; k < playerChunkX + renderChunkRadius; k++) {
				for (Chunk chunk : chunks) {
					if ((chunk.positionX == (k) * chunkWidth) && (chunk.positionY == (j) * chunkWidth)) {
						foundChunk = true;
//						System.out.println("found the tile " + k + " " + j);
						break;
					}
				}
				if (!foundChunk) {
					chunks.add(new Chunk(surface, (k) * chunkWidth, (j) * chunkWidth));
					System.out.println("creating a chunk");
				}
			}
		}

		// remove chunks that were not confirmed
		for (int i = 0; i < checkedChunks.length; i++) {
			if (checkedChunks[i] == false) {
				chunksToDestroy.add(i);
			}
		}

		removed = 0;
		for (int chunkInt : chunksToDestroy) {
			chunks.remove(chunkInt - removed);
			removed++;
		}
		chunksToDestroy.clear();

		for (Chunk chunk : chunks) {
			chunk.draw(surface);
		}
		surface.fill(Color.red.getRGB());
		surface.circle(playerChunkX * chunkWidth, playerChunkY * chunkWidth, 50);
		surface.noFill();
		surface.rect(playerChunkX * chunkWidth - renderRadius, playerChunkY * chunkWidth - renderRadius, renderRadius * 2, renderRadius * 2);
	}

	private boolean isWithinRenderDist(int playerChunkX, int playerChunkY, int inputChunkX, int inputChunkY) {
		return inputChunkX >= playerChunkX - renderChunkRadius && inputChunkX < playerChunkX + renderChunkRadius
				&& inputChunkY >= playerChunkY - renderChunkRadius && inputChunkY < playerChunkY + renderChunkRadius;
	}
}
