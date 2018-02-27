package audiotests;

import java.net.URL;

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
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
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
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;
import javafx.util.Duration;



public class Visualizer_2 extends Application {
	
	private Polyline pol;

	private MediaPlayer player;


	private Timeline timeline;
	private Button play;

	private Image pl;
	private Image pa;
	private ImageView view;

	private Slider vol;

	private Slider time;

	private RotateTransition rot;
	
	private Slider speed;
	
	private Scene scene;
	
	@Override
	public void init() {
		URL u = getClass().getResource("../audios/A - B.mp3");
		String url = u.toExternalForm();
		player = new MediaPlayer(new Media(url));

		player.setCycleCount(MediaPlayer.INDEFINITE);

		player.setAudioSpectrumThreshold(-90);
		
		player.setVolume(0);
		
		pol = new Polyline();
		pol.setFill(Color.DARKMAGENTA);
		
		Lighting li = new Lighting();
		Light.Distant l = new Light.Distant();
		l.setColor(Color.ANTIQUEWHITE);
		l.setAzimuth(-135);
		li.setLight(l);
		li.setSurfaceScale(10.0);
		pol.setEffect(li);
		pol.setStroke(Color.CRIMSON);
		pol.setStrokeWidth(3);

		for (int i = 0; i < 96; i++) {
				pol.getPoints().addAll(Math.cos((2*Math.PI)*96/(i+1))*100+300,Math.sin(2*Math.PI*96/(i+1))*100+300);

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
		vol.setValue(0);
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
				rot.stop();
				rot.setDuration(Duration.seconds(speed.getValue()));
				rot.play();
				
			}
		});

	}

	public void addVisuals() {
		player.setAudioSpectrumListener(new AudioSpectrumListener() {

			@Override
			public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
				pol.getPoints().clear();
				for (int i = 0; i <96; i++) {
					
					double m = Math.pow((90+magnitudes[i]),1.1);
					double x = Math.cos((Math.PI)/96*(i+1)+Math.PI/2)*m*(5)+scene.getWidth()/2+Math.cos((Math.PI)/96*(i+1)+Math.PI/2)*80;
					double y = Math.sin(Math.PI/96*(i+1)+Math.PI/2)*m*(5)+scene.getHeight()/2+Math.sin(Math.PI/96*(i+1)+Math.PI/2)*80;
					pol.getPoints().addAll(x,y);
					//System.out.println(magnitudes[i]);
					
				}
				
				for (int i = 0;i<96;i++) {
					double m = Math.pow((90+magnitudes[96-i]),1.1);
					double x = Math.cos((Math.PI)/96*(i+1)+Math.PI/2)*m*(-5)+scene.getWidth()/2+Math.cos((Math.PI)/96*(i+1)+Math.PI/2)*(-1)*80;
					double y = Math.sin(Math.PI/96*(i+1)+Math.PI/2)*m*(-5)+scene.getHeight()/2+Math.sin(Math.PI/96*(i+1)+Math.PI/2)*(-1)*80;
					pol.getPoints().addAll(x,y);
				}
				int i = 0;
				double m = Math.pow((90+magnitudes[i]),1.1);
				double x = Math.cos((Math.PI)/96*(i+1)+Math.PI/2)*m*(5)+scene.getWidth()/2+Math.cos((Math.PI)/96*(i+1)+Math.PI/2)*80;
				double y = Math.sin(Math.PI/96*(i+1)+Math.PI/2)*m*(5)+scene.getHeight()/2+Math.sin(Math.PI/96*(i+1)+Math.PI/2)*80;
				pol.getPoints().addAll(x,y);
				
				
			}
			
			
		});
	}

	@Override
	public void start(Stage stage) {
		Group r = new Group();
		r.getChildren().addAll(pol);
		Pane pane = new Pane();
		pane.getChildren().addAll(r, play, vol, time, speed);
		pane.setBackground(Background.EMPTY);

		scene = new Scene(pane, 600, 600, true, SceneAntialiasing.BALANCED);
		scene.setFill(Color.rgb(30, 6, 40));

		stage.setScene(scene);
		stage.setTitle("Audio Visualizer");

		stage.widthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			

				for (int i = 0; i < 128; i++) {
					
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
				

				for (int i = 0; i < 128; i++) {
			
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