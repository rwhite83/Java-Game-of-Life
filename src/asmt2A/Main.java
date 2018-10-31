package asmt2A;

import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class Main extends Application {
	
	static World world;
	static Game game;
	
	/** launches game in the main */
	
	public static void main(String[] args) {
		game = new Game();
		game.play(); 
	}

	/** creates a world which launches world in World constructor */
	
	/** launches GUI */
	
	public static void launchGUI(World GUIworld) {
		world = GUIworld;
		buttons = new Button[World.worldVert][World.worldHori];
		launch();
	}
	
	/** the event handler for a button press to advance a turn through Game class*/
	
    public void processButtonPress(ActionEvent lifeClick) {
        game.turn();
    }

    /** the GUI stuff --> creates an array of buttons coloured 
     * according to what is in the cells of the cell array*/
    
    // !!!!click not properly processing!!!!
    
    public static void colorize(Button button, int x, int y) {
    	String colourString = world.cell[x][y].getCellColour();
    	buttons[x][y].setStyle("-fx-background-color: " + colourString);
    }
    
    static Button[][] buttons;
    
    static GridPane root;
    
	@Override
	public void start(Stage stage){
		root = new GridPane();
		int i, j;
		for(i = 0; i < world.worldVert; i++)
        {
            for(j = 0; j < world.worldHori; j++)
            {
            	buttons[i][j] = new Button();
                root.add(buttons[i][j], i, j);
                colorize(buttons[i][j], i, j);
                buttons[i][j].setOnAction(this::processButtonPress);
            }
        }
		root.setMinSize(10,10);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Game of Life");
		stage.show();
	}
}
