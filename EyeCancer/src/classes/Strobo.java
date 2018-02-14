package classes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Strobo extends Application {

	private Timer timer;
	private ArrayList<Hexagon> hexs = new ArrayList<Hexagon>();
	
	@Override
	public void init() {
		int radius = 40;
		
		for (int i = 0;i<30;i++) {
			for (int j = 0;j<20;j+=2) {
				hexs.add(new Hexagon(radius+(i*Math.sqrt(3)*radius),((j)*(radius+Math.sin(Math.PI/6)*radius)+radius),radius));
				hexs.add(new Hexagon(radius+Math.sqrt(3)*radius/2+(i*Math.sqrt(3)*radius),((j+1)*(radius+Math.sin(Math.PI/6)*radius)+radius),radius));
			}
		}
		
		timer = new Timer(20,new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0;i<30;i++) {
					for (int j = 0;j<20;j++) {
						hexs.get((i*20)+j).setFill(Color.hsb((float)Math.random()*360, 1, 1));
					}
				}
			}
			
		});
		
	}

	@Override
	public void start(final Stage stage) throws Exception {
		
		Group b = new Group();
		b.getChildren().addAll(hexs);
		
		StackPane pane = new StackPane();
		pane.setPadding(new Insets(20));
		pane.getChildren().addAll(b);

		Scene scene = new Scene(pane);
		scene.setFill(Color.rgb(30, 6, 40));

		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.setFullScreenExitHint("Press ESC to exit Fullscreen");
		stage.show();
		
		timer.start();
		
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}

}
