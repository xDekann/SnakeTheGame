package game;


import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import resources.ValueConfig;

/**
 * Class which is responsible, for creation of upper board (above snake's moving ground) where the player information is
 */
public class UpperBoard {
	private HBox upperBox;
	private Text player;
	private Text score;
	private Text bestScoreT;
	private int bestScore;
	private String userName;
	
	private int currScore=0;
	
	private ValueConfig constant;
	
	public UpperBoard() {
		
		constant = ValueConfig.getInstance();
		
		upperBox = new HBox();
		upperBox.setId("upperBox");
		player = new Text("Player: "); player.setId("player");
		score = new Text("Score: " + currScore); score.setId("score");
		bestScoreT = new Text("Best score: " + bestScore); bestScoreT.setId("bestScore"); 
	}
	
	public void checkScore(int snakeSize) {
		currScore = snakeSize-constant.getSnakeStartingSize();
		score.setText("Score: " + currScore);
		if(this.bestScore<=currScore) bestScoreT.setText("Best score: " + currScore);
		
	}
	
	public void initUpperBoard() {
		player.setText("Player: " + userName);
		bestScoreT.setText("Best score: " + bestScore);
		upperBox.getChildren().addAll(player,score,bestScoreT);
	}

	public HBox getUpperBox() {
		return upperBox;
	}

	public void setUpperBox(HBox upperBox) {
		this.upperBox = upperBox;
	}

	public int getCurrScore() {
		return currScore;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getBestScore() {
		return bestScore;
	}

	public void setBestScore(int bestScore) {
		this.bestScore = bestScore;
	}
}
