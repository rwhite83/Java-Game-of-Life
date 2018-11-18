package asmt2A;

import java.awt.Point;

/** extends lifeForm and implements relevant methods */

public class Carnivore extends LifeForm implements OmnivoreEdible {

	public static final int MAX_UNFED = 5;
	public static final int MAX_MOVE_ATTEMPTS = 8;
	public static final int MINIMUM_MATE_NEIGHBOURS = 1;
	public static final int MINIMUM_NULL_NEIGHBOURS = 2;
	public static final int MINIMUM_FOOD_NEIGHBOURS = 2;

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

	public boolean isEdibleCheck(Point Point) {
		return (World.cell[Point.x][Point.y].life instanceof CarnivoreEdible);
	}
	
	public boolean giveBirthCheck() {
		return (carnivoreNeighbours >= MINIMUM_MATE_NEIGHBOURS && nullNeighbours >= MINIMUM_NULL_NEIGHBOURS && carnivoreEdibleCount >= MINIMUM_FOOD_NEIGHBOURS);
	}

	public void live() {
		if (lastFeed >= MAX_UNFED) {
			die();
			return;
		}
		moveAttempts = 0;
		while (moved == false && moveAttempts < MAX_MOVE_ATTEMPTS) {
			moveAttempts++;
			int randomPositionInt = RandomGenerator.nextNumber(moves.length);
			Point temp = moves[randomPositionInt];
			Point newCarnivorePoint = new Point(position.x + temp.x, position.y + temp.y);
			Point oldCarnivorePoint = new Point(position.x, position.y);
			if (inBoundsCheck(newCarnivorePoint)) {
				if (isNullCheck(newCarnivorePoint)) {
					moveSpace(newCarnivorePoint);
					lastFeed++;
				}
				if (isEdibleCheck(newCarnivorePoint)) {
					eat(position, newCarnivorePoint);
					lastFeed = 0;
					moved = true;
				}
				if (giveBirthCheck()) {
					World.cell[oldCarnivorePoint.x][oldCarnivorePoint.y].life = new Carnivore(world, oldCarnivorePoint);
					World.cell[oldCarnivorePoint.x][oldCarnivorePoint.y].life.setMoved(true);
				}
			}
		}
	}
}
