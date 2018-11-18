package asmt2A;

import java.awt.Point;

/** extends lifeForm and implements relevant methods */

public class Omnivore extends LifeForm implements CarnivoreEdible {

	public static final int MAX_UNFED = 5;
	public static final int MAX_MOVE_ATTEMPTS = 8;

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

	public void neighborCheck(Point tempPoint) {
		omnivoreNeighbours = 0;
		omnivoreEdibleCount = 0;
		nullNeighbours = 0;
		viableMoves.clear();
		for (int i = 0; i < moves.length; i++) {
			Point currentPoint = new Point(position.x + moves[i].x, position.y + moves[i].y);
			if (currentPoint.x < world.worldBounds.x && currentPoint.y < world.worldBounds.y && currentPoint.x >= 0
					&& currentPoint.y >= 0) {
				if (World.cell[currentPoint.x][currentPoint.y].life instanceof Omnivore) {
					omnivoreNeighbours++;
				} else if (World.cell[currentPoint.x][currentPoint.y].life instanceof OmnivoreEdible) {
					omnivoreEdibleCount++;
					viableMoves.add(currentPoint);
				} else if (World.cell[currentPoint.x][currentPoint.y].life == null) {
					nullNeighbours++;
					viableMoves.add(currentPoint);
				}
			}
		}
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
			}
		}
	}
}
