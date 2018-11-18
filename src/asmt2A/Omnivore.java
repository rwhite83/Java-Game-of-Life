package asmt2A;

import java.awt.Point;

/** extends lifeForm and implements relevant methods */

public class Omnivore extends LifeForm implements CarnivoreEdible {

	public static final int MAX_UNFED = 5;
	public static final int MAX_MOVE_ATTEMPTS = 8;
	public static final int MINIMUM_MATE_NEIGHBOURS = 1;
	public static final int MINIMUM_NULL_NEIGHBOURS = 2;
	public static final int MINIMUM_FOOD_NEIGHBOURS = 2;

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

	public boolean isEdibleCheck(Point Point) {
		return (World.cell[Point.x][Point.y].life instanceof OmnivoreEdible);
	}
	
	public boolean giveBirthCheck() {
		return (omnivoreNeighbours >= MINIMUM_MATE_NEIGHBOURS && nullNeighbours >= MINIMUM_NULL_NEIGHBOURS && omnivoreEdibleCount >= MINIMUM_FOOD_NEIGHBOURS);
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
			Point newOmnivorePoint = new Point(position.x + temp.x, position.y + temp.y);
			Point oldOmnivorePoint = new Point(position.x, position.y);
			if (inBoundsCheck(newOmnivorePoint)) {
				if (isNullCheck(newOmnivorePoint)) {
					moveSpace(newOmnivorePoint);
					lastFeed++;
				}
				if (isEdibleCheck(newOmnivorePoint)) {
					eat(position, newOmnivorePoint);
					lastFeed = 0;
					moved = true;
				}
				if (giveBirthCheck()) {
					World.cell[oldOmnivorePoint.x][oldOmnivorePoint.y].life = new Omnivore(world, oldOmnivorePoint);
					World.cell[oldOmnivorePoint.x][oldOmnivorePoint.y].life.setMoved(true);
				}
			}
		}
	}
}
