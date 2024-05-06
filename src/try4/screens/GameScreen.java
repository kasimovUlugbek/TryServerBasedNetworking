package try4.screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Queue;

import try4.Bullet;
import try4.DrawingSurface;
import try4.Enemy;
import networking.frontend.NetworkDataObject;
import networking.frontend.NetworkListener;
import networking.frontend.NetworkMessenger;
import processing.core.PApplet;
import try4.Player;
import try4.PlayerData;
import try4.worldGen.WorldGen;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameScreen extends Screen implements NetworkListener {

	// Drawing stuff
	private Player me;
	private ArrayList<Player> players;
	private ArrayList<Integer> keysDown;
	private CopyOnWriteArrayList<Enemy> enemies;
	private DrawingSurface surface;
	private WorldGen worldGen;

	public int randomSeed, lastCalledFrame;

	private String username;

	private float baseTextSize = 25;

	private NetworkMessenger nm;

//	private static final String messageTypePlayerJoined = "PLAYER_JOINED";
	private static final String messageTypeSeedSync = "SEED_SYNC";
	private static final String messageTypeInit = "CREATE_PLAYER";
	private static final String messageTypePlayerUpdate = "PLAYER_UPDATE";

	public GameScreen(DrawingSurface surface) {
		super(800, 600);
		this.surface = surface;
		keysDown = new ArrayList<Integer>();
		players = new ArrayList<Player>();
		enemies = new CopyOnWriteArrayList<Enemy>();
	}

	@Override
	public void setup() {
		zoomScale = 0.5f;

		worldGen = new WorldGen(surface);
		randomSeed = (int) (69420 * Math.random());
//		worldGen.setSeed(randomSeed);
	}

	@Override
	public void onSwitchedTo() {
		me = new Player("me!", username, surface.selectedClass, DRAWING_WIDTH / 2, DRAWING_HEIGHT / 2);

		// will not let the player join or host if they are already in a world.
//		surface.hideNetworkingWindow(); //uncoment this when exporting game.
	}

	public void setUsername(String username) {
		this.username = username;
	}

	float zoomScale = 1;

	@Override
	public void draw() {
		surface.background(255);

		surface.push();

		// move/zoom camera
		float actualWidth = DRAWING_WIDTH / zoomScale;
		float actualHeight = DRAWING_HEIGHT / zoomScale;
		surface.scale(zoomScale);
		surface.translate((float) (-me.getX() + actualWidth * 0.5), (float) (-me.getY() + actualHeight * 0.5));

		worldGen.draw(me.getX(), me.getY());

		float screenleftX = (float) (me.getX() - actualWidth * 0.5);
		float screenrightX = (float) (me.getX() + actualWidth * 0.5);
		float screentopY = (float) (me.getY() - actualHeight * 0.5);
		float screenbottomY = (float) (me.getY() + actualHeight * 0.5);

		for (Enemy e : enemies) {
			e.draw(surface);
			for (Player p : players) {
				double dist = Math.sqrt(Math.pow(p.getX() - e.getX(), 2) + Math.pow(p.getY() - e.getY(), 2));
				if (e.getclosestEnemy() != null
						&& dist < Math.sqrt(Math.pow(e.getclosestEnemy().getX() - e.getX(), 2) + Math.pow(e.getclosestEnemy().getY() - e.getY(), 2))) {
					e.setclosestEnemy(p);
				} else if (e.getclosestEnemy() == null) {
					e.setclosestEnemy(p);
					// e.setChaseDirection((float)e.getclosestEnemy().getX(),
					// (float)e.getclosestEnemy().getY(), 1f);
				}
			}

			e.chase();

			// e.createProjectiles(surface, (float)e.getclosestEnemy().getX(),
			// (float)e.getclosestEnemy().getY());
			for (Bullet b : e.getProjectiles()) {
				b.draw(surface, true);
			}
		}

		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).getProjectiles().removeIf(bullet -> {
				return (bullet.getHitBox().issTouching(players.get(0).getHitBox()));
			});
		}

		enemies.removeIf(enemy -> {
			return enemy.dead();
		});

		for (int i = 0; i < players.size(); i++) {
			players.get(i).draw(surface);// draw other players

			// draw user-names
			surface.fill(0);
			surface.textSize(baseTextSize / zoomScale);
			surface.textAlign(PApplet.CENTER, PApplet.BOTTOM);
			surface.text(players.get(i).getUsername(), (float) players.get(i).getX(), (float) (players.get(i).getY() - players.get(i).getHeight() * 0.5 - 5));

			surface.push();// draw arrow pointing at player if they are off screen

			if (players.get(i).getX() < screenleftX || players.get(i).getX() > screenrightX || players.get(i).getY() < screentopY
					|| players.get(i).getY() > screenbottomY) {

				float clampedX = (float) Math.max(screenleftX + 50, Math.min(screenrightX - 50, players.get(i).getX()));
				float clampedY = (float) Math.max(screentopY + 50, Math.min(screenbottomY - 50, players.get(i).getY()));

				double angle = Math.atan2(players.get(i).getY() - clampedY, players.get(i).getX() - clampedX) + PApplet.radians(90);

				int dist = (int) (Math.sqrt(Math.pow(players.get(i).getX() - me.getX(), 2) + Math.pow(players.get(i).getY() - me.getX(), 2)) * 0.01);
				surface.translate(clampedX, clampedY);
				surface.rotate((float) angle);
				surface.image(DrawingSurface.playerArrowPointer_img, -50, -50, 100, 100);

				surface.translate(0, 70);
				surface.rotate((float) -angle);
				surface.fill(Color.white.getRGB());
				surface.textSize(40);
				surface.textAlign(PApplet.CENTER, PApplet.CENTER);
				surface.text(dist + " m", 0, 0);// gonna call them meters
			}
			surface.pop();
		}
		me.draw(surface);

		surface.image(DrawingSurface.unchosenClass_img, 10, 10);// point of reference. to see the movement
		// make an enviroment.
		surface.pop();

		if (keysDown.contains(KeyEvent.VK_W))
			me.move(0, -5);
		if (keysDown.contains(KeyEvent.VK_S))
			me.move(0, 5);
		if (keysDown.contains(KeyEvent.VK_A))
			me.move(-5, 0);
		if (keysDown.contains(KeyEvent.VK_D))
			me.move(5, 0);
		if (keysDown.contains(KeyEvent.VK_R))
			me.SwitchClassType();

		if (nm != null && me.isDataChanged()) {

			PlayerData data = me.getDataObject();
			nm.sendMessage(NetworkDataObject.MESSAGE, messageTypePlayerUpdate, data);

		}

		spawnNewEnemy();
		processNetworkMessages();
	}

	@Override
	public void keyPressed() {
		// debugging keys
		if (surface.key == KeyEvent.VK_Z) {// too zoooom
			zoomScale *= 0.5;
			System.out.println("zoomed out " + zoomScale);
		}
		if (surface.key == KeyEvent.VK_X) {// too unzoooom
			zoomScale *= 2;
			System.out.println("zoomed in " + zoomScale);
		}
		if (!keysDown.contains(surface.keyCode))
			keysDown.add(surface.keyCode);
		if (surface.key == PApplet.ESC)
			surface.key = 0;// This prevents a processing program from closing on escape key
	}

	@Override
	public void keyReleased() {
		keysDown.remove(Integer.valueOf(surface.keyCode));
	}

	public boolean isPressed(Integer code) {
		return keysDown.contains(code);
	}

	@Override
	public void connectedToServer(NetworkMessenger nm) {
		this.nm = nm;
//		nm.sendMessage(NetworkDataObject.MESSAGE, messageTypePlayerJoined);
	}

	@Override
	public void networkMessageReceived(NetworkDataObject ndo) {

	}

	public void processNetworkMessages() {

		if (nm == null)
			return;

		Queue<NetworkDataObject> queue = nm.getQueuedMessages();

		while (!queue.isEmpty()) {
			NetworkDataObject ndo = queue.poll();

			String host = ndo.getSourceIP();// the person who originated the data-object

			if (ndo.messageType.equals(NetworkDataObject.MESSAGE)) {
				if (ndo.message[0].equals(messageTypePlayerUpdate)) {

					for (Player p : players) {
						if (p.idMatch(host)) {
							p.syncWithDataObject((PlayerData) ndo.message[1]);
							break;
						}
					}

				} else if (ndo.message[0].equals(messageTypeInit)) {// message from client that tells that

					for (Player p : players) {
						if (p.idMatch(host))// don't run this code on the originator
							return;
					}

					Player p = new Player(host, (PlayerData) ndo.message[1]);
					players.add(p);

				} else if (ndo.message[0].equals(messageTypeSeedSync)) {// message from client that tells that
					System.out.println("got seedSync message: " + (int) ndo.message[1]);
					if (!ndo.dataSource.equals(ndo.serverHost))
						return;

					System.out.println("syncing seed: " + (int) ndo.message[1]);
					randomSeed = (int) ndo.message[1];
					worldGen.setSeed(randomSeed);
					worldGen.reset();

				}
			} else if (ndo.messageType.equals(NetworkDataObject.CLIENT_LIST)) {
				// server tells the clients to all share their info
				nm.sendMessage(NetworkDataObject.MESSAGE, messageTypeSeedSync, randomSeed);
				nm.sendMessage(NetworkDataObject.MESSAGE, messageTypeInit, me.getDataObject());

			} else if (ndo.messageType.equals(NetworkDataObject.DISCONNECT)) {

				if (ndo.dataSource.equals(ndo.serverHost)) {// if it was sent by the server
					players.clear();
				} else {
					for (int i = players.size() - 1; i >= 0; i--)
						if (players.get(i).idMatch(host))
							players.remove(i);
				}

			}
//			else if (ndo.message[0].equals(messageTypeSeedSync)) {
//
//				for (Player p : players) {
//					if (p.idMatch(host))// don't run this code on the originator
//						return;
//				}
//
//				randomSeed = (int) ndo.message[1];
//				System.out.println("my new seed is: " + randomSeed);
//
//			} else if (ndo.message[0].equals(messageTypePlayerJoined)) {
//
//				System.out.print(" got message playerjoined ");
//				
//				for (Player p : players) {
//					if (p.idMatch(host))// found the originator
//						return;
//				}
//				
//
//				for (Player p : players) {
//					if (p.idMatch(ndo.serverHost.toString())) {// is the server host
//						System.out.println("I am host, and someone joined");
//						nm.sendMessage(NetworkDataObject.MESSAGE, messageTypeSeedSync, randomSeed);
//						return;
//					}
//				}
//
//				System.out.println("I am not host, and someone joined");
//
//			}

		}

	}

	public void spawnNewEnemy() {
		if (surface.frameCount - lastCalledFrame >= surface.frameRate) {
			Enemy e = new Enemy(100, "resources\\oryxSanctuaryChars16x16.png", 100, 0, 128, 16);
			enemies.add(e);
			lastCalledFrame = surface.frameCount - 5;
		}
	}
}
