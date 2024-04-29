package demo2;

import java.awt.Color;
import java.io.Serializable;


public class Cursor implements Serializable {
	private static final long serialVersionUID = 1651417152103363037L;

	public static final int TIMEOUT_MAX = 250;

	public int x, y;
	public String host;
	public Color color;
	public int timeOut;

	public Cursor makeTrailCopy() {
		Cursor c = new Cursor();
		c.x = x;
		c.y = y;
		c.host = host;
		c.color = color;
		c.timeOut = TIMEOUT_MAX;
		return c;
	}
}