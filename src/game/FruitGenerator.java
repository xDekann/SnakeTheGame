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
		Position pos;
		applePos = new Position();
		boolean rangeAccepted = false;
		do {
			
			applePos.setX(randomizer.nextDouble(20,610)); // boardPane not initialized?
			applePos.setY(randomizer.nextDouble(30,410));
			
			for(int i=0;i<segmentPos.size();i++){
				pos = segmentPos.get(i);
				// checking if generated fruit pos is not in snake/is a bit away from snake 
				// (fruit radius + segment radius * 2)
				if(Math.sqrt(Math.pow(applePos.getX()-pos.getX(),2)+Math.pow(applePos.getY()-pos.getY(),2))<30) {
					break;
				}else if(Math.sqrt(Math.pow(applePos.getX()-pos.getX(),2)+Math.pow(applePos.getY()-pos.getY(),2))>=30 && i==segmentPos.size()-1) {
					//if the whole snake is checked successfully
					rangeAccepted=true;
				}
				
			}
		}while(rangeAccepted==false);
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
