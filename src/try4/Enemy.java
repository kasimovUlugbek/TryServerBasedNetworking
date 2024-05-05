package try4;

import java.util.concurrent.CopyOnWriteArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import libraryStuff.SpriteExtractor;
import libraryStuff.Rectangle;

public class Enemy {
	
	private PImage heroImg;
	private float x, y, width, height, xSpd, ySpd;
	private int health, maxHealth;
	private boolean dead;
	private Player ClosestEnemy;
	
	private int lastCalledFrame;
	private CopyOnWriteArrayList<Bullet> bullets;
	
	public Enemy(int maxHealth, String imagePath, int newSize, int startX, int startY, int pxls) {
		this.maxHealth = maxHealth;
		this.health = maxHealth;
		this.heroImg = SpriteExtractor.extractSprite(imagePath, startX, startY, pxls, pxls);
		this.heroImg.resize(newSize, newSize);
		this.width = heroImg.width;
		this.height = heroImg.height;
		
		bullets = new CopyOnWriteArrayList<>();
	}
	
	public void chase() {
		this.x += xSpd;
		this.y += ySpd;
	}
	
	public void draw(PApplet surface) {
		surface.fill(0);
		surface.image(heroImg, x, y);
		double onePercent = width/100;
		surface.fill(255, 0, 0);
		surface.rect(x + (float)onePercent*(health), y+height, maxHealth - health, height/8);
		surface.fill(108, 245, 66);
		surface.rect(x, y+height, (float)onePercent*(health), height/8);
		
	}
	
	public float getX() {
		return x+width/2;
	}
	
	public float getY() {
		return y+height/2;
	}
	
	public void subtractHP(int damage) {
		this.health -= damage;
		if (health <= 0) {
			dead = true;
		}
	}
	
	public boolean dead() {
		return dead;
	}
	
	public Rectangle getHitBox() {
		return new Rectangle(x, y, width, height);
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setChaseDirection(float x, float y, float spd) {
		if (getX() < x) {
			xSpd = spd;
		} else if (getX() > x) {
			xSpd = -spd;
		} else {
			xSpd = 0;
		} if (getY() > y) {
			ySpd = -spd;
		} else if (getY() < y) {
			ySpd = spd;
		} else {
			ySpd = 0;
		}
	}
	
	public void createProjectiles(PApplet surface, float x, float y) {
		if (projectileFatigue(surface)) {
			Bullet b = new Bullet(this.getX(), this.getY(), "resources\\1525.png", 32, 16, 152, 8, PApplet.PI/4, surface.millis());
			bullets.add(b);
			b.propel(b.getX(), b.getY(), x, y, 5);
		} 
		for (Bullet b : bullets) {
			b.spin(0.1f*(float)(Math.random()*5));
			b.translate(b.getXSpd(), b.getYSpd());
			//b.translate(b.getXSpd(), b.getYSpd() + PApplet.sin((float)(surface.frameCount - b.getTimeCreated()) / 15)*10);
		}
	}
	
	public CopyOnWriteArrayList<Bullet> getProjectiles() {
		return bullets;
	}
	
	public boolean projectileFatigue(PApplet surface) {
		if (surface.frameCount - lastCalledFrame >= surface.frameRate) {
			lastCalledFrame = surface.frameCount - 5;
			return true;
		} else {
			return false;
		}
	}
	
	public void setProjectilesCount(CopyOnWriteArrayList<Bullet> bulllets) {
		bullets = bulllets;
	}
	
	public Player getclosestEnemy() {
		return ClosestEnemy;
	}
	
	public void setclosestEnemy(Player aa) {
		ClosestEnemy = aa;
	}
	
}
