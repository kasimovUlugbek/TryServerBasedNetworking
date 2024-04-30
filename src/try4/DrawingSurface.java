package try4;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Queue;

import javax.swing.JOptionPane;

import networking.frontend.NetworkDataObject;
import networking.frontend.NetworkListener;
import networking.frontend.NetworkMessenger;
import processing.core.PApplet;

public class DrawingSurface extends PApplet implements NetworkListener {

	// Window stuff
	private static final int DRAWING_WIDTH = 800, DRAWING_HEIGHT = 600;
	private ArrayList<Integer> keysDown;

	// Drawing stuff
	private Player me;
	private ArrayList<Player> players;

	private NetworkMessenger nm;

	private static final String messageTypeInit = "CREATE_PLAYER";
	private static final String messageTypePlayerUpdate = "PLAYER_UPDATE";

	public DrawingSurface() {

		keysDown = new ArrayList<Integer>();
		players = new ArrayList<Player>();

	}

	public void settings() {
		setSize(DRAWING_WIDTH, DRAWING_HEIGHT);
	}

	public void setup() {
		// The Player uses the PApplet in its constructor, so we're initializing a bunch
		// of stuff in setup() instead of the constructor
		// so the PApplet is definitely ready to go.

		String username = JOptionPane.showInputDialog("Give yourself a username:");

		me = new Player("me!", username, DRAWING_WIDTH / 2, DRAWING_HEIGHT / 2, this);

	}

	public void draw() {
		background(255);

		push();
		scale((float) width / DRAWING_WIDTH, (float) height / DRAWING_HEIGHT);

		for (int i = 0; i < players.size(); i++) {
			players.get(i).draw(this);
		}
		me.draw(this);

		pop();

		if (keysDown.contains(KeyEvent.VK_UP))
			me.move(0, -5);
		if (keysDown.contains(KeyEvent.VK_DOWN))
			me.move(0, 5);
		if (keysDown.contains(KeyEvent.VK_LEFT))
			me.move(-5, 0);
		if (keysDown.contains(KeyEvent.VK_RIGHT))
			me.move(5, 0);

		if (nm != null && me.isDataChanged()) {

			PlayerData data = me.getDataObject();
			nm.sendMessage(NetworkDataObject.MESSAGE, messageTypePlayerUpdate, data);

		}

		processNetworkMessages();

	}

	public void keyPressed() {
		if (!keysDown.contains(super.keyCode))
			keysDown.add(super.keyCode);

	}

	public void keyReleased() {
		keysDown.remove(Integer.valueOf(super.keyCode));
	}

	@Override
	public void connectedToServer(NetworkMessenger nm) {
		this.nm = nm;
	}

	@Override
	public void networkMessageReceived(NetworkDataObject ndo) {
		// TODO Auto-generated method stub

	}

	public void processNetworkMessages() {

		if (nm == null)
			return;

		Queue<NetworkDataObject> queue = nm.getQueuedMessages();

		while (!queue.isEmpty()) {
			NetworkDataObject ndo = queue.poll();

			String host = ndo.getSourceIP();

			if (ndo.messageType.equals(NetworkDataObject.MESSAGE)) {
				if (ndo.message[0].equals(messageTypePlayerUpdate)) {

					for (Player p : players) {
						if (p.idMatch(host)) {
							p.syncWithDataObject((PlayerData) ndo.message[1]);
							break;
						}
					}

				} else if (ndo.message[0].equals(messageTypeInit)) {

					for (Player p : players) {
						if (p.idMatch(host))
							return;
					}

					Player p = new Player(host, (PlayerData) ndo.message[1], this);
					players.add(p);

				}
			} else if (ndo.messageType.equals(NetworkDataObject.CLIENT_LIST)) {
				nm.sendMessage(NetworkDataObject.MESSAGE, messageTypeInit, me.getDataObject());

			} else if (ndo.messageType.equals(NetworkDataObject.DISCONNECT)) {

				if (ndo.dataSource.equals(ndo.serverHost)) {
					players.clear();
				} else {
					for (int i = players.size() - 1; i >= 0; i--)
						if (players.get(i).idMatch(host))
							players.remove(i);
				}

			}

		}

	}

}
