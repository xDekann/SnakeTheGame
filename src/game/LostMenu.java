package game;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Class which is responsible for creating a small menu pop up after the game is lost
 */
public class LostMenu {
	
	private VBox lostMenuBox;
	private Text lostMessage;
	private Button continueButton;
	private Button endButton;
	@SuppressWarnings("unused")
	private SnakeGame game;
	
	public LostMenu(SnakeGame game) {
		
		lostMenuBox = new VBox();
		lostMenuBox.setId("lostMenuBox");
		
		lostMessage = new Text("You lost!");
		
		continueButton = new Button("Try again");
		continueButton.setId("buttonLost");
		continueButton.setOnAction(e->{
			game.setGameON(true);
			game.init(game.getUserName(), game.getBestScore());
		});
		
		endButton = new Button("Quit");
		endButton.setId("buttonLost");
		endButton.setOnAction(e->{
			Platform.exit();
		});
	}
	
	public void init() {
		lostMenuBox.getChildren().addAll(lostMessage,continueButton,endButton);
	}

	public VBox getLostMenuBox() {
		return lostMenuBox;
	}

	public void setLostMenuBox(VBox lostMenuBox) {
		this.lostMenuBox = lostMenuBox;
	}
}
