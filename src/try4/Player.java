package try4;
import java.awt.Color;


import processing.core.PApplet;
import processing.core.PShape;

public class Player {

	private String uniqueID;
	
	private String username;
	private double x, y;
	private boolean rolling;
	
	private int classType;
	
	private boolean dataUpdated;  // Allows us to limit database writes by only sending data when something has actually been modified
	private PlayerData data;
	
	public Player(String uniqueID, String username, double x, double y) {
		this.uniqueID = uniqueID;
		this.username = username;
		this.x = x;
		this.y = y;
		
		data = new PlayerData();
		data.username = username;

		dataUpdated = false;
	}
	
	public Player(String uniqueID, PlayerData data) {
		this.uniqueID = uniqueID;
		this.data = data;
		this.username = data.username;
		this.syncWithDataObject(data);
		
		dataUpdated = false;
	}
	
	public boolean idMatch(String uid) {
		return this.uniqueID.equals(uid);
	}
	
	public boolean isDataChanged() {
		return dataUpdated;
	}
	
	public String getUsername() {
		return username;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public void move(double x, double y) {
		this.x += x;
		
		this.y += y;
		
		dataUpdated = true;
	}
	
	
	public void draw(PApplet surface) {
		if(classType == 0)
			surface.image(null, (float)x, (float)y);
		surface.fill(0);
		surface.textSize(14);
		surface.textAlign(PApplet.CENTER, PApplet.BOTTOM);
		surface.text(username, (float)x, (float)y-30);
	}
	
	
	public PlayerData getDataObject() {
		dataUpdated = false;
		
		data.x = x;
		data.y = y;
		return data;
	}
	
	public void syncWithDataObject(PlayerData data) {
		dataUpdated = false;
		
		this.x = data.x;
		this.y = data.y;
	}
	
	
	
}
