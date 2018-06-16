package characters;

import gameController.Game;
import gameController.SpriteAnimation;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import levelData.Block;

/**
 * This class is a pane for protagonist. It includes the parameters like width,
 * height, direction etc and methods that describe the movements.
 * @author VladMigura
 * @version 1.0
 */
public class Protagonist extends CharacterModel{
	public Protagonist(){
		super.sprite = new ImageView("Pictures/CharacterSprites.png");
		super.width = 90;
		super.height = 100;
		super.movingColumnsNum = 6;
		super.movingAnimationDuration = 0.6;
		
		super.gravity = 10;
		super.speed = 7;
		super.health = 100;
		
		super.movingAnimationRight = new SpriteAnimation(this.sprite, width, height, movingColumnsNum, movingAnimationDuration, 0);
		super.movingAnimationLeft = new SpriteAnimation(this.sprite, width, height, movingColumnsNum, movingAnimationDuration, 100);

		
		super.movingAnimationRight.setCycleCount(Transition.INDEFINITE);
		super.movingAnimationLeft.setCycleCount(Transition.INDEFINITE);
		
		super.sprite.setViewport(new Rectangle2D(0, 200, width, height));
		this.getChildren().add(sprite);
	}

	@Override
	public void moveLeft() {
		for(int i = 0; i < speed; i++) {
			for(Block block: Game.blockList) {
				if(this.getBoundsInParent().intersects(block.getBoundsInParent())) {
					if(this.getTranslateX() == block.getTranslateX() + Block.BLOCK_SIZE) {
						this.setTranslateX(this.getTranslateX() + 1);
						return;
					}
				}
			}
			this.setTranslateX(this.getTranslateX() - 1);
		}
	}

	@Override
	public void moveRight() {
		for(int i = 0; i < speed; i++) {
			for(Block block: Game.blockList) {
				if(this.getBoundsInParent().intersects(block.getBoundsInParent())) {
					if(this.getTranslateX() + this.width == block.getTranslateX()) {
						this.setTranslateX(this.getTranslateX() - 1);
						return;
					}
				}
			}
			this.setTranslateX(this.getTranslateX() + 1);
		}
	}
	
	@Override
	public void moveUp() {
		for(int i = 0; i< Math.abs(gravity); i++) {
			for(Block block: Game.blockList) {
				if(this.getBoundsInParent().intersects(block.getBoundsInParent())) {
					if(this.getTranslateY() == block.getTranslateY() + Block.BLOCK_SIZE) {
						this.setTranslateY(this.getTranslateY() + 1);
						gravity = 10;
						return;
					}
				}
			}
			this.setTranslateY(this.getTranslateY() - 1);
		}
	}
	
	@Override
	public void moveDown() {
		for(int i = 0; i<gravity; i++) {
			for(Block block: Game.blockList) {
				if(this.getBoundsInParent().intersects(block.getBoundsInParent())) {
					if(this.getTranslateY() + this.height == block.getTranslateY()) {
						this.setTranslateY(this.getTranslateY() - 1);
						canJump = true;
						return;
					}
				}
			}
			this.setTranslateY(this.getTranslateY() + 1);
		}
	}
	
	@Override
	public void jump() {
		if(canJump) {
			gravity -= 30;
			canJump = false;
		}
	}
	
	@Override
	public void changeHealth(double damage) {
		health -= damage;
	}
	
	@Override
	public int getPositionX() {
		return (int)(this.getTranslateX() + this.width / 2);
	}
	
	@Override
	public void setDamage(int newDamage) {
		damage = newDamage;
	}
}
