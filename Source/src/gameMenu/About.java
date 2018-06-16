package gameMenu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import application.Main;
import gameMenu.Menu.MenuButton;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * This class is a pane for about menu.
 * @author VladMigura
 * @version 1.0
 */
public class About extends AnchorPane{
	MenuButton returnButton;
	Label aboutInformation;
	String aboutString;
	
	About() {
		this.setPrefSize(Main.WIDTH, Main.HEIGHT);
		returnButton = new MenuButton("ÍÀÇÀÄ");
		aboutInformation = new Label();
		aboutString = "";
		
		Scanner input;
		try {
			input = new Scanner(new File("src/Files/ABOUT.txt"));
			while(input.hasNext()) aboutString += input.nextLine() + "\r\n";
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		aboutInformation.setText(aboutString);
		aboutInformation.setFont(Font.font("CALIBRY", FontWeight.BOLD, 16));
		aboutInformation.setTextFill(Color.RED);
		
		AnchorPane.setBottomAnchor(returnButton, 20d);
		AnchorPane.setLeftAnchor(returnButton, 20d);
		AnchorPane.setTopAnchor(aboutInformation, 20d);
		AnchorPane.setLeftAnchor(aboutInformation, 20d);
		AnchorPane.setRightAnchor(aboutInformation, 20d);
		
		this.getChildren().add(returnButton);
		this.getChildren().add(aboutInformation);
	}
}
