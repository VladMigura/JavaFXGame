package characters;

import gameController.SpriteAnimation;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * This abstract class describes all parameters and methods,
 * that used to control character.
 * @author VladMigura
 * @version 1.3
 */
public abstract class CharacterModel extends Pane{
	public ImageView sprite;
	public int width;
	public int height;
	protected int movingColumnsNum;
	protected int attackColumnsNum;
	protected double movingAnimationDuration;
	protected double attackAnimationDuration;
	
	public int direction = 1;
	public boolean canJump = false;
	public boolean isDead = false;
	public int gravity;
	public double speed;
	public int health;
	public int damage;
	
	public SpriteAnimation movingAnimationRight;
	public SpriteAnimation movingAnimationLeft;
	public SpriteAnimation attackAnimationRight;
	public SpriteAnimation attackAnimationLeft;
	
	/**
	 * 	This method allows to move the character on the game 
	 * scene to the right side. 
	 */
	public abstract void moveRight();
	
	/**
	 * 	This method allows to move the character on the game 
	 * scene to the left side. 
	 */
	public abstract void moveLeft();
	
	/**
	 * 	This method allows to move the character on the game 
	 * scene to the up side. 
	 */
	public abstract void moveUp();
	
	/**
	 * 	This method allows to move the character on the game 
	 * scene to the down side. 
	 */
	public abstract void moveDown();
	
	/**
	 * 	This method allows the character to jump.
	 */
	public abstract void jump();
	
	/**
	 * This method allows to change the health of character. 
	 * @param damage to the character.
	 */
	public abstract void changeHealth(double damage);
	
	/**
	 * This method allows to get the center of characters texture.
	 * @return value that describe the center of characters texture.
	 */
	public abstract int getPositionX();
	
	/**
	 * This method set the new damage of character.
	 * @param newDamage 
	 */
	public void setDamage(int newDamage) {}
}
