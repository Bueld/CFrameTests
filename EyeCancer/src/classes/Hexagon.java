package classes;

import javafx.scene.shape.Polygon;

public class Hexagon extends Polygon {


	public Hexagon(double x, double y, double r) {

		getPoints().addAll((x + Math.sin(0) * r), (y + Math.cos(0) * r), (x + Math.sin(Math.PI / 3) * r),
				(y + Math.cos(Math.PI / 3) * r), (x + Math.sin(Math.PI / 3 * 2) * r),
				(y + Math.cos(Math.PI / 3 * 2) * r), (x + Math.sin(Math.PI) * r), (y + Math.cos(Math.PI) * r),
				(x + Math.sin(Math.PI / 3 * 4) * r), (y + Math.cos(Math.PI / 3 * 4) * r),
				(x + Math.sin(Math.PI / 3 * 5) * r), (y + Math.cos(Math.PI / 3 * 5) * r));

		

	}

}
