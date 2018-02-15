package classes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Hexagon extends Polygon {
	private Timer timer;

	public Hexagon(double x, double y, double r) {

		getPoints().addAll((x + Math.sin(0) * r), (y + Math.cos(0) * r), (x + Math.sin(Math.PI / 3) * r),
				(y + Math.cos(Math.PI / 3) * r), (x + Math.sin(Math.PI / 3 * 2) * r),
				(y + Math.cos(Math.PI / 3 * 2) * r), (x + Math.sin(Math.PI) * r), (y + Math.cos(Math.PI) * r),
				(x + Math.sin(Math.PI / 3 * 4) * r), (y + Math.cos(Math.PI / 3 * 4) * r),
				(x + Math.sin(Math.PI / 3 * 5) * r), (y + Math.cos(Math.PI / 3 * 5) * r));

		

	}

	public void startT(int t) {
		
		timer = new Timer(t,new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFill(Color.hsb((float)Math.random()*360, 1, 1));
			}
			
		});
		timer.start();
	}

}
