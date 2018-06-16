package levelData;


import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * This class is a pane for blocks in the game.
 * @author VladMigura
 * @version 1.0
 */
public class Block extends Pane{
	public static int BLOCK_SIZE = 100;
	
	private ImageView sprite;
	
	public Block() {
		sprite = new ImageView("Pictures/Blocks.png");
		sprite.setViewport(new Rectangle2D(0, 0, BLOCK_SIZE, BLOCK_SIZE));
		this.getChildren().add(sprite);
	}
	
	/**
	 * This method allows to set position of block.
	 * @param posX
	 * @param posY
	 */
	public void setPosition(int posX, int posY) {
		this.setTranslateX(posX);
		this.setTranslateY(posY);
	}
}
