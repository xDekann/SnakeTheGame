package game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class SnakeBoard {

	private HBox board;
	private Pane boardPane;
	private UpperBoard upperBoard;
	private static Snake snake;
	private FruitGenerator fruitGen;
	
	private boolean isLeft = false;
	private boolean isRight = true;
	private boolean isTop = false;
	private boolean isBottom = false;
	
	private boolean isGameON = true;
	
	private Circle snakeHead;
	private Circle fruit;
	private double distanceHeadAndFruit; // sum of head and fruit radiuses
	private double distanceHeadAndBody;
	
	private ControllerHandler handler;
	
	public SnakeBoard(HBox board, UpperBoard upperBoard) {
		this.board = board;
		boardPane = new Pane();
		boardPane.setId("boardPane");
		
		this.upperBoard = upperBoard;
		
		fruitGen = new FruitGenerator();
		fruit = fruitGen.getFruit();
		
		snake = new Snake();
		snakeHead = snake.getHead();
		
		distanceHeadAndFruit = snakeHead.getRadius()+fruit.getRadius(); // sum of head and fruit radiuses
		distanceHeadAndBody = (snakeHead.getRadius()*2)-4;
		
		handler = new ControllerHandler();
	}
	
	public void moveSnake() {
		
		Circle snakeUpperPart;
		double preMoveX = snakeHead.getCenterX();
		double preMoveY = snakeHead.getCenterY();
		
		for(int i=snake.getSnakeCurrSize()-1;i>0;i--) {
			snakeUpperPart = snake.getSnakeBody().get(i-1);
			snake.getSnakeBody().get(i).setCenterX(snakeUpperPart.getCenterX());
			snake.getSnakeBody().get(i).setCenterY(snakeUpperPart.getCenterY());
		}
		
			 if(isRight)  snakeHead.setCenterX(preMoveX+20);
		else if(isLeft)   snakeHead.setCenterX(preMoveX-20);
		else if(isTop)    snakeHead.setCenterY(preMoveY-20);
		else if(isBottom) snakeHead.setCenterY(preMoveY+20);
			 	 
	}
	
	public void gameStateChecker() {
		
		// check if the snake touched himself (except two first segments)
		Circle snakePart;
		if(snake.getSnakeCurrSize()>=5) {
			for(int i=4;i<snake.getSnakeCurrSize();i++) {
				snakePart = snake.getSnakeBody().get(i);
				if(Math.sqrt(Math.pow(snakePart.getCenterX()-snakeHead.getCenterX(),2)+Math.pow(snakePart.getCenterY()-snakeHead.getCenterY(),2))<=distanceHeadAndBody) {
					snakePart.setFill(Color.RED);
					isGameON = false;
					return;
				}
			}
		}
		System.out.println("X: "+snakeHead.getCenterX()+" "+"Y: "+snakeHead.getCenterY());
		if(snakeHead.getCenterX()+10 >= 630.0)  isGameON=false; // right border
		if(snakeHead.getCenterX()-10 <= 0.0) 	isGameON=false; // left border v
		if(snakeHead.getCenterY()+10 >= 420.0)  isGameON=false; // bottom border
		if(snakeHead.getCenterY()-10 <= 10.0)    isGameON=false; // top border v
		if(!isGameON) return;
		
		if(Math.sqrt(Math.pow(fruit.getCenterX()-snakeHead.getCenterX(),2)+Math.pow(fruit.getCenterY()-snakeHead.getCenterY(),2))<=distanceHeadAndFruit) {
			fruitGen.generateFruit(snake.genSnakeSegments(), boardPane);
			snake.addSegment();
			boardPane.getChildren().add(snake.getSnakeBody().get(snake.getSnakeCurrSize()-1));
			upperBoard.checkScore(snake.getSnakeCurrSize());
		}	
	}
	
	public void initGame() {
		
		boardPane.getChildren().addAll(snake.getSnakeBody());
		boardPane.getChildren().add(fruitGen.getFruit());
		board.getChildren().add(boardPane);
		fruitGen.generateFruit(snake.genSnakeSegments(), boardPane);
		
		Timeline timeline = new Timeline();
		KeyFrame kFrame = new KeyFrame(Duration.millis(87), event->{
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
