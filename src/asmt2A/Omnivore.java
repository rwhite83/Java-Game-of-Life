package asmt2A;

import java.awt.Point;

/** extends lifeForm and implements relevant methods */

public class Omnivore extends LifeForm implements CarnivoreEdible {

	/**
	 * standard constructor for Omnivore
	 * 
	 * @param world is the world referred to
	 * @param point refers to its position on the grid
	 */
	Omnivore(World world, Point position) {
		super(world, position, Colour.BLUE);
		lastFeed = 0;
	}
	public boolean isEdible(Point Point) {
		return (World.cell[Point.x][Point.y].life instanceof OmnivoreEdible);
	}
}

