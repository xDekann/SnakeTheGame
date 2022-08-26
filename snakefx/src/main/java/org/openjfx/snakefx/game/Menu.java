package org.openjfx.snakefx.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URISyntaxException;
import java.security.CodeSource;

import org.openjfx.snakefx.resources.ValueConfig;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
	private ValueConfig constant;
	
	public Menu(Stage stage) {
		
		constant = ValueConfig.getInstance();
		
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
			
			if(userName.length()<constant.getUserNameLimit()) {
				bestScore=0;
				//new File("/savedScores/"+userName+"score.txt"
				//path for the jar file
				CodeSource codeSource = MainGame.class.getProtectionDomain().getCodeSource();
				File jarFile;
				String jarDir="";
				try {
					// getting the location of jar
					jarFile = new File(codeSource.getLocation().toURI().getPath());
					// getting the location of parent directory
					jarDir = jarFile.getParentFile().getPath();
					// create directory if it does not exist (for saving scores)
					File directory = new File(jarDir+"/savedScores");
					if(!directory.exists()) directory.mkdir();
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
				
				// opening saveScores directory (which has been created above if it did not exist) to manage saves (from JAR file)
				try (ObjectInputStream readBest = new ObjectInputStream(new FileInputStream(new File(jarDir+"/savedScores/"+userName+"score.txt")))){
					System.out.println("Save found");
					bestScore =  readBest.readInt();
				} catch (FileNotFoundException f) {
					System.out.println("Save not found");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
					
				game.initGame(userName,bestScore);
			}
			else {
				nickTooLong.setText("Nickname must be less than 15 characters");
				nicknameField.setText("Your nickname");
				nicknameField.requestFocus();
			}
			
		});
		
		this.stage = stage;

	}
	
	public void initMenu() {
		menuBoard.getChildren().addAll(welcomeMess,nicknameField,nickTooLong,enterGame);
		currentScene = new Scene(menuBoard,constant.getFirstMenuDim(),constant.getFirstMenuDim());
		currentScene.getStylesheets().add(getClass().getResource("/org/style.css").toExternalForm());
		stage.setScene(currentScene);
	}

	public SnakeGame getGame() {
		return game;
	}

	public void setGame(SnakeGame game) {
		this.game = game;
	}
	
}
