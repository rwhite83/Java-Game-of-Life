package asmt2A;

import javafx.scene.control.Button;

public class World {

	/**
	 * worldHori global variable for height of game worldVert global variable for
	 * width of game cell declares a 2D array of cells
	 */
	public static int worldVert;
	public static int worldHori;
	public static final int RANDOM_PULL = 99;
	public static final int PLANT_RANDOM = 65;
	public static final int HERBIVORE_RANDOM = 85;

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
	public World(int x, int y) {
		World.worldHori = x;
		World.worldVert = y;
		createWorld(worldHori, worldVert);
	}

	/**
	 * inner cell class includes vertical and horizontal position in the cell array,
	 * the colour to be associated with that kind of cell, a lifeform to associate
	 * with it (passed in the constructor)
	 */
	public class Cell {

		/**
		 * vertPosi and horiPosi are the dimensions of a particular cell within the 2D
		 * cell array
		 */
		public int vertPosi;
		public int horiPosi;

		/**
		 * a colour associated with a cell which is drawn on GUI draw
		 */
		public Colour colour;

		/**
		 * declaration of a lifeform used to associate a lifeform with a cell
		 */
		public LifeForm life;

		/**
		 * cell constructor
		 * 
		 * @param y          is the vertical variable for a cell
		 * @param x          is the horizontal variable for a cell
		 * @param life       the lifeform variable for a cell
		 * @param cellColour the colour variable for a cell
		 */
		Cell(int x, int y, LifeForm life, Colour cellColour) {
			this.life = life;
			horiPosi = x;
			vertPosi = y;
			colour = cellColour;
		}

		/** various getters and setters */

		public void setLifeType(LifeForm life) {
			this.life = life;
		}

		public void setCellHoriPosi(int x) {
			this.horiPosi = x;
		}

		public void setCellVertPosi(int y) {
			this.vertPosi = y;
		}

		public void setCellColour(Colour colour) {
			this.colour = colour;
		}

		public String getCellColour() {
			String stringColour = "" + colour;
			return stringColour;
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
	
	public void createWorld(int hori, int vert) {
		cell = new Cell[hori][vert];
		int x, y, randomPositionInt;
		for (x = 0; x < worldHori; x++) {
			for (y = 0; y < worldVert; y++) {
				randomPositionInt = RandomGenerator.nextNumber(99);
				if (randomPositionInt >= HERBIVORE_RANDOM) {
					cell[x][y] = new Cell(x, y, new Herbivore(x, y), Colour.YELLOW);
				} else if (randomPositionInt >= PLANT_RANDOM) {
					cell[x][y] = new Cell(x, y, new Plant(x, y), Colour.GREEN);

				} else {
					cell[x][y] = new Cell(x, y, null, Colour.SIENNA);
				}
			}
		}
	}

	/**
	 * the GUI stuff --> creates an array of buttons coloured according to what is
	 * in the cells of the cell array
	 */
	public static void colorize(Button button, int x, int y) {
		String colourString = World.cell[x][y].getCellColour();
		Game.buttons[x][y].setStyle("-fx-background-color: " + colourString);
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
		for (x = 0; x < worldHori; x++) {
			for (y = 0; y < worldVert; y++) {
				if ((cell[x][y].life != null) && (!(cell[x][y].life.getMoved()))) 
					cell[x][y].life.live();
			}
		}
		for (x = 0; x < worldHori; x++) {
			for (y = 0; y < worldVert; y++) {
				if (cell[x][y].life != null)
					cell[x][y].life.setMoved(false);
			}
		}
		for (x = 0; x < World.worldHori; x++) {
			for (y = 0; y < World.worldVert; y++)
				colorize(Game.buttons[x][y], x, y);
		}
	}
}