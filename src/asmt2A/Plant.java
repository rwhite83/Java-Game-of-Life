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

	public void neighborCheck(Point tempPoint) {
		plantNeighbours = 0;
		nullNeighbours = 0;
		viableMoves.clear();
		for (int i = 0; i < moves.length; i++) {
			Point currentPoint = new Point(position.x + moves[i].x, position.y + moves[i].y);
			if (currentPoint.x < world.worldBounds.x && currentPoint.y < world.worldBounds.y && currentPoint.x >= 0
					&& currentPoint.y >= 0) {
				if (World.cell[currentPoint.x][currentPoint.y].life instanceof Plant) {
					plantNeighbours++;
				} else if (World.cell[currentPoint.x][currentPoint.y].life == null) {
					nullNeighbours++;
					viableMoves.add(currentPoint);
				}
			}
		}
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
			Point seedVect = new Point(viableMoves.get(randomPositionInt));
			World.cell[seedVect.x][seedVect.y].life = new Plant(world, seedVect);
			World.cell[seedVect.x][seedVect.y].life.setMoved(true);
		}
	}
}
