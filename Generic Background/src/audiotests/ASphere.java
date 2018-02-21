package audiotests;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class ASphere extends Ellipse {

	private double radius;
	private double radiusOld;
	
	private int animC;
	


	public ASphere(double x, double y, double r) {

		this.setTranslateX(x);
		this.setTranslateY(y);
		this.setRadiusX(r);
		this.setRadiusY(r);
		
		radius = r;
		radiusOld = r;

		this.setFill(Color.RED);
		
		animC = 0;
		
	}

	public void setR(double r) {
		radiusOld = radius + ((radius - r) / 2);
		radius = r;
	}
	
	
	public void setRAD() {
		switch (animC) {
		case 0:
			setRadiusX(radius);
			setRadiusY(radius);
			animC = 1;
			break;
		case 1:
			setRadiusX(radiusOld);
			setRadiusY(radiusOld);
			animC = 0;
			break;
		}
	}

}
