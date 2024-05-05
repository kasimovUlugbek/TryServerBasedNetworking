package try4;

import processing.core.*;
import libraryStuff.SpriteExtractor;
import libraryStuff.Rectangle;

public class Bullet{

	private PImage bulletImg;
	private float xPos, yPos, width, height, angle;
	private double xSpd, ySpd, timeCreated;
	
	public Bullet(float x, float y, String imagePath, int newSize, int startExtractionX, int startExtractionY, int imgSize, float angle, double timeCreated) {
		xPos = x;
		yPos = y;
		bulletImg = SpriteExtractor.extractSprite(imagePath, startExtractionX, startExtractionY, imgSize, imgSize);
		bulletImg.resize(newSize, newSize);
		width = bulletImg.width;
		height = bulletImg.height;
		this.angle = angle;
		this.timeCreated = timeCreated;
	}
	
	public Rectangle getHitBox() {
		return new Rectangle(xPos, yPos, width, height);
	}
	
	public void draw(PApplet surface, boolean rotate) {
		if (rotate) {
		    surface.pushMatrix(); 
		    surface.translate(xPos + width/2, yPos + height/2); 
		    surface.rotate(angle); 
			surface.image(bulletImg, -width/2, -height/2, width, height); 
		    surface.popMatrix(); 
		} else {
			surface.image(bulletImg, xPos, yPos, width, height); 
		}
	}
	
	public void translate(double deltaX, double deltaY) {
		this.xPos += deltaX;
		this.yPos += deltaY;
	}
	
	public void propel(double startX, double startY, double endX, double endY, double speed) {
		
		double dist = Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
	
		xSpd = ((endX - startX) / dist) * speed;
		ySpd = ((endY - startY) / dist) * speed;
		
	}
	
	public double getXSpd() {
		return xSpd;
	}
	public double getYSpd() {
		return ySpd;
	}
	
	public double getX() {
		return getHitBox().getX()+width/2;
	}
	
	public double getY() {
		return getHitBox().getY()-height/2;
	}
	
	public void spin(float angleChange) {
		angle += angleChange;
	}
	
	public void damage(Enemy hero, int damage) {
		hero.subtractHP(damage);
	}
	
	public double getTimeCreated() {
		return timeCreated;
	}
	
}
