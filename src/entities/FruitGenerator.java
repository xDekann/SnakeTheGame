package entities;

import java.util.ArrayList;
import java.util.Random;


import javafx.scene.shape.Circle;
import resources.ValueConfig;
import resources.ValueConfig.Position;

/**
 * Class responsible for fruit generation
 */
public class FruitGenerator {

	private Position generatedPosition;
	private Random randomizer;
	private ValueConfig constantVals;
	private Fruit fruit;
	
	public FruitGenerator() {
		
		constantVals = ValueConfig.getInstance();
		fruit = new Fruit();
		randomizer = new Random();
	}
	
	public void generateFruit(ArrayList<Position> segmentPos) {
		Position segPos;
		generatedPosition = new Position();
		double generatedPosX=0;
		double generatedPosY=0;
		boolean rangeAccepted = false;
		do {
			
			generatedPosition.setX(constantVals.getMinFruitSpawnW()+
					(randomizer.nextDouble()*
							(constantVals.getMaxFruitSpawnW()-constantVals.getMinFruitSpawnW())));
			generatedPosition.setY(constantVals.getMinFruitSpawnH()+
					(randomizer.nextDouble()*
							(constantVals.getMaxFruitSpawnH()-constantVals.getMinFruitSpawnH())));
			
			generatedPosX = generatedPosition.getX();
			generatedPosY = generatedPosition.getY();
			
			for(int i=0;i<segmentPos.size();i++){
				segPos = segmentPos.get(i);
				// checking if generated fruit pos is not in snake/is a bit away from snake 
				// (fruit radius + segment radius * 2)
				// using basic math formula for distance between two points
				if(constantVals.calculateDist(generatedPosX, segPos.getX(), generatedPosY, segPos.getY())<constantVals.getFruitGoodSpawn()) {
					break;
				}else if(constantVals.calculateDist(generatedPosX, segPos.getX(), generatedPosY, segPos.getY())>=constantVals.getFruitGoodSpawn() && i==segmentPos.size()-1) {
					//if the whole snake is checked successfully
					rangeAccepted=true;
				}
			}
		}while(rangeAccepted==false);
		
		fruit.getFruitBody().setCenterX(generatedPosition.getX());
		fruit.getFruitBody().setCenterY(generatedPosition.getY());
	}
	
	public Circle getFruitBody() {
		return fruit.getFruitBody();
	}

}
