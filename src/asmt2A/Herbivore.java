package asmt2A;

import java.awt.Point;

/** extends lifeForm and implements relevant methods */

public class Herbivore extends LifeForm implements CarnivoreEdible, OmnivoreEdible {

	public static final int MAX_UNFED = 5;
	public static final int MAX_MOVE_ATTEMPTS = 8;
	public static final int MINIMUM_MATE_NEIGHBOURS = 1;
	public static final int MINIMUM_NULL_NEIGHBOURS = 2;
	public static final int MINIMUM_FOOD_NEIGHBOURS = 2;

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

	public boolean isEdibleCheck(Point Point) {
		return (World.cell[Point.x][Point.y].life instanceof HerbivoreEdible);
	}
	
	public boolean giveBirthCheck() {
		return (myNeighbours >= MINIMUM_MATE_NEIGHBOURS && nullNeighbours >= MINIMUM_NULL_NEIGHBOURS && myEdibleCount >= MINIMUM_FOOD_NEIGHBOURS);
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
			Point newHerbivorePoint = new Point(position.x + temp.x, position.y + temp.y);
			Point oldHerbivorePoint = new Point(position.x, position.y);
			if (inBoundsCheck(newHerbivorePoint)) {
				if (isNullCheck(newHerbivorePoint)) {
					moveSpace(newHerbivorePoint);
					lastFeed++;
				}
				if (isEdibleCheck(newHerbivorePoint)) {
					eat(position, newHerbivorePoint);
					lastFeed = 0;
					moved = true;
				}
				if (giveBirthCheck()) {
					World.cell[oldHerbivorePoint.x][oldHerbivorePoint.y].life = new Herbivore(world, oldHerbivorePoint);
					World.cell[oldHerbivorePoint.x][oldHerbivorePoint.y].life.setMoved(true);
				}
			}
		}
	}
}
