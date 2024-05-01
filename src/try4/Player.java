package try4;


import processing.core.PApplet;
import processing.core.PImage;

public class Player {

	private String uniqueID;

	private String username;
	private double x, y;
	private double width = 100, height = 100;
//	private boolean rolling;

	private int classType = 0;

	private boolean dataUpdated; // Allows us to limit database writes by only sending data when something has
									// actually been modified
	private PlayerData data;

	public Player(String uniqueID, String username, int classType, double x, double y) {
		this.uniqueID = uniqueID;
		this.username = username;
		this.classType = classType;
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
		this.classType = data.classType;
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
		PImage tempImgChosen;
		if (classType <= 0)
			tempImgChosen = DrawingSurface.healerClass_img;
		else if (classType == 1)
			tempImgChosen = DrawingSurface.rangedClass_img;
		else
			tempImgChosen = DrawingSurface.knightClass_img;

		surface.image(tempImgChosen, (float) (x - width * 0.5), (float) (y - height * 0.5), (float) width,
				(float) height);

//		surface.noFill();
//		surface.rect((float) x, (float) y, (float) width, (float) height);
//		surface.fill(Color.red.getRGB());// origin
//		surface.circle((float) x, (float) y, 5f);

		surface.fill(0);
		surface.textSize(14);
		surface.textAlign(PApplet.CENTER, PApplet.BOTTOM);
		surface.text(username, (float) x, (float) (y - height * 0.5 - 5));
	}

	public void SwitchClassType() {
		classType += 1;
		if (classType > 2)
			classType = 0;
		dataUpdated = true;
	}

	public PlayerData getDataObject() {
		dataUpdated = false;

		data.x = x;
		data.y = y;
		data.classType = classType;
		return data;
	}

	public void syncWithDataObject(PlayerData data) {
		dataUpdated = false;

		this.x = data.x;
		this.y = data.y;
		this.classType = data.classType;
	}

}
