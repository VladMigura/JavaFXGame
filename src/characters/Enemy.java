package characters;

import java.util.Random;

import gameController.Game;
import gameController.SpriteAnimation;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import levelData.Block;

public class Enemy extends CharacterModel{
	private final int ATTACK_WIDTH = 80;
	public boolean onMap = false;
	
	public Enemy(){
		Random random = new Random();
		super.speed = random.nextDouble() * 4 + 1;
		super.movingAnimationDuration = 1.675 - 0.22 * speed;
		
		super.sprite = new ImageView("Pictures/EnemySprites.png");
		super.width = 50;
		super.height = 100;
		super.movingColumnsNum = 8;
		super.attackColumnsNum = 6;
		super.attackAnimationDuration = 1.0;
		
		super.gravity = 10;
		super.health = 100;
		super.damage = 1;
		
		super.movingAnimationRight = new SpriteAnimation(this.sprite, width, height, movingColumnsNum, movingAnimationDuration, 0);
		super.movingAnimationLeft = new SpriteAnimation(this.sprite, width, height, movingColumnsNum, movingAnimationDuration, 100);
		super.attackAnimationRight = new SpriteAnimation(this.sprite, ATTACK_WIDTH, height, attackColumnsNum, attackAnimationDuration, 300);
		super.attackAnimationLeft = new SpriteAnimation(this.sprite, ATTACK_WIDTH, height, attackColumnsNum, attackAnimationDuration, 400);
		
		super.movingAnimationRight.setCycleCount(Transition.INDEFINITE);
		super.movingAnimationLeft.setCycleCount(Transition.INDEFINITE);
		super.attackAnimationRight.setCycleCount(Transition.INDEFINITE);
		super.attackAnimationLeft.setCycleCount(Transition.INDEFINITE);
		
		super.sprite.setViewport(new Rectangle2D(0, 100, width, height));
		this.getChildren().add(sprite);
	}
	
	public void setPosition(int posX, int posY) {
		this.setTranslateX(posX);
		this.setTranslateY(posY);
	}
	
	@Override
	public void moveLeft() {
		for(double i = 0; i < speed; i += 0.1) {
			for(Block block: Game.blockList) {
				if(this.getBoundsInParent().intersects(block.getBoundsInParent())) {
					if(this.getTranslateX() < block.getTranslateX() + Block.BLOCK_SIZE) {
						this.setTranslateX(this.getTranslateX() + 1);
						jump();
						return;
					}
				}
			}
			this.setTranslateX(this.getTranslateX() - 0.1);
		}
	}

	@Override
	public void moveRight() {
		for(double i = 0; i < speed; i += 0.1) {
			for(Block block: Game.blockList) {
				if(this.getBoundsInParent().intersects(block.getBoundsInParent())) {
					if(this.getTranslateX() + this.width > block.getTranslateX()) {
						this.setTranslateX(this.getTranslateX() - 1);
						this.jump();
						return;
					}
				}
			}
			this.setTranslateX(this.getTranslateX() + 0.1);
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
	public String toString() {
		String State;
		State = "Health: " + String.valueOf(this.health) + " ";
		State += "Gravity: " + String.valueOf(this.gravity) + " ";
		State += "onMap: " + String.valueOf(this.onMap) + " ";
		State += "canJump: " + String.valueOf(this.canJump) + " ";
		
		return State;
	}
}
