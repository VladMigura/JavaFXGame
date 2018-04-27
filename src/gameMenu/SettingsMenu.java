package gameMenu;

import application.Main;
import gameMenu.Menu.BoxButton;
import gameMenu.Menu.MenuButton;
import javafx.scene.layout.AnchorPane;

/**
 * This class is a pane for settings menu.
 * @author VladMigura
 * @version 1.0
 */
public class SettingsMenu extends AnchorPane{
	MenuButton graphicsSettings;
	MenuButton soundSettings;
	MenuButton returnButton;
	
	SettingsMenu(){
		this.setPrefSize(Main.WIDTH, Main.HEIGHT);
		graphicsSettings = new MenuButton("√–¿‘» ¿");
		soundSettings = new MenuButton("«¬” ");
		returnButton = new MenuButton("Õ¿«¿ƒ");
		
		BoxButton settingsMenuBox = new BoxButton(graphicsSettings, soundSettings, returnButton);
		
		AnchorPane.setBottomAnchor(settingsMenuBox, 20d);
		AnchorPane.setLeftAnchor(settingsMenuBox, 20d);
		
		this.getChildren().add(settingsMenuBox);
	}
}
