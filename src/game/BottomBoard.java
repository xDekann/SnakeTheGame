package game;

import entities.FruitGenerator;
import entities.Snake;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

/**
 * Class used for creating bottom part of main game app (part where snake can move). 
 * It also creates/keeps references to FruitGenerator and Snake objects
 */
public class BottomBoard {

	private HBox bottomBox;
	private Pane boardPane;
	
	private Snake snake;
	private Circle snakeHead;
	
	private FruitGenerator fruitGen;
	private Circle fruit;
	
	public BottomBoard() {
		bottomBox = new HBox();
		bottomBox.setId("mainGameBox");
		
		boardPane = new Pane();
		boardPane.setId("boardPane");
		
		snake = new Snake();
		snakeHead = snake.getHead();
		
		fruitGen = new FruitGenerator();
		fruit= fruitGen.getFruitBody();
		
	}

	public void init() {
		
		boardPane.getChildren().addAll(snake.getSnakeBody());
		fruitGen.generateFruit(snake.genSnakeSegments());
		boardPane.getChildren().add(fruitGen.getFruitBody());
		
		bottomBox.getChildren().add(boardPane);
		
	}


	public HBox getBottomBox() {
		return bottomBox;
	}

	public void setBottomBox(HBox bottomBox) {
		this.bottomBox = bottomBox;
	}

	public Pane getBoardPane() {
		return boardPane;
	}


	public void setBoardPane(Pane boardPane) {
		this.boardPane = boardPane;
	}

	public Circle getFruit() {
		return fruit;
	}

	public void setFruit(Circle fruit) {
		this.fruit = fruit;
	}

	public Snake getSnake() {
		return snake;
	}

	public void setSnake(Snake snake) {
		this.snake = snake;
	}

	public Circle getSnakeHead() {
		return snakeHead;
	}

	public void setSnakeHead(Circle snakeHead) {
		this.snakeHead = snakeHead;
	}

	public FruitGenerator getFruitGen() {
		return fruitGen;
	}

	public void setFruitGen(FruitGenerator fruitGen) {
		this.fruitGen = fruitGen;
	}
}
