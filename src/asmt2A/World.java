package asmt2A;

import java.util.Random;
import javafx.scene.shape.Rectangle;

public class World {
	
	/** 
	 * worldHori global variable for height of game
	 * worldVert global variable for width of game
	 * cell declares a 2D array of cells
	 */
	
	public static int worldHori;
	public static int worldVert;
	
	static Cell[][] cell;
	
	/** world constructor
	 * @param hori passes to worldHori
	 * @param vert passes to worldVert
	 * implements createWorld function 
	 */
	public World (int vert, int hori) {
		this.worldHori = hori;
		this.worldVert = vert;
		createWorld(worldHori, worldVert);
	}

	/** temporary random number generator in lieu of 
	 * RandomGenerator class to be provided by prof
	 */
	Random rand = new Random();
	
	/** inner cell class
	 * includes vertical and horizontal position in the cell array,
	 * the colour to be associated with that kind of cell,
	 * a LifeForm to associate with it (passed in the constructor)
	 */
	public class Cell {
		
		public Rectangle border;
		public int vertPosi;
		public int horiPosi;
		
		public Colour colour;

		public LifeForm life;

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
		
		public void setCellvertPosi(int x) {
			this.vertPosi = x;
		}
		
		public void setCellhoriPosi(int y) {
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
	 * create world method
	 * instantiates the 2D array and creates a new cell at 
	 * each position, assigned a lifeType (or null) depending
	 * on results of a random number generator
	 * @param hori global board horizontal parameter passed in
	 * @param vert global board vertical parameter passed in
	 */
	public void createWorld(int vert, int hori) {
		cell = new Cell[vert][hori];
		int i, j, n;
		for (i = 0; i < worldVert; i++) {
			for (j = 0; j < worldHori; j++) {
				n = RandomGenerator.nextNumber(99);
				if (n >= 85) {
					cell[i][j] = new Cell(i, j, new Herbivore(i, j), Colour.YELLOW);
				}
				else if (n >= 65) {
					cell[i][j] = new Cell(i, j, new Plant(i, j), Colour.GREEN);
					
				}
				else {
					cell[i][j] = new Cell(i, j, null, Colour.SIENNA);
				}
			}
		}
	}

	/** 
	 * the function to initiate all life forms on the board to do a 'live'
	 *  function implements properly, but the javafx board doesn't update and change 
	 *  */
	public void worldTurn() {
		int i, j;
		for (i = 0; i < worldVert; i++) {
			for (j = 0; j < worldHori; j++) {
				if ((cell[i][j].life != null) && ((cell[i][j].life.getMoved()))) 
					cell[i][j].life.live();
			}
		}
	}








}
