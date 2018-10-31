package asmt2A;

public class Plant extends LifeForm implements HerbivoreEdible {
	
	/**
	 * a particular plant's horizontal and vertical
	 */
	private int plantVert;
	private int plantHori;
	
	/** standard constructor for Plant
	 * @param v vertical position
	 * @param h horizontal position
	 */
	Plant(int v, int h) {
		this.plantVert = v;
		this.plantHori = h;
	}
	
	public void spawn() {
		//plant specific spawn commands
		//A new plant is created in a cell if there are plants in 5 neighboring cells
		//  Question: would it be better to go through each plant and check it's neighboring 
		//  	cells, or iterate through the whole array and and if it finds a null cell
		//		check how many neighboring cells are plants?
	}
	
	
	/**
	 * method which responds to turn and initiates a spawn function
	 */
	public void live() {
		System.out.print("plant");
		spawn();
	}
	
	public void die() {
		// still to be defined
	}

}
