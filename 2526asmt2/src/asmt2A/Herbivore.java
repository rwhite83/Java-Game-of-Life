package asmt2A;

import java.util.Random;

import com.sun.javafx.geom.Vec2d;

/** extends lifeForm and implements relevant methods */

public class Herbivore extends LifeForm {

	/**
	 * this particular herbivore's vertical and horizontal, as well as a counter for
	 * the last time it's fed so it can die after enough turns without feeding
	 */
	private int herbivoreVert;
	private int herbivoreHori;
	private int lastFeed;
	
	/** another temporary random number generator */
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
		System.out.print("herbivore");
		move();
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
	public boolean isNotNullCheck(Vec2d vect) {
		boolean isNotNull = false;
		if (World.cell[(int) vect.x][(int) vect.y] == null)
			isNotNull = true;
		return isNotNull;
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
	Vec2d UpLeft = new Vec2d(-1, 1);
	Vec2d Up = new Vec2d(0, 1);
	Vec2d UpRight = new Vec2d(1, 1);
	Vec2d Left = new Vec2d(-1, 0);
	Vec2d Right = new Vec2d(1, 0);
	Vec2d DownLeft = new Vec2d(-1, -1);
	Vec2d Down = new Vec2d(0, -1);
	Vec2d DownRight = new Vec2d(-1, 1);

	Vec2d[] moves = new Vec2d[] {UpLeft, Up, UpRight, Left, Right, DownLeft, Down, DownRight};

	public void move() {
		int n = rand.nextInt(8);
		Vec2d temp = moves[n];
		Vec2d newHerbivoreVect = new Vec2d(herbivoreVert + (int) temp.x, herbivoreHori + (int) temp.y);
		if (inBoundsCheck(newHerbivoreVect))
			if ((isEdibleCheck(newHerbivoreVect)) || isNotNullCheck(newHerbivoreVect)) {
				if (isEdibleCheck(newHerbivoreVect))
					lastFeed = 0;
				else
					lastFeed++;
				World.cell[herbivoreVert][herbivoreHori].colour = Colour.SIENNA;
				World.cell[(int) newHerbivoreVect.x][(int) newHerbivoreVect.y].colour = Colour.YELLOW;
				World.cell[(int) newHerbivoreVect.x][(int) newHerbivoreVect.y].life = World.cell[herbivoreVert][herbivoreHori].life;
				World.cell[herbivoreVert][herbivoreHori].life = null;
				herbivoreVert += (int) newHerbivoreVect.x;
				herbivoreHori += (int) newHerbivoreVect.y;
			}
			if (lastFeed >= 5)
				die();
	}
}
