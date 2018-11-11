package asmt2A;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Game extends Application {

	/**
	 * creates a world
	 */
	static World world = new World(30, 80);

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
		buttons = new Button[World.worldVert][World.worldHori];
		launch();
	}

	/**
	 * starts the gridpane, creates buttons and populates the 2D array
	 */
	public void start(Stage stage) {
		root = new GridPane();
		int x, y;
		for (y = 0; y < World.worldVert; y++) {
			for (x = 0; x < World.worldHori; x++) {
				buttons[y][x] = new Button();
				root.add(buttons[y][x], y, x);
				World.colorize(buttons[y][x], y, x);
				buttons[y][x].setOnAction(this::processButtonPress);
			}
		}
		root.setMinSize(1, 1);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Game of Life");
		stage.show();
	}
}