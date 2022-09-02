package org.openjfx.snakefx.entities;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Objects;

import org.openjfx.snakefx.resources.ValueConfig;
import org.openjfx.snakefx.resources.ValueConfig.Position;

import javafx.scene.shape.Circle;


/**
 * Class responsible for snake creation
 */
public class Snake {
	private ArrayList<Circle> snakeBody;
	private ArrayList<Position> segmentPos;
	// starting size of snake (including head)
	private int snakeCurrSize;
	private final int snakeMaxSize;
	private final int head;
	// movement 
	private ArrayDeque<String> direction;
	private String currentDirection;
	
	private ValueConfig constantVals;
	
	public Snake() {
		constantVals = ValueConfig.getInstance();
		snakeMaxSize = constantVals.getSnakeMaxSize();
		snakeCurrSize = constantVals.getSnakeStartingSize();
		head = 0;
		createSnake();
	}
	
	public void createSnake() {
		snakeBody = new ArrayList<>(snakeMaxSize);
		for(int i=0;i<snakeCurrSize;i++) {
			if(i==head) snakeBody.add(new Circle(constantVals.getSnakeStartX(),
												 constantVals.getSnakeStartY(),
												 constantVals.getSnakeRadius(),
												 constantVals.getSnakeHColor()));
			else snakeBody.add(new Circle(snakeBody.get(i-1).getCenterX()-constantVals.getSnakeInitPartDistance(), 
									      snakeBody.get(i-1).getCenterY(), 
									      snakeBody.get(i-1).getRadius(),
									      constantVals.getSnakeBColor()));
		}		
	}

	// 4 variants of movement --> <-- ^ v
	public void addSegment() {
		
		Circle lastSegment = snakeBody.get(snakeCurrSize-1);
		Circle preLastSegment = snakeBody.get(snakeCurrSize-2);
		
			 if(lastSegment.getCenterX()<preLastSegment.getCenterX()) 
				 snakeBody.add(new Circle(lastSegment.getCenterX()-constantVals.getSnakeInitPartDistance(),lastSegment.getCenterY(),
							 constantVals.getSnakeRadius(),constantVals.getSnakeBColor()));
			 
		else if(lastSegment.getCenterX()>preLastSegment.getCenterX()) 
			snakeBody.add(new Circle(lastSegment.getCenterX()+constantVals.getSnakeInitPartDistance(),lastSegment.getCenterY(),
						constantVals.getSnakeRadius(),constantVals.getSnakeBColor()));
			 
		else if(lastSegment.getCenterY()<preLastSegment.getCenterY()) 
			snakeBody.add(new Circle(lastSegment.getCenterX(),lastSegment.getCenterY()-constantVals.getSnakeInitPartDistance(),
						constantVals.getSnakeRadius(),constantVals.getSnakeBColor()));
			 
		else if(lastSegment.getCenterY()>preLastSegment.getCenterY()) 
			snakeBody.add(new Circle(lastSegment.getCenterX(),lastSegment.getCenterY()+constantVals.getSnakeInitPartDistance(),
						constantVals.getSnakeRadius(),constantVals.getSnakeBColor()));
			 
	    snakeCurrSize++;
	}
	
	public ArrayList<Position> genSnakeSegments() {
		segmentPos = new ArrayList<>(snakeBody.size());
		for(Circle segment : snakeBody) {
			segmentPos.add(new Position(segment.getCenterX(), segment.getCenterY()));
		}
		return segmentPos;
	}
	
	/**
	 * Used for moving the snake
	 */
	public void moveSnake() {
		
		Circle snakeUpperPart;
		double preMoveX = snakeBody.get(head).getCenterX();
		double preMoveY = snakeBody.get(head).getCenterY();
		
		for(int i=this.snakeCurrSize-1;i>0;i--) {
			snakeUpperPart = snakeBody.get(i-1);
			snakeBody.get(i).setCenterX(snakeUpperPart.getCenterX());
			snakeBody.get(i).setCenterY(snakeUpperPart.getCenterY());
		}
		
		if(!direction.isEmpty()) 
			currentDirection=direction.removeFirst();
		
		switch(currentDirection) {
			case "R":
				snakeBody.get(head).setCenterX(preMoveX+constantVals.getSnakeInitPartDistance());
				break;
			case "L":
				snakeBody.get(head).setCenterX(preMoveX-constantVals.getSnakeInitPartDistance());
				break;
			case "D":
				snakeBody.get(head).setCenterY(preMoveY+constantVals.getSnakeInitPartDistance());
				break;				
			case "U":
				snakeBody.get(head).setCenterY(preMoveY-constantVals.getSnakeInitPartDistance());
				break;				
			}	
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

	public Circle getHead() {
		return snakeBody.get(head);
	}

	public ArrayList<Position> getSegmentPos() {
		return segmentPos;
	}

	public ArrayDeque<String> getDirection() {
		return direction;
	}

	public void setDirection(ArrayDeque<String> direction) {
		this.direction = direction;
	}

	public String getCurrentDirection() {
		return currentDirection;
	}

	public void setCurrentDirection(String currentDirection) {
		this.currentDirection = currentDirection;
	}
}
