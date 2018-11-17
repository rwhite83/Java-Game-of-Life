package asmt2A;

import java.awt.Point;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Game extends Application {

	/**
	 * Global width and height parameters Global height parameter
	 */
	public static final int GLOBAL_HORI = 50;
	public static final int GLOBAL_VERT = 50;

	/**
	 * creates a world
	 */
	private World world;

	/**
	 * declares a 2D array of buttons for the world
	 */
	private Button[][] buttons;

	/**
	 * declares a gridpane
	 */
	static GridPane root;

	/**
	 * instantiates a world with global parameters and 
	 * a complementary 2d array of buttons
	 */
	public Game() {
		world = new World(new Point(GLOBAL_HORI, GLOBAL_VERT));
		buttons = new Button [world.worldBounds.x][world.worldBounds.y];
	}

	/**
	 * launches the GUI
	 */
	public void play() {
		launch();
	}

	/**
	 * calls a turn in the World class
	 */
	public void turn() {
		world.worldTurn();
		colorizeButtons();
	}

	/**
	 * the GUI stuff --> creates an array of buttons coloured according to what is
	 * in the cells of the cell array, now drawing the colours in lifeforms, and a
	 * default colour for cells with null lifeforms
	 */
	public static void colorize(Button button, World.Cell cell) {
		if (cell.life == null)
			button.setStyle("-fx-background-color: SIENNA");
		else {
			String colourString = "" + cell.life.getColour();
			button.setStyle("-fx-background-color: " + colourString);
		}
	}

	/**
	 * a function to re-colorize buttons after a turn is processed
	 */
	public void colorizeButtons() {
		for (int x = 0; x < world.worldBounds.x; x++) 
			for (int y = 0; y < world.worldBounds.y; y++) 
				colorize(buttons[x][y], World.cell[x][y]);
	}

	/**
	 * the event handler for a button press to advance a turn
	 */
	public void processButtonPress(ActionEvent lifeClick) {
		turn();
	}

	/**
	 * starts the gridpane, creates buttons and populates the 2D array
	 */
	public void start(Stage stage) {
		root = new GridPane();
		int x, y;
		for (x = 0; x < world.worldBounds.x; x++) {
			for (y = 0; y < world.worldBounds.y; y++) {
				buttons[x][y] = new Button();
				root.add(buttons[x][y], x, y);
				colorize(buttons[x][y], World.cell[x][y]);
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