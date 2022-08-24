package game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainGame extends Application {

	@Override
	public void start(Stage stage){
		int picker=0;
		Scene game;
		
		
		VBox menuBox = new VBox();
		menuBox.setId("menuBox");
		Menu menuBoard = new Menu(menuBox);
		game = new Scene(menuBox,300,300);

		HBox upperBox = new HBox();
		upperBox.setId("upperBox");
		UpperBoard upperBoard = new UpperBoard(upperBox);
		upperBoard.initUpperBoard();
		
		HBox mainGameBox = new HBox();
		mainGameBox.setId("mainGameBox");
		SnakeBoard snakeBoard = new SnakeBoard(mainGameBox, upperBoard);
		snakeBoard.initGame();

		
		VBox combinationBox = new VBox();
		combinationBox.getChildren().addAll(upperBox,mainGameBox);
		
		if(picker==0) game = new Scene(combinationBox,640,480);
		game.getStylesheets().add(getClass().getResource("../resources/style.css").toExternalForm());	
		game.setOnKeyPressed(snakeBoard.getHandler());
		stage.setScene(game);
		stage.setTitle("Snake the game");
		stage.setResizable(false);
		stage.show();
	
		
	}
	
	public static void main(String[] args) {
		launch();
	}

}
