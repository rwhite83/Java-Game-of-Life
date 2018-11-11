package asmt2A;

import java.util.ArrayList;

import com.sun.javafx.geom.Vec2d;

public class Plant extends LifeForm implements HerbivoreEdible {

	/**
	 * a particular plant's horizontal and vertical, a vector of those variables,
	 * and a moved boolean to tag if a plant has been instantiated this turn
	 */
	private int plantVert;
	private int plantHori;
	private Vec2d plantVect;
	public boolean moved;

	/**
	 * a counter for cells with null and plant lifeforms when iterationg thorough
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
	Plant(int y, int x) {
		this.plantVert = y;
		this.plantHori = x;
		plantVect = new Vec2d(plantVert, plantHori);

	}

	/**
	 * a setter for the moved variable
	 */
	public void setMoved(boolean m) {
		moved = m;
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
			Vec2d currentVect = new Vec2d(plantVect.y + moves[i].y, plantVect.x + moves[i].x);
			if (currentVect.y < World.worldVert && currentVect.x < World.worldHori && currentVect.y >= 0
					&& currentVect.x >= 0) {
				if (World.cell[(int) currentVect.y][(int) currentVect.x].life instanceof HerbivoreEdible) {
					plantNeighbours++;
				}
				if (World.cell[(int) currentVect.y][(int) currentVect.x].life == null) {
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
			int n = RandomGenerator.nextNumber(fertileViable.size());
			Vec2d seedVect1 = new Vec2d(fertileViable.get(n));
			neighborCheck(seedVect1);
			if (fertileViable.size() > 0) {
				Vec2d seedVect2 = new Vec2d(fertileViable.get(n));
				if (plantNeighbours == 4 && (nullNeighbours >= 3)) {
					World.cell[(int) seedVect2.y][(int) seedVect2.x].life = new Plant((int) seedVect2.y,
							(int) seedVect2.x);
					World.cell[(int) seedVect2.y][(int) seedVect2.x].colour = Colour.GREEN;
					World.cell[(int) seedVect2.y][(int) seedVect2.x].life.setMoved(true);
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
