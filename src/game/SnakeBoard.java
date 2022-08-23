package game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class SnakeBoard {

	private HBox board;
	private Pane boardPane;
	private Snake snake;
	private FruitGenerator fruitGen;
	
	private boolean isLeft = false;
	private boolean isRight = true;
	private boolean isTop = false;
	private boolean isBottom = false;
	
	private boolean isGameON = true;
	
	private ControllerHandler handler;
	
	public SnakeBoard(HBox board) {
		this.board = board;
		boardPane = new Pane();
		boardPane.setId("boardPane");
		
		fruitGen = new FruitGenerator();
		
		snake = new Snake();
		
		handler = new ControllerHandler();
	}
	
	public void paint(){
		boardPane.getChildren().addAll(snake.getSnakeBody());
		boardPane.getChildren().add(fruitGen.getFruit());
		board.getChildren().add(boardPane);
	}
	
	
	public void moveSnake() {
		
		Circle snakeHead = snake.getSnakeBody().get(snake.getHead());
		Circle snakeUpperPart;
		double preMoveX = snakeHead.getCenterX();
		double preMoveY = snakeHead.getCenterY();
		
		
		for(int i=snake.getSnakeCurrSize()-1;i>0;i--) {
			snakeUpperPart = snake.getSnakeBody().get(i-1);
			snake.getSnakeBody().get(i).setCenterX(snakeUpperPart.getCenterX());
			snake.getSnakeBody().get(i).setCenterY(snakeUpperPart.getCenterY());
		}
		
			 if(isRight)  snakeHead.setCenterX(preMoveX+10);
		else if(isLeft)   snakeHead.setCenterX(preMoveX-10);
		else if(isTop)    snakeHead.setCenterY(preMoveY-10);
		else if(isBottom) snakeHead.setCenterY(preMoveY+10);
			 	 
	}
	
	public void gameStateChecker() {
		
		Circle snakeHead = snake.getSnakeBody().get(snake.getHead());
		Circle fruit = fruitGen.getFruit();
		
		// check if snake touched himself (except two first segments)
		
		
		if(snakeHead.getCenterX() == boardPane.getWidth())  isGameON=false;
		if(snakeHead.getCenterX() == 0) 				    isGameON=false;
		if(snakeHead.getCenterY() == boardPane.getHeight()) isGameON=false;
		if(snakeHead.getCenterY() == 0) 					isGameON=false;
		
		double distanceHeadAndFruit = snakeHead.getRadius()+fruit.getRadius();
		
		if(Math.sqrt(Math.pow(fruit.getCenterX()-snakeHead.getCenterX(),2)+Math.pow(fruit.getCenterY()-snakeHead.getCenterY(),2))<=distanceHeadAndFruit) {
			fruitGen.generateFruit(snake.getSnakeBody(), boardPane);
			snake.addSegment();
			boardPane.getChildren().add(snake.getSnakeBody().get(snake.getSnakeCurrSize()-1)); 
		}
		
		
		
	}
	
	public void initBoard() {
		
		paint();
		fruitGen.generateFruit(snake.getSnakeBody(), boardPane);
		
		Timeline timeline = new Timeline();
		KeyFrame kFrame = new KeyFrame(Duration.millis(80), event->{
			moveSnake();
			gameStateChecker();
			if(!isGameON) timeline.stop();
		});
		timeline.getKeyFrames().add(kFrame);
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		
	}
	
	private class ControllerHandler implements EventHandler<KeyEvent>{

		@Override
		public void handle(KeyEvent kStroke) {
			if(kStroke.getCode() == KeyCode.RIGHT && isLeft==false) { 
				isRight=true;
				isTop=false;
				isBottom=false;
			}
			if(kStroke.getCode() == KeyCode.LEFT  && isRight==false) {
				isLeft=true;
				isTop=false;
				isBottom=false;
			}
			if(kStroke.getCode() == KeyCode.UP    && isBottom==false) {
				isTop=true;
				isRight=false;
				isLeft=false;
			}
			if(kStroke.getCode() == KeyCode.DOWN  && isTop==false) {
				isBottom=true;
				isRight=false;
				isLeft=false;
			}
		}
		
	}

	public ControllerHandler getHandler() {
		return handler;
	}

	public HBox getBoard() {
		return board;
	}

	public void setBoard(HBox board) {
		this.board = board;
	}
	
	
}
