package game;



import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LostMenu {
	
	private VBox lostMenuBox;
	private Text lostMessage;
	private Button continueButton;
	private Button endButton;
	private Stage stage;
	private SnakeGame game;
	
	public LostMenu(SnakeGame game) {
		
		lostMenuBox = new VBox();
		lostMenuBox.setId("lostMenuBox");
		
		lostMessage = new Text("You lost!");
		
		continueButton = new Button("Try again");
		continueButton.setOnAction(e->{
			game.setGameON(true);
			game.initGame();
		});
		
		endButton = new Button("Quit");
		
		
	}
	
	public void initLost() {
		lostMenuBox.getChildren().addAll(lostMessage,continueButton,endButton);
	}

	public VBox getLostMenuBox() {
		return lostMenuBox;
	}

	public void setLostMenuBox(VBox lostMenuBox) {
		this.lostMenuBox = lostMenuBox;
	}
	
	
	
}
