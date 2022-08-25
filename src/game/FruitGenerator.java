package game;

import java.util.ArrayList;
import java.util.Random;

import game.Snake.Position;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class FruitGenerator {

	private Circle fruit;
	private Position applePos;
	private Random randomizer;
	
	public FruitGenerator() {
		fruit = new Circle();
		fruit.setRadius(5);
		fruit.setFill(Color.DARKORANGE);
		applePos = new Position();
		randomizer = new Random();
	}
	
	public void generateFruit(ArrayList<Position> segmentPos) {
		applePos = new Position();
		do {
			applePos.setX(randomizer.nextDouble(20,610)); // boardPane not initialized?
			applePos.setY(randomizer.nextDouble(30,410));
		}while(segmentPos.contains(applePos));
		
		fruit.setCenterX(applePos.getX());
		fruit.setCenterY(applePos.getY());
	}
	

	public Circle getFruit() {
		return fruit;
	}

	public void setFruit(Circle fruit) {
		this.fruit = fruit;
	}
	
	
	
}
