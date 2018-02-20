package audiotests;

import java.net.URL;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Visualizer_1 extends Application {

	private MediaPlayer player;

	private ArrayList<Rectangle> rects = new ArrayList<Rectangle>();

	@Override
	public void init() {
		URL u = getClass().getResource("../audios/JAEGER - Until Dawn [Bass Boosted].mp3");
		String url = u.toExternalForm();
		player = new MediaPlayer(new Media(url));

		player.setCycleCount(MediaPlayer.INDEFINITE);

		player.setAudioSpectrumThreshold(-90);

		for (int i = 0; i < 128; i++) {
			Rectangle r = new Rectangle(i * 4 + 2, 2, 2, 0);
			r.setFill(Color.BEIGE);
			rects.add(r);

		}

		addVisuals();

	}

	public void addVisuals() {
		player.setAudioSpectrumListener(new AudioSpectrumListener() {

			@Override
			public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {

				for (int i = 0; i < magnitudes.length; i++) {
					rects.get(i).setHeight((magnitudes[i] - player.getAudioSpectrumThreshold()) * 8);
				}
			}
		});
	}

	@Override
	public void start(Stage stage) {
		Group r = new Group();
		r.getChildren().addAll(rects);
		Pane pane = new Pane();
		pane.getChildren().addAll(r);
		Scene scene = new Scene(pane, 600, 600, true, SceneAntialiasing.BALANCED);
		scene.setFill(Color.rgb(30, 6, 40));

		stage.setScene(scene);
		stage.setTitle("Audio Visualizer");
		stage.show();

		player.play();
	}

	public static void main(String[] args) {
		launch(args);
	}

}