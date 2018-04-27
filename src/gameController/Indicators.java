package gameController;

import application.Main;
import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

/**
 * This class contain set of different indicators.
 * @author VladMigura
 * @version 1.5
 */
public class Indicators {
	
	/**
	 * This class is a pane for health indicator of protagonist.
	 * @author VladMigura
	 * @version 1.5
	 */
	public static class HealthIndicator extends StackPane {
		private Rectangle healthBar;
		private Rectangle healthStatus;
		private Label healthLabel;
		
		public final int WIDTH = 400;
		public final int HEIGHT = 40;
		
		private int maxValue;
		
		public HealthIndicator(int maxValue) {
			this.maxValue = maxValue;
			
			healthBar = new Rectangle(WIDTH, HEIGHT, Color.GREY);
			healthStatus = new Rectangle(WIDTH, HEIGHT, Color.RED);
			healthLabel = new Label(String.valueOf(maxValue));
			healthLabel.setTextFill(Color.WHITE);
			healthLabel.setFont(Font.font("CALIBRY", FontWeight.BOLD, 18));
			
			healthBar.setStrokeWidth(4.0);
			healthBar.setStroke(Color.WHITE);
			healthBar.setArcHeight(10d);
			healthBar.setArcWidth(10d);
			healthStatus.setArcHeight(10d);
			healthStatus.setArcWidth(10d);
			
			this.getChildren().addAll(healthBar, healthStatus, healthLabel);
		}
		
		/**
		 * This method changes health indicators state.
		 * @param damage to the protagonist.
		 */
		public void changeHealthIndicator(int damage) {
			maxValue -= damage; 
			healthStatus.setWidth(healthStatus.getWidth() - 4 * damage);
			healthLabel.setText(String.valueOf(maxValue));
		}
		
		public void setHealthIndicator(int maxValue) {
			this.maxValue = maxValue;
			healthStatus.setWidth(maxValue * 4);
			healthLabel.setText(String.valueOf(maxValue));
		}
	}
	
	/**
	 * This class is a pane for kill counter indicator of
	 * protagonist.
	 * @author VladMigura
	 * @version 1.5
	 */
	public static class KillCounterIndicator extends Pane {
		private Label counter;
		private ScaleTransition scaleTransition;
		
		public KillCounterIndicator() {
			counter = new Label();
			counter.setTextFill(Color.WHITE);
			counter.setFont(Font.font("CALIBRY", FontWeight.BOLD, 30));
			
			scaleTransition = new ScaleTransition(Duration.seconds(0.2), counter);
			scaleTransition.setToX(1.5);
			scaleTransition.setToY(1.5);
			scaleTransition.setAutoReverse(true);
			scaleTransition.setCycleCount(2);
			
			this.getChildren().add(counter);
		}
		
		public int getCounter() {
			return Integer.parseInt(counter.getText());
		}
		
		/**
		 * This method allows to set counter indicator number.
		 * @param count of enemies on the level.
		 */
		public void setCounter(int count) {
			counter.setText(String.valueOf(count));
		}
		
		/**
		 * This method decrements the kill counter indicator if protagonist
		 * kills the enemy.
		 */
		public void decrementCounter() {
			counter.setText(String.valueOf(Integer.parseInt(counter.getText()) - 1));
			scaleTransition.play();
		}
		
		/**
		 * This method check state of kill counter indicator.
		 * @return true if counter == 0, false if counter != 0.
		 */
		public boolean isNull() {
			if(Integer.parseInt(counter.getText()) == 0) return true;
			else return false;
		}
	}
	
	/**
	 * This class is a pane for labels that show you when you are win
	 * or you are lose. 
	 * @author VladMigura
	 * @version 1.5
	 */
	static class EndGameIndicator extends BorderPane {
		private Label endGameLabel;
		private Label pressEnterLabel;
		ScaleTransition scaleTransition;
		
		public EndGameIndicator(boolean isWon) {
			this.setPrefSize(Main.WIDTH, Main.HEIGHT);
			
			VBox center = new VBox(5);
			
			if(isWon) endGameLabel = new Label("You are WON!!!");
			else endGameLabel = new Label("GAME OVER");
			pressEnterLabel = new Label("PRESS 'ENTER' TO EXIT...");
			
			endGameLabel.setTextFill(Color.RED);
			endGameLabel.setFont(Font.font("CALIBRY", FontWeight.BOLD, 24));
			
			pressEnterLabel.setTextFill(Color.RED);
			pressEnterLabel.setFont(Font.font("CALIBRY", FontWeight.BOLD, 24));
			
			center.getChildren().add(endGameLabel);
			center.getChildren().add(pressEnterLabel);
			center.setAlignment(Pos.CENTER);
			this.setCenter(center);
			
			scaleTransition = new ScaleTransition(Duration.seconds(1.0), center);
			scaleTransition.setToX(2);
			scaleTransition.setToY(2);
			scaleTransition.setAutoReverse(true);
			scaleTransition.setCycleCount(Animation.INDEFINITE);
			scaleTransition.play();
		}
		
		/**
		 * This method set the position of a label. 
		 * @param posX
		 */
		public void setPosition(int posX) {
			this.setTranslateX(posX);
		}
	}
}
