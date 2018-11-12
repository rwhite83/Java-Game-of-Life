package asmt2A;

import java.awt.Point;

/** extends lifeForm and implements relevant methods */

public class Herbivore extends LifeForm {

	public static final int MAX_UNFED = 5;
	public static final int MAX_MOVE_ATTEMPTS = 8;

	/**
	 * this particular herbivore's vertical and horizontal, as well as a counter for
	 * the last time it's fed so it can die after enough turns without feeding, and
	 * a boolean which is turned true if the animal moves, so it doesn't move more
	 * than once in a single turn. also a constant for max unfed number to
	 * reference. also a colour variable asociated with herbivores
	 */
	private int moveAttempts;
	private int lastFeed;

	/**
	 * Herbivore constructor
	 * 
	 * @param world The world the herbivore belongs to
	 * @param position grid position of herbivore
	 */
	Herbivore(World world, Point position)	{
		super(world, position, Colour.YELLOW);
		lastFeed = 0;
	}

	/**
	 * method to check if a step the animal tries to move to is within the
	 * boundaries of the world
	 * 
	 * @param p position to check in bounds of the world
	 * @return boolean result
	 */
	public boolean inBoundsCheck(Point p) {
		return (p.x < world.worldBounds.x && p.y < world.worldBounds.y) && (p.x >= 0 && p.y >= 0);
	}

	/**
	 * method to check if a cell the animal tries to move to is null
	 * 
	 * @param p position to check if lifeform is null
	 * @return boolean result
	 */
	public boolean isNullCheck(Point p) {
		return world.cell[p.x][p.y].life == null;
	}

	/**
	 * method to check if the contents of a cell moving into are edible
	 * 
	 * @param p position to check if is edible
	 * @return boolean result
	 */
	public boolean isEdibleCheck(Point p) {
		return (world.cell[p.x][p.y].life instanceof HerbivoreEdible);
	}

	/**
	 * 
	 * @param p the position to check if a herbivore can move to.
	 * @return returns true if it's a viable move position
	 */
	public boolean viablePosition(Point p) {
		boolean herbivoreEdible = isEdibleCheck(p);
		boolean isNull = isNullCheck(p);
		return (herbivoreEdible || isNull);
	}

	/**
	 * 
	 * @param p the target point orchestrates a move of an Herbivore to a new
	 *             cell
	 */
	void moveSpace(Point p) {
		world.cell[p.x][p.y].life = world.cell[position.x][position.y].life;
		world.cell[position.x][position.y].life = null;
		position = p;
		moved = true;
	}

	/**
	 * destroys the animal by making it's own cell null and sets the cell's colour
	 * to null's sienna
	 */
	public void die() {
		world.cell[position.x][position.y].life = null;
	}

	/**
	 * kills animal if lastfeed reaches 5. while moved conditional ensures that the
	 * animal keeps trying to move until it finds a move. selects a random
	 * neighbouring cell and attempts to move there after performing required checks
	 */

	public void move() {
		if (lastFeed == MAX_UNFED) {
			die();
			return;
		}
		moveAttempts = 0;
		while (moved == false && moveAttempts < MAX_MOVE_ATTEMPTS) {
			moveAttempts++;
			int randomPositionInt = RandomGenerator.nextNumber(moves.length);
			Point temp = moves[randomPositionInt];
			Point newHerbivoreVect = new Point(position.x + temp.x, position.y + temp.y);
			if (inBoundsCheck(newHerbivoreVect)) {
				if (isNullCheck(newHerbivoreVect)) {
					moveSpace(newHerbivoreVect);
					lastFeed++;
				}
				if (isEdibleCheck(newHerbivoreVect)) {
					moveSpace(newHerbivoreVect);
					lastFeed = 0;
				}
			}
		}
	}

	/**
	 * primary live method responding to 'turn' printout to console is only
	 * temporary for testing calls Herbivore specific move method
	 */
	public void live() {
		move();
	}
}
