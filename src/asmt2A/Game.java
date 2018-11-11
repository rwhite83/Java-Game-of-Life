package asmt2A;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Game extends Application {
	

	/**
	 * 	Global width and height paramters
	 *  Global height parameter 
	 */
	public static final int GLOBAL_HORI = 50;
	public static final int GLOBAL_VERT = 50;
	
	/**
	 * creates a world
	 */
	static World world = new World(GLOBAL_HORI, GLOBAL_VERT);

	/**
	 * declares a 2D array of buttons for the world
	 */
	static Button[][] buttons;

	/**
	 * declares a gridpane
	 */
	static GridPane root;

	/**
	 * launches the GUI
	 */
	public void play() {
		launchGUI(world);
	}

	/**
	 * calls a turn in the World class
	 */
	public void turn() {
		world.worldTurn();
	}

	/**
	 * the event handler for a button press to advance a turn
	 */
	public void processButtonPress(ActionEvent lifeClick) {
		turn();
	}

	/**
	 * launches the GUI with a launch command
	 * 
	 * @param GUIworld is the GUI passed in variable
	 */
	public static void launchGUI(World GUIworld) {
		world = GUIworld;
		buttons = new Button[World.worldHori][World.worldVert];
		launch();
	}

	/**
	 * starts the gridpane, creates buttons and populates the 2D array
	 */
	public void start(Stage stage) {
		root = new GridPane();
		int x, y;
		for (x = 0; x < World.worldHori; x++) {
			for (y = 0; y < World.worldVert; y++) {
				buttons[x][y] = new Button();
				root.add(buttons[x][y], x, y);
				World.colorize(buttons[x][y], x, y);
				buttons[x][y].setOnAction(this::processButtonPress);
			}
		}
		root.setMinSize(1, 1);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Game of Life");
		stage.show();
	}
}