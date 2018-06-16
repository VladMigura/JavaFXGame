package gameController;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * This class creates sprite animation for characters. You need to pass sprite
 * image of the character to the constructor. Also you need to specify the width
 * and height of the characters frame.
 * @author VladMigura
 * @version 1.0
 */
public class SpriteAnimation extends Transition{
	public ImageView sprite;
	private int columns;
	private int offsetY;
	private int width;
	private int height;
	
	public SpriteAnimation(ImageView sprite, 
							int width, 
							int height, 
							int columns, 
							double animationDuration, 
							int offsetY){
		this.sprite = sprite;
		this.width = width;
		this.height = height;
		this.columns = columns;
		this.offsetY = offsetY;
		setCycleDuration(Duration.seconds(animationDuration));
		setInterpolator(Interpolator.LINEAR);
	}
	
	/**
	 * 	This method calculates the frame number of animation. For example
	 * State = 0.76, Columns = 4. Its mean that this method will select the fourth frame. 
	 * @param State can take a value from 0 to 1
	 */
	protected void interpolate(double state) {
		int index = 0;
		double segment = 1 / (double)columns;
		
		for(int i = 0; i < columns; i++) {
			if(state <= segment * (i + 1)) {
				index = i;
				break;
			}
		}
		
		int x = index * width;
		int y = offsetY;
		
		sprite.setViewport(new Rectangle2D(x, y, width, height));
	}
}
