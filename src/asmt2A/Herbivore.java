package asmt2A;

import java.util.Random;

import com.sun.javafx.geom.Vec2d;

/** extends lifeForm and implements relevant methods */

public class Herbivore extends LifeForm {
	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * Note to me:
	 * 
	 * 
	 * 
	 * things only move once
	 * 
	 * herbivores don't move to null  
	 *  
	 *  
	 *  
	 *  plants are not what you thought
	 *  
	 * 
	 * 
	 */

	/**
	 * this particular herbivore's vertical and horizontal, as well as a counter for
	 * the last time it's fed so it can die after enough turns without feeding
	 */
	private int herbivoreVert;
	private int herbivoreHori;
	private int lastFeed;
	public boolean moved;
	
	
	/** a generic random number generator */
	Random rand = new Random();

	/**
	 * Overloaded Herbivore constructor type 1
	 * 
	 * @param v vertical position
	 * @param h horizontal position
	 * @param f specified last feed
	 */
	Herbivore(int v, int h, int f) {
		this.herbivoreVert = v;
		this.herbivoreHori = h;
		lastFeed = f;
	}

	/**
	 * Overloaded Herbivore constructor type 2
	 * 
	 * @param v vertical position
	 * @param h horizontal position
	 */
	Herbivore(int v, int h) {
		this.herbivoreVert = v;
		this.herbivoreHori = h;
		lastFeed = 0;
	}

	/**
	 * primary live method responding to 'turn' printout to console is only
	 * temporary for testing calls Herbivore specific move method
	 */
	public void live() {
		//System.out.print("herbivore");
		move();
	}
	
	public void setMoved(boolean x) {
		moved = x;
	}
	
	public boolean getMoved() {
		return moved;
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
		if ((vect.x < World.worldVert && vect.y < World.worldHori)
			&& (vect.x > 0 && vect.y > 0))
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
		boolean isNull = false;
		if (World.cell[(int) vect.x][(int) vect.y] == null)
			isNull = true;
		return isNull;
	}

	/**
	 * method to check if the contents of a cell moving into are edible
	 * 
	 * @param x vertical position
	 * @param y horizontal position
	 * @return boolean result
	 */
	public boolean isEdibleCheck(Vec2d vect) {
		boolean isEdible = false;
		if (World.cell[(int) vect.x][(int) vect.y].life instanceof HerbivoreEdible)
			isEdible = true;
		return isEdible;
	}
	
	/**
	 * 
	 * @param vect the target vector
	 * @return returns true if it's a viable move potision
	 */
	public boolean viablePosition(Vec2d vect) {
		
		boolean clearToMove = false;
		boolean herbivoreEdible = isEdibleCheck(vect);
		boolean isNull = isNullCheck(vect);
		if (herbivoreEdible || isNull)
				clearToMove = true;
		return clearToMove;
	}
	
	/**
	 * 
	 * @param vect the target vector
	 * orchestrates a move of an Herbivore to a new cell
	 */
	void moveSpace(Vec2d vect) {
		World.cell[herbivoreVert][herbivoreHori].colour = Colour.SIENNA;
		World.cell[(int) vect.x][(int) vect.y].colour = Colour.YELLOW;
		World.cell[(int) vect.x][(int) vect.y].life = World.cell[herbivoreVert][herbivoreHori].life;
		World.cell[herbivoreVert][herbivoreHori].life = null;
		herbivoreVert += (int) vect.x;
		herbivoreHori += (int) vect.y;
		moved = true;
	}

	/**
	 * destroys the animal by making it's own cell null
	 */
	public void die() {
		World.cell[herbivoreVert][herbivoreHori].life = null;
		World.cell[herbivoreVert][herbivoreHori].colour = Colour.SIENNA;
	}
	
	/**
	 * vector directions to associate with adjacent
	 * cells and operate on
	 */

	public void move() {
		if (lastFeed == 5)
			die();
		int n = rand.nextInt(7);
		Vec2d temp = moves[n];
		Vec2d newHerbivoreVect = new Vec2d(herbivoreVert + (int) temp.x, herbivoreHori + (int) temp.y);
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
