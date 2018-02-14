package classes;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Strobo extends Application{

	@Override
	public void init() {
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		StackPane pane = new StackPane();
		pane.setPadding(new Insets(20));
		pane.getChildren().addAll();

		Scene scene = new Scene(pane);
		scene.setFill(Color.rgb(30, 6, 40));

		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.setFullScreenExitHint("Press ESC to exit Fullscreen");
		stage.show();
		
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}

}
