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
	
	private boolean isLeft = false;
	private boolean isRight = true;
	private boolean isTop = false;
	private boolean isBottom = false;
	
	private boolean isGameON = true;
	
	ControllerHandler handler;
	
	public SnakeBoard(HBox board) {
		this.board = board;
		board.setId("mainGameBox");
		
		boardPane = new Pane();
		boardPane.setId("boardPane");
		
		snake = new Snake();
		
		handler = new ControllerHandler();
	}
	
	
	public void paint(){
		boardPane.getChildren().addAll(snake.getSnakeBody());
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
		
		if(snakeHead.getCenterX() == boardPane.getWidth())  isGameON=false;
		if(snakeHead.getCenterX() == 0) 				    isGameON=false;
		if(snakeHead.getCenterY() == boardPane.getHeight()) isGameON=false;
		if(snakeHead.getCenterY() == 0) 					isGameON=false;
	}
	
	public void initBoard() {
		
		paint();
		
		Timeline timeline = new Timeline();
		KeyFrame kFrame = new KeyFrame(Duration.millis(100), event->{
			moveSnake();
			gameStateChecker();
			if(!isGameON) timeline.stop();
		});
		timeline.getKeyFrames().add(kFrame);
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		
	}
	

	public HBox getBoard() {
		return board;
	}

	public void setBoard(HBox board) {
		this.board = board;
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
	
	
}
