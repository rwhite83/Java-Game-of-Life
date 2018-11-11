package asmt2A;

import javafx.scene.control.Button;

public class World {

	/**
	 * worldHori global variable for height of game worldVert global variable for
	 * width of game cell declares a 2D array of cells
	 */
	public static int worldVert;
	public static int worldHori;

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
		World.worldVert = y;
		World.worldHori = x;
		createWorld(worldVert, worldHori);
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
		Cell(int y, int x, LifeForm life, Colour cellColour) {
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
			this.vertPosi = x;
		}

		public void setCellVertPosi(int y) {
			this.horiPosi = y;
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
	public void createWorld(int vert, int hori) {
		cell = new Cell[vert][hori];
		int x, y, n;
		for (y = 0; y < worldVert; y++) {
			for (x = 0; x < worldHori; x++) {
				n = RandomGenerator.nextNumber(99);
				if (n >= 85) {
					cell[y][x] = new Cell(y, x, new Herbivore(y, x), Colour.YELLOW);
				} else if (n >= 65) {
					cell[y][x] = new Cell(y, x, new Plant(y, x), Colour.GREEN);

				} else {
					cell[y][x] = new Cell(y, x, null, Colour.SIENNA);
				}
			}
		}
	}

	/**
	 * the GUI stuff --> creates an array of buttons coloured according to what is
	 * in the cells of the cell array
	 */
	public static void colorize(Button button, int y, int x) {
		String colourString = World.cell[y][x].getCellColour();
		Game.buttons[y][x].setStyle("-fx-background-color: " + colourString);
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
		for (y = 0; y < worldVert; y++) {
			for (x = 0; x < worldHori; x++) {
				if ((cell[y][x].life != null) && (!(cell[y][x].life.getMoved())))
					cell[y][x].life.live();
			}
		}
		for (y = 0; y < worldVert; y++) {
			for (x = 0; x < worldHori; x++) {
				if (cell[y][x].life != null)
					cell[y][x].life.setMoved(false);
			}
		}
		for (y = 0; y < World.worldVert; y++) {
			for (x = 0; x < World.worldHori; x++)
				colorize(Game.buttons[y][x], y, x);
		}
	}
}