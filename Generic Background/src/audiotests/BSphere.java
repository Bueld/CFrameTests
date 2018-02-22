package audiotests;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class BSphere extends Sphere {

	private double radius;
	
	private PhongMaterial mat;

	public BSphere(double x, double y, double r) {

		this.setTranslateX(x);
		this.setTranslateY(y);
		this.setRadius(r);
		
		radius = r;
		
		mat = new PhongMaterial();
		Color c = Color.hsb(Math.random()*360, 1, 1, 1);
		mat.setSpecularColor(c);
		mat.setDiffuseColor(c);
		this.setMaterial(mat);
		
	}

	public void setR(double r) {
		radius = r;
	}
	
	
	public void setRAD() {
		
			setRadius(radius);
	}

}
