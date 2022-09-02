package org.openjfx.snakefx.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.ArrayDeque;

import org.openjfx.snakefx.entities.Snake;
import org.openjfx.snakefx.resources.ValueConfig;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Class that connects all the pieces (boards+menus, except Menu.java) together, manages the game state
 */
public final class SnakeGame {

	private BottomBoard bottomBoard;
	private UpperBoard upperBoard;
	private String userName;
	private int bestScore;
	
	private boolean isGameON = true;
	
	// defined in ValueConfig.java, I use it here for shortening statement's length
	private double distanceHeadAndFruit;
	private double distanceHeadAndBody;
	
	private VBox wholeBoard;
	
	private LostMenu lostMenu;
	private StackPane lostScreen;
	private int retries = 0;
	
	private Scene currentScene;
	private Stage stage;
	private ControllerHandler handler;
	private ValueConfig constantVals;
	
	public SnakeGame(Stage stage) {
		
		constantVals = ValueConfig.getInstance();
		
		bottomBoard = new BottomBoard();
		upperBoard = new UpperBoard();
		
		distanceHeadAndFruit = constantVals.getDistanceHeadAndFruit();
		distanceHeadAndBody = constantVals.getDistanceHeadAndBody();
		
		handler = new ControllerHandler();
		
		lostMenu = new LostMenu(this);
		
		this.stage = stage;
		
	}		
	/**
	 * Used for checking the game state
	 */
	public void gameStateChecker() {
		
		// check if the snake touched himself (except two first segments)
		Circle snakePart;
		if(bottomBoard.getSnake().getSnakeCurrSize()>=2) {
			for(int i=2;i<bottomBoard.getSnake().getSnakeCurrSize();i++) {
				snakePart = bottomBoard.getSnake().getSnakeBody().get(i);
				if(Math.sqrt(Math.pow(snakePart.getCenterX()-bottomBoard.getSnakeHead().getCenterX(),2)+Math.pow(snakePart.getCenterY()-bottomBoard.getSnakeHead().getCenterY(),2))<=distanceHeadAndBody) {
					snakePart.setFill(constantVals.getSnakeHColor());
					isGameON = false;
					return;
				}
			}
		}
		// check if snake's head has touched game board border 
		// right border
		if(bottomBoard.getSnakeHead().getCenterX()+constantVals.getSnakeRadius() >= constantVals.getGameWidth() - constantVals.getBorderS())  
			isGameON=false;
		// left border 
		if(bottomBoard.getSnakeHead().getCenterX()-constantVals.getSnakeRadius() <= constantVals.getBorderS()) 	
			isGameON=false;
		// bottom border
		if(bottomBoard.getSnakeHead().getCenterY()+constantVals.getSnakeRadius() >= constantVals.getGameHeight()-constantVals.getUpperBoxH()-constantVals.getBorderS())
			isGameON=false; 
		// top border 
		if(bottomBoard.getSnakeHead().getCenterY()-constantVals.getSnakeRadius() <= constantVals.getBorderS())
			isGameON=false;
		if(!isGameON) return;
		
		
		double snakeHeadX = bottomBoard.getSnakeHead().getCenterX();
		double snakeHeadY = bottomBoard.getSnakeHead().getCenterY();
		double fruitX = bottomBoard.getFruit().getCenterX();
		double fruitY = bottomBoard.getFruit().getCenterY();
		
		// checking if snake's head touches fruit (mathematical formula)
		if(constantVals.calculateDist(fruitX, snakeHeadX, fruitY, snakeHeadY)<=distanceHeadAndFruit) {
			bottomBoard.getFruitGen().generateFruit(bottomBoard.getSnake().genSnakeSegments());
			bottomBoard.getSnake().addSegment();
			// adding newly created snake segment (circle) to the board
			bottomBoard.getBoardPane().getChildren().add(bottomBoard.getSnake().getSnakeBody().get(bottomBoard.getSnake().getSnakeCurrSize()-1));
			upperBoard.checkScore(bottomBoard.getSnake().getSnakeCurrSize());
			
		}	
	}
	
