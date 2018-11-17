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
	 * reference. also a colour variable associated with herbivores
	 */
	
	private int moveAttempts;
	private int lastFeed;

	/**
	 * Herbivore constructor
	 * 
	 * @param v vertical position
	 * @param h horizontal position
	 */
	Herbivore(World world, Point position) {
		super(world, position, Colour.YELLOW);
		lastFeed = 0;
	}

	/**
	 * method to check if a step the animal tries to move to is within the
	 * boundaries of the world
	 * 
	 * @param x vertical position
	 * @param y horizontal position
	 * @return boolean result
	 */
	public boolean inBoundsCheck(Point vect) {
		return (vect.x < world.worldBounds.x && vect.y < world.worldBounds.y) && (vect.x >= 0 && vect.y >= 0);
	}

	/**
	 * method to check if a cell the animal tries to move to is null
	 * 
	 * @param x vertical position
	 * @param y horizontal position
	 * @return boolean result
	 */
	public boolean isNullCheck(Point vect) {
		return World.cell[(int)vect.x][(int)vect.y].life == null;
	}

	/**
	 * method to check if the contents of a cell moving into are edible
	 * 
	 * @param x vertical position
	 * @param y horizontal position
	 * @return boolean result
	 */
	public boolean isEdibleCheck(Point vect) {
		return (World.cell[vect.x][vect.y].life instanceof HerbivoreEdible);
	}

	/**
	 * 
	 * @param vect the target vector
	 * @return returns true if it's a viable move position
	 */
	public boolean viablePosition(Point vect) {
		boolean herbivoreEdible = isEdibleCheck(vect);
		boolean isNull = isNullCheck(vect);
		return (herbivoreEdible || isNull);

	}

	/**
	 * 
	 * @param vect the target vector orchestrates a move of an Herbivore to a new
	 *             cell
	 */
	void moveSpace(Point vect) {
		World.cell[vect.x][vect.y].life = World.cell[position.x][position.y].life;
		World.cell[position.x][position.y].life = null;
		position = vect;
		moved = true;
	}

	/**
	 * destroys the animal by making it's own cell null and sets the cell's colour
	 * to null's sienna
	 */
	public void die() {
		World.cell[position.x][position.y].life = null;
	}

	/**
	 * kills animal if lastfeed reaches 5. while moved conditional ensures that the
	 * animal keeps trying to move until it finds a move. selects a random
	 * neighboring cell and attempts to move there after performing required checks
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
			Point newHerbivoreVect = new Point(position.x + temp.x, position.y +temp.y);
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
