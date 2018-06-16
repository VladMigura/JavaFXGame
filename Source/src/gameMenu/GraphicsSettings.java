package gameMenu;

import application.Main;
import gameMenu.Menu.MenuButton;
import gameMenu.Menu.MenuCheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * This class is a pane for graphics settings.
 * @author VladMigura
 * @version 1.0
 */
public class GraphicsSettings extends AnchorPane{
	MenuButton returnButton;
	MenuCheckBox checkBoxes[];
	
	GraphicsSettings(){
		this.setPrefSize(Main.WIDTH, Main.HEIGHT);
		GridPane root = new GridPane();
		returnButton = new MenuButton("НАЗАД");
		checkBoxes = new MenuCheckBox[5];
		
		checkBoxes[0] = new MenuCheckBox("HD Текстуры", false);
		checkBoxes[1] = new MenuCheckBox("Тройная буфферизация", false);
		checkBoxes[2] = new MenuCheckBox("FXAA", false);
		checkBoxes[3] = new MenuCheckBox("Размытие в движении", false);
		checkBoxes[4] = new MenuCheckBox("Повышенная производительность", false);
		
		GridPane.setConstraints(checkBoxes[0], 0, 0);
		GridPane.setConstraints(checkBoxes[1], 0, 1);
		GridPane.setConstraints(checkBoxes[2], 0, 2);
		GridPane.setConstraints(checkBoxes[3], 0, 3);
		GridPane.setConstraints(checkBoxes[4], 0, 4);
		
		AnchorPane.setBottomAnchor(returnButton, 20d);
		AnchorPane.setLeftAnchor(returnButton, 20d);
		AnchorPane.setTopAnchor(root, 20d);
		AnchorPane.setLeftAnchor(root, 20d);
		
		//root.setGridLinesVisible(true);
		root.setVgap(10);
		root.setHgap(10);
		root.getChildren().addAll(checkBoxes);
		this.getChildren().add(root);
		this.getChildren().add(returnButton);
	}
}
