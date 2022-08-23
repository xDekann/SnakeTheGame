package game;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class FruitGenerator {

	private Circle fruit;
	private ArrayList<Position> segmentPos;
	private Position applePos;
	private Random randomizer;
	
	public FruitGenerator() {
		fruit = new Circle();
		fruit.setRadius(5);
		fruit.setFill(Color.DARKORANGE);
		applePos = new Position();
		randomizer = new Random();
	}
	
	public void generateFruit(ArrayList<Circle> snakeBody, Pane boardPane) {
		segmentPos = new ArrayList<>(snakeBody.size());
		for(Circle segment : snakeBody) {
			segmentPos.add(new Position(segment.getCenterX(), segment.getCenterY()));
		}
		applePos = new Position();
		do {
			applePos.setX(randomizer.nextDouble(10,630)); // boardPane not initialized?
			applePos.setY(randomizer.nextDouble(10,420));
		}while(segmentPos.contains(applePos));
		
		fruit.setCenterX(applePos.getX());
		fruit.setCenterY(applePos.getY());
	}
	
	private class Position{
		
		private double x;
		private double y;
		
		
		private Position() {
			
		}
		
		private Position(double x, double y) {
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

	public Circle getFruit() {
		return fruit;
	}

	public void setFruit(Circle fruit) {
		this.fruit = fruit;
	}
	
	
	
}
