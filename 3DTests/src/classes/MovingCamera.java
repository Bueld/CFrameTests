package classes;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.stage.Stage;

public class MovingCamera extends Application {

	private PerspectiveCamera camera;
	
	private ArrayList<Box> blocks = new ArrayList<Box>();

	@Override
	public void init() {
		
		Box block1 = new Box();
		block1.setWidth(50);
		block1.setDepth(50);
		block1.setHeight(50);
		block1.setTranslateX(0);
		block1.setTranslateY(0);
		block1.setTranslateZ(0);
		blocks.add(block1);
		
		camera = new PerspectiveCamera();
		camera.setTranslateX(0);
		camera.setTranslateY(0);
		camera.setTranslateZ(-100);
		camera.setNearClip(0.1);
		camera.setFarClip(5000.0);
	}

	@Override
	public void start(final Stage stage) {
		Group bGroup = new Group();
		bGroup.getChildren().addAll(blocks);

		StackPane pane = new StackPane();
		pane.setPadding(new Insets(10, 10, 10, 10));
		pane.getChildren().addAll(bGroup);

		Scene scene = new Scene(pane, 800, 800, true, SceneAntialiasing.DISABLED);
		scene.setCamera(camera);
		scene.setFill(Color.rgb(20, 4, 40));

		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);

	}
}
