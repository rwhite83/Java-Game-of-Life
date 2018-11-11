package asmt2A;

public class Main {
	
	/**
	 * declares a world and a game
	 */
	static World world;
	static Game game;
	
	/** 
	 * creates a game object and calls a play function 
	 * */
	public static void main(String[] args) {
		game = new Game();
		game.play(); 
	}
}
