package gameMenu;

import application.Main;
import gameMenu.Menu.BoxButton;
import gameMenu.Menu.MenuButton;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * This class is a pane for start menu.
 * @author VladMigura
 * @version 1.0
 */
public class StartMenu extends AnchorPane{
	private final int COUNT_OF_LEVELS = 5;
	
	MenuButton resumeButton;
	MenuButton startButton;
	MenuButton settingsButton;
	MenuButton aboutButton;
	MenuButton exitButton;
	
	private Label difficultyLabel;
	private Label levelLabel;
	
	private RadioButton rbDifficultyEasy;
	private RadioButton rbDifficultyMedium;
	private RadioButton rbDifficultyHard;
	ToggleGroup tgChoiceDifficulty;
	
	private RadioButton rbLevelOne;
	private RadioButton rbLevelTwo;
	private RadioButton rbLevelThree;
	private RadioButton rbLevelFour;
	private RadioButton rbLevelFive;
	ToggleGroup tgChoiceLevel;
	
	Image levelImages[];
	ImageView screenshot;
	
	StartMenu(){
		this.setPrefSize(Main.WIDTH, Main.HEIGHT);
		
		levelImages = new Image[5];
		for(int i = 0; i < COUNT_OF_LEVELS; i++) levelImages[i] = new Image(getClass().getResourceAsStream("/Files/Screenshots/Level" + (i+1) + ".png"));
		
		screenshot = new ImageView(levelImages[0]);
		screenshot.setFitWidth(Main.WIDTH * 0.2);
		screenshot.setFitHeight(9 * Main.WIDTH * 0.2 / 16);
		
		resumeButton = new MenuButton("ÏÐÎÄÎËÆÈÒÜ");
		startButton = new MenuButton("ÍÎÂÀß ÈÃÐÀ");
		settingsButton = new MenuButton("ÍÀÑÒÐÎÉÊÈ");
		aboutButton = new MenuButton("ÎÁ ÈÃÐÅ");
		exitButton = new MenuButton("ÂÛÕÎÄ");
		
		difficultyLabel = new Label("Cëîæíîñòü");
		difficultyLabel.setTextFill(Color.WHITE);
		difficultyLabel.setFont(Font.font("CALIBRY", FontWeight.BOLD, 24));
		
		levelLabel = new Label("Óðîâåíü");
		levelLabel.setTextFill(Color.WHITE);
		levelLabel.setFont(Font.font("CALIBRY", FontWeight.BOLD, 24));
		
		tgChoiceDifficulty = new ToggleGroup();
		tgChoiceLevel = new ToggleGroup();
		
		rbDifficultyEasy = new RadioButton("ËÅÃÊÈÉ");
		rbDifficultyMedium = new RadioButton("ÑÐÅÄÍÈÉ");
		rbDifficultyHard = new RadioButton("ÑËÎÆÍÛÉ");
		
		rbLevelOne = new RadioButton("ÓÐÎÂÅÍÜ 1");
		rbLevelTwo = new RadioButton("ÓÐÎÂÅÍÜ 2");
		rbLevelThree = new RadioButton("ÓÐÎÂÅÍÜ 3");
		rbLevelFour = new RadioButton("ÓÐÎÂÅÍÜ 4");
		rbLevelFive = new RadioButton("ÓÐÎÂÅÍÜ 5");
		
		rbDifficultyEasy.setUserData("EASY");
		rbDifficultyMedium.setUserData("MEDIUM");
		rbDifficultyHard.setUserData("HARD");
		
		rbLevelOne.setUserData("Level1");
		rbLevelTwo.setUserData("Level2");
		rbLevelThree.setUserData("Level3");
		rbLevelFour.setUserData("Level4");
		rbLevelFive.setUserData("Level5");
		
		rbDifficultyEasy.setTextFill(Color.WHITE);
		rbDifficultyEasy.setFont(Font.font("CALIBRY", FontWeight.BOLD, 16));
		rbDifficultyMedium.setTextFill(Color.WHITE);
		rbDifficultyMedium.setFont(Font.font("CALIBRY", FontWeight.BOLD, 16));
		rbDifficultyHard.setTextFill(Color.WHITE);
		rbDifficultyHard.setFont(Font.font("CALIBRY", FontWeight.BOLD, 16));
		
		rbLevelOne.setTextFill(Color.WHITE);
		rbLevelOne.setFont(Font.font("CALIBRY", FontWeight.BOLD, 16));
		rbLevelTwo.setTextFill(Color.WHITE);
		rbLevelTwo.setFont(Font.font("CALIBRY", FontWeight.BOLD, 16));
		rbLevelThree.setTextFill(Color.WHITE);
		rbLevelThree.setFont(Font.font("CALIBRY", FontWeight.BOLD, 16));
		rbLevelFour.setTextFill(Color.WHITE);
		rbLevelFour.setFont(Font.font("CALIBRY", FontWeight.BOLD, 16));
		rbLevelFive.setTextFill(Color.WHITE);
		rbLevelFive.setFont(Font.font("CALIBRY", FontWeight.BOLD, 16));
		
		rbDifficultyEasy.setToggleGroup(tgChoiceDifficulty);
		rbDifficultyMedium.setToggleGroup(tgChoiceDifficulty);
		rbDifficultyHard.setToggleGroup(tgChoiceDifficulty);
		
		rbLevelOne.setToggleGroup(tgChoiceLevel);
		rbLevelTwo.setToggleGroup(tgChoiceLevel);
		rbLevelThree.setToggleGroup(tgChoiceLevel);
		rbLevelFour.setToggleGroup(tgChoiceLevel);
		rbLevelFive.setToggleGroup(tgChoiceLevel);
		
		VBox difficultyControl = new VBox(10);
		VBox levelControl = new VBox(10);
		
		difficultyControl.getChildren().add(difficultyLabel);
		difficultyControl.getChildren().add(rbDifficultyEasy);
		difficultyControl.getChildren().add(rbDifficultyMedium);
		difficultyControl.getChildren().add(rbDifficultyHard);
		
		levelControl.getChildren().add(levelLabel);
		levelControl.getChildren().add(rbLevelOne);
		levelControl.getChildren().add(rbLevelTwo);
		levelControl.getChildren().add(rbLevelThree);
		levelControl.getChildren().add(rbLevelFour);
		levelControl.getChildren().add(rbLevelFive);
		
		rbDifficultyMedium.fire();
		rbLevelOne.fire();
		
		BoxButton startMenuBox = new BoxButton(startButton, resumeButton, settingsButton, aboutButton, exitButton);
		
		AnchorPane.setBottomAnchor(startMenuBox, 20d);
		AnchorPane.setLeftAnchor(startMenuBox, 20d);
		AnchorPane.setTopAnchor(difficultyControl, 20d);
		AnchorPane.setLeftAnchor(difficultyControl, 20d);
		AnchorPane.setTopAnchor(levelControl, 20d);
		AnchorPane.setLeftAnchor(levelControl, 200d);
		AnchorPane.setTopAnchor(screenshot, 20d);
		AnchorPane.setLeftAnchor(screenshot, 350d);
		
		tgChoiceLevel.selectedToggleProperty().addListener((obsValue, oldValue, newValue) -> {
			if(newValue.getUserData().toString() == "Level1") screenshot.setImage(levelImages[0]);
			else if(newValue.getUserData().toString() == "Level2") screenshot.setImage(levelImages[1]);
			else if(newValue.getUserData().toString() == "Level3") screenshot.setImage(levelImages[2]);
			else if(newValue.getUserData().toString() == "Level4") screenshot.setImage(levelImages[3]);
			else screenshot.setImage(levelImages[4]);
		});
		
		this.getChildren().add(startMenuBox);
		this.getChildren().add(difficultyControl);
		this.getChildren().add(levelControl);
		this.getChildren().add(screenshot);
	}
}
