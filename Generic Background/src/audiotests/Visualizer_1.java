package audiotests;

import java.net.URL;
import java.util.ArrayList;

import com.sun.org.apache.xml.internal.security.keys.content.KeyValue;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Visualizer_1 extends Application {

	private MediaPlayer player;

	// private ArrayList<Rectangle> rects = new ArrayList<Rectangle>();

	private ArrayList<ASphere> spheres = new ArrayList<ASphere>();
	
	private Timeline timeline;

	@Override
	public void init() {
		URL u = getClass().getResource("../audios/Yello - Desire.mp3");
		String url = u.toExternalForm();
		player = new MediaPlayer(new Media(url));

		player.setCycleCount(MediaPlayer.INDEFINITE);

		player.setAudioSpectrumThreshold(-90);

		/*
		 * for (int i = 0; i < 128; i++) { Rectangle r = new Rectangle(i * 4 + 2, 2, 2,
		 * 0); r.setFill(Color.BEIGE); rects.add(r);
		 * 
		 * }
		 */

		for(int i = 0;i<8;i++) {
			for (int j = 0;j<16; j++) {
				ASphere s = new ASphere(((i*70)+20),(j*35)+20,5);
				spheres.add(s);
				
			}
		}
		
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				for (int i = 0;i<128;i++) {
					spheres.get(i).setRAD();
				}
			}
			
		}));
		
		
		
		addVisuals();

	}

	public void addVisuals() {
		player.setAudioSpectrumListener(new AudioSpectrumListener() {

			@Override
			public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {

				for (int i = 0; i < magnitudes.length; i++) {
					/*
					 * rects.get(i).setHeight((magnitudes[i] - player.getAudioSpectrumThreshold()) *
					 * 8); rects.get(i).setTranslateY(600-(magnitudes[i] -
					 * player.getAudioSpectrumThreshold()) * 8);
					 */
					
					spheres.get(i).setR(Math.pow((magnitudes[i] - player.getAudioSpectrumThreshold()),0.9));
				}
			}
		});
	}

	@Override
	public void start(Stage stage) {
		Group r = new Group();
		r.getChildren().addAll(spheres);
		Pane pane = new Pane();
		pane.getChildren().addAll(r);
		Scene scene = new Scene(pane, 600, 600, true, SceneAntialiasing.BALANCED);
		scene.setFill(Color.rgb(30, 6, 40));

		stage.setScene(scene);
		stage.setTitle("Audio Visualizer");
		stage.show();

		timeline.play();
		player.play();
	}

	public static void main(String[] args) {
		launch(args);
	}

}