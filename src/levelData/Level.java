package levelData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import application.Main;
import characters.Enemy;
import gameController.Game;
import levelData.Items.Bullet;

/**
 * This class allows to create the game level and
 * determines count of different objects on game scene. 
 * @author VladMigura
 * @version 1.5
 */
public class Level {
	private final int MAX_COUNT_OF_BLOCKS = 60;
	public final int MAX_COUNT_OF_ENEMIES = 15;
	public final int MAX_COUNT_OF_BULLETS = 40;
	public int width;
	public int height = 5;
	private String data[];
	private Block blocks[];
	public Enemy enemies[];
	public Bullet bullets[];
	
	public Level() {
		data = new String[height];
		blocks = new Block[MAX_COUNT_OF_BLOCKS];
		enemies = new Enemy[MAX_COUNT_OF_ENEMIES];
		bullets = new Bullet[MAX_COUNT_OF_BULLETS];
		
		for(int i = 0; i < MAX_COUNT_OF_BLOCKS; i++) blocks[i] = new Block();
		for(int i = 0; i < MAX_COUNT_OF_ENEMIES; i++) enemies[i] = new Enemy();
		for(int i = 0; i < MAX_COUNT_OF_BULLETS; i++) bullets[i] = new Bullet();
		for(int i = 0; i < height; i++) data[i] = new String();
	}
	
