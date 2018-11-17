package asmt2A;

import java.awt.Point;
import java.util.ArrayList;

public class Plant extends LifeForm implements HerbivoreEdible {

	/**
	 * a particular plant's horizontal and vertical, a vector of those variables,
	 * and a moved boolean to tag if a plant has been instantiated this turn. also
	 * including constants for seed function and a colour associated with plants
	 */
	public static final int MINIMUM_NULL = 3;
	public static final int MINIMUM_PLANT = 2;

	/**
	 * a counter for cells with null and plant lifeforms when iterating thorough
	 * neighboring cells
	 */
	private int nullNeighbours;
	private int plantNeighbours;

	/**
	 * an arraylist to check for fertile positions around a particular grid location
	 */
	ArrayList<Point> fertile = new ArrayList<Point>();

	/**
	 * standard constructor for Plant
	 * 
	 * @param v vertical position
	 * @param h horizontal position
	 */
	Plant(World world, Point position) {
		super(world, position, Colour.GREEN);
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

	/**
	 * goes through all neighboring cells, if they are within global bounds it
	 * checks if they are herbivore edible and increments plantNeighbors count if it
	 * is, and checks if the cell is null and increments if it is. if it's a viable
	 * position, it adds that position to a the fertileViable array list
	 */
	public void neighborCheck(Point tempVect) {
		plantNeighbours = 0;
		nullNeighbours = 0;
		fertile.clear();
		for (int i = 0; i < moves.length; i++) {
			Point currentVect = new Point(position.x + moves[i].x, position.y + moves[i].y);
			if (currentVect.x < world.worldBounds.x && currentVect.y < world.worldBounds.y && currentVect.x >= 0
					&& currentVect.y >= 0) {
				if (World.cell[currentVect.x][currentVect.y].life instanceof Plant) {
					plantNeighbours++;
				} else if (World.cell[currentVect.x][currentVect.y].life == null) {
					nullNeighbours++;
					fertile.add(currentVect);
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
		neighborCheck(position);
		if (fertile.size() > 0 && nullNeighbours >= MINIMUM_NULL && plantNeighbours >= MINIMUM_PLANT) {
			int randomPositionInt = RandomGenerator.nextNumber(fertile.size());
			Point seedVect = new Point(fertile.get(randomPositionInt));
					World.cell[seedVect.x][seedVect.y].life = new Plant(world, seedVect);
					World.cell[seedVect.x][seedVect.y].life.setMoved(true);
				}

			}
		
	

	/**
	 * calls a live function on the plant to conduct a spawn
	 */
	public void live() {
		spawn();
	}
}
