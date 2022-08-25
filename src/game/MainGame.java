package game;

import javafx.application.Application;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainGame extends Application {

	@Override
	public void start(Stage stage){
		
		// score box
		HBox upperBox = new HBox(); 
		upperBox.setId("upperBox");
		
		UpperBoard upperBoard = new UpperBoard(upperBox);
		upperBoard.initUpperBoard(); // create top row of in game screen (Score etc.)
		
		// main game box
		HBox mainGameBox = new HBox(); 
		mainGameBox.setId("mainGameBox");
		
		SnakeBoard snakeBoard = new SnakeBoard(mainGameBox, upperBoard);
		snakeBoard.setStage(stage); // connecting stage to the game screen
		snakeBoard.initObjects(); // initialize in game screen objects (snake, fruit, score etc.)

		// pre game menu
		VBox menuBox = new VBox();
		menuBox.setId("menuBox");
		
		Menu menuBoard = new Menu(menuBox, snakeBoard); // snakeBoard added for start game purposes
		menuBoard.setStage(stage); // connecting stage to the pre game menu screen
		menuBoard.initMenu();
		
		
		

		stage.setTitle("Snake the game");
		stage.setResizable(false);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch();
	}

}
