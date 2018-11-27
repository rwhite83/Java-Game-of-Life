package asmt2A;

import java.awt.Point;
import java.io.Serializable;

public class World implements Serializable {

	/**
	 * worldHori global variable for height of game worldVert global variable for
	 * width of game cell declares a 2D array of cells
	 */
	public static final int RANDOM_PULL = 99;
	public static final int PLANT_RANDOM = 60;
	public static final int HERBIVORE_RANDOM = 80;
	public static final int CARNIVORE_RANDOM = 50;
	public static final int OMNIVORE_RANDOM = 45;

	////////////////// testing///////////////////////////
/*	int plantCount = 0;
	int herbivoreCount = 0;
	int carnivoreCount = 0;
	int omnivoreCount = 0;
	int turnCounter = 0;*/
	////////////////////////////////////////////////////

	/**
	 * 
	 */
	public Point worldBounds;

	/**
	 * declares a 2D array or cells
	 */
	static Cell[][] cell;

	/**
	 * world constructor
	 * 
	 * @param hori passes to worldHori
	 * @param vert passes to worldVert implements createWorld function
	 */
	public World(Point worldBounds) {
		this.worldBounds = worldBounds;
		World.cell = new Cell[worldBounds.x][worldBounds.y];
		populateWorld();
	}

	/**
	 * inner cell class includes vertical and horizontal position in the cell array,
	 * the colour to be associated with that kind of cell, a lifeform to associate
	 * with it (passed in the constructor)
	 */
	public class Cell implements Serializable {

		/**
		 * vertPosi and horiPosi are the dimensions of a particular cell within the 2D
		 * cell array
		 */
		public int vertPosi;
		public int horiPosi;

		/**
		 * declaration of a lifeform used to associate a lifeform with a cell
		 */
		public LifeForm life;
		public Point position;

		/**
		 * cell constructor
		 * 
		 * @param y          is the vertical variable for a cell
		 * @param x          is the horizontal variable for a cell
		 * @param life       the lifeform variable for a cell
		 * @param cellColour the colour variable for a cell
		 */
		Cell(Point position, LifeForm life) {
			this.life = life;
			this.position = position;
		}
	}

	/**
	 * create world method instantiates the 2D array and creates a new cell at each
	 * position, assigned a lifeType (or null) depending on results of a random
	 * number generator
	 * 
	 * @param hori global board horizontal parameter passed in
	 * @param vert global board vertical parameter passed in
	 */

	public void populateWorld() {
		int x, y, randomPositionInt;
		for (x = 0; x < worldBounds.x; x++) {
			for (y = 0; y < worldBounds.y; y++) {
				Point newPoint = new Point(x, y);
				randomPositionInt = RandomGenerator.nextNumber(99);
				if (randomPositionInt >= HERBIVORE_RANDOM) {
					cell[x][y] = new Cell(newPoint, new Herbivore(this, newPoint));
				} else if (randomPositionInt >= PLANT_RANDOM) {
					cell[x][y] = new Cell(newPoint, new Plant(this, newPoint));
				} else if (randomPositionInt >= CARNIVORE_RANDOM) {
					cell[x][y] = new Cell(newPoint, new Carnivore(this, newPoint));
				} else if (randomPositionInt >= OMNIVORE_RANDOM) {
					cell[x][y] = new Cell(newPoint, new Omnivore(this, newPoint));
				} else {
					cell[x][y] = new Cell(newPoint, null);
				}
/*				if (cell[x][y].life instanceof Plant)
					plantCount++;
				if (cell[x][y].life instanceof Herbivore)
					herbivoreCount++;
				if (cell[x][y].life instanceof Carnivore)
					carnivoreCount++;
				if (cell[x][y].life instanceof Omnivore)
					omnivoreCount++;*/
			}
		}
		//System.out.println("turn: " + turnCounter + " p: " + plantCount + " h: " + herbivoreCount + " :c "
				//+ carnivoreCount + " o: " + omnivoreCount);
	}

	/**
	 * the function to initiate all life forms on the board to do a 'live' function
	 * implements properly. also sets all life moved variable to false after a turn,
	 * a variable which is set to true when a lifeform is created or moves to avoid
	 * duplicate moves/spawns on a single turn. also recolorizes every cell after a
	 * turn
	 */
	public void worldTurn() {
		int x, y;
		for (x = 0; x < worldBounds.x; x++) {
			for (y = 0; y < worldBounds.y; y++) {

				////////////////// testing///////////////////////////

/*				if (cell[x][y].life instanceof Plant)
					plantCount++;
				if (cell[x][y].life instanceof Herbivore) {
					herbivoreCount++;
					//System.out.print("h: " + cell[x][y].life.lastFeed);
				}
				if (cell[x][y].life instanceof Carnivore) {
					carnivoreCount++;
					//System.out.print("c: " + cell[x][y].life.lastFeed);
				}
				if (cell[x][y].life instanceof Omnivore) {
					omnivoreCount++;
					//System.out.print("o: " + cell[x][y].life.lastFeed);
				}*/

				////////////////////////////////////////////////////

				if ((cell[x][y].life != null) && (!(cell[x][y].life.getMoved())))
					cell[x][y].life.live();
			}
		}
		for (x = 0; x < worldBounds.x; x++) {
			for (y = 0; y < worldBounds.y; y++) {
				if (cell[x][y].life != null)
					cell[x][y].life.setMoved(false);
			}
		}

		////////////////// testing///////////////////////////
/*		System.out.println("turn: " + turnCounter + " p: " + plantCount + " h: " + herbivoreCount + " :c "
				+ carnivoreCount + " o: " + omnivoreCount);
		plantCount = 0;
		herbivoreCount = 0;
		carnivoreCount = 0;
		omnivoreCount = 0;
		turnCounter++;*/
		////////////////////////////////////////////////////
	}
}