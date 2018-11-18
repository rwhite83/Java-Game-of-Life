package asmt2A;

import java.awt.Point;
import java.util.ArrayList;

public class Plant extends LifeForm implements HerbivoreEdible, OmnivoreEdible {

	/**
	 * a particular plant's horizontal and vertical, a Pointor of those variables,
	 * and a moved boolean to tag if a plant has been instantiated this turn. also
	 * including constants for seed function and a colour associated with plants
	 */
	public static final int MINIMUM_NULL = 3;
	public static final int MINIMUM_PLANT = 2;

	/**
	 * standard constructor for Plant
	 * 
	 * @param world is the world referred to
	 * @param point refers to its position on the grid
	 */
	Plant(World world, Point position) {
		super(world, position, Colour.GREEN);
	}

	/**
	 * first implements neighborCheck, then picks a random number based on the
	 * number of nullNeighbors. it then pull a Pointor from the fertile viable array
	 * based on the index position of plantNeighbors. puts a new plant in the chosen
	 * cell, changes that cell's colour, and sets moved to true
	 */
	public void live() {
		neighborCheck(position);
		if (viableMoves.size() > 0 && nullNeighbours >= MINIMUM_NULL && plantNeighbours >= MINIMUM_PLANT) {
			int randomPositionInt = RandomGenerator.nextNumber(viableMoves.size());
			Point seedPoint = new Point(viableMoves.get(randomPositionInt));
			World.cell[seedPoint.x][seedPoint.y].life = new Plant(world, seedPoint);
			World.cell[seedPoint.x][seedPoint.y].life.setMoved(true);
		}
	}
}
