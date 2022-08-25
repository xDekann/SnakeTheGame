package game;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Menu {

	private VBox menuBoard;
	private TextField nicknameField;
	private Text welcomeMess;
	private Button enterGame;
	private Scene currentScene;
	@SuppressWarnings("unused")
	private SnakeBoard snakeBoard;
	private Stage stage;
	
	public Menu(VBox menuBoard, SnakeBoard snakeBoard) {
		
		this.menuBoard = menuBoard;
		welcomeMess = new Text("Welcome to the Snake");
		
		nicknameField = new TextField();
		nicknameField.setText("Your nickname");
		
		this.snakeBoard = snakeBoard;
		
		enterGame = new Button("Play!");
		enterGame.setOnAction(e->{
			snakeBoard.initGame();
		});

	}
	
	public void initMenu() {
		menuBoard.getChildren().addAll(welcomeMess,nicknameField,enterGame);
		currentScene = new Scene(menuBoard,300,300);
		currentScene.getStylesheets().add(getClass().getResource("../resources/style.css").toExternalForm());
		stage.setScene(currentScene);
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	
	
}
