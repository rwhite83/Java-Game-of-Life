package asmt2A;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.sun.javafx.geom.Vec2d;

public class Plant extends LifeForm implements HerbivoreEdible {

	/**
	 * a particular plant's horizontal and vertical
	 */
	private int plantVert;
	private int plantHori;
	private Vec2d plantVect;
	// private Vec2d tempVect = new Vec2d(plantVert, plantHori);

	private int nullNeighbors;
	private int plantNeighbors;
	// private int instantNullNeighbors;
	// private int instantPlantNeighbors;

	ArrayList<Vec2d> fertile = new ArrayList<Vec2d>();
	ArrayList<Vec2d> fertileViable = new ArrayList<Vec2d>();

	/** a generic random number generator */
	Random rand = new Random();

	/**
	 * standard constructor for Plant
	 * 
	 * @param v vertical position
	 * @param h horizontal position
	 */
	Plant(int v, int h) {
		this.plantVert = v;
		this.plantHori = h;
		plantVect = new Vec2d(plantVert, plantHori);

	}

	/**
	 * goes through all neighboring cells, if they are within global bounds it
	 * checks if they are herbivore edible and increments plantNeighbors count if it
	 * is, and checks if the cell is null and increments if it is
	 */
	public void neighborCheck(Vec2d tempVect) {
		plantNeighbors = 0;
		nullNeighbors = 0;
		fertileViable.clear();
		for (int i = 0; i < moves.length; i++) {
			Vec2d currentVect = new Vec2d(plantVect.x + moves[i].x, plantVect.y + moves[i].y);
			if (currentVect.y < World.worldVert && currentVect.x < World.worldHori && currentVect.y >= 0
					&& currentVect.x >= 0) {
				if (World.cell[(int) currentVect.y][(int) currentVect.x].life instanceof HerbivoreEdible) {
					plantNeighbors++;
				}
				if (World.cell[(int) currentVect.y][(int) currentVect.x].life == null) {
					nullNeighbors++;
					fertileViable.add(currentVect);
				}
			}
			// System.out.println(plantNeighbors + " " + nullNeighbors);
		}
	}

	/**
	 * first implements neighborCheck, then picks a random number based on the
	 * number of nullNeighbors. it then pull a vector from the fertile array based
	 * on the index position of plantNeighbors
	 * 
	 * currently runs runs without errors, but does not function, but I had to
	 * initialize nullNeighbors and plantNeighbors to 1 to avoid random potentially
	 * searching a zero number
	 */
	public void spawn() {
		neighborCheck(plantVect);
		if (fertileViable.size() > 0) {
			int n = rand.nextInt(fertileViable.size());
			Vec2d seedVect1 = new Vec2d(fertileViable.get(n));
			System.out.println("n: " + n);
			// System.out.println("plantVert: " + plantVert);
			// System.out.println("plantHori: " + plantHori);
			System.out.println("plantVect: " + plantVect);
			// System.out.println("seedVert: " + seedVect.y);
			// System.out.println("seedHori: " + seedVect.x);
			System.out.println("seedVect: " + seedVect1);
			neighborCheck(seedVect1);
			if (fertileViable.size() > 0) {
				Vec2d seedVect2 = new Vec2d(fertileViable.get(n));
				// System.out.println("plantVert: " + plantVert);
				// System.out.println("plantHori: " + plantHori);
				System.out.println("plantVect: " + plantVect);
				// System.out.println("seedVert: " + seedVect.y);
				// System.out.println("seedHori: " + seedVect.x);
				System.out.println("seedVect: " + seedVect1);
				System.out.println();
				System.out.println("plantNeighbours: " + plantNeighbors);
				if (plantNeighbors == 4) {
					System.out.println("i am in great pain please help me");
					World.cell[(int) seedVect2.y][(int) seedVect2.x].life = new Plant((int) seedVect2.y, (int) seedVect2.x);
					World.cell[(int) seedVect2.y][(int) seedVect2.x].colour = Colour.GREEN;
				}
				//World.cell[(int) seedVect2.y][(int) seedVect2.x].life = new Plant((int) seedVect2.y, (int) seedVect2.x);
				//World.cell[0][0].life = new Plant((int) seedVect2.y, (int) seedVect2.x);
				// seedVect.y,(int) seedVect.x);
				
			}
		}

	}
	// System.out.println("n: " + n);
	/*
	 * //System.out.println(fertileViable); if (fertileViable.size() > 0) { int n =
	 * rand.nextInt(fertileViable.size()); Vec2d seedVect = new
	 * Vec2d(fertileViable.get(n)); World.cell[(int) seedVect.y][(int)
	 * seedVect.x].life = new Plant(((int) seedVect.y),((int) seedVect.x));
	 * //System.out.println(fertileViable.size()); System.out.println(n);
	 * System.out.println(fertileViable.get(n)); neighborCheck(seedVect); if
	 * (plantNeighbors == 4) {
	 * //System.out.println("it means i am in great pain please help me");
	 * World.cell[0][0].colour = Colour.GREEN;
	 * System.out.println("something didn't happen"); World.cell[(int)
	 * seedVect.y][(int) seedVect.x].life = new Plant(((int) seedVect.y),((int)
	 * seedVect.x)); //World.cell[0][0].life = new Plant((int) seedVect.y, (int)
	 * seedVect.x);
	 * 
	 * } }
	 */

	public void live() {
		spawn();
	}
}
