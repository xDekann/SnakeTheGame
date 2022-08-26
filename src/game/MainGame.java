package game;

import javafx.application.Application;
import javafx.stage.Stage;
/**
 * Main class application
 * @Author Dekann
 */

public class MainGame extends Application {

	@Override
	public void start(Stage stage){
		
		Menu menuBoard = new Menu(stage);
		menuBoard.initMenu();

		stage.setTitle("Snake the game");
		stage.setResizable(false);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch();
	}

}
