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
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
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

	@Override
	public void init() {
		URL u = getClass().getResource("../audios/Axel Thesleff - Bad Karma.mp3");
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

		pol.setStroke(Color.CRIMSON.brighter());
		pol2.setStroke(Color.CADETBLUE.brighter());
		pol3.setStroke(Color.DARKMAGENTA.brighter());
		pol4.setStroke(Color.AZURE.brighter());
		pol5.setStroke(Color.DARKORANGE.brighter());
		pol6.setStroke(Color.YELLOWGREEN.brighter());

		pol.setFill(Color.CRIMSON);
		pol2.setFill(Color.CADETBLUE);
		pol3.setFill(Color.DARKMAGENTA);
		pol4.setFill(Color.AZURE);
		pol5.setFill(Color.DARKORANGE);
		pol6.setFill(Color.YELLOWGREEN);

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

		CheckMenuItem v1 = new CheckMenuItem("Polygon 1");
		v1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (v1.isSelected()) {
					pol2.setVisible(true);
				} else {
					pol2.setVisible(false);
				}
			}

		});
		CheckMenuItem v2 = new CheckMenuItem("Polygon 2");
		v2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (v2.isSelected()) {
					pol3.setVisible(true);
				} else {
					pol3.setVisible(false);
				}
			}

		});
		CheckMenuItem v3 = new CheckMenuItem("Polygon 3");
		v3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (v3.isSelected()) {
					pol.setVisible(true);
				} else {
					pol.setVisible(false);
				}
			}

		});
		CheckMenuItem v4 = new CheckMenuItem("Polygon 4");
		v4.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (v4.isSelected()) {
					pol4.setVisible(true);
				} else {
					pol4.setVisible(false);
				}
			}

		});
		CheckMenuItem v5 = new CheckMenuItem("Polygon 5");
		v5.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (v5.isSelected()) {
					pol5.setVisible(true);
				} else {
					pol5.setVisible(false);
				}
			}

		});
		CheckMenuItem v6 = new CheckMenuItem("Polygon 6");
		v6.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (v6.isSelected()) {
					pol6.setVisible(true);
				} else {
					pol6.setVisible(false);
				}
			}

		});
		v1.setSelected(true);
		v2.setSelected(true);
		v3.setSelected(true);
		v4.setSelected(true);
		v5.setSelected(true);
		v6.setSelected(true);

		Menu pMen1 = new Menu("Color Polygon 1");
		RadioMenuItem cMen1_1 = new RadioMenuItem("CRIMSON");
		RadioMenuItem cMen1_2 = new RadioMenuItem("DARKMAGENTA");
		RadioMenuItem cMen1_3 = new RadioMenuItem("AQUA");
		RadioMenuItem cMen1_4 = new RadioMenuItem("DARKGOLDENROD");
		RadioMenuItem cMen1_5 = new RadioMenuItem("STEELBLUE");
		RadioMenuItem cMen1_6 = new RadioMenuItem("CADETBLUE");
		RadioMenuItem cMen1_7 = new RadioMenuItem("AZURE");
		RadioMenuItem cMen1_8 = new RadioMenuItem("DARKORANGE");
		RadioMenuItem cMen1_9 = new RadioMenuItem("YELLOWGREEN");

		cMen1_1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol2.setFill(Color.CRIMSON);
				pol2.setStroke(Color.CRIMSON.brighter());
			}

		});
		cMen1_2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol2.setFill(Color.DARKMAGENTA);
				pol2.setStroke(Color.DARKMAGENTA.brighter());
			}

		});
		cMen1_3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol2.setFill(Color.AQUA);
				pol2.setStroke(Color.AQUA.brighter());
			}

		});
		cMen1_4.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol2.setFill(Color.DARKGOLDENROD);
				pol2.setStroke(Color.DARKGOLDENROD.brighter());
			}

		});
		cMen1_5.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol2.setFill(Color.STEELBLUE);
				pol2.setStroke(Color.STEELBLUE.brighter());
			}

		});
		cMen1_6.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol2.setFill(Color.CADETBLUE);
				pol2.setStroke(Color.CADETBLUE.brighter());
			}

		});
		cMen1_7.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol2.setFill(Color.AZURE);
				pol2.setStroke(Color.AZURE.brighter());
			}

		});
		cMen1_8.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol2.setFill(Color.DARKORANGE);
				pol2.setStroke(Color.DARKORANGE.brighter());
			}

		});
		cMen1_9.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol2.setFill(Color.YELLOWGREEN);
				pol2.setStroke(Color.YELLOWGREEN.brighter());
			}

		});

		ToggleGroup gM1 = new ToggleGroup();
		cMen1_1.setToggleGroup(gM1);
		cMen1_2.setToggleGroup(gM1);
		cMen1_3.setToggleGroup(gM1);
		cMen1_4.setToggleGroup(gM1);
		cMen1_5.setToggleGroup(gM1);
		cMen1_6.setToggleGroup(gM1);
		cMen1_7.setToggleGroup(gM1);
		cMen1_8.setToggleGroup(gM1);
		cMen1_9.setToggleGroup(gM1);

		Menu pMen2 = new Menu("Color Polygon 2");
		RadioMenuItem cMen2_1 = new RadioMenuItem("CRIMSON");
		RadioMenuItem cMen2_2 = new RadioMenuItem("DARKMAGENTA");
		RadioMenuItem cMen2_3 = new RadioMenuItem("AQUA");
		RadioMenuItem cMen2_4 = new RadioMenuItem("DARKGOLDENROD");
		RadioMenuItem cMen2_5 = new RadioMenuItem("STEELBLUE");
		RadioMenuItem cMen2_6 = new RadioMenuItem("CADETBLUE");
		RadioMenuItem cMen2_7 = new RadioMenuItem("AZURE");
		RadioMenuItem cMen2_8 = new RadioMenuItem("DARKORANGE");
		RadioMenuItem cMen2_9 = new RadioMenuItem("YELLOWGREEN");

		cMen2_1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol3.setFill(Color.CRIMSON);
				pol3.setStroke(Color.CRIMSON.brighter());
			}

		});
		cMen2_2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol3.setFill(Color.DARKMAGENTA);
				pol3.setStroke(Color.DARKMAGENTA.brighter());
			}

		});
		cMen2_3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol3.setFill(Color.AQUA);
				pol3.setStroke(Color.AQUA.brighter());
			}

		});
		cMen2_4.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol3.setFill(Color.DARKGOLDENROD);
				pol3.setStroke(Color.DARKGOLDENROD.brighter());
			}

		});
		cMen2_5.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol3.setFill(Color.STEELBLUE);
				pol3.setStroke(Color.STEELBLUE.brighter());
			}

		});
		cMen2_6.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol3.setFill(Color.CADETBLUE);
				pol3.setStroke(Color.CADETBLUE.brighter());
			}

		});
		cMen2_7.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol3.setFill(Color.AZURE);
				pol3.setStroke(Color.AZURE.brighter());
			}

		});
		cMen2_8.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol3.setFill(Color.DARKORANGE);
				pol3.setStroke(Color.DARKORANGE.brighter());
			}

		});
		cMen2_9.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol3.setFill(Color.YELLOWGREEN);
				pol3.setStroke(Color.YELLOWGREEN.brighter());
			}

		});

		ToggleGroup gM2 = new ToggleGroup();
		cMen2_1.setToggleGroup(gM2);
		cMen2_2.setToggleGroup(gM2);
		cMen2_3.setToggleGroup(gM2);
		cMen2_4.setToggleGroup(gM2);
		cMen2_5.setToggleGroup(gM2);
		cMen2_6.setToggleGroup(gM2);
		cMen2_7.setToggleGroup(gM2);
		cMen2_8.setToggleGroup(gM2);
		cMen2_9.setToggleGroup(gM2);

		Menu pMen3 = new Menu("Color Polygon 3");
		RadioMenuItem cMen3_1 = new RadioMenuItem("CRIMSON");
		RadioMenuItem cMen3_2 = new RadioMenuItem("DARKMAGENTA");
		RadioMenuItem cMen3_3 = new RadioMenuItem("AQUA");
		RadioMenuItem cMen3_4 = new RadioMenuItem("DARKGOLDENROD");
		RadioMenuItem cMen3_5 = new RadioMenuItem("STEELBLUE");
		RadioMenuItem cMen3_6 = new RadioMenuItem("CADETBLUE");
		RadioMenuItem cMen3_7 = new RadioMenuItem("AZURE");
		RadioMenuItem cMen3_8 = new RadioMenuItem("DARKORANGE");
		RadioMenuItem cMen3_9 = new RadioMenuItem("YELLOWGREEN");

		cMen3_1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol.setFill(Color.CRIMSON);
				pol.setStroke(Color.CRIMSON.brighter());
			}

		});
		cMen3_2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol.setFill(Color.DARKMAGENTA);
				pol.setStroke(Color.DARKMAGENTA.brighter());
			}

		});
		cMen3_3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol.setFill(Color.AQUA);
				pol.setStroke(Color.AQUA.brighter());
			}

		});
		cMen3_4.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol.setFill(Color.DARKGOLDENROD);
				pol.setStroke(Color.DARKGOLDENROD.brighter());
			}

		});
		cMen3_5.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol.setFill(Color.STEELBLUE);
				pol.setStroke(Color.STEELBLUE.brighter());
			}

		});
		cMen3_6.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol.setFill(Color.CADETBLUE);
				pol.setStroke(Color.CADETBLUE.brighter());
			}

		});
		cMen3_7.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol.setFill(Color.AZURE);
				pol.setStroke(Color.AZURE.brighter());
			}

		});
		cMen3_8.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol.setFill(Color.DARKORANGE);
				pol.setStroke(Color.DARKORANGE.brighter());
			}

		});
		cMen3_9.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol.setFill(Color.YELLOWGREEN);
				pol.setStroke(Color.YELLOWGREEN.brighter());
			}

		});

		ToggleGroup gM3 = new ToggleGroup();
		cMen3_1.setToggleGroup(gM3);
		cMen3_2.setToggleGroup(gM3);
		cMen3_3.setToggleGroup(gM3);
		cMen3_4.setToggleGroup(gM3);
		cMen3_5.setToggleGroup(gM3);
		cMen3_6.setToggleGroup(gM3);
		cMen3_7.setToggleGroup(gM3);
		cMen3_8.setToggleGroup(gM3);
		cMen3_9.setToggleGroup(gM3);

		Menu pMen4 = new Menu("Color Polygon 4");
		RadioMenuItem cMen4_1 = new RadioMenuItem("CRIMSON");
		RadioMenuItem cMen4_2 = new RadioMenuItem("DARKMAGENTA");
		RadioMenuItem cMen4_3 = new RadioMenuItem("AQUA");
		RadioMenuItem cMen4_4 = new RadioMenuItem("DARKGOLDENROD");
		RadioMenuItem cMen4_5 = new RadioMenuItem("STEELBLUE");
		RadioMenuItem cMen4_6 = new RadioMenuItem("CADETBLUE");
		RadioMenuItem cMen4_7 = new RadioMenuItem("AZURE");
		RadioMenuItem cMen4_8 = new RadioMenuItem("DARKORANGE");
		RadioMenuItem cMen4_9 = new RadioMenuItem("YELLOWGREEN");

		cMen4_1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol4.setFill(Color.CRIMSON);
				pol4.setStroke(Color.CRIMSON.brighter());
			}

		});
		cMen4_2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol4.setFill(Color.DARKMAGENTA);
				pol4.setStroke(Color.DARKMAGENTA.brighter());
			}

		});
		cMen4_3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol4.setFill(Color.AQUA);
				pol4.setStroke(Color.AQUA.brighter());
			}

		});
		cMen4_4.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol4.setFill(Color.DARKGOLDENROD);
				pol4.setStroke(Color.DARKGOLDENROD.brighter());
			}

		});
		cMen4_5.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol4.setFill(Color.STEELBLUE);
				pol4.setStroke(Color.STEELBLUE.brighter());
			}

		});
		cMen4_6.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol4.setFill(Color.CADETBLUE);
				pol4.setStroke(Color.CADETBLUE.brighter());
			}

		});
		cMen4_7.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol4.setFill(Color.AZURE);
				pol4.setStroke(Color.AZURE.brighter());
			}

		});
		cMen4_8.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol4.setFill(Color.DARKORANGE);
				pol4.setStroke(Color.DARKORANGE.brighter());
			}

		});
		cMen4_9.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol4.setFill(Color.YELLOWGREEN);
				pol4.setStroke(Color.YELLOWGREEN.brighter());
			}

		});
		ToggleGroup gM4 = new ToggleGroup();
		cMen4_1.setToggleGroup(gM4);
		cMen4_2.setToggleGroup(gM4);
		cMen4_3.setToggleGroup(gM4);
		cMen4_4.setToggleGroup(gM4);
		cMen4_5.setToggleGroup(gM4);
		cMen4_6.setToggleGroup(gM4);
		cMen4_7.setToggleGroup(gM4);
		cMen4_8.setToggleGroup(gM4);
		cMen4_9.setToggleGroup(gM4);

		Menu pMen5 = new Menu("Color Polygon 5");
		RadioMenuItem cMen5_1 = new RadioMenuItem("CRIMSON");
		RadioMenuItem cMen5_2 = new RadioMenuItem("DARKMAGENTA");
		RadioMenuItem cMen5_3 = new RadioMenuItem("AQUA");
		RadioMenuItem cMen5_4 = new RadioMenuItem("DARKGOLDENROD");
		RadioMenuItem cMen5_5 = new RadioMenuItem("STEELBLUE");
		RadioMenuItem cMen5_6 = new RadioMenuItem("CADETBLUE");
		RadioMenuItem cMen5_7 = new RadioMenuItem("AZURE");
		RadioMenuItem cMen5_8 = new RadioMenuItem("DARKORANGE");
		RadioMenuItem cMen5_9 = new RadioMenuItem("YELLOWGREEN");

		cMen5_1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol5.setFill(Color.CRIMSON);
				pol5.setStroke(Color.CRIMSON.brighter());
			}

		});
		cMen5_2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol5.setFill(Color.DARKMAGENTA);
				pol5.setStroke(Color.DARKMAGENTA.brighter());
			}

		});
		cMen5_3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol5.setFill(Color.AQUA);
				pol5.setStroke(Color.AQUA.brighter());
			}

		});
		cMen5_4.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol5.setFill(Color.DARKGOLDENROD);
				pol5.setStroke(Color.DARKGOLDENROD.brighter());
			}

		});
		cMen5_5.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol5.setFill(Color.STEELBLUE);
				pol5.setStroke(Color.STEELBLUE.brighter());
			}

		});
		cMen5_6.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol5.setFill(Color.CADETBLUE);
				pol5.setStroke(Color.CADETBLUE.brighter());
			}

		});
		cMen5_7.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol5.setFill(Color.AZURE);
				pol5.setStroke(Color.AZURE.brighter());
			}

		});
		cMen5_8.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol5.setFill(Color.DARKORANGE);
				pol5.setStroke(Color.DARKORANGE.brighter());
			}

		});
		cMen5_9.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol5.setFill(Color.YELLOWGREEN);
				pol5.setStroke(Color.YELLOWGREEN.brighter());
			}

		});

		ToggleGroup gM5 = new ToggleGroup();
		cMen5_1.setToggleGroup(gM5);
		cMen5_2.setToggleGroup(gM5);
		cMen5_3.setToggleGroup(gM5);
		cMen5_4.setToggleGroup(gM5);
		cMen5_5.setToggleGroup(gM5);
		cMen5_6.setToggleGroup(gM5);
		cMen5_7.setToggleGroup(gM5);
		cMen5_8.setToggleGroup(gM5);
		cMen5_9.setToggleGroup(gM5);

		Menu pMen6 = new Menu("Color Polygon 6");
		RadioMenuItem cMen6_1 = new RadioMenuItem("CRIMSON");
		RadioMenuItem cMen6_2 = new RadioMenuItem("DARKMAGENTA");
		RadioMenuItem cMen6_3 = new RadioMenuItem("AQUA");
		RadioMenuItem cMen6_4 = new RadioMenuItem("DARKGOLDENROD");
		RadioMenuItem cMen6_5 = new RadioMenuItem("STEELBLUE");
		RadioMenuItem cMen6_6 = new RadioMenuItem("CADETBLUE");
		RadioMenuItem cMen6_7 = new RadioMenuItem("AZURE");
		RadioMenuItem cMen6_8 = new RadioMenuItem("DARKORANGE");
		RadioMenuItem cMen6_9 = new RadioMenuItem("YELLOWGREEN");

		cMen6_1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol6.setFill(Color.CRIMSON);
				pol6.setStroke(Color.CRIMSON.brighter());
			}

		});
		cMen6_2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol6.setFill(Color.DARKMAGENTA);
				pol6.setStroke(Color.DARKMAGENTA.brighter());
			}

		});
		cMen6_3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol6.setFill(Color.AQUA);
				pol6.setStroke(Color.AQUA.brighter());
			}

		});
		cMen6_4.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol6.setFill(Color.DARKGOLDENROD);
				pol6.setStroke(Color.DARKGOLDENROD.brighter());
			}

		});
		cMen6_5.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol6.setFill(Color.STEELBLUE);
				pol6.setStroke(Color.STEELBLUE.brighter());
			}

		});
		cMen6_6.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol6.setFill(Color.CADETBLUE);
				pol6.setStroke(Color.CADETBLUE.brighter());
			}

		});
		cMen6_7.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol6.setFill(Color.AZURE);
				pol6.setStroke(Color.AZURE.brighter());
			}

		});
		cMen6_8.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol6.setFill(Color.DARKORANGE);
				pol6.setStroke(Color.DARKORANGE.brighter());
			}

		});
		cMen6_9.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pol6.setFill(Color.YELLOWGREEN);
				pol6.setStroke(Color.YELLOWGREEN.brighter());
			}

		});

		ToggleGroup gM6 = new ToggleGroup();
		cMen6_1.setToggleGroup(gM6);
		cMen6_2.setToggleGroup(gM6);
		cMen6_3.setToggleGroup(gM6);
		cMen6_4.setToggleGroup(gM6);
		cMen6_5.setToggleGroup(gM6);
		cMen6_6.setToggleGroup(gM6);
		cMen6_7.setToggleGroup(gM6);
		cMen6_8.setToggleGroup(gM6);
		cMen6_9.setToggleGroup(gM6);

		gM1.selectToggle(cMen1_6);
		gM2.selectToggle(cMen2_2);
		gM3.selectToggle(cMen3_1);
		gM4.selectToggle(cMen4_7);
		gM5.selectToggle(cMen5_8);
		gM6.selectToggle(cMen6_9);

		pMen1.getItems().addAll(cMen1_1, cMen1_2, cMen1_3, cMen1_4, cMen1_5, cMen1_6, cMen1_7, cMen1_8, cMen1_9);
		pMen2.getItems().addAll(cMen2_1, cMen2_2, cMen2_3, cMen2_4, cMen2_5, cMen2_6, cMen2_7, cMen2_8, cMen2_9);
		pMen3.getItems().addAll(cMen3_1, cMen3_2, cMen3_3, cMen3_4, cMen3_5, cMen3_6, cMen3_7, cMen3_8, cMen3_9);
		pMen4.getItems().addAll(cMen4_1, cMen4_2, cMen4_3, cMen4_4, cMen4_5, cMen4_6, cMen4_7, cMen4_8, cMen4_9);
		pMen5.getItems().addAll(cMen5_1, cMen5_2, cMen5_3, cMen5_4, cMen5_5, cMen5_6, cMen5_7, cMen5_8, cMen5_9);
		pMen6.getItems().addAll(cMen6_1, cMen6_2, cMen6_3, cMen6_4, cMen6_5, cMen6_6, cMen6_7, cMen6_8, cMen6_9);

		conMen.getItems().addAll(v1, pMen1, v2, pMen2, v3, pMen3, v4, pMen4, v5, pMen5, v6, pMen6);

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

		stage.show();

		timeline.play();
		player.play();
	}

	public static void main(String[] args) {
		launch(args);
	}

}