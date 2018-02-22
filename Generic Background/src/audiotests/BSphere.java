package audiotests;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class BSphere extends Sphere {

	private double radius;
	private double radiusOld;
	
	private int animC;
	
	private PhongMaterial mat;

	public BSphere(double x, double y, double r) {

		this.setTranslateX(x);
		this.setTranslateY(y);
		this.setRadius(r);
		
		radius = r;
		radiusOld = r;

		animC = 0;
		
		mat = new PhongMaterial();
		Color c = Color.hsb(Math.random()*360, 1, 1, 1);
		mat.setSpecularColor(c);
		mat.setDiffuseColor(c);
		this.setMaterial(mat);
		
	}

	public void setR(double r) {
		radiusOld = radius + ((radius - r) / 2);
		radius = r;
	}
	
	
	public void setRAD() {
		switch (animC) {
		case 0:
			setRadius(radius);
			animC = 1;
			break;
		case 1:
			setRadius(radiusOld);
			animC = 0;
			break;
		}
	}

}
