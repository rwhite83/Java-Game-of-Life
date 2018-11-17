package asmt2A;

import java.awt.Point;

public abstract class LifeForm {

	/**
	 * an array list of vector positions relative any given cell
	 */
	public static Point UpLeft = new Point(-1, 1);
	public static Point Up = new Point(0, 1);
	public static Point UpRight = new Point(1, 1);
	public static Point Left = new Point(-1, 0);
	public static Point Right = new Point(1, 0);
	public static Point DownLeft = new Point(-1, -1);
	public static Point Down = new Point(0, -1);
	public static Point DownRight = new Point(1, -1);

	Point[] moves = new Point[] { UpLeft, Up, UpRight, Left, Right, DownLeft, Down, DownRight };
	
	protected Point position;
	protected Colour colour;
	protected boolean moved = false;
	protected World world;
	
	protected LifeForm(World world, Point position, Colour colour) {
		this.world = world;
		this.position = position;
		this.colour = colour;
	}

	/**
	 * various parent methods defined in children classes
	 */
	public void live() {
	}

	public void move() {
	}

	public void spawn() {
	}

	public void die() {
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	public boolean getMoved() {
		return false;
	}
	
	public Colour getColour() {
		return colour;
	}
}