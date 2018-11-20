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
		minMateNeighbours = 1;
		minNullNeighbours = 3;
		minFoodNeighbours = 1;
	}
	
	public boolean isEdible(Point point) {
		return (World.cell[point.x][point.y].life instanceof CarnivoreEdible);
	}

	@Override
	protected boolean isMyType(Point point) {
		return (World.cell[point.x][point.y].life instanceof Carnivore);
	}

	@Override
	protected void giveBirth(Point newSpawnPoint) {
		World.cell[newSpawnPoint.x][newSpawnPoint.y].life = new Carnivore(world, newSpawnPoint);
		
	}

}
