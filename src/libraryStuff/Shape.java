package libraryStuff;
import java.awt.Color;

import processing.core.PApplet;

public abstract class Shape {
	// FIELDS
	
	protected double x, y;
	
	// Processing graphics properties:
	
	private Color fillColor, strokeColor; // Datatype is up to you
	private int strokeWidth = 1;
	
	// CONSTRUCTORS
	/**
	 * Constructs a shape and sets its x and y values.
	 * @param x The x-value of the shape.
	 * @param y The y-value of the shape.
	 */
	public Shape(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public Shape (double x, double y, Color fillColor, Color strokeColor, int strokeWidth) {
		this.x = x;
		this.y = y;
		this.fillColor = fillColor;
		this.strokeColor = strokeColor;
		this.strokeWidth = strokeWidth;
	}
	// Others definitely possible.
	// METHODS
	// MUST-HAVES
	/**
	 * Sets the x value of the shape.
	 * @param x The wanted x-value of the shape.
	 */
	public void setX(double x) {
		this.x = x;
		}
	/**
	 * Sets the y value of the shape.
	 * @param y The wanted y-value of the shape.
	 */
	public void setY(double y) {
		this.y = y;
		}
	/**
	 * 
	 * @return Returns the x-value of the shape.
	 */
	public double getX() {
		return x;
		}
	/**
	 * 
	 * @return Returns the y-value of the shape.
	 */
	public double getY() {
		return y;
		}
	/**
	 * Sets the inside color of the shape.
	 * @param c The wanted color of the shape.
	 */
	public void setFillColor(Color c) {
		fillColor = c;
		}
	/**
	 * Sets the border color of the shape.
	 * @param c The wanted color of the Shape.
	 */
	public void setStrokeColor(Color c) {
		strokeColor = c;
		}
	/**
	 * Sets the border thickness of the shape.
	 * @param x The wanted border thickness.
	 */
	public void setStrokeWidth(int x) {
		strokeWidth = x;
	} 
	//public void move(double x, double y) {}
	/**
	 * Translates the shape along the cardinal directions.
	 * @param deltaX The change in the x-coordinate of the shape.
	 * @param deltaY The change in the y-coordinate of the shape.
	 */
	public void translate(double deltaX, double deltaY) {
		this.x += deltaX;
		this.y += deltaY;
	}
	// public abstract double getWidth();
	// public abstract double getHeight();
	// This is a nice way to combine getWidth() and getHeight() into 1 method:
	//public abstract Rectangle getBoundingRectangle();
	/**
	 * Uses calculations to determine the perimeter of the given shape.
	 * @return The perimeter of the shape.
	 */
	public abstract double getPerimeter();
	/**
	 * Uses calculations to determine the area of the given shape.
	 * @return The area of the shape.
	 */
	public abstract double getArea();
	/**
	 * Uses calculations to determine whether the two shapes are touching.
	 * @param other The other shape that is involved in the interaction.
	 * @return Whether the two shapes are touching.
	 */
	public abstract boolean isTouching(Shape other);
	/**
	 * Uses comparisons to determine whether a point is inside the given shape.
	 * @param x The x-coordinate of the point.
	 * @param y The y-coordinate of the point.
	 * @return Whether the point is or is not inside the shape.
	 */
	public abstract boolean isPointInside(double x, double y);
	/**
	 * Returns text providing you with the information based on the shape's properties.
	 */
	public abstract String toString();
	/**
	 * Scales the shapes' dimensions up or down; making them larger or smaller.
	 * @param factor The amount in which the shapes' dimensions are enlarged or shrunk.
	 */
	public String getStrokeWidth() {
		return "StrokeWidth: " + strokeWidth;
	}
	public String getStrokeColor() {
		return "StrokeColor: " + strokeColor;
	}
	public abstract void scale(double factor);
	/**
	 * Sets the shape's properties.
	 * @param surface The PApplet that is making these changes.
	 */
	public void draw(PApplet surface) {
		if (fillColor != null) {
			surface.fill(fillColor.getRGB());
		} if (strokeColor != null) {
			surface.stroke(strokeColor.getRGB());
		} 
		surface.strokeWeight(strokeWidth);
	}
	public abstract double getWidth();
	public abstract double getheight();
}