package game;

import javafx.application.Application;

import javafx.stage.Stage;

/**
 * Main class application
 * @Author Dekann
 * @version 1.1
 */
public class MainGame extends Application {

	@Override
	public void start(Stage stage){
		
		Menu menuBoard = new Menu(stage);
		menuBoard.init();

		stage.setTitle("Snake the game");
		stage.setResizable(false);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch();
	}
}
