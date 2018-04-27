package gameMenu;

import application.Main;
import levelData.Level;

import javafx.animation.FillTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

/**
 * This class is a container for different menus like StartMenu, Setting etc. It
 * allows for switching between them and handling events.
 * @author VladMigura
 * @version 1.0
 */
public class Menu extends AnchorPane {
	private ImageView background;
	
	private StartMenu start;
	private SettingsMenu settings;
	private About about;
	private GraphicsSettings graphicsSettings;
	public SoundSettings soundSettings;
	
	public Scene menuScene;
	
	public Menu() {
		this.setPrefSize(Main.WIDTH, Main.HEIGHT);
		menuScene = new Scene(this, Main.WIDTH, Main.HEIGHT);
		
		background = new ImageView("Pictures/MainMenu.jpg");
		background.setFitHeight(Main.HEIGHT + 20);
		background.setFitWidth(Main.WIDTH + 20);
		background.setPreserveRatio(true);
		
		this.getChildren().addAll(background);
		
		start = new StartMenu();
		settings = new SettingsMenu();
		about = new About();
		graphicsSettings = new GraphicsSettings();
		soundSettings = new SoundSettings();
		
		this.getChildren().add(start);

		eventHandler();
	}
	
	/**
	 * This method handles events from button presses. 
	 */
	private void eventHandler() {
		start.resumeButton.setOnMouseClicked(event -> {
			if(!Level.checkAccess(start.tgChoiceLevel.getSelectedToggle().getUserData().toString())) return;
			if(!Level.checkSaveFile(start.tgChoiceLevel.getSelectedToggle().getUserData().toString())) return;
			Main.gameField.isResume = true;
			Main.gameField.loadLevel(start.tgChoiceDifficulty.getSelectedToggle().getUserData().toString(), 
										start.tgChoiceLevel.getSelectedToggle().getUserData().toString());
			Main.mainStage.setScene(Main.gameField.gameScene);
			Main.gameField.startTimer();
		});
		
		start.startButton.setOnMouseClicked(event -> {
			if(!Level.checkAccess(start.tgChoiceLevel.getSelectedToggle().getUserData().toString())) return;
			Main.gameField.loadLevel(start.tgChoiceDifficulty.getSelectedToggle().getUserData().toString(), 
										start.tgChoiceLevel.getSelectedToggle().getUserData().toString());
			Main.mainStage.setScene(Main.gameField.gameScene);
			Main.gameField.startTimer();
		});
		
		start.settingsButton.setOnMouseClicked(event -> setPane(start, settings));
		start.aboutButton.setOnMouseClicked(event -> setPane(start, about));
		start.exitButton.setOnMouseClicked(event -> System.exit(0));
		
		settings.graphicsSettings.setOnMouseClicked(event -> setPane(settings, graphicsSettings));
		settings.soundSettings.setOnMouseClicked(event -> setPane(settings, soundSettings));
		
		settings.returnButton.setOnMouseClicked(event -> setPane(settings, start));
		about.returnButton.setOnMouseClicked(event -> setPane(about, start));
		graphicsSettings.returnButton.setOnMouseClicked(event -> setPane(graphicsSettings, settings));
		soundSettings.returnButton.setOnMouseClicked(event -> setPane(soundSettings, settings));
	}
	
	/**
	 * This method allows switching between menus.
	 * @param Old (Old menu)
	 * @param New (New menu)
	 */
	void setPane(Pane oldPane, Pane newPane) {  
		this.getChildren().remove(oldPane);
		this.getChildren().add(newPane);
	}
	
	/**
	 * This class is a graphical container for button. In order to create a button
	 * object, you need to pass a String (Name) to the constructor.
	 * @author VladMigura
	 * @version 1.0
	 */
	static class MenuButton extends StackPane {
		MenuButton(String name) {
			Rectangle button = new Rectangle(200, 40, Color.GREY);
			button.setStrokeWidth(2.0);
			button.setStroke(Color.WHITE);

			Label buttonName = new Label(name);
			buttonName.setTextFill(Color.WHITE);
			buttonName.setFont(Font.font("CALIBRY", FontWeight.BOLD, 16));

			FillTransition fillTransition = new FillTransition(Duration.seconds(0.3), button, Color.GREY, Color.BLACK);

			this.setOnMouseEntered(event -> {
				ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.1), button);
				scaleTransition.setToX(1.1);
				scaleTransition.setToY(1.1);
				scaleTransition.play();
				fillTransition.play();
			});

			this.setOnMouseExited(event -> {
				ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.1), button);
				scaleTransition.setFromX(1.1);
				scaleTransition.setFromY(1.1);
				scaleTransition.setToX(1);
				scaleTransition.setToY(1);
				button.setFill(Color.GREY);
				scaleTransition.play();
				fillTransition.stop();
			});

			this.getChildren().addAll(button, buttonName);
		}
	}
	
	/**
	 * This class is a container for objects like MenuButton. You can simply pass
	 * MenuButtons to the constructor and it will create VBox object with your
	 * MenuButtons.
	 * @author VladMigura
	 * @version 1.0
	 */
	static class BoxButton extends VBox {
		private final int SPACING = 10;
		
		BoxButton(MenuButton ... buttons) {
			setSpacing(SPACING);
			for (MenuButton btn : buttons)
				this.getChildren().add(btn);
		}
	}
	
	static class MenuCheckBox extends Pane {
		private ImageView background = new ImageView("Pictures/CheckBox.png");
		private final int SIZE = 35;
		private Label text;
		boolean state = false;
		
		MenuCheckBox(String text, boolean state) {
			this.text = new Label(text);
			this.state = state;
			
			this.text.setFont(Font.font("CALIBRY", FontWeight.BOLD, 16));
			this.text.setTextFill(Color.WHITE);
			
			if(state) background.setViewport(new Rectangle2D(2 * SIZE, 0, SIZE, SIZE));
			else background.setViewport(new Rectangle2D(0, 0, SIZE, SIZE));
			
			this.setOnMouseClicked(event -> {
				changeState();
				checkState();
			});
			this.setOnMouseEntered(event -> {
				if(this.state == false) background.setViewport(new Rectangle2D(SIZE, 0, SIZE, SIZE));
				else background.setViewport(new Rectangle2D(3 * SIZE, 0, SIZE, SIZE));
			});
			this.setOnMouseExited(event -> {
				if(this.state == false) background.setViewport(new Rectangle2D(0, 0, SIZE, SIZE));
				else background.setViewport(new Rectangle2D(2 * SIZE, 0, SIZE, SIZE));
			});
			
			this.text.setTranslateX(SIZE + 10);
			this.getChildren().addAll(background, this.text);
		}
		
		/**
		 * This method allows to switch between textures that show state of MenuCheckBox.
		 */
		private void checkState() {
			if(state) background.setViewport(new Rectangle2D(2 * SIZE, 0, SIZE, SIZE));
			else background.setViewport(new Rectangle2D(0, 0, SIZE, SIZE));
		}
		
		/**
		 * This method allows to change state of MenuCheckBox.
		 */
		private void changeState() {
			if(state) state = false;
			else state = true;
		}
	}
}
