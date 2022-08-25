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
	private SnakeGame game;
	
	private Stage stage;
	
	
	public Menu(Stage stage) {
		
		menuBoard = new VBox();
		menuBoard.setId("preMenuBox");
		
		welcomeMess = new Text("Welcome to the Snake");
		
		nicknameField = new TextField();
		nicknameField.setText("Your nickname");
		
		game = new SnakeGame(stage);
		
		enterGame = new Button("Play!");
		enterGame.setOnAction(e->{
			game.initGame();
		});
		
		this.stage = stage;

	}
	
	public void initMenu() {
		menuBoard.getChildren().addAll(welcomeMess,nicknameField,enterGame);
		currentScene = new Scene(menuBoard,300,300);
		currentScene.getStylesheets().add(getClass().getResource("../resources/style.css").toExternalForm());
		stage.setScene(currentScene);
	}

	public SnakeGame getGame() {
		return game;
	}

	public void setGame(SnakeGame game) {
		this.game = game;
	}
	
	
	
}
