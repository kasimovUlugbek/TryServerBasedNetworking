package libraryStuff;

import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class MP3Player {

	private Player player;

	public void play(String filename, int skipInSeconds) {
		new Thread(() -> {
			try {
				FileInputStream fis = new FileInputStream(filename);
				player = new Player(fis);
				int startTimeMillis = skipInSeconds*1000;
				player.play();
				
			} catch (Exception e) {
				System.out.println("Skibidi had a problem playing file " + filename);
				System.out.println();
			}
		}).start();
	}

	public void stop() {
		if (player != null) {
			player.close();
			player = null;
		}
	}

	public boolean isTurnedOn() {
		return player != null;
	}

	public int getMusicProgress() {
		if (player == null)
			return 0;
		return player.getPosition();
	}

	public boolean isFinished() {
		if (player == null)
			return false;
		return player.isComplete();
	}

}
