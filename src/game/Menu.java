package game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import resources.ValueConfig;

/**
 * Class responsible for the small menu that appears, at the very beginning of application launch
 */
public class Menu {

	private VBox menuBoard;
	private TextField nicknameField;
	private String userName;
	private Text welcomeMess;
	private Text nickTooLong;
	private Button enterGame;
	
	private SnakeGame game;
	private int bestScore;
	
	private Scene currentScene;
	private Stage stage;
	private ValueConfig constantVals;
	
	public Menu(Stage stage) {
		
		constantVals = ValueConfig.getInstance();
		
		menuBoard = new VBox();
		menuBoard.setId("preMenuBox");
		
		welcomeMess = new Text("Welcome to the Snake");
		welcomeMess.setId("welcome");
		
		nicknameField = new TextField();
		nicknameField.setId("nicknameField");
		nicknameField.setText("Your nickname");
		
		nickTooLong = new Text();
		nickTooLong.setId("nickLong");
		
		game = new SnakeGame(stage);
		
		enterGame = new Button("Play!");
		enterGame.setId("buttonMenu");
		enterGame.setOnAction(e->{
			userName=nicknameField.getText();
			
			if(userName.length()<constantVals.getUserNameLimit()) {
				bestScore=0;
				
				try (ObjectInputStream readBest = new ObjectInputStream(new FileInputStream(new File("savedScores/"+userName+"score.txt")))){
					System.out.println("Save found");
					bestScore =  readBest.readInt();
				} catch (FileNotFoundException f) {
					System.out.println("Save not found");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				game.init(userName,bestScore);
			}
			else {
				nickTooLong.setText("Nickname must be less than "+constantVals.getUserNameLimit()+" characters length");
				nicknameField.setText("Your nickname");
				nicknameField.requestFocus();
			}
			
		});
		
		this.stage = stage;

	}
	
	public void init() {
		menuBoard.getChildren().addAll(welcomeMess,nicknameField,nickTooLong,enterGame);
		currentScene = new Scene(menuBoard,constantVals.getFirstMenuDim(),constantVals.getFirstMenuDim());
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
