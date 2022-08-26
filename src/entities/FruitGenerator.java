package entities;

import java.util.ArrayList;
import java.util.Random;

import entities.Snake.Position;
import javafx.scene.shape.Circle;
import resources.ValueConfig;

/**
 * Class responsible for fruit generation
 */
public class FruitGenerator {

	private Circle fruit;
	private Position applePos;
	private Random randomizer;
	private ValueConfig constant;
	
	public FruitGenerator() {
		
		constant = ValueConfig.getInstance();

		fruit = new Circle();
		fruit.setRadius(constant.getFruitRadius());
		fruit.setFill(constant.getFruitColor());
		applePos = new Position();
		randomizer = new Random();
	}
	
	public void generateFruit(ArrayList<Position> segmentPos) {
		Position pos;
		applePos = new Position();
		boolean rangeAccepted = false;
		do {
			
			applePos.setX(constant.getMinFruitSpawnW()+
					(randomizer.nextDouble()*
							(constant.getMaxFruitSpawnW()-constant.getMinFruitSpawnW())));
			applePos.setY(constant.getMinFruitSpawnH()+
					(randomizer.nextDouble()*
							(constant.getMaxFruitSpawnH()-constant.getMinFruitSpawnH())));
			
			for(int i=0;i<segmentPos.size();i++){
				pos = segmentPos.get(i);
				// checking if generated fruit pos is not in snake/is a bit away from snake 
				// (fruit radius + segment radius * 2)
				// using basic math formula for distance between two points
				if(Math.sqrt(Math.pow(applePos.getX()-pos.getX(),2)+Math.pow(applePos.getY()-pos.getY(),2))<constant.getFruitGoodSpawn()) {
					break;
				}else if(Math.sqrt(Math.pow(applePos.getX()-pos.getX(),2)+Math.pow(applePos.getY()-pos.getY(),2))>=constant.getFruitGoodSpawn() && i==segmentPos.size()-1) {
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
