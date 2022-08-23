package game;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Snake {
	private ArrayList<Circle> snakeBody;
	private int snakeCurrSize; // starting size of snake (including head)
	private final int snakeMaxSize = 100;
	private final int head=0;
	
	public Snake() {
		this.snakeCurrSize = 3;
		this.createSnake();
	}
	
	public void createSnake() {
		snakeBody = new ArrayList<>(snakeMaxSize);
		for(int i=0;i<snakeCurrSize;i++) {
			if(i==head) snakeBody.add(new Circle(350,300,10,Color.RED));
			else snakeBody.add(new Circle(snakeBody.get(i-1).getCenterX()-20, 
									      snakeBody.get(i-1).getCenterY(), 
									      snakeBody.get(i-1).getRadius(),
									      Color.GREEN));
		}		
	}

	public void addSegment() { // 4 variants of movement --> <-- ^ v
		
		Circle lastSegment = snakeBody.get(snakeCurrSize-1);
		Circle preLastSegment = snakeBody.get(snakeCurrSize-2);
		
			 if(lastSegment.getCenterX()<preLastSegment.getCenterX()) snakeBody.add(new Circle(lastSegment.getCenterX()-10,lastSegment.getCenterY()   ,10,Color.GREEN));
		else if(lastSegment.getCenterX()>preLastSegment.getCenterX()) snakeBody.add(new Circle(lastSegment.getCenterX()+10,lastSegment.getCenterY()   ,10,Color.GREEN));
		else if(lastSegment.getCenterY()<preLastSegment.getCenterY()) snakeBody.add(new Circle(lastSegment.getCenterX()   ,lastSegment.getCenterY()-10,10,Color.GREEN));
		else if(lastSegment.getCenterY()>preLastSegment.getCenterY()) snakeBody.add(new Circle(lastSegment.getCenterX()   ,lastSegment.getCenterY()+10,10,Color.GREEN));
			 snakeCurrSize++;
	}
	
	public ArrayList<Circle> getSnakeBody() {
		return snakeBody;
	}

	public void setSnakeBody(ArrayList<Circle> snake) {
		this.snakeBody = snake;
	}

	public int getSnakeCurrSize() {
		return snakeCurrSize;
	}

	public void setSnakeCurrSize(int snakeCurrSize) {
		this.snakeCurrSize = snakeCurrSize;
	}

	public int getSnakeMaxSize() {
		return snakeMaxSize;
	}

	public int getHead() {
		return head;
	}
	
	
	
}
