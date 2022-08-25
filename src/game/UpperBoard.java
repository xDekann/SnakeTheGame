package game;


import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class UpperBoard {
	private HBox upperBox;
	private Text player;
	private Text score;
	private Text bestScore;
	
	private int currScore=0;
	
	public UpperBoard() {
		upperBox = new HBox();
		upperBox.setId("upperBox");
		player = new Text("Player: "); player.setId("player");
		score = new Text("Score: " + currScore); score.setId("score");
		bestScore = new Text("Best score:"); bestScore.setId("bestScore"); 
	}
	
	public void checkScore(int snakeSize) {
		currScore = snakeSize-3;
		score.setText("Score: " + currScore);
		
	}
	
	public void initUpperBoard() {
		upperBox.getChildren().addAll(player,score,bestScore);
	}

	public HBox getUpperBox() {
		return upperBox;
	}

	public void setUpperBox(HBox upperBox) {
		this.upperBox = upperBox;
	}
	
	

}
