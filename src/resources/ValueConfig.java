package resources;

import java.util.Objects;

import javafx.scene.paint.Color;

/**
 * Singleton class that contains constant (final) values
 */
public final class ValueConfig {

	// singleton reference
	private static ValueConfig instance;
	
	// if you decide to change values, remember about style.css file!
	private final double fruitRadius = 5;
	private final double minFruitSpawnW = 20;
	private final double minFruitSpawnH = 30;
	private final double maxFruitSpawnW = 610;
	private final double maxFruitSpawnH = 410;
	private final Color fruitColor = Color.DARKORANGE;
	
	private final double gameWidth = 640;
	private final double gameHeight = 480;
	private final double upperBoxH = 50;
	private final double borderS = 10;
	// first menu is a square
	private final double firstMenuDim = 300;
	private final int userNameLimit = 15;
	private final double refreshDuration = 87;
	
	// according to my calcs, limit should be ~842 for current resolution
	private final int snakeMaxSize = 800;
	private final int snakeStartingSize = 3;
	private final Color snakeHColor = Color.RED;
	private final Color snakeBColor = Color.GREEN;
	private final double snakeStartX = 350;
	private final double snakeStartY = 300;
	private final double snakeRadius = 10;
	// distance between snake each two body parts (part radius *2), used for movement as well
	private final double snakeInitPartDistance = snakeRadius*2;
	
	// used for snake eating fruit calcs
	private final double distanceHeadAndFruit = fruitRadius + snakeRadius;
	private final double fruitGoodSpawn = distanceHeadAndFruit*2;
	// used for snake's head collision with it's tail, -4 to avoid collision just by touching
	private final double distanceHeadAndBody = (snakeRadius*2)-4;
	private final String startingDirection = "R";
	
	private ValueConfig() {
		
	}
	
	public static ValueConfig getInstance() {
		if(instance == null) instance = new ValueConfig();
		return instance;
	}

	public double getFruitRadius() {
		return fruitRadius;
	}

	public Color getFruitColor() {
		return fruitColor;
	}

	public double getMinFruitSpawnW() {
		return minFruitSpawnW;
	}

	public double getMinFruitSpawnH() {
		return minFruitSpawnH;
	}

	public double getMaxFruitSpawnW() {
		return maxFruitSpawnW;
	}

	public double getMaxFruitSpawnH() {
		return maxFruitSpawnH;
	}

	public double getGameWidth() {
		return gameWidth;
	}

	public double getGameHeight() {
		return gameHeight;
	}

	public double getBorderS() {
		return borderS;
	}

	public double getFirstMenuDim() {
		return firstMenuDim;
	}

	public int getUserNameLimit() {
		return userNameLimit;
	}

	public double getRefreshDuration() {
		return refreshDuration;
	}

	public int getSnakeMaxSize() {
		return snakeMaxSize;
	}

	public int getSnakeStartingSize() {
		return snakeStartingSize;
	}

	public Color getSnakeHColor() {
		return snakeHColor;
	}

	public Color getSnakeBColor() {
		return snakeBColor;
	}

	public double getSnakeStartX() {
		return snakeStartX;
	}

	public double getSnakeStartY() {
		return snakeStartY;
	}

	public double getSnakeRadius() {
		return snakeRadius;
	}

	public double getSnakeInitPartDistance() {
		return snakeInitPartDistance;
	}

	public double getDistanceHeadAndFruit() {
		return distanceHeadAndFruit;
	}

	public double getDistanceHeadAndBody() {
		return distanceHeadAndBody;
	}

	public String getStartingDirection() {
		return startingDirection;
	}

	public static void setInstance(ValueConfig instance) {
		ValueConfig.instance = instance;
	}

	public double getFruitGoodSpawn() {
		return fruitGoodSpawn;
	}

	public double getUpperBoxH() {
		return upperBoxH;
	}
	
	public double calculateDist(double x1, double x2, double y1, double y2) {
		return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
	}
	
	public static class Position{
		
		private double x;
		private double y;
		
		
		public Position() {
			
		}
		
		public Position(double x, double y) {
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
}
