package asmt2A;

import java.util.ArrayList;

import com.sun.javafx.geom.Vec2d;

public class Plant extends LifeForm implements HerbivoreEdible {

	/**
	 * a particular plant's horizontal and vertical, a vector of those variables,
	 * and a moved boolean to tag if a plant has been instantiated this turn. also
	 * including constants for seed function and a colour associated with plants
	 */
	private int plantVert;
	private int plantHori;
	private Vec2d plantVect;
	public boolean moved;
	public static final int MINIMUM_NULL = 3;
	public static final int EXACT_PLANT = 4;
	public Colour plantColour = Colour.GREEN;

	/**
	 * a counter for cells with null and plant lifeforms when iterating thorough
	 * neighbouring cells
	 */
	private int nullNeighbours;
	private int plantNeighbours;

	/**
	 * two arraylists, one for fertile positions around plant, the other for viable
	 * positions of those
	 */
	ArrayList<Vec2d> fertile = new ArrayList<Vec2d>();
	ArrayList<Vec2d> fertileViable = new ArrayList<Vec2d>();

	/**
	 * standard constructor for Plant
	 * 
	 * @param v vertical position
	 * @param h horizontal position
	 */
	Plant(int x, int y) {
		this.plantHori = x;
		this.plantVert = y;
		plantVect = new Vec2d(plantHori, plantVert);

	}

	/**
	 * a setter for the moved variable
	 */
	public void setMoved(boolean moved) {
		moved = this.moved;
	}

	/**
	 * returns the colour of the plant
	 */
	public Colour getColour() {
		return plantColour;
	}

	/**
	 * goes through all neighboring cells, if they are within global bounds it
	 * checks if they are herbivore edible and increments plantNeighbors count if it
	 * is, and checks if the cell is null and increments if it is. if it's a viable
	 * position, it adds that position to a the fertileViable array list
	 */
	public void neighborCheck(Vec2d tempVect) {
		plantNeighbours = 0;
		nullNeighbours = 0;
		fertileViable.clear();
		for (int i = 0; i < moves.length; i++) {
			Vec2d currentVect = new Vec2d(plantVect.x + moves[i].x, plantVect.y + moves[i].y);
			if (currentVect.x < World.worldHori && currentVect.y < World.worldVert && currentVect.x >= 0
					&& currentVect.y >= 0) {
				if (World.cell[(int) currentVect.x][(int) currentVect.y].life instanceof HerbivoreEdible) {
					plantNeighbours++;
				}
				if (World.cell[(int) currentVect.x][(int) currentVect.y].life == null) {
					nullNeighbours++;
					fertileViable.add(currentVect);
				}
			}
		}
	}

	/**
	 * first implements neighborCheck, then picks a random number based on the
	 * number of nullNeighbors. it then pull a vector from the fertile viable array
	 * based on the index position of plantNeighbors. puts a new plant in the chosen
	 * cell, changes that cell's colour, and sets moved to true
	 */
	public void spawn() {
		neighborCheck(plantVect);
		if (fertileViable.size() > 0) {
			int randomPositionInt = RandomGenerator.nextNumber(fertileViable.size());
			Vec2d seedVect1 = new Vec2d(fertileViable.get(randomPositionInt));
			neighborCheck(seedVect1);
			if (fertileViable.size() > 0) {
				Vec2d seedVect2 = new Vec2d(fertileViable.get(randomPositionInt));
				if (plantNeighbours == EXACT_PLANT && (nullNeighbours >= MINIMUM_NULL)) {
					World.cell[(int) seedVect2.x][(int) seedVect2.y].life = new Plant((int) seedVect2.x,
							(int) seedVect2.y);
					World.cell[(int) seedVect2.x][(int) seedVect2.y].life.setMoved(true);
				}
			}
		}

	}

	/**
	 * calls a live function on the plant to conduct a spawn
	 */
	public void live() {
		spawn();
	}
}
