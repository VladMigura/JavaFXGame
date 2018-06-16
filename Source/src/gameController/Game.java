package gameController;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import application.Main;
import characters.CharacterModel;
import characters.Enemy;
import characters.Protagonist;
import gameController.Indicators.*;
import levelData.Block;
import levelData.Level;
import levelData.Items.Bullet;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;


/**
 * This class is a container for game scene. It show characters and levels. Also
 * it controls the actions of protagonist and enemies.
 * @author VladMigura
 * @version 1.0
 */
public class Game extends AnchorPane{
	private String levelName;
	public int enemyCounter;
	private int enemySpawnCoefficient;
	private boolean endGame = false;
	public boolean isResume = false;
	private int latency = 0;
	
	Level level;
	ImageView background;
	public HealthIndicator soldierHealthIndicator;
	public KillCounterIndicator soldierKillCounterIndicator;
	EndGameIndicator gameOverIndicator;
	EndGameIndicator youAreWonIndicator;
	
	public CharacterModel soldier;
	public static List<Enemy> enemyList;
	public static List<Block> blockList;
	public static List<Bullet> bulletList;
	HashMap<KeyCode, Boolean> keyKeeper = new HashMap<KeyCode, Boolean>();
	
	AnimationTimer timer;
	
	public Scene gameScene;
	
