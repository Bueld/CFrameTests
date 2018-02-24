package audiotests;

import java.net.URL;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;



public class Visualizer_1 extends Application {

	private MediaPlayer player;

	// private ArrayList<Rectangle> rects = new ArrayList<Rectangle>();

	private ArrayList<ASphere> spheres = new ArrayList<ASphere>();

	private Timeline timeline;
	private Button play;

	private Image pl;
	private Image pa;
	private ImageView view;

	private Slider vol;

	private Slider time;

	private RotateTransition rot;
	
	private Slider speed;
	
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

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 16; j++) {
				ASphere s = new ASphere(((i * 70) + 20), (j * 35) + 20, 5);
				spheres.add(s);

			}
		}

		time = new Slider();
		time.setMin(0);
		time.setMax(player.getMedia().getDuration().toSeconds());
		time.setPrefSize(280, 16);
		time.setValue(0);
		time.setTranslateX(20);
		time.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				player.seek((Duration.seconds(time.getValue())));

			}
		});
		time.setOnMouseDragged(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				player.seek((Duration.seconds(time.getValue())));
			}

		});

		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				for (int i = 0; i < 128; i++) {
					spheres.get(i).setRAD();
				}

				if (!time.isPressed() && !time.isHover()) {
					time.setValue((player.getCurrentTime().toSeconds()));
				}
				if (time.isHover()) {
					time.setValue((player.getCurrentTime().toSeconds()));
				}

			}

		}));

		addVisuals();

		vol = new Slider();
		vol.setMin(0);
		vol.setMax(100);
		vol.setPrefWidth(240);
		vol.setPrefHeight(16);
		vol.setValue(66);
		vol.valueProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				player.setVolume(Double.parseDouble(newValue.toString()) / 100);
			}
		});

		pa = new Image(getClass().getResourceAsStream("../img/pause.png"));
		pl = new Image(getClass().getResourceAsStream("../img/play.png"));
		view = new ImageView(pa);

		play = new Button();
		play.setGraphic(view);
		play.setDefaultButton(true);
		play.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (player.getStatus() == Status.PLAYING) {
					player.pause();
					timeline.pause();
					view.setImage(pl);
					play.setGraphic(view);
				} else {
					player.play();
					timeline.play();
					view.setImage(pa);
					play.setGraphic(view);

				}

			}
		});
		
		
		
		speed = new Slider();
		speed.setMin(0);
		speed.setMax(60);
		speed.setPrefSize(120, 16);
		speed.valueProperty().addListener(new ChangeListener() {
			
			

			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				rot.setDuration(Duration.seconds(speed.getValue()));
				
			}
		});

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

					spheres.get(i).setR(Math.pow((magnitudes[i] - player.getAudioSpectrumThreshold())+1, 1.05) * 2);
				}
			}
		});
	}

	@Override
	public void start(Stage stage) {
		Group r = new Group();
		r.getChildren().addAll(spheres);
		Pane pane = new Pane();
		pane.getChildren().addAll(r, play, vol, time, speed);
		pane.setBackground(Background.EMPTY);

		Scene scene = new Scene(pane, 600, 600, true, SceneAntialiasing.BALANCED);
		scene.setFill(Color.rgb(30, 6, 40));

		stage.setScene(scene);
		stage.setTitle("Audio Visualizer");

		stage.widthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				/*
				 * for(int i = 0;i<8;i++) { for (int j = 0;j<16; j++) {
				 * spheres.get((i*16)+j).setTranslateX(newValue.doubleValue()/9*(i+1));
				 * 
				 * } }
				 */

				for (int i = 0; i < 128; i++) {
					//spheres.get(i).setTranslateX(newValue.doubleValue() * Math.random());
					// spheres.get(i).setTranslateZ(200 * Math.random() - 50);
					/*
					spheres.get(127-i).setTranslateX(Math.cos(newValue.doubleValue()/360 *(i))*100*i/32 + newValue.doubleValue()/2);
					spheres.get(127-i).setTranslateY(Math.sin(stage.getWidth()/360*(i) )*100*i/32+ stage.getWidth()/2);
					*/
					
					spheres.get(127-i).setTranslateX(4*i*Math.cos(i/(2*Math.PI*Math.PI)*5)+newValue.doubleValue()/2);
					spheres.get(127-i).setTranslateY(4*i*Math.sin(i/(2*Math.PI*Math.PI)*5)+stage.getHeight()/2);
				}
				play.setTranslateX(newValue.doubleValue() / 2 - play.getWidth() / 2);
				vol.setTranslateX(play.getTranslateX()+play.getWidth()+20);

				vol.setPrefWidth(newValue.doubleValue()-vol.getTranslateX()-30);
				time.setPrefWidth(play.getTranslateX() - 20 - time.getTranslateX());
			}

		});

		stage.heightProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				/*
				 * for(int i = 0;i<8;i++) { for (int j = 0;j<16; j++) {
				 * spheres.get((i*16)+j).setTranslateY(newValue.doubleValue()/18*(j+1));
				 * 
				 * } }
				 */

				for (int i = 0; i < 128; i++) {
					//spheres.get(i).setTranslateY(newValue.doubleValue() * Math.random());
					// spheres.get(i).setTranslateZ(200 * Math.random() - 50);
					/*
					spheres.get(127-i).setTranslateX(Math.cos(stage.getHeight()/360*(i))*100*i/32 + stage.getHeight()/2);
					spheres.get(127-i).setTranslateY(Math.sin(newValue.doubleValue()/360*(i))*100*i/32 + newValue.doubleValue()/2);
					*/
					
					spheres.get(127-i).setTranslateX(4*i*Math.cos(i/(2*Math.PI*Math.PI)*5)+stage.getWidth()/2);
					spheres.get(127-i).setTranslateY(4*i*Math.sin(i/(2*Math.PI*Math.PI)*5)+newValue.doubleValue()/2);
				}

				play.setTranslateY(newValue.doubleValue() - 60 - play.getHeight());
				vol.setTranslateY(newValue.doubleValue() - 60 - vol.getHeight()-(play.getHeight()-vol.getHeight())/2);
				time.setTranslateY(newValue.doubleValue() - 60 - time.getHeight()-(play.getHeight()-time.getHeight())/2);

			}

		});
		
		rot = new RotateTransition(Duration.seconds(20), r);
		rot.setAxis(new Point3D(0, 0, 1));
		rot.setFromAngle(0);
		rot.setToAngle(360);
		rot.setInterpolator(Interpolator.LINEAR);
		rot.setCycleCount(RotateTransition.INDEFINITE);
		

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {

				if (e.getCode() == KeyCode.F11) {
					stage.setFullScreen(!stage.isFullScreen());
				}
				if (e.getCode() == KeyCode.R) {
					if (rot.getStatus() == javafx.animation.Animation.Status.RUNNING) {
						rot.pause();
					} else {
						rot.play();
					}
				}
			}
		});
		
		

		stage.show();

		timeline.play();
		player.play();
	}

	public static void main(String[] args) {
		launch(args);
	}

}