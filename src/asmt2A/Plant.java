package asmt2A;

import java.util.ArrayList;
import java.awt.Point;

public class Plant extends LifeForm implements HerbivoreEdible {

	public static final int MINIMUM_NULL = 3;
	public static final int EXACT_PLANT = 4;

	/**
	 * a counter for cells with null and plant lifeforms when iterating thorough
	 * neighbouring cells
	 */
	private int nullNeighbours;
	private int plantNeighbours;

	/**
	 * an arraylist to check for fertile positions around a particular grid location
	 */
	private ArrayList<Point> fertile = new ArrayList<Point>();

	/**
	 * standard constructor for Plant
	 * 
	 * @param world the world the plant belongs to
	 * @param position the grid position the plant exists in 
	 */
	Plant(World world, Point position) {
		super(world, position, Colour.GREEN);
	}

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
				if (world.cell[currentVect.x][currentVect.y].life instanceof HerbivoreEdible) {
					plantNeighbours++;
				} else if (world.cell[currentVect.x][currentVect.y].life == null) {
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
		if (fertile.size() > 0 && nullNeighbours >= MINIMUM_NULL) {
			int randomPositionInt = RandomGenerator.nextNumber(fertile.size());
			Point seedVect1 = fertile.get(randomPositionInt);
			neighborCheck(seedVect1);
			if (fertile.size() > 0) {
				Point seedVect2 = fertile.get(randomPositionInt);
				if (plantNeighbours == EXACT_PLANT) {
					world.cell[seedVect2.x][seedVect2.y].life = new Plant(world, seedVect2);
					world.cell[seedVect2.x][seedVect2.y].life.setMoved(true);
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
