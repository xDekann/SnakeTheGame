package game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainGame extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		HBox upperBox = new HBox();
		//upperBox.setStyle("-fx-border-color: transparent transparent red transparent");
		upperBox.setId("upperBox");
		HBox mainGameBox = new HBox();
		VBox combinationBox = new VBox();
		combinationBox.getChildren().addAll(upperBox,mainGameBox);
		Scene game = new Scene(combinationBox,900,900);
		
		game.getStylesheets().add(getClass().getResource("../resources/style.css").toExternalForm());		
		stage.setScene(game);
		stage.show();
	
		
	}
	
	public static void main(String[] args) {
		launch();
	}

}
