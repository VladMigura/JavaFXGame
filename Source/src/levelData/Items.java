package levelData;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * This class contain all class of item, that used in the game.
 * @author VladMigura
 * @version 1.5
 */
public class Items {
	
	/**
	 * This class is the pane for bullet. It describes
	 * different parameters such as radius, speed, etc.
	 * @author VladMigura
	 * @version 1.5
	 */
	public static class Bullet extends Pane {
		private final int RADIUS = 3;
		private final int SPEED = 20;
		public boolean onMap = false;
		public int direction;
		
		public Bullet() {
			Circle bullet = new Circle(RADIUS, Color.RED);
			
			this.getChildren().add(bullet);
		}
		
		/**
		 * This method allows to set the position of bullet on the screen. 
		 * @param posX
		 * @param posY
		 */
		public void setPosition(int posX, int posY) {
			this.setTranslateX(posX);
			this.setTranslateY(posY);
		}
		
		/**
		 * This method moves the bullet to the right side of game scene.
		 */
		public void moveRight() {
			for(int i = 0; i < SPEED; i++) {
				this.setTranslateX(this.getTranslateX() + 1);
			}
		}
		
		/**
		 * This method moves the bullet to the left side of game scene.
		 */
		public void moveLeft() {
			for(int i = 0; i < SPEED; i++) { 
				this.setTranslateX(this.getTranslateX() - 1);
			}
		}
	}
}
