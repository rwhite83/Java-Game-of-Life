package asmt2A;

import javafx.scene.control.Button;

public class Game {
	
	World world = new World(30, 30);
	
	/** methods to start a new game and process a turn for a world 
	 * created in Main */
	
	public void play() {
		Main.launchGUI(world);
	}
	
	public void turn() {
		world.worldTurn();
		for(int i = 0; i < World.worldVert; i++) {
			for (int j = 0; j < World.worldHori; j++) {
				Main.colorize(Main.buttons[i][j], i, j);
			}
		}
		System.out.println();
	}
	
}