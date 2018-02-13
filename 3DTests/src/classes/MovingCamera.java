package classes;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class MovingCamera extends Application {

	private PerspectiveCamera camera;

	private ArrayList<Box> blocks = new ArrayList<Box>();
	private Point3D cPoint = new Point3D(-300, 0, -100);
	private double speed = 6;
	
	private Group cGroup;

	@Override
	public void init() {

		for (int i = 0; i <= 9; i++) {
			for (int j = 0; j <= 9; j++) {
				for (int k = 0; k <= 9; k++) {
					Box b = new Box();
					b.setDepth(20);
					b.setWidth(20);
					b.setHeight(20);

					b.setTranslateX((i * 50) + 20);
					b.setTranslateY((j * 50) + 20);
					b.setTranslateZ((k * 50) + 20);

					blocks.add(b);
				}
			}
		}

		camera = new PerspectiveCamera();
		camera.setNearClip(0.1);
		camera.setFarClip(5000.0);
		camera.setFieldOfView(52);
	}

	@Override
	public void start(final Stage stage) {
		Group bGroup = new Group();
		bGroup.getChildren().addAll(blocks);
		
		cGroup = new Group();
		cGroup.getChildren().add(camera);
		
		cGroup.setTranslateX(cPoint.getX());
		cGroup.setTranslateY(cPoint.getY());
		cGroup.setTranslateZ(cPoint.getZ());

		StackPane pane = new StackPane();
		pane.getChildren().addAll(bGroup, cGroup);

		Scene scene = new Scene(pane, 800, 800, true, SceneAntialiasing.DISABLED);
		scene.setCamera(camera);
		scene.setFill(Color.rgb(20, 4, 40));

		stage.setScene(scene);

		scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				switch (e.getText()) {
				case "w":
					cPoint = new Point3D(cPoint.getX(), cPoint.getY(), cPoint.getZ() + speed);
					break;
				case "s":
					cPoint = new Point3D(cPoint.getX(), cPoint.getY(), cPoint.getZ() - speed);
					break;
				case "a":
					cPoint = new Point3D(cPoint.getX() - speed, cPoint.getY(), cPoint.getZ());
					break;
				case "d":
					cPoint = new Point3D(cPoint.getX() + speed, cPoint.getY(), cPoint.getZ());
					break;
				case "q":
					rotateCamera(-3.0);
					break;
				case "e":
					rotateCamera(3.0);
					break;

				}

				cGroup.setTranslateX(cPoint.getX());
				cGroup.setTranslateZ(cPoint.getZ());
			}

		});

		stage.show();
	}

	public static void main(String[] args) {
		launch(args);

	}

	public void rotateCamera(double d) {
		camera.setRotationAxis(Rotate.Y_AXIS);
		camera.setRotate(camera.getRotate()+d);
	}

}
