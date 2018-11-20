package asmt2A;

import java.awt.Point;

/** extends lifeForm and implements relevant methods */

public class Herbivore extends LifeForm implements CarnivoreEdible, OmnivoreEdible {

	/**
	 * standard constructor for Herbivore
	 * 
	 * @param world is the world referred to
	 * @param point refers to its position on the grid
	 */
	Herbivore(World world, Point position) {
		super(world, position, Colour.YELLOW);
		lastFeed = 0;
	}

	public boolean isEdible(Point Point) {
		return (World.cell[Point.x][Point.y].life instanceof HerbivoreEdible);
	}
}
