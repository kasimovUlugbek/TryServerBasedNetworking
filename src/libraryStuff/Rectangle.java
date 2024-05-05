package libraryStuff;
import java.awt.Color;

import processing.core.PApplet;

/**
 * @version 9/29/2023
 * @author Nebul
 *
 */
public class Rectangle extends Shape{
	//fields
	private double width, height;
	public double timeCreated;
	//constructors
	/**
	 * Constructs a new rectangle object with all dimensions set to 0.
	 */
	public Rectangle() {
		super(0, 0);
	}
	/**
	 * Creates a new rectangle object with dimensions set to that of the parameters.
	 * @param x X-value of the left side of the rectangle.
	 * @param y Y-value of the top side of the rectangle.
	 * @param width The width of the rectangle.
	 * @param height The height of the rectangle.
	 */
	public Rectangle(double x, double y, double width, double height) {
		super(x, y);
		this.width = width;
		this.height = height;
	}
	
	public Rectangle(double x, double y, double width, double height, double time) {
		super(x, y);
		this.width = width;
		this.height = height;
		timeCreated = time;
	}
	
	
	
	public Rectangle(double x, double y, double width, double height, Color fillColor, Color strokeColor, int strokeWidth) {
		super(x, y, fillColor, strokeColor, strokeWidth);
		this.width = width;
		this.height = height;
	}
	/**
	 * 
	 * @return Returns the perimeter of the rectangle.
	 */
	public double getPerimeter() {
		return (width + height)*2;
	}
	/**
	 * 
	 * @return Returns the area of the rectangle.
	 */
	public double getArea() {
		return width*height;
	}
	/**
	 * 
	 * @param x The tested x-value.
	 * @param y The tested y-value.
	 * @return Returns a boolean based on whether the tested x and y values are inside the rectangle.
	 */
	public boolean isPointInside(double x, double y) {
		if (x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height) {
			return true;
		} else {	
			return false;
		}
	}
	@Override
	/**
	 * Draws the rectangle object onto the screen.
	 * @param surface A PApplet that draws objects onto a screen.
	 */
	public void draw(PApplet surface) {
		super.draw(surface);
		surface.rect((float)x, (float)y, (float)width, (float)height);
	}
	/**
	 * @param other The second rectangle that is part of the collision.
	 * @return Returns a boolean based on whether two Rectangles are touching.
	 */
	public boolean issTouching(Rectangle other) {
	    Rectangle r = (Rectangle) other;
	    boolean xOverlap = this.x < r.x + r.width && this.x + this.width > r.x;
	    boolean yOverlap = this.y < r.y + r.height && this.y + this.height > r.y;
	    return xOverlap && yOverlap;
	}
	@Override
	/**
	 * Translates the rectangle along the cardinal directions.
	 * @param x Changes the x-value of the rectangle
	 * @param y Changes the y-value of the rectangle.
	 */
	public void translate(double deltaX, double deltaY) {
		this.x += deltaX;
		this.y += deltaY;
	}
	@Override
	/**
	 * Returns text based on the properties of the shape.
	 */
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	@Override
	/**
	 * Scales the dimensions of the rectangle up or down.
	 */
	public void scale(double factor) {
		x *= factor;
		y *= factor;
		width *= factor;
		height *= factor;
	}
	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return width;
	}
	@Override
	public double getheight() {
		// TODO Auto-generated method stub
		return height;
	}
	public double getX2() {
		return x + width;
	}
	public double getY2() {
		return y+height;
	}
	@Override
	public boolean isTouching(Shape other) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public double timeCreated() {
		return timeCreated;
	}
}