	public Game(){
		this.setPrefSize(Main.WIDTH, Main.HEIGHT);
		gameScene = new Scene(this, Main.WIDTH, Main.HEIGHT);
		
		soldier = new Protagonist();
		enemyList = new ArrayList<Enemy>();
		blockList = new ArrayList<Block>();
		bulletList = new ArrayList<Bullet>();
		level = new Level();
		
		background = new ImageView("Pictures/Background.png");
		background.setFitHeight(Main.HEIGHT);
		background.setFitWidth(level.width);
		
		soldierHealthIndicator = new HealthIndicator(soldier.health);
		soldierKillCounterIndicator = new KillCounterIndicator();
		gameOverIndicator = new EndGameIndicator(false);
		youAreWonIndicator = new EndGameIndicator(true);
		
		timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				update();
			}
		};
		
		AnchorPane.setTopAnchor(soldierHealthIndicator, 20d);
		AnchorPane.setLeftAnchor(soldierHealthIndicator, 120d);
		AnchorPane.setTopAnchor(soldierKillCounterIndicator, 40d + soldierHealthIndicator.HEIGHT);
		AnchorPane.setLeftAnchor(soldierKillCounterIndicator, 120d);
	}	
	
	/**
	 * This method handles events from key presses. 
	 */
	private void eventHandler() {
		gameScene.setOnKeyPressed(event -> {
			if(event.getCode() == KeyCode.ESCAPE && endGame == false) {
				level.saveGame(levelName);
				Main.mainStage.setScene(Main.menuField.menuScene);
				removeGameScene();
			}
			else if(event.getCode() == KeyCode.ENTER && endGame == true) {
				Main.mainStage.setScene(Main.menuField.menuScene);
				removeGameScene();
			}
			else keyKeeper.put(event.getCode(), true);
		});
		
		gameScene.setOnKeyReleased(event -> keyKeeper.put(event.getCode(), false));
		
		soldier.translateXProperty().addListener((obsValue, oldValue, newValue) -> {
			if(newValue.intValue() > Main.WIDTH/2 + Block.BLOCK_SIZE && newValue.intValue() < level.width - Main.WIDTH / 2 - Block.BLOCK_SIZE) {
				this.setLayoutX( - (newValue.intValue() - Main.WIDTH / 2));
				soldierHealthIndicator.setTranslateX(newValue.intValue() - Main.WIDTH / 2 - Block.BLOCK_SIZE);
				soldierKillCounterIndicator.setTranslateX(newValue.intValue() - Main.WIDTH / 2 - Block.BLOCK_SIZE);
			}
		});
	}
	
	/**
	 * This method construct the level and set the difficulty.
	 * Also it add indicators to the game scene.
	 * @param difficulty that you choose in the main menu.
	 */
	public void loadLevel(String difficulty, String levelName) {
		this.levelName = levelName;
		
		level.readLevel(levelName);
		level.construct();
		
		if(isResume) level.loadSave(levelName);
		else {
			this.setLayoutX(-100);
			soldier.health = 100;
			soldier.direction = 1;
			soldier.gravity = 10;
			soldier.canJump = false;
			soldier.setTranslateX(150);
			soldier.setTranslateY(Main.HEIGHT - 300);	
			
			soldierKillCounterIndicator.setTranslateX(0);
			soldierHealthIndicator.setTranslateX(0);
		}
	
		switch(difficulty) {
			case "EASY":
				soldier.setDamage(50);
				enemySpawnCoefficient = 600;
				break;
				
			case "MEDIUM":
				soldier.setDamage(20);
				enemySpawnCoefficient = 500;
				break;
				
			case "HARD":
				soldier.setDamage(10);
				enemySpawnCoefficient = 400;
				break;
		}
		
		endGame = false;
		soldier.isDead = false;
		soldierHealthIndicator.setHealthIndicator(soldier.health);
		soldierKillCounterIndicator.setCounter(enemyCounter);
		
		this.getChildren().add(background);
		this.getChildren().add(soldierHealthIndicator);
		this.getChildren().add(soldierKillCounterIndicator);
		this.getChildren().add(soldier);	
		this.getChildren().addAll(enemyList);
		this.getChildren().addAll(blockList);
		this.getChildren().addAll(bulletList);
	}
	
	/**
	 * This method remove all object from the game scene and stop game timer.
	 */
	private void removeGameScene() {
		if(endGame) {
			if(soldier.isDead) this.getChildren().remove(gameOverIndicator);
			else this.getChildren().remove(youAreWonIndicator);
		}
		
		isResume = false;
		
		this.getChildren().remove(background);
		this.getChildren().remove(soldierHealthIndicator);
		this.getChildren().remove(soldierKillCounterIndicator);
		this.getChildren().remove(soldier);
		this.getChildren().removeAll(blockList);
		this.getChildren().removeAll(enemyList);
		this.getChildren().removeAll(bulletList);
		blockList.clear();
		enemyList.clear();
		bulletList.clear();
		keyKeeper.clear();
		for(Enemy enemy: level.enemies) {
			enemy.onMap = false;
			enemy.health = 100;
			enemy.gravity = 10;
			enemy.canJump = true;
		}
		
		for(Bullet bullet: level.bullets) bullet.onMap = false;
		
		timer.stop();
		System.gc();
	}
	
	/**
	 * This method allows to start the game timer. 
	 */
	public void startTimer() {
		timer.start();
		eventHandler();
	}
	
	/**
	 * This method is called every tact of the game timer and update
	 * position of all objects on the game scene.
	 */
	private void update() {
		enemySpawn();
		protagonistActions();
		enemyActions();
		bulletMotion();
	}
	
	/**
	 * This method describes the logic of protagonist actions.
	 */
	private void protagonistActions() {
		if(keyKeeper.getOrDefault(KeyCode.SPACE, false)) {
			soldierAttack();
		}
		if(keyKeeper.getOrDefault(KeyCode.D, false) && keyKeeper.getOrDefault(KeyCode.A, false)) {
			soldier.movingAnimationRight.stop();
			soldier.movingAnimationLeft.stop();
			if(soldier.direction == 0) soldier.sprite.setViewport(new Rectangle2D(0, 300, soldier.width, soldier.height));
			else soldier.sprite.setViewport(new Rectangle2D(0, 200, soldier.width, soldier.height));
			if(soldier.gravity < 10) soldier.gravity += 1;
			if(soldier.gravity > 0) soldier.moveDown();
			else soldier.moveUp();
			return;
		}
		if(keyKeeper.getOrDefault(KeyCode.W, false)) {
			soldier.jump();
		}
		if(keyKeeper.getOrDefault(KeyCode.D, false) && soldier.getTranslateX() + soldier.width < level.width - 100) {
			soldier.movingAnimationRight.play();
			soldier.moveRight();
			soldier.direction = 1;
		} else if(keyKeeper.getOrDefault(KeyCode.A, false) && soldier.getTranslateX() > 100) {
			soldier.movingAnimationLeft.play();
			soldier.moveLeft();
			soldier.direction = 0;
		} else {
			soldier.movingAnimationRight.stop();
			soldier.movingAnimationLeft.stop();
			if(soldier.direction == 0) soldier.sprite.setViewport(new Rectangle2D(0, 300, soldier.width, soldier.height));
			else soldier.sprite.setViewport(new Rectangle2D(0, 200, soldier.width, soldier.height));
		}
		
		if(soldier.gravity < 10) soldier.gravity += 1;
		if(soldier.gravity > 0) soldier.moveDown();
		else soldier.moveUp();
	}
	
	/**
	 * This method describes the logic of enemy actions.
	 */
	private void enemyActions() {
		for(Enemy enemy: enemyList) {
			if(enemy.health < 1) {
				enemy.onMap = false;
				enemy.movingAnimationRight.stop();
				enemy.movingAnimationLeft.stop();
				this.getChildren().removeAll(enemyList);
				enemy.health = 100;
				enemyList.remove(enemy);
				this.getChildren().addAll(enemyList);
				soldierKillCounterIndicator.decrementCounter();
				if(soldierKillCounterIndicator.isNull()) endGame();
				return;
			}
			
			if(soldier.getPositionX() < enemy.getPositionX() && enemy.getPositionX() - soldier.getPositionX() < 50 && soldier.getTranslateY() + soldier.height == enemy.getTranslateY() + enemy.height) {
				enemy.movingAnimationRight.stop();
				enemy.movingAnimationLeft.stop();
				enemy.attackAnimationLeft.play();
				enemyAttack();
			} else if(soldier.getTranslateX() > enemy.getTranslateX() && soldier.getTranslateX() - (enemy.getTranslateX() + enemy.width) < 0 && soldier.getTranslateY() + soldier.height == enemy.getTranslateY() + enemy.height) {
				enemy.movingAnimationRight.stop();
				enemy.movingAnimationRight.stop();
				enemy.attackAnimationRight.play();
				enemyAttack();
			} else if(enemy.getPositionX() - soldier.getPositionX() > 1 /*&& enemy.getTranslateX() - Soldier.getTranslateX() < 500*/) {
				enemy.attackAnimationRight.stop();
				enemy.attackAnimationLeft.stop();
				enemy.movingAnimationRight.stop();
				enemy.movingAnimationLeft.play();
				enemy.moveLeft();
				enemy.direction = 0;
			} else if (soldier.getPositionX() - enemy.getPositionX() > 1 /*&& Soldier.getTranslateX() - enemy.getTranslateX() < 500*/) {
				enemy.attackAnimationRight.stop();
				enemy.attackAnimationLeft.stop();
				enemy.movingAnimationLeft.stop();
				enemy.movingAnimationRight.play();
				enemy.moveRight();
				enemy.direction = 1;
			} else {
				if(soldier.getTranslateY() + soldier.height == enemy.getTranslateY() + enemy.height) {
					if(enemy.direction == 1) enemy.setTranslateX(enemy.getTranslateX() - 1);
					else enemy.setTranslateX(enemy.getTranslateX() + 1);
					return;
				}

				enemy.attackAnimationRight.stop();
				enemy.attackAnimationLeft.stop();
				enemy.movingAnimationLeft.stop();
				enemy.movingAnimationRight.stop();
			}
			
			if(enemy.gravity < 10) enemy.gravity += 1;
			if(enemy.gravity > 0) enemy.moveDown();
			else enemy.moveUp();
		}
	}
	
	/**
	 * This method spawns the enemy by the enemy spawn coefficient.
	 */
	private void enemySpawn() {
		Random random = new Random();
		int randomValue = random.nextInt(enemySpawnCoefficient);
		
		if(enemyList.size() == level.MAX_COUNT_OF_ENEMIES) return;
		if(randomValue == 1 || randomValue == 2) {
			for(Enemy enemy: level.enemies) {
				if(enemy.onMap) continue;
				enemy.onMap = true;
				
				if(randomValue == 1) enemy.setPosition(0, Main.HEIGHT - 300);
				else enemy.setPosition(level.width - Block.BLOCK_SIZE, Main.HEIGHT - 300);
				
				enemyList.add(enemy);
				this.getChildren().removeAll(enemyList);
				this.getChildren().addAll(enemyList);
				return;
			}
		}
	}
	
	/**
	 * This method calculate damage of enemy to the protagonist.
	 */
	private void enemyAttack() {
		Random random = new Random();
		
		if(random.nextInt(10) == 1) {
			if(soldier.health < 1) {
				if(!soldier.isDead) {
					soldier.isDead = true;
					endGame();
				}
				return;
			}
			soldier.changeHealth(enemyList.get(0).damage);
			soldierHealthIndicator.changeHealthIndicator(enemyList.get(0).damage);
		}
	}
	
	/**
	 * This method describes the logic of protagonist attack. Also
	 * it add the new bullet to the game scene.
	 */
	private void soldierAttack() {
		if(latency < 7) {
			latency++; 
			return;
		}
		else latency = 0;

		for(Bullet bullet: level.bullets) {
			if(bullet.onMap) continue;
			bullet.onMap = true;
			
			bulletList.add(bullet);
			if(soldier.direction == 0) {
				bullet.setPosition((int)soldier.getTranslateX(), (int)(soldier.getTranslateY() + 40));
				bullet.direction = 0;
			}
			else {
				bullet.setPosition((int)(soldier.getTranslateX() + soldier.width), (int)(soldier.getTranslateY() + 40));
				bullet.direction = 1;
			}
			
			this.getChildren().removeAll(bulletList);
			this.getChildren().addAll(bulletList);
			return;
		}
	}
	
	/**
	 * This method describes the logic of bullet motion and damages enemies,
	 * when bullet intersects them.
	 */
	private void bulletMotion() {
		for(Bullet bullet: bulletList) {
			if(bullet.direction == 0) bullet.moveLeft();
			else bullet.moveRight();
			
			if(bullet.getTranslateX() < 0 || bullet.getTranslateX() > level.width) {
				bullet.onMap = false;
				this.getChildren().removeAll(bulletList);
				bulletList.remove(bullet);
				this.getChildren().addAll(bulletList);
				return;
			}
			
			for(Block block: blockList) {
				if(bullet.getBoundsInParent().intersects(block.getBoundsInParent())) {
					bullet.onMap = false;
					this.getChildren().removeAll(bulletList);
					bulletList.remove(bullet);
					this.getChildren().addAll(bulletList);
					return;
				}
			}
			
			for(Enemy enemy: enemyList) {
				if(bullet.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
					bullet.onMap = false;
					this.getChildren().removeAll(bulletList);
					bulletList.remove(bullet);
					this.getChildren().addAll(bulletList);
					enemy.changeHealth(soldier.damage);
					return;
				}
			}
 		}
	}
	
	/**
	 * This method determines the actions, when the game is over.
	 */
	private void endGame() {
		int xPosition;
		
		endGame = true;
		for(Enemy enemy: enemyList) {
			enemy.movingAnimationRight.stop();
			enemy.movingAnimationLeft.stop();
			enemy.attackAnimationRight.stop();
			enemy.attackAnimationLeft.stop();
			if(enemy.direction == 0) enemy.sprite.setViewport(new Rectangle2D(50, 200, enemy.width, enemy.height));
			else enemy.sprite.setViewport(new Rectangle2D(0, 200, enemy.width, enemy.height));
		}
		timer.stop();
		
		soldier.movingAnimationRight.stop();
		soldier.movingAnimationLeft.stop();
		if(soldier.direction == 0) soldier.sprite.setViewport(new Rectangle2D(0, 300, soldier.width, soldier.height));
		else soldier.sprite.setViewport(new Rectangle2D(0, 200, soldier.width, soldier.height));
		
		if(soldier.getTranslateX() < Main.WIDTH / 2) xPosition = 0;
		else if(soldier.getTranslateX() > level.width - Main.WIDTH / 2) xPosition = level.width - Main.WIDTH;
		else xPosition = (int)(soldier.getTranslateX() - Main.WIDTH / 2);
		
		if(soldier.isDead) {
			gameOverIndicator.setPosition(xPosition);
			this.getChildren().add(gameOverIndicator);
		}
		else {
			youAreWonIndicator.setPosition(xPosition);
			this.getChildren().add(youAreWonIndicator);
		}
		
		File file = new File("src/Files/LevelData/Saves/" + levelName + ".txt");
		if(file.exists()) file.delete();
	}
}
