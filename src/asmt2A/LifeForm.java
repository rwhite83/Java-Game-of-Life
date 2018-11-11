package asmt2A;

import com.sun.javafx.geom.Vec2d;

public abstract class LifeForm {

	/**
	 * an array list of vector positions relative any given cell
	 */
	Vec2d UpLeft = new Vec2d(-1, 1);
	Vec2d Up = new Vec2d(0, 1);
	Vec2d UpRight = new Vec2d(1, 1);
	Vec2d Left = new Vec2d(-1, 0);
	Vec2d Right = new Vec2d(1, 0);
	Vec2d DownLeft = new Vec2d(-1, -1);
	Vec2d Down = new Vec2d(0, -1);
	Vec2d DownRight = new Vec2d(1, -1);

	Vec2d[] moves = new Vec2d[] { UpLeft, Up, UpRight, Left, Right, DownLeft, Down, DownRight };

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

	public void setMoved(boolean x) {
	}

	public boolean getMoved() {
		return false;
	}
	
	public Colour getColour() {
		return null;
	}
}
