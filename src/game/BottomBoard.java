package game;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class BottomBoard {

	private HBox bottomBox; // snake field
	private Pane boardPane; // placed inside snake field, so css can be used
	
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
		fruit= fruitGen.getFruit();
		
	}

	public void initBottomBoard() {
		
		boardPane.getChildren().addAll(snake.getSnakeBody());
		fruitGen.generateFruit(snake.genSnakeSegments());
		boardPane.getChildren().add(fruitGen.getFruit());
		
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
