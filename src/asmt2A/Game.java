package asmt2A;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Game extends Application {

	/**
	 * Global width and height parameters Global height parameter
	 */
	public static final int GLOBAL_HORI = 30;
	public static final int GLOBAL_VERT = 30;

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
	
	static GridPane deepRoot;
	static GridPane root;
	
	static GridPane gameState;

	/**
	 * instantiates a world with global parameters and a complementary 2d array of
	 * buttons
	 */
	public Game() {
		world = new World(new Point(GLOBAL_HORI, GLOBAL_VERT));
		buttons = new Button[world.worldBounds.x][world.worldBounds.y];
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
	
	public void processLPress(ActionEvent loadClick) {
		loadGame();
		colorizeButtons();
	}
	
	public void processSPress(ActionEvent saveClick) {
		saveGame();
	}

	public void saveGame() {
		try {
			FileOutputStream f = new FileOutputStream("Class1.ser");
			ObjectOutput out = new ObjectOutputStream(f);
			out.writeObject(world);
			out.writeObject(World.cell);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadGame() {
		try {
        FileInputStream f2 = new FileInputStream("Class1.ser");
        ObjectInputStream in = new ObjectInputStream(f2);
        world = (World) in.readObject();
        World.cell = (World.Cell[][]) in.readObject();
        in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * starts the gridpane, creates buttons and populates the 2D array
	 */
	public void start(Stage stage) {
		stage.setTitle("Game of Life");
		Button load = new Button("L");
		load.setOnAction(this::processLPress);
		Button save = new Button("S");
		save.setOnAction(this::processSPress);
		root = new GridPane();
		gameState = new GridPane();
		root.add(load, GLOBAL_HORI/2 +1, 0);
		root.add(save, GLOBAL_HORI/2 -2, 0);
		int x, y;
		for (x = 0; x < world.worldBounds.x; x++) {
			for (y = 0; y < world.worldBounds.y; y++) {
				buttons[x][y] = new Button("  ");
				root.add(buttons[x][y], x, y + 1);
				colorize(buttons[x][y], World.cell[x][y]);
				buttons[x][y].setOnAction(this::processButtonPress);
			}
		}
		root.setMinSize(1, 1);
		Scene scene = new Scene(root);

		stage.setScene(scene);

		stage.show();
	}
}