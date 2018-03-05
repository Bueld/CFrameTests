package audiotests;

import java.io.File;
import java.net.MalformedURLException;
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
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Visualizer_3 extends Application {

	private Visual pol;
	private Visual pol2;
	private Visual pol3;
	private Visual pol4;
	private Visual pol5;
	private Visual pol6;

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

	private ContextMenu conMen;

	private String[] colors;

	private Button chooseFile;

	@Override
	public void init() {
		URL u = getClass().getResource("../audios/Tryptamoon - Yule.mp3");
		String url = u.toExternalForm();
		player = new MediaPlayer(new Media(url));

		player.setCycleCount(MediaPlayer.INDEFINITE);

		player.setAudioSpectrumThreshold(-90);

		player.setVolume(66);

		pol = new Visual(300);
		pol2 = new Visual(300);
		pol3 = new Visual(300);
		pol4 = new Visual(300);
		pol5 = new Visual(300);
		pol6 = new Visual(300);

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
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(35), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (!time.isPressed() && !time.isHover()) {
					time.setValue((player.getCurrentTime().toSeconds()));
				}
				if (time.isHover()) {
					time.setValue((player.getCurrentTime().toSeconds()));
				}

				pol.animate(scene.getWidth(), scene.getHeight());
				pol2.animate(scene.getWidth(), scene.getHeight());
				pol3.animate(scene.getWidth(), scene.getHeight());
				pol4.animate(scene.getWidth(), scene.getHeight());
				pol5.animate(scene.getWidth(), scene.getHeight());
				pol6.animate(scene.getWidth(), scene.getHeight());

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

		conMen = new ContextMenu();

		colors = new String[] { "CRIMSON", "DARKMAGENTA", "AQUA", "DARKGOLDENROD", "STEELBLUE", "CADETBLUE", "DARKCYAN",
				"DARKORANGE", "YELLOWGREEN", "DARKRED", "DARKSLATEGREY", "DARKVIOLET", "DEEPPINK", "DEEPSKYBLUE",
				"WHITE", "FIREBRICK", "FORESTGREEN", "WHITESMOKE", "TEAL", "BLACK" };

		conMen.getItems().addAll(pol2.toggleVis("1"), pol2.men("1", colors), pol3.toggleVis("2"), pol3.men("2", colors),
				pol.toggleVis("3"), pol.men("3", colors), pol4.toggleVis("4"), pol4.men("4", colors),
				pol5.toggleVis("5"), pol5.men("5", colors), pol6.toggleVis("6"), pol6.men("6", colors));

		pol.setFill(Color.DARKMAGENTA);
		pol2.setFill(Color.DARKMAGENTA);
		pol3.setFill(Color.DARKMAGENTA);
		pol4.setFill(Color.DARKMAGENTA);
		pol5.setFill(Color.DARKMAGENTA);
		pol6.setFill(Color.DARKMAGENTA);

		chooseFile = new Button();
		chooseFile.setDefaultButton(true);

	}

	public void addVisuals() {
		player.setAudioSpectrumListener(new AudioSpectrumListener() {

			@Override
			public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {

				pol.setNewWorth(magnitudes);

				float[] f1 = new float[128];
				float[] f2 = new float[128];
				float[] f3 = new float[128];
				float[] f4 = new float[128];
				float[] f5 = new float[128];
				for (int i = 0; i < magnitudes.length; i++) {
					f1[i] = (float) (magnitudes[i] * 1.2);
					f2[i] = (float) (magnitudes[i] * 1.1);
					f3[i] = (float) (magnitudes[i] * 0.9);
					f4[i] = (float) (magnitudes[i] * 0.8);
					f5[i] = (float) (magnitudes[i] * 0.7);
				}
				pol2.setNewWorth(f1);
				pol3.setNewWorth(f2);
				pol4.setNewWorth(f3);
				pol5.setNewWorth(f4);
				pol6.setNewWorth(f5);

			}

		});
	}

	@Override
	public void start(Stage stage) {

		Group r = new Group();
		r.getChildren().addAll(pol6, pol5, pol4, pol, pol3, pol2);
		Pane pane = new Pane();
		pane.getChildren().addAll(r, play, vol, time, speed, chooseFile);
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
				vol.setTranslateX(play.getTranslateX() + play.getWidth() + 20);

				vol.setPrefWidth(newValue.doubleValue() - vol.getTranslateX() - 30);
				time.setPrefWidth(play.getTranslateX() - 20 - time.getTranslateX());
			}

		});

		stage.heightProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				for (int i = 0; i < 128; i++) {

				}

				play.setTranslateY(newValue.doubleValue() - 60 - play.getHeight());
				vol.setTranslateY(
						newValue.doubleValue() - 60 - vol.getHeight() - (play.getHeight() - vol.getHeight()) / 2);
				time.setTranslateY(
						newValue.doubleValue() - 60 - time.getHeight() - (play.getHeight() - time.getHeight()) / 2);

			}

		});

		scene.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

			@Override
			public void handle(ContextMenuEvent event) {
				conMen.show(stage, event.getScreenX(), event.getScreenY());
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
						// rot.play();
					}
				}
			}
		});

		chooseFile.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				FileChooser f = new FileChooser();
				f.setTitle("Open Music File");

				
				f.setInitialDirectory(new File("M:\\git\\CFrameTests\\Generic Background\\src\\audios"));

				f.showOpenDialog(stage);
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