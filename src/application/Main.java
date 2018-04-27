package application;

import java.awt.Dimension;
import java.awt.Toolkit;

import gameController.Game;
import gameMenu.Menu;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class is the main class. It creates main window (mainStage) and sets the
 * first scene.
 * @author VladMigura
 * @version 1.0
 */
public class Main extends Application {
	public static int WIDTH;
	public static int HEIGHT;
	
	public static Stage mainStage;
	
	public static Menu menuField;
	public static Game gameField;
	
	@Override
	public void start(Stage primaryStage) {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		WIDTH = (int)(dimension.width * 0.8);
		HEIGHT = (int)(dimension.height * 0.8);
		
		menuField = new Menu();
		gameField = new Game();
		
		mainStage = primaryStage;
		mainStage.setTitle("MyGame");
		mainStage.setScene(menuField.menuScene);
		
		mainStage.setResizable(false);
		mainStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
