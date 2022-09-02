package entities;

import javafx.scene.shape.Circle;
import resources.ValueConfig;

public class Fruit {
	private Circle fruitBody;
	private ValueConfig constantVals;
	
	public Fruit() {
		
		constantVals = ValueConfig.getInstance();
		
		fruitBody = new Circle();
		fruitBody.setRadius(constantVals.getFruitRadius());
		fruitBody.setFill(constantVals.getFruitColor());
	}

	public Circle getFruitBody() {
		return fruitBody;
	}

	public void setFruitBody(Circle fruitBody) {
		this.fruitBody = fruitBody;
	}
	
}
