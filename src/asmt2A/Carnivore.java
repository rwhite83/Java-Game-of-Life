package asmt2A;

import java.awt.Point;

/** 
 * extends lifeForm and implements relevant methods 
 * */
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
		minNullNeighbours = 10;
		minFoodNeighbours = 1;
		maxUnfed = 5;
	}
	
	/**
	 * locally defined version of parent class abstract method
	 */
	public boolean isEdible(Point point) {
		return (World.cell[point.x][point.y].life instanceof CarnivoreEdible);
	}

	/**
	 * locally defined version of parent class abstract method
	 */
	protected boolean isMyType(Point point) {
		return (World.cell[point.x][point.y].life instanceof Carnivore);
	}

	/**
	 * locally defined version of parent class abstract method
	 */
	protected void giveBirth(Point newSpawnPoint) {
		World.cell[newSpawnPoint.x][newSpawnPoint.y].life = new Carnivore(world, newSpawnPoint);
		
	}

}
