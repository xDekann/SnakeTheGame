package game;

import java.util.ArrayList;
import java.util.Objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Snake {
	private ArrayList<Circle> snakeBody;
	private ArrayList<Position> segmentPos;
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
	
	public ArrayList<Position> genSnakeSegments() {
		segmentPos = new ArrayList<>(snakeBody.size());
		for(Circle segment : snakeBody) {
			segmentPos.add(new Position(segment.getCenterX(), segment.getCenterY()));
		}
		return segmentPos;
	}
	
	protected static class Position{
		
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
