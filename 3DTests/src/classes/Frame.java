package classes;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Frame extends Application {

	private DObject obj;
	private static RotateTransition rot;
	private static PointLight light1;
	private static PointLight light2;
	private static PointLight light3;
	
	private static PerspectiveCamera cam1;

	@Override
	public void init() {

		obj = new DObject();
		
		light1 = new PointLight();
		light1.setVisible(true);
		light1.setTranslateX(-1000);
		light1.setTranslateY(-400);
		light1.setTranslateZ(-1000);
		light1.setColor(Color.DARKMAGENTA);
		light2 = new PointLight();
		light2.setVisible(true);
		light2.setTranslateX(2000);
		light2.setTranslateY(-1000);
		light2.setTranslateZ(0);
		light2.setColor(Color.DARKCYAN);
		light3 = new PointLight();
		light3.setVisible(true);
		light3.setTranslateX(-3000);
		light3.setTranslateY(-100);
		light3.setTranslateZ(-100);
		light3.setColor(Color.WHITESMOKE);
		
		cam1 = new PerspectiveCamera(true);
		cam1.setTranslateZ(-800);
		cam1.setNearClip(250);
		cam1.setFarClip(2000.0);
		cam1.setFieldOfView(170);
		cam1.setVerticalFieldOfView(false);


	}

	@Override
	public void start(final Stage stage) {

		Group map = new Group();
		map.getChildren().addAll(obj);
		
		Group lights = new Group();
		lights.getChildren().addAll(light1,light2,light3);
		
		rot = new RotateTransition(Duration.seconds(6),map);
		rot.setAxis(new Point3D(1,2,0));
		rot.setFromAngle(0);
		rot.setToAngle(360);
		rot.setInterpolator(Interpolator.LINEAR);
		rot.setCycleCount(RotateTransition.INDEFINITE);

		StackPane pane = new StackPane();
		pane.setPadding(new Insets(20));
		pane.getChildren().addAll(map);

		Scene scene = new Scene(pane, Color.DARKSLATEGRAY);
		scene.setCamera(cam1);

		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setFullScreenExitHint("Press F11 to toggle Fullscreen.");
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				if (e.getCode() == KeyCode.F11) {
					stage.setFullScreen(!stage.isFullScreen());
				}

			}

		});
		stage.setFullScreen(true);
		stage.show();
	}

	@Override
	public void stop() {
	}

	public static void startRotate() {
		rot.play();
	}

	public static void stopRotate() {
		rot.pause();
	}

	public static void main(String[] parameters) {
		launch(parameters);
	}

}
