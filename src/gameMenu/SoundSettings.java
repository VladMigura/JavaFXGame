package gameMenu;

import java.io.File;

import application.Main;
import gameMenu.Menu.MenuButton;
import javafx.animation.Animation;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * This class is a pane for sound settings menu.
 * @author VladMigura
 * @version 1.0
 */
public class SoundSettings extends AnchorPane{
	MenuButton returnButton;
	MenuButton playStopButton;
	
	public MediaPlayer player;
	private Slider volume;
	private boolean state = false;
	
	File file;
	
	RadioButton rbMenuMusic;
	RadioButton rbLezginka;
	RadioButton rbLezginka2;
	RadioButton rbKeltskaya;
	RadioButton rbVorovskaya;
	RadioButton rbBoevaya;
	RadioButton rbZadornaya;
	RadioButton rbVeter;
	RadioButton rbSyabri;
	
	ToggleGroup tgMusicButtons;
	
	SoundSettings(){
		this.setPrefSize(Main.WIDTH, Main.HEIGHT);
		
		HBox controlPanel = new HBox(10);
		VBox musicPanel = new VBox(10);
		returnButton = new MenuButton("ÍÀÇÀÄ");
		playStopButton = new MenuButton("Play/Stop");
		volume = new Slider(0, 1.0, 0.5);
		controlPanel.getChildren().addAll(playStopButton, volume);
		
		rbMenuMusic = new RadioButton("MAINMENU");
		rbLezginka = new RadioButton("ËÅÇÃÈÍÊÀ");
		rbLezginka2 = new RadioButton("ËÅÇÃÈÍÊÀ 2");
		rbKeltskaya = new RadioButton("ÊÅËÜÒÑÊÀß");
		rbVorovskaya = new RadioButton("ÂÎÐÎÂÑÊÀß");
		rbBoevaya = new RadioButton("ÁÎÅÂÀß");
		rbZadornaya = new RadioButton("ÇÀÄÎÐÍÀß");
		rbVeter = new RadioButton("ÂÅÒÅÐ Ñ ÌÎÐß ÄÓË");
		rbSyabri = new RadioButton("ÑßÁÐÛ");
		
		tgMusicButtons = new ToggleGroup();
		
		rbMenuMusic.setUserData("MenuMusic.mp3");
		rbLezginka.setUserData("Lezginka.mp3");
		rbLezginka2.setUserData("Lezginka2.mp3");
		rbKeltskaya.setUserData("Keltskaya.mp3");
		rbVorovskaya.setUserData("Vorovskaya.mp3");
		rbBoevaya.setUserData("Boevaya.mp3");
		rbZadornaya.setUserData("Zadornaya.mp3");
		rbVeter.setUserData("Veter.mp3");
		rbSyabri.setUserData("Syabri.mp3");
		
		rbMenuMusic.setTextFill(Color.WHITE);
		rbMenuMusic.setFont(Font.font("CALIBRY", FontWeight.BOLD, 16));
		rbLezginka.setTextFill(Color.WHITE);
		rbLezginka.setFont(Font.font("CALIBRY", FontWeight.BOLD, 16));
		rbLezginka2.setTextFill(Color.WHITE);
		rbLezginka2.setFont(Font.font("CALIBRY", FontWeight.BOLD, 16));
		rbKeltskaya.setTextFill(Color.WHITE);
		rbKeltskaya.setFont(Font.font("CALIBRY", FontWeight.BOLD, 16));
		rbVorovskaya.setTextFill(Color.WHITE);
		rbVorovskaya.setFont(Font.font("CALIBRY", FontWeight.BOLD, 16));
		rbBoevaya.setTextFill(Color.WHITE);
		rbBoevaya.setFont(Font.font("CALIBRY", FontWeight.BOLD, 16));
		rbZadornaya.setTextFill(Color.WHITE);
		rbZadornaya.setFont(Font.font("CALIBRY", FontWeight.BOLD, 16));
		rbVeter.setTextFill(Color.WHITE);
		rbVeter.setFont(Font.font("CALIBRY", FontWeight.BOLD, 16));
		rbSyabri.setTextFill(Color.WHITE);
		rbSyabri.setFont(Font.font("CALIBRY", FontWeight.BOLD, 16));
		
		rbMenuMusic.setToggleGroup(tgMusicButtons);
		rbLezginka.setToggleGroup(tgMusicButtons);
		rbLezginka2.setToggleGroup(tgMusicButtons);
		rbKeltskaya.setToggleGroup(tgMusicButtons);
		rbVorovskaya.setToggleGroup(tgMusicButtons);
		rbBoevaya.setToggleGroup(tgMusicButtons);
		rbZadornaya.setToggleGroup(tgMusicButtons);
		rbVeter.setToggleGroup(tgMusicButtons);
		rbSyabri.setToggleGroup(tgMusicButtons);
		
		musicPanel.getChildren().add(rbMenuMusic);
		musicPanel.getChildren().add(rbLezginka);
		musicPanel.getChildren().add(rbLezginka2);
		musicPanel.getChildren().add(rbKeltskaya);
		musicPanel.getChildren().add(rbVorovskaya);
		musicPanel.getChildren().add(rbBoevaya);
		musicPanel.getChildren().add(rbZadornaya);
		musicPanel.getChildren().add(rbVeter);
		musicPanel.getChildren().add(rbSyabri);
		
		rbMenuMusic.fire();
		
		file = new File("src/Files/Music/MenuMusic.mp3");
		player = new MediaPlayer(new Media(file.toURI().toString()));

		player.setVolume(volume.getValue());
		player.setCycleCount(Animation.INDEFINITE);
		
		
		volume.valueProperty().addListener((obsValue, oldValue, newValue) -> {
			player.setVolume((double)newValue);
		});
		
		playStopButton.setOnMouseClicked(event -> {
			if(state) {
				player.stop();
				state = false;
			}
			else {
				setMusic(tgMusicButtons.getSelectedToggle().getUserData().toString());
				player.play();
				state = true;
			}
		});
		
		tgMusicButtons.selectedToggleProperty().addListener((obsValue, oldValue, newValue) -> {
			if(state) {
				player.stop();
				setMusic(newValue.getUserData().toString());
				player.play();
			}
		});
		
		AnchorPane.setBottomAnchor(returnButton, 20d);
		AnchorPane.setLeftAnchor(returnButton, 20d);
		AnchorPane.setTopAnchor(controlPanel, 20d);
		AnchorPane.setLeftAnchor(controlPanel, 20d);
		AnchorPane.setTopAnchor(musicPanel, 100d);
		AnchorPane.setLeftAnchor(musicPanel, 20d);
		
		this.getChildren().add(returnButton);
		this.getChildren().add(controlPanel);
		this.getChildren().add(musicPanel);
	}
	
	private void setMusic(String fileName) {
		file = new File("src/Files/Music/" + fileName);
		player = new MediaPlayer(new Media(file.toURI().toString()));
	}
}
