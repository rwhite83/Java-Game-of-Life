package asmt2A;

import java.util.ArrayList;
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
	
	ArrayList<Vec2d> fertile = new ArrayList<Vec2d>();

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
	public void neighborCheck2() {
		int checkTempVert, checkTempHori;
		for (int i = 0, j = 0; i < moves.length; i++) {
			checkTempVert = (int) moves[i].x + plantVert;
			checkTempHori = (int) moves[i].y + plantHori;
			if (checkTempVert < World.worldVert && checkTempHori < World.worldHori && checkTempVert >= 0 && checkTempHori >= 0) {
				if (World.cell[checkTempVert][checkTempHori].life instanceof HerbivoreEdible) {
					plantNeighbors++;
				}
				if (World.cell[checkTempHori][checkTempHori].life == null) {
					nullNeighbors++;
					fertile.add(moves[i]);
				}
			}
		}
	}


	/**
	 * first implements neighborCheck, then picks a random number based on
	 * the number of nullNeighbors.  it then pull a vector from the fertile array 
	 * based on the index position of plantNeighbors
	 * 
	 * currently runs runs without errors, but does not function,
	 * but I had to initialize nullNeighbors and plantNeighbors
	 * to 1 to avoid random potentially searching a zero number 
	 */
	public void spawn() {
		neighborCheck2();
		if ((nullNeighbors <= 3) && (plantNeighbors == 4)) {
			try {
			int n = rand.nextInt(nullNeighbors);
				System.out.println("result of n:" + n);
				
			Vec2d arrayPull = fertile.get(n);
			int spawnTempVert = (int) arrayPull.x + plantVert;
			int spawnTempHori = (int) arrayPull.y + plantHori;
			World.cell[spawnTempVert][spawnTempHori].life = new Plant(spawnTempVert, spawnTempHori);
			} 
			catch (ArrayIndexOutOfBoundsException b)
			{
				System.out.println("outof bounds exception: nullNeighbors: " + nullNeighbors + ": plantNeighbors:" + plantNeighbors);		
			}
			catch (IllegalArgumentException a) {
				System.out.println("illegal argument exception: nullNeighbors: " + nullNeighbors + ": plantNeighbors:" + plantNeighbors);
			}
			finally 
			{
				System.out.println("finally: nullNeighbors: " + nullNeighbors + ": plantNeighbors:" + plantNeighbors);	
			}
				
			}
		}
	
	/**
	 * method which responds to turn and initiates a spawn function
	 */
	public void live() {
		spawn();
	}
}
