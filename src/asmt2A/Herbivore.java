package asmt2A;

import java.awt.Point;

/** extends lifeForm and implements relevant methods */

public class Herbivore extends LifeForm implements CarnivoreEdible, OmnivoreEdible {

	public static final int MAX_UNFED = 5;
	public static final int MAX_MOVE_ATTEMPTS = 8;

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

	/**
	 * kills animal if lastfeed reaches 5. while moved conditional ensures that the
	 * animal keeps trying to move until it finds a move. selects a random
	 * neighboring cell and attempts to move there after performing required checks
	 */
	public void live() {
		if (lastFeed == MAX_UNFED) {
			die();
			return;
		}
		moveAttempts = 0;
		neighborCheck(position);
		if (viableMoves.size() > 0) {
			int randomPositionInt = RandomGenerator.nextNumber(viableMoves.size());
			Point seedPoint = new Point(viableMoves.get(randomPositionInt));
			World.cell[seedPoint.x][seedPoint.y].life = new Herbivore(world, seedPoint);
			World.cell[seedPoint.x][seedPoint.y].life.setMoved(true);
		}
	}
}