	/**
	 * Main game initialization
	 */
	public void init(String userName, int bestScore) {
		
		upperBoard = new UpperBoard();
		this.userName=userName;
		this.bestScore = bestScore;
		upperBoard.setUserName(userName);
		upperBoard.setBestScore(bestScore);
		
		bottomBoard = new BottomBoard();
		bottomBoard.getSnake().setCurrentDirection("R");
		bottomBoard.getSnake().setDirection(new ArrayDeque<>());
		
		upperBoard.init();
		bottomBoard.init();
		
		
		wholeBoard = new VBox();
		// connecting score board and main game board
		wholeBoard.getChildren().addAll(upperBoard.getUpperBox(),bottomBoard.getBottomBox());
		
		currentScene = new Scene(wholeBoard,constantVals.getGameWidth(),constantVals.getGameHeight());
		
		currentScene.getStylesheets().add(getClass().getResource("/org/style.css").toExternalForm());
		currentScene.setOnKeyPressed(getHandler());
		stage.setScene(currentScene);
		
		Timeline timeline = new Timeline();
		KeyFrame kFrame = new KeyFrame(Duration.millis(constantVals.getRefreshDuration()), event->{
			bottomBoard.getSnake().moveSnake();
			gameStateChecker();
			if(!isGameON) { 
				// lost menu loading stuff (for the first time)
				if(retries==0) { 
					lostMenu.init();
					retries++;
				}
				// saving (in file) & updating best score (in programme session)
				if(this.bestScore<upperBoard.getCurrScore()) {
					this.bestScore = upperBoard.getCurrScore();
					
					//path for the jar file
					CodeSource codeSource = MainGame.class.getProtectionDomain().getCodeSource();
					File jarFile;
					String jarDir="";
					
					try {
						jarFile = new File(codeSource.getLocation().toURI().getPath()); // getting the location of jar
						jarDir = jarFile.getParentFile().getPath(); // getting the location of parent directory
					} catch (URISyntaxException e1) {
						e1.printStackTrace();
					}
					
					try(ObjectOutputStream readBest = new ObjectOutputStream(new FileOutputStream(new File(jarDir+"/savedScores/"+userName+"score.txt")))) {
						readBest.writeInt(upperBoard.getCurrScore());
					} catch (FileNotFoundException e) {
						System.out.println("Failed to save score");
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
				
				lostScreen = new StackPane();
				lostScreen.getChildren().addAll(lostMenu.getLostMenuBox(),wholeBoard);
				lostMenu.getLostMenuBox().toFront();
				
				currentScene = new Scene(lostScreen,constantVals.getGameWidth(),constantVals.getGameHeight());
				currentScene.getStylesheets().add(getClass().getResource("/org/style.css").toExternalForm());
				currentScene.setOnKeyPressed(getHandler());
				stage.setScene(currentScene);
				
				timeline.stop();
			}
		});
		timeline.getKeyFrames().add(kFrame);
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}
	
	private class ControllerHandler implements EventHandler<KeyEvent>{

		@SuppressWarnings("incomplete-switch")
		@Override
		public void handle(KeyEvent kStroke) {
			Snake snake = bottomBoard.getSnake();
			switch(kStroke.getCode()) {
				case RIGHT:
				case D:
					if(!snake.getCurrentDirection().equals("L") && !snake.getCurrentDirection().equals("R"))
					snake.getDirection().addLast("R");
					break;
				case LEFT:
				case A:
					if(!snake.getCurrentDirection().equals("R") && !snake.getCurrentDirection().equals("L"))
					snake.getDirection().addLast("L");
					break;
				case DOWN:
				case S:
					if(!snake.getCurrentDirection().equals("U") && !snake.getCurrentDirection().equals("D"))
					snake.getDirection().addLast("D");
					break;
				case UP:
				case W:
					if(!snake.getCurrentDirection().equals("D") && !snake.getCurrentDirection().equals("U"))
					snake.getDirection().addLast("U");
					break;
			}
		}
	}

	public ControllerHandler getHandler() {
		return handler;
	}

	public boolean isGameON() {
		return isGameON;
	}

	public void setGameON(boolean isGameON) {
		this.isGameON = isGameON;
	}

	public String getUserName() {
		return userName;
	}

	public int getBestScore() {
		return bestScore;
	}
}
