package game;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayDeque;

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
import resources.ValueConfig;

/**
 * Class that connects all the pieces (boards+menus, except Menu.java) together, manages the game state
 */
public final class SnakeGame {

	private BottomBoard bottomBoard;
	private UpperBoard upperBoard;
	private String userName;
	private int bestScore;
	
	private ArrayDeque<String> direction;
	private String currentDirection;
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
	private ValueConfig constant;
	
	public SnakeGame(Stage stage) {
		
		constant = ValueConfig.getInstance();
		
		bottomBoard = new BottomBoard();
		upperBoard = new UpperBoard();
		
		distanceHeadAndFruit = constant.getDistanceHeadAndFruit();
		distanceHeadAndBody = constant.getDistanceHeadAndBody();
		
		handler = new ControllerHandler();
		
		lostMenu = new LostMenu(this);
		
		this.stage = stage;
		
		
	}
	
	/**
	 * Used for moving the snake
	 */
	public void moveSnake() {
		
		Circle snakeUpperPart;
		double preMoveX = bottomBoard.getSnakeHead().getCenterX();
		double preMoveY = bottomBoard.getSnakeHead().getCenterY();
		
		for(int i=bottomBoard.getSnake().getSnakeCurrSize()-1;i>0;i--) {
			snakeUpperPart = bottomBoard.getSnake().getSnakeBody().get(i-1);
			bottomBoard.getSnake().getSnakeBody().get(i).setCenterX(snakeUpperPart.getCenterX());
			bottomBoard.getSnake().getSnakeBody().get(i).setCenterY(snakeUpperPart.getCenterY());
		}
		
		if(!direction.isEmpty()) 
			currentDirection=direction.removeFirst();
		
		switch(currentDirection) {
			case "R":
				bottomBoard.getSnakeHead().setCenterX(preMoveX+constant.getSnakeInitPartDistance());
				break;
			case "L":
				bottomBoard.getSnakeHead().setCenterX(preMoveX-constant.getSnakeInitPartDistance());
				break;
			case "D":
				bottomBoard.getSnakeHead().setCenterY(preMoveY+constant.getSnakeInitPartDistance());
				break;				
			case "U":
				bottomBoard.getSnakeHead().setCenterY(preMoveY-constant.getSnakeInitPartDistance());
				break;				
			}	
	}
	
	/**
	 * Used for checking the game state
	 */
	public void gameStateChecker() {
		
		// check if the snake touched himself (except four first segments)
		Circle snakePart;
		if(bottomBoard.getSnake().getSnakeCurrSize()>=2) {
			for(int i=2;i<bottomBoard.getSnake().getSnakeCurrSize();i++) {
				snakePart = bottomBoard.getSnake().getSnakeBody().get(i);
				if(Math.sqrt(Math.pow(snakePart.getCenterX()-bottomBoard.getSnakeHead().getCenterX(),2)+Math.pow(snakePart.getCenterY()-bottomBoard.getSnakeHead().getCenterY(),2))<=distanceHeadAndBody) {
					snakePart.setFill(constant.getSnakeHColor());
					isGameON = false;
					return;
				}
			}
		}
		// check if snake's head has touched game board border 
		// right border
		if(bottomBoard.getSnakeHead().getCenterX()+constant.getSnakeRadius() >= constant.getGameWidth() - constant.getBorderS())  
			isGameON=false;
		// left border 
		if(bottomBoard.getSnakeHead().getCenterX()-constant.getSnakeRadius() <= constant.getBorderS()) 	
			isGameON=false;
		// bottom border
		if(bottomBoard.getSnakeHead().getCenterY()+constant.getSnakeRadius() >= constant.getGameHeight()-constant.getUpperBoxH()-constant.getBorderS())
			isGameON=false; 
		// top border 
		if(bottomBoard.getSnakeHead().getCenterY()-constant.getSnakeRadius() <= constant.getBorderS())
			isGameON=false;
		if(!isGameON) return;
		
		// checking if snake's head touches fruit (mathematical formula)
		if(Math.sqrt(Math.pow(bottomBoard.getFruit().getCenterX()-bottomBoard.getSnakeHead().getCenterX(),2)+Math.pow(bottomBoard.getFruit().getCenterY()-bottomBoard.getSnakeHead().getCenterY(),2))<=distanceHeadAndFruit) {
			bottomBoard.getFruitGen().generateFruit(bottomBoard.getSnake().genSnakeSegments());
			bottomBoard.getSnake().addSegment();
			bottomBoard.getBoardPane().getChildren().add(bottomBoard.getSnake().getSnakeBody().get(bottomBoard.getSnake().getSnakeCurrSize()-1));
			upperBoard.checkScore(bottomBoard.getSnake().getSnakeCurrSize());
			
		}	
	}
	
	/**
	 * Main game initialization
	 */
	public void initGame(String userName, int bestScore) {
		
		direction = new ArrayDeque<>();
		currentDirection="R";
		
		upperBoard = new UpperBoard();
		this.userName=userName;
		this.bestScore = bestScore;
		upperBoard.setUserName(userName);
		upperBoard.setBestScore(bestScore);
		
		bottomBoard = new BottomBoard();
		
		upperBoard.initUpperBoard();
		bottomBoard.initBottomBoard();
		
		
		wholeBoard = new VBox();
		// connecting score board and main game board
		wholeBoard.getChildren().addAll(upperBoard.getUpperBox(),bottomBoard.getBottomBox());
		
		currentScene = new Scene(wholeBoard,constant.getGameWidth(),constant.getGameHeight());
		
		currentScene.getStylesheets().add(getClass().getResource("../resources/style.css").toExternalForm());
		currentScene.setOnKeyPressed(getHandler());
		stage.setScene(currentScene);
		
		Timeline timeline = new Timeline();
		KeyFrame kFrame = new KeyFrame(Duration.millis(constant.getRefreshDuration()), event->{
			moveSnake();
			gameStateChecker();
			if(!isGameON) { 
				// lost menu loading stuff (for the first time)
				if(retries==0) { 
					lostMenu.initLost();
					retries++;
				}
				// saving (in file) & updating best score (in programme session)
				if(this.bestScore<upperBoard.getCurrScore()) {
					this.bestScore = upperBoard.getCurrScore();
					try(ObjectOutputStream readBest = new ObjectOutputStream(new FileOutputStream(new File("savedScores/"+userName+"score.txt")))) {
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
				
				currentScene = new Scene(lostScreen,constant.getGameWidth(),constant.getGameHeight());
				currentScene.getStylesheets().add(getClass().getResource("../resources/style.css").toExternalForm());
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
		
			switch(kStroke.getCode()) {
				case RIGHT:
				case D:
					if(!currentDirection.equals("L") && !currentDirection.equals("R"))
					direction.addLast("R");
					break;
				case LEFT:
				case A:
					if(!currentDirection.equals("R") && !currentDirection.equals("L"))
					direction.addLast("L");
					break;
				case DOWN:
				case S:
					if(!currentDirection.equals("U") && !currentDirection.equals("D"))
					direction.addLast("D");
					break;
				case UP:
				case W:
					if(!currentDirection.equals("D") && !currentDirection.equals("U"))
					direction.addLast("U");
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
