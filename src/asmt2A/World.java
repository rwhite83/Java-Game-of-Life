package asmt2A;

import java.awt.Point;

import javafx.scene.control.Button;

public class World {

	public static final int RANDOM_PULL = 99;
	public static final int PLANT_RANDOM = 65;
	public static final int HERBIVORE_RANDOM = 85;

	/**
	 * worldHori global variable for height of game worldVert global variable for
	 * width of game cell declares a 2D array of cells
	 */
	public Point worldBounds;

	/**
	 * declares a 2D array or cells
	 */
	public Cell[][] cell;

	/**
	 * world constructor
	 * 
	 * @param hori passes to worldHori
	 * @param vert passes to worldVert implements createWorld function
	 */
	public World(Point worldBounds) {
		this.worldBounds = worldBounds;
		this.cell = new Cell[worldBounds.x][worldBounds.y];

		populateWorld();
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

				} else {
					cell[x][y] = new Cell(newPoint, null);
				}
			}
		}
	}

	/**
	 * the function to initiate all life forms on the board to do a 'live' function
	 * implements properly, but the javafx board doesn't update and change. also
	 * sets all life moved variable to false after a turn, a variable which is set
	 * to true when a lifeform is created or moves to avoid duplicate moves/spawns
	 * on a single turn. also recolorizes every cell after a turn
	 */
	public void worldTurn() {
		int x, y;
		for (x = 0; x < worldBounds.x; x++) {
			for (y = 0; y < worldBounds.y; y++) {
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
	}
}