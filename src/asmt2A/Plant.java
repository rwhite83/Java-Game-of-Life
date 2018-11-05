package asmt2A;

import java.util.Random;

import com.sun.javafx.geom.Vec2d;

public class Plant extends LifeForm implements HerbivoreEdible {
	
	/**
	 * a particular plant's horizontal and vertical
	 */
	private int plantVert;
	private int plantHori;
	
	private int nullNeighbors;
	private int plantNeighbors;
	
	Vec2d[] fertile = new Vec2d[7];;
	
	/** a generic random number generator */
	Random rand = new Random();
	
	/** standard constructor for Plant
	 * @param v vertical position
	 * @param h horizontal position
	 */
	Plant(int v, int h) {
		this.plantVert = v;
		this.plantHori = h;
	}
	
	/**
	 * goes through all neighboring cells, if they are within global bounds
	 * it checks if they are herbivore edible and increments plantNeighbors count
	 * if it is, and checks if the cell is null and increments if it is
	 */
	public void neighborCheck() {
		for (int i = 0,j = 0; i < moves.length; i++) {
			int checkTempVert = (int) moves[i].x + plantVert;
			int checkTempHori = (int) moves[i].y + plantHori;
			if (checkTempVert < World.worldVert && checkTempHori < World.worldHori) {
				if (World.cell[checkTempVert][checkTempHori].life instanceof HerbivoreEdible)
					plantNeighbors++;
				if (World.cell[checkTempHori][checkTempHori] == null) {
					j++;
					nullNeighbors++;
					Vec2d tempVect = new Vec2d(checkTempVert, checkTempHori);
					fertile[j] = tempVect;
				}
			}
		}
	}

	/**
	 * first implements neighborCheck, then picks a random number based on
	 * the number of nullNeighbors.  it then pull 
	 */
	public void spawn() {
		neighborCheck();
		int n = rand.nextInt(nullNeighbors -1);
		Vec2d arrayPull = fertile[n];
		if (plantNeighbors == 4 && nullNeighbors >= 3) {
			int spawnTempVert = (int) arrayPull.x;
			int spawnTempHori = (int) arrayPull.y;
			World.cell[spawnTempVert][spawnTempHori].life = new Plant(spawnTempVert, spawnTempHori);
		}
	}
	
	/**
	 * method which responds to turn and initiates a spawn function
	 */
	public void live() {
		//System.out.print("plant");
		spawn();
	}
	
	public void die() {
		// still to be defined
	}

}
