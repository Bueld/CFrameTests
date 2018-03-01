package audiotests;

import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

public class Visual extends Polyline {

	private Lighting li;
	private Light l;
	private float[] mOld = new float[128];
	private float[] mNew = new float[128];
	private float[] buff1 = new float[128];
	private float[] buff2 = new float[128];

	private int animCount;
	
	private double rad;

	public Visual(double radius) {
		
		this.rad = radius;
		
		this.setStrokeWidth(5.6);
		li = new Lighting();
		l = new Light.Distant(-135, 50, Color.BLANCHEDALMOND);
		li.setSurfaceScale(4.8);
		li.setLight(l);
		li.setDiffuseConstant(0.8);
		this.setEffect(li);

		for (int i = 0; i < 192; i++) {
			this.getPoints().addAll(Math.cos((2 * Math.PI) * 192 / (i + 1)) * 100 + rad,
					Math.sin(2 * Math.PI * 192 / (i + 1)) * 100 + rad);
		}

		animCount = 0;
	}

	public void setNewWorth(float[] magNew) {

		for (int i = 0; i < 128; i++) {
			mOld[i] = mNew[i];
			mNew[i] = magNew[i];
			buff1[i] = magNew[i] + ((mOld[i] - magNew[i]) / 3);
			buff2[i] = magNew[i] + ((mOld[i] - magNew[i]) / 3 * 2);
		}
	}
	
	public void animate(double width, double height) {
		
		this.getPoints().clear();
		
		switch(animCount) {
		case 0:
		{
			for (int i = 0; i <96; i++) {
				
				double m = 90+buff1[i];
				double x = Math.cos((Math.PI)/96*(i+1)+Math.PI/2)*m*(5)+width/2+Math.cos((Math.PI)/96*(i+1)+Math.PI/2)*80;
				double y = Math.sin(Math.PI/96*(i+1)+Math.PI/2)*m*(5)+height/2+Math.sin(Math.PI/96*(i+1)+Math.PI/2)*80;
				getPoints().addAll(x,y);
				
			}
			
			for (int i = 0;i<96;i++) {
				double m = 90+buff1[96-i];
				double x = Math.cos((Math.PI)/96*(i+1)+Math.PI/2)*m*(-5)+width/2+Math.cos((Math.PI)/96*(i+1)+Math.PI/2)*(-1)*80;
				double y = Math.sin(Math.PI/96*(i+1)+Math.PI/2)*m*(-5)+height/2+Math.sin(Math.PI/96*(i+1)+Math.PI/2)*(-1)*80;
				getPoints().addAll(x,y);
			}
			int i = 0;
			double m = 90+buff1[i];
			double x = Math.cos((Math.PI)/96*(i+1)+Math.PI/2)*m*(5)+width/2+Math.cos((Math.PI)/96*(i+1)+Math.PI/2)*80;
			double y = Math.sin(Math.PI/96*(i+1)+Math.PI/2)*m*(5)+height/2+Math.sin(Math.PI/96*(i+1)+Math.PI/2)*80;
			getPoints().addAll(x,y);
			animCount = 1;
		}
		
			
			break;
		case 1:
		{
			for (int i = 0; i <96; i++) {
				
				double m = 90+buff1[i];
				double x = Math.cos((Math.PI)/96*(i+1)+Math.PI/2)*m*(5)+width/2+Math.cos((Math.PI)/96*(i+1)+Math.PI/2)*80;
				double y = Math.sin(Math.PI/96*(i+1)+Math.PI/2)*m*(5)+height/2+Math.sin(Math.PI/96*(i+1)+Math.PI/2)*80;
				getPoints().addAll(x,y);
				
			}
			
			for (int i = 0;i<96;i++) {
				double m = 90+buff1[96-i];
				double x = Math.cos((Math.PI)/96*(i+1)+Math.PI/2)*m*(-5)+width/2+Math.cos((Math.PI)/96*(i+1)+Math.PI/2)*(-1)*80;
				double y = Math.sin(Math.PI/96*(i+1)+Math.PI/2)*m*(-5)+height/2+Math.sin(Math.PI/96*(i+1)+Math.PI/2)*(-1)*80;
				getPoints().addAll(x,y);
			}
			int i = 0;
			double m = 90+buff1[i];
			double x = Math.cos((Math.PI)/96*(i+1)+Math.PI/2)*m*(5)+width/2+Math.cos((Math.PI)/96*(i+1)+Math.PI/2)*80;
			double y = Math.sin(Math.PI/96*(i+1)+Math.PI/2)*m*(5)+height/2+Math.sin(Math.PI/96*(i+1)+Math.PI/2)*80;
			getPoints().addAll(x,y);
			animCount = 2;
		}
			break;
		case 2:
		{
			for (int i = 0; i <96; i++) {
				
				double m = 90+buff1[i];
				double x = Math.cos((Math.PI)/96*(i+1)+Math.PI/2)*m*(5)+width/2+Math.cos((Math.PI)/96*(i+1)+Math.PI/2)*80;
				double y = Math.sin(Math.PI/96*(i+1)+Math.PI/2)*m*(5)+height/2+Math.sin(Math.PI/96*(i+1)+Math.PI/2)*80;
				getPoints().addAll(x,y);
				
			}
			
			for (int i = 0;i<96;i++) {
				double m = 90+buff1[96-i];
				double x = Math.cos((Math.PI)/96*(i+1)+Math.PI/2)*m*(-5)+width/2+Math.cos((Math.PI)/96*(i+1)+Math.PI/2)*(-1)*80;
				double y = Math.sin(Math.PI/96*(i+1)+Math.PI/2)*m*(-5)+height/2+Math.sin(Math.PI/96*(i+1)+Math.PI/2)*(-1)*80;
				getPoints().addAll(x,y);
			}
			int i = 0;
			double m = 90+buff1[i];
			double x = Math.cos((Math.PI)/96*(i+1)+Math.PI/2)*m*(5)+width/2+Math.cos((Math.PI)/96*(i+1)+Math.PI/2)*80;
			double y = Math.sin(Math.PI/96*(i+1)+Math.PI/2)*m*(5)+height/2+Math.sin(Math.PI/96*(i+1)+Math.PI/2)*80;
			getPoints().addAll(x,y);
			animCount = 0;
		}
			break;
		}
	}

}
