package try4;
import javazoom.jl.player.Player;
import java.io.FileInputStream;

public class MP3Player {

	private Player player;
	
	public void play(String filename) {
		new Thread(() -> {
			try {
				FileInputStream fis = new FileInputStream(filename);
				player = new Player(fis);
				player.play();
			} catch (Exception e) {
				System.out.println("Skibidi had a problem playing file " + filename);
				System.out.println();
			}
		}).start();
	}
	
	public void stop() {
		player.close();
	}
	
}
