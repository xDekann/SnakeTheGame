package org.openjfx.snakefx.entities;

import java.util.ArrayList;
import java.util.Objects;

import org.openjfx.snakefx.resources.ValueConfig;

import javafx.scene.shape.Circle;


public class Snake {
	private ArrayList<Circle> snakeBody;
	private ArrayList<Position> segmentPos;
	private int snakeCurrSize; // starting size of snake (including head)
	private final int snakeMaxSize;
	private final int head;
	private ValueConfig constant;
	
	public Snake() {
		constant = ValueConfig.getInstance();
		snakeMaxSize = constant.getSnakeMaxSize();
		snakeCurrSize = constant.getSnakeStartingSize();
		head = 0;
		createSnake();
	}
	
	public void createSnake() {
		snakeBody = new ArrayList<>(snakeMaxSize);
		for(int i=0;i<snakeCurrSize;i++) {
			if(i==head) snakeBody.add(new Circle(constant.getSnakeStartX(),
												 constant.getSnakeStartY(),
												 constant.getSnakeRadius(),
												 constant.getSnakeHColor()));
			else snakeBody.add(new Circle(snakeBody.get(i-1).getCenterX()-constant.getSnakeInitPartDistance(), 
									      snakeBody.get(i-1).getCenterY(), 
									      snakeBody.get(i-1).getRadius(),
									      constant.getSnakeBColor()));
		}		
	}

	public void addSegment() { // 4 variants of movement --> <-- ^ v
		
		Circle lastSegment = snakeBody.get(snakeCurrSize-1);
		Circle preLastSegment = snakeBody.get(snakeCurrSize-2);
		
			 if(lastSegment.getCenterX()<preLastSegment.getCenterX()) 
				 snakeBody.add(new Circle(lastSegment.getCenterX()-constant.getSnakeInitPartDistance(),lastSegment.getCenterY(),
							 constant.getSnakeRadius(),constant.getSnakeBColor()));
			 
		else if(lastSegment.getCenterX()>preLastSegment.getCenterX()) 
			snakeBody.add(new Circle(lastSegment.getCenterX()+constant.getSnakeInitPartDistance(),lastSegment.getCenterY(),
						constant.getSnakeRadius(),constant.getSnakeBColor()));
			 
		else if(lastSegment.getCenterY()<preLastSegment.getCenterY()) 
			snakeBody.add(new Circle(lastSegment.getCenterX(),lastSegment.getCenterY()-constant.getSnakeInitPartDistance(),
						constant.getSnakeRadius(),constant.getSnakeBColor()));
			 
		else if(lastSegment.getCenterY()>preLastSegment.getCenterY()) 
			snakeBody.add(new Circle(lastSegment.getCenterX(),lastSegment.getCenterY()+constant.getSnakeInitPartDistance(),
						constant.getSnakeRadius(),constant.getSnakeBColor()));
			 
			 snakeCurrSize++;
	}
	
	public ArrayList<Position> genSnakeSegments() {
		segmentPos = new ArrayList<>(snakeBody.size());
		for(Circle segment : snakeBody) {
			segmentPos.add(new Position(segment.getCenterX(), segment.getCenterY()));
		}
		return segmentPos;
	}
	
	static class Position{
		
		private double x;
		private double y;
		
		
		protected Position() {
			
		}
		
		protected Position(double x, double y) {
			this.x=x;
			this.y=y;
		}
		
		public double getX() {
			return x;
		}

		public void setX(double x) {
			this.x = x;
		}

		public double getY() {
			return y;
		}

		public void setY(double y) {
			this.y = y;
		}
		
		@Override
		public boolean equals(Object object) {
			boolean check = false;
			if(object instanceof Position) {
				if(this.getX()==((Position) object).getX() && this.getY()==((Position) object).getY()) check=true;
			}
			return check;
		}
		@Override
		public int hashCode() {
			return Objects.hash(x,y);
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
	
}
