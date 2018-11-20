package asmt2A;

import java.awt.Point;

/** extends lifeForm and implements relevant methods */

public class Carnivore extends LifeForm implements OmnivoreEdible {

	/**
	 * standard constructor for Carnivore
	 * 
	 * @param world is the world referred to
	 * @param point refers to its position on the grid
	 */
	Carnivore(World world, Point position) {
		super(world, position, Colour.RED);
		lastFeed = 0;
	}
	
	public boolean isEdible(Point Point) {
		return (World.cell[Point.x][Point.y].life instanceof CarnivoreEdible);
	}

}
