package try4.screens;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Queue;

import try4.DrawingSurface;
import networking.frontend.NetworkDataObject;
import networking.frontend.NetworkListener;
import networking.frontend.NetworkMessenger;
import processing.core.PApplet;
import try4.Player;
import try4.PlayerData;

public class GameScreen extends Screen implements NetworkListener {

	// Drawing stuff
	private Player me;
	private ArrayList<Player> players;
	private ArrayList<Integer> keysDown;
	private DrawingSurface surface;

	private String username;

	private NetworkMessenger nm;

	private static final String messageTypeInit = "CREATE_PLAYER";
	private static final String messageTypePlayerUpdate = "PLAYER_UPDATE";

	public GameScreen(DrawingSurface surface) {
		super(800, 600);
		this.surface = surface;
		keysDown = new ArrayList<Integer>();
		players = new ArrayList<Player>();
	}

	@Override
	public void setup() {
//		me = new Player("me!", username, surface.selectedClass, DRAWING_WIDTH / 2, DRAWING_HEIGHT / 2);
	}
	
	@Override
	public void onSwitchedTo() {
		me = new Player("me!", username, surface.selectedClass, DRAWING_WIDTH / 2, DRAWING_HEIGHT / 2);
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public void draw() {
		surface.background(255);

		surface.push();

		for (int i = 0; i < players.size(); i++) {
			players.get(i).draw(surface);
		}
		me.draw(surface);

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

		processNetworkMessages();
	}

	@Override
	public void keyPressed() {
		if (!keysDown.contains(surface.keyCode))
			keysDown.add(surface.keyCode);
		if(surface.key == PApplet.ESC)
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

					Player p = new Player(host, (PlayerData) ndo.message[1]);
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
