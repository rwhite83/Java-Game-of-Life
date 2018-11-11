package asmt2A;

import com.sun.javafx.geom.Vec2d;

/** extends lifeForm and implements relevant methods */

public class Herbivore extends LifeForm {

	/**
	 * this particular herbivore's vertical and horizontal, as well as a counter for
	 * the last time it's fed so it can die after enough turns without feeding, and
	 * a boolean which is turned true if the animal moves, so it doesn't move more
	 * than once in a single turn. also a constant for max unfed number to
	 * reference. also a colour variable asociated with herbivores
	 */
	private int herbivoreVert;
	private int herbivoreHori;
	private int lastFeed;
	public boolean moved;
	public static final int MAX_UNFED = 5;
	public Colour herbivoreColour = Colour.YELLOW;

	/**
	 * Overloaded Herbivore constructor type 1
	 * 
	 * @param v vertical position
	 * @param h horizontal position
	 * @param f specified last feed
	 */
	Herbivore(int x, int y, int f) {
		this.herbivoreHori = x;
		this.herbivoreVert = y;
		lastFeed = f;
	}

	/**
	 * Overloaded Herbivore constructor type 2
	 * 
	 * @param v vertical position
	 * @param h horizontal position
	 */
	Herbivore(int x, int y) {
		this.herbivoreHori = x;
		this.herbivoreVert = y;
		lastFeed = 0;
	}

	/**
	 * getters and setters for the moved boolean
	 */
	public boolean getMoved() {
		return moved;
	}

	public void setMoved(boolean m) {
		moved = m;
	}

	/**
	 * getter for the colour variable
	 */
	public Colour getColour() {
		return herbivoreColour;
	}

	/**
	 * method to check if a step the animal tries to move to is within the
	 * boundaries of the world
	 * 
	 * @param x vertical position
	 * @param y horizontal position
	 * @return boolean result
	 */
	public boolean inBoundsCheck(Vec2d vect) {
		boolean inBounds = false;
		if ((vect.x < World.worldHori && vect.y < World.worldVert) && (vect.x >= 0 && vect.y >= 0))
			inBounds = true;
		return inBounds;
	}

	/**
	 * method to check if a cell the animal tries to move to is null
	 * 
	 * @param x vertical position
	 * @param y horizontal position
	 * @return boolean result
	 */
	public boolean isNullCheck(Vec2d vect) {
		return World.cell[(int)vect.x][(int)vect.y].life == null;
	}

	/**
	 * method to check if the contents of a cell moving into are edible
	 * 
	 * @param x vertical position
	 * @param y horizontal position
	 * @return boolean result
	 */
	public boolean isEdibleCheck(Vec2d vect) {
		return (World.cell[(int) vect.x][(int) vect.y].life instanceof HerbivoreEdible);
	}

	/**
	 * 
	 * @param vect the target vector
	 * @return returns true if it's a viable move position
	 */
	public boolean viablePosition(Vec2d vect) {
		boolean herbivoreEdible = isEdibleCheck(vect);
		boolean isNull = isNullCheck(vect);
		return (herbivoreEdible || isNull);

	}

	/**
	 * 
	 * @param vect the target vector orchestrates a move of an Herbivore to a new
	 *             cell
	 */
	void moveSpace(Vec2d vect) {
		World.cell[(int) vect.x][(int) vect.y].life = World.cell[herbivoreHori][herbivoreVert].life;
		World.cell[herbivoreHori][herbivoreVert].life = null;
		herbivoreHori = (int) vect.x;
		herbivoreVert = (int) vect.y;
		moved = true;
	}

	/**
	 * destroys the animal by making it's own cell null and sets the cell's colour
	 * to null's sienna
	 */
	public void die() {
		World.cell[herbivoreHori][herbivoreVert].life = null;
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
		while (moved == false) {
			int randomPositionInt = RandomGenerator.nextNumber(moves.length);
			Vec2d temp = moves[randomPositionInt];
			Vec2d newHerbivoreVect = new Vec2d(herbivoreHori + (int) temp.x, herbivoreVert + (int) temp.y);
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
