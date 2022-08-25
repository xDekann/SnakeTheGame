package game;

import javafx.application.Application;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainGame extends Application {

	@Override
	public void start(Stage stage){
		
		// pre game menu
		Menu menuBoard = new Menu(stage); // snakeBoard added for start game purposes
		menuBoard.initMenu();

		stage.setTitle("Snake the game");
		stage.setResizable(false);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch();
	}

}
