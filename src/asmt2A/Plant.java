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

	private int nullNeighbors;
	private int plantNeighbors;
	private int instantNullNeighbors;
	private int instantPlantNeighbors;

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
	public void neighborCheck1(Vec2d tempVect) {
		plantNeighbors = 0;
		nullNeighbors = 0;
		for (int i = 0, j = 0; i < moves.length; i++) {
			Vec2d currentVect = new Vec2d(tempVect.y + moves[i].y, tempVect.x + moves[i].x);
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
		neighborCheck1(plantVect);
		int n = rand.nextInt(fertileViable.size());
		Vec2d seedVect = new Vec2d(fertileViable.get(n));
		neighborCheck1(seedVect);
		if (plantNeighbors == 4) {
			System.out.println("it means i am in great pain please help me");
			World.cell[(int) seedVect.y][(int) seedVect.x].life = new Plant(plantHori,plantVert);
		}
		
/*		for (int i = 0; i < fertileViable.size(); i++)
		if (nullNeighbors >= 3) {
			if (fertileViable.size() != 0) {
				int n = rand.nextInt(fertileViable.size());
				Vec2d seedVect = new Vec2d(fertileViable.get(n));
				World.cell[(int) seedVect.y][(int) seedVect.x].life = new Plant((int) seedVect.y, (int) seedVect.x);
			}
		}*/
	}

	/*
	 * } try { int fertileSize = fertile.size(); if (fertileSize != 0) { int n =
	 * rand.nextInt(nullNeighbors); // System.out.println("result of n:" + n); Vec2d
	 * arrayPull = fertile.get(n); int spawnTempVert = (int) arrayPull.x +
	 * plantVert; int spawnTempHori = (int) arrayPull.y + plantHori;
	 * World.cell[spawnTempVert][spawnTempHori].life = new Plant(spawnTempVert,
	 * spawnTempHori); } } catch (ArrayIndexOutOfBoundsException b) {
	 * System.out.println("outof bounds exception: nullNeighbors: " + nullNeighbors
	 * + ": plantNeighbors:" + plantNeighbors); } catch (IllegalArgumentException a)
	 * { System.out.println("illegal argument exception: nullNeighbors: " +
	 * nullNeighbors + ": plantNeighbors:" + plantNeighbors); } finally {
	 * System.out.println("finally: nullNeighbors: " + --nullNeighbors +
	 * ": plantNeighbors:" + plantNeighbors); } } }
	 * 
	 * /** method which responds to turn and initiates a spawn function
	 */
	public void live() {
		spawn();
	}
}