	public static boolean checkAccess(String levelName) {
		Scanner fileReader;
		
		try {
			fileReader = new Scanner(new File("src/Files/LevelData/Maps/" + levelName + ".txt"));
			if(fileReader.nextLine().equals("ACCESS = 1")) {
				fileReader.close();
				return true;
			}
			else {
				fileReader.close();
				return false;
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean checkSaveFile(String levelName) {
		File file = new File("src/Files/LevelData/Saves/" + levelName + ".txt");
		if(file.exists()) return true; 
		return false;
	}
	
	public void readLevel(String levelName) {
		Scanner fileReader;
		
		try {
			fileReader = new Scanner(new File("src/Files/LevelData/Maps/" + levelName + ".txt"));
			fileReader.nextLine();
			Main.gameField.enemyCounter = Integer.parseInt(fileReader.nextLine().substring(19));
			
			fileReader.nextLine();
			
			for(int i = 0; fileReader.hasNext(); i++) data[i] = fileReader.nextLine();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		width = Block.BLOCK_SIZE * data[0].length();
	}
	
	public void loadSave(String levelName) {
		Scanner fileReader;
		int counter = 0;
		String buffer;
		
		try {
			fileReader = new Scanner(new File("src/Files/LevelData/Saves/" + levelName + ".txt"));
			Main.gameField.setLayoutX(Double.parseDouble(fileReader.nextLine().substring(15)));
			Main.gameField.soldierKillCounterIndicator.setTranslateX(Double.parseDouble(fileReader.nextLine().substring(19)));
			Main.gameField.soldierHealthIndicator.setTranslateX(Double.parseDouble(fileReader.nextLine().substring(21)));
			Main.gameField.enemyCounter = Integer.parseInt(fileReader.nextLine().substring(19));
			
			fileReader.nextLine();
			fileReader.nextLine();
			fileReader.nextLine();
			
			Main.gameField.soldier.health = Integer.parseInt(fileReader.nextLine().substring(9));
			Main.gameField.soldier.direction = Integer.parseInt(fileReader.nextLine().substring(12));
			Main.gameField.soldier.gravity = Integer.parseInt(fileReader.nextLine().substring(10));
			Main.gameField.soldier.canJump = Boolean.parseBoolean(fileReader.nextLine().substring(10));
			Main.gameField.soldier.setTranslateX(Double.parseDouble(fileReader.nextLine().substring(7)));
			Main.gameField.soldier.setTranslateY(Double.parseDouble(fileReader.nextLine().substring(7)));
			
			fileReader.nextLine();
			fileReader.nextLine();
			buffer = fileReader.nextLine();
			
			while(!buffer.equals("[BULLETS]")) {
				enemies[counter].health = Integer.parseInt(buffer.substring(9));
				enemies[counter].direction = Integer.parseInt(fileReader.nextLine().substring(12));
				enemies[counter].gravity = Integer.parseInt(fileReader.nextLine().substring(10));
				enemies[counter].canJump = Boolean.parseBoolean(fileReader.nextLine().substring(10));
				enemies[counter].setTranslateX(Double.parseDouble(fileReader.nextLine().substring(7)));
				enemies[counter].setTranslateY(Double.parseDouble(fileReader.nextLine().substring(7)));
				enemies[counter].onMap = true;
				Game.enemyList.add(enemies[counter]);
				counter++;
				fileReader.nextLine();
				buffer = fileReader.nextLine();
			}
			
			counter = 0;
			while(fileReader.hasNext()) {
				bullets[counter].direction = Integer.parseInt(fileReader.nextLine().substring(12));
				bullets[counter].setTranslateX(Double.parseDouble(fileReader.nextLine().substring(7)));
				bullets[counter].setTranslateY(Double.parseDouble(fileReader.nextLine().substring(7)));
				bullets[counter].onMap = true;
				Game.bulletList.add(bullets[counter]);
				counter++;
				fileReader.nextLine();
			}
			fileReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void saveGame(String levelName) {
		try(FileWriter fileWriter = new FileWriter(new File("src/Files/LevelData/Saves/" + levelName + ".txt"), false)){
			fileWriter.write("SCENE_LAYOUT = " + (int)Main.gameField.getLayoutX() + "\r\n");
			fileWriter.write("KILL_COUNTER_IND = " + (int)Main.gameField.soldierKillCounterIndicator.getTranslateX() + "\r\n");
			fileWriter.write("HEALTH_COUNTER_IND = " + (int)Main.gameField.soldierHealthIndicator.getTranslateX() + "\r\n");
			fileWriter.append("COUNT_OF_ENEMIES = " + Main.gameField.soldierKillCounterIndicator.getCounter() + "\r\n");
			fileWriter.append("\r\n");
			
			fileWriter.append("[PROTAGONIST]\r\n");
			fileWriter.append("\r\n");
			fileWriter.append("HEALTH = " + Main.gameField.soldier.health + "\r\n");
			fileWriter.append("DIRECTION = " + Main.gameField.soldier.direction + "\r\n");
			fileWriter.append("GRAVITY = " + Main.gameField.soldier.gravity + "\r\n");
			fileWriter.append("CANJUMP = " + Main.gameField.soldier.canJump + "\r\n");
			fileWriter.append("PosX = " + (int)Main.gameField.soldier.getTranslateX() + "\r\n");
			fileWriter.append("PosY = " + (int)Main.gameField.soldier.getTranslateY() + "\r\n");
			fileWriter.append("\r\n");
			
			fileWriter.append("[ENEMIES]\r\n");
			for(Enemy enemy: Game.enemyList) {
				fileWriter.append("HEALTH = " + enemy.health + "\r\n");
				fileWriter.append("DIRECTION = " + enemy.direction + "\r\n");
				fileWriter.append("GRAVITY = " + enemy.gravity + "\r\n");
				fileWriter.append("CANJUMP = " + enemy.canJump + "\r\n");
				fileWriter.append("PosX = " + (int)enemy.getTranslateX() + "\r\n");
				fileWriter.append("PosY = " + (int)enemy.getTranslateY() + "\r\n");
				fileWriter.append("\r\n");
			}
			
			fileWriter.append("[BULLETS]\r\n");
			for(Bullet bullet: Game.bulletList) {
				fileWriter.append("DIRECTION = " + bullet.direction + "\r\n");
				fileWriter.append("PosX = " + (int)bullet.getTranslateX() + "\r\n");
				fileWriter.append("PosY = " + (int)bullet.getTranslateY() + "\r\n");
				fileWriter.append("\r\n");
			}
			fileWriter.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteSave(String levelName) {
		
	}
	
	/**
	 * This method construct the level and determines the position for each block.
	 */
	public void construct() {
		int counter = 0;
		
		for(int i = 0; i < height; i++)
			for(int j = 0; j < data[i].length(); j++) {
				switch(data[i].charAt(j)) {
					case '0':
						break;
						
					case '1':
						blocks[counter].setPosition(Block.BLOCK_SIZE * j, (int)(Main.HEIGHT - Block.BLOCK_SIZE * i - Block.BLOCK_SIZE));
						Game.blockList.add(blocks[counter]);
						counter++;
						break;
				}
			}
	}
}
