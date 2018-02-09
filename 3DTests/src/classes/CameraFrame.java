package classes;

import java.util.ArrayList;

import javafx.animation.Animation.Status;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CameraFrame extends Application {

	private ArrayList<Box> blocks = new ArrayList<Box>();

	private static PerspectiveCamera cam1;

	private Point3D cCoords = new Point3D(500, 500, -2500);

	private static RotateTransition rot;

	private static PointLight light1;
	private static PointLight light2;
	private static PointLight light3;
	
	private PhongMaterial mat;

	@Override
	public void init() {

		cam1 = new PerspectiveCamera(true);
		cam1.setNearClip(0.1);
		cam1.setFarClip(10000.0);
		cam1.setFieldOfView(30);

		cam1.setTranslateX(cCoords.getX());
		cam1.setTranslateY(cCoords.getY());
		cam1.setTranslateZ(cCoords.getZ());

		light1 = new PointLight();
		light1.setVisible(true);
		light1.setTranslateX(cCoords.getX());
		light1.setTranslateY(cCoords.getY());
		light1.setTranslateZ(cCoords.getZ());
		light1.setColor(Color.GOLDENROD);
		light2 = new PointLight();
		light2.setVisible(true);
		light2.setTranslateX(250);
		light2.setTranslateY(250);
		light2.setTranslateZ(-2000);
		light2.setColor(Color.STEELBLUE);
		light3 = new PointLight();
		light3.setVisible(true);
		light3.setTranslateX(2000);
		light3.setTranslateY(250);
		light3.setTranslateZ(250);
		light3.setColor(Color.GOLDENROD);
		
		mat = new PhongMaterial();
		mat.setDiffuseMap(new Image("img/map1.png"));

		for (int i = 0; i <= 19; i++) {
			for (int j = 0; j <= 19; j++) {
				for (int k = 0; k <= 19; k++) {
					Box b = new Box();
					b.setDepth(20);
					b.setWidth(20);
					b.setHeight(20);

					b.setTranslateX((i * 50) + 20);
					b.setTranslateY((j * 50) + 20);
					b.setTranslateZ((k * 50) + 20);
					
					
					b.setMaterial(mat);
				
					blocks.add(b);
				}
			}
		}

	}

	@Override
	public void start(final Stage stage) {

		Group bGroup = new Group();
		bGroup.getChildren().addAll(blocks);

		Group lights = new Group();
		lights.getChildren().addAll(light1);

		rot = new RotateTransition(Duration.seconds(20), bGroup);
		rot.setAxis(new Point3D(1, 1, 0));
		rot.setFromAngle(0);
		rot.setToAngle(360);
		rot.setInterpolator(Interpolator.LINEAR);
		rot.setCycleCount(RotateTransition.INDEFINITE);

		Pane pane = new Pane();
		pane.getChildren().addAll(bGroup, lights);

		Scene scene = new Scene(pane, 800, 800, true, SceneAntialiasing.DISABLED);
		scene.setCamera(cam1);
		scene.setFill(Color.rgb(20, 4, 40));

		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setFullScreenExitHint("Press F11 to toggle Fullscreen.");
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {

				double speed = 10;
				if (e.getCode() == KeyCode.F11) {
					stage.setFullScreen(!stage.isFullScreen());
				}
				if (e.getCode() == KeyCode.W) {
					cam1.setTranslateZ(cCoords.getZ() + speed);
					cCoords = new Point3D(cCoords.getX(), cCoords.getY(), cCoords.getZ() + speed);
				}
				if (e.getCode() == KeyCode.S) {
					cam1.setTranslateZ(cCoords.getZ() - speed);
					cCoords = new Point3D(cCoords.getX(), cCoords.getY(), cCoords.getZ() - speed);
				}
				if (e.getCode() == KeyCode.A) {
					cam1.setTranslateY(cCoords.getY() - speed);
					cCoords = new Point3D(cCoords.getX(), cCoords.getY() - speed, cCoords.getZ());
				}
				if (e.getCode() == KeyCode.D) {
					cam1.setTranslateY(cCoords.getY() + speed);
					cCoords = new Point3D(cCoords.getX(), cCoords.getY() + speed, cCoords.getZ());
				}
				if (e.getCode() == KeyCode.Q) {
					cam1.setTranslateX(cCoords.getX() - speed);
					cCoords = new Point3D(cCoords.getX() - speed, cCoords.getY(), cCoords.getZ());
				}
				if (e.getCode() == KeyCode.E) {
					cam1.setTranslateX(cCoords.getX() + speed);
					cCoords = new Point3D(cCoords.getX() + speed, cCoords.getY(), cCoords.getZ());
				}
				if (e.getCode() == KeyCode.R) {
					if (rot.getStatus() == Status.RUNNING) {
						rot.pause();
					} else {
						rot.play();
					}
				}

			}

		});
		stage.setFullScreen(true);
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
