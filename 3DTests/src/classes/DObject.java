package classes;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class DObject extends MeshView {

	private Lighting l = new Lighting();

	public DObject() {
		
		/*float a = (float) Math.sqrt(32);

		float[] points = {
				-4, 0, 4,
				4, 0, 4,
				4, 0, -4,
				-4, 0, -4,
				0, -a, 0,
				0, a, 0
				};
		int[] faces = { 
				0,0,1,0,5,0,
				1,0,2,0,5,0,
				2,0,3,0,5,0,
				3,0,0,0,5,0,
				0,0,4,0,1,0,
				1,0,4,0,2,0,
				2,0,4,0,3,0,
				3,0,4,0,0,0
				};

		TriangleMesh mesh = new TriangleMesh();
		mesh.getTexCoords().addAll(0,0);
		mesh.getPoints().addAll(points);
		mesh.getFaces().addAll(faces);
		
		setDrawMode(DrawMode.FILL);
		setVisible(true);

		setMesh(mesh);

		setScaleX(40);
		setScaleY(40);
		setScaleZ(40);
		
		l.setSurfaceScale(10.0);
		setEffect(l);*/
		
		
		float[] points = {
				-3,-5,-3,	//0 A
				3,-5,-3,	//1 B
				3,-5,3,		//2 C
				-3,-5,3,	//3 D
				-1,0,-1,	//4 E
				1,0,-1,		//5 F
				1,0,1,		//6 G
				-1,0,1,		//7 H
				-3,5,-3,	//8 I
				3,5,-3,		//9 J
				3,5,3,		//10 K
				-3,5,3		//11 L
		};
		
		
		
		int[] faces = {
				5,0,4,0,7,0,
				7,0,6,0,5,0,
				4,0,5,0,0,0,
				5,0,1,0,0,0,
				5,0,6,0,1,0,
				6,0,2,0,1,0,
				2,0,6,0,7,0,
				2,0,7,0,3,0,
				3,0,4,0,0,0,
				3,0,7,0,4,0,
				0,0,1,0,2,0,
				2,0,3,0,0,0
			
		};
		

		/*float[] points = {
				-3,-5,-3,	//0 A
				3,-5,-3,	//1 B
				3,-5,3,		//2 C
				-3,-5,3,	//3 D
				-1,0,-1,	//4 E
				1,0,-1,		//5 F
				1,0,1,		//6 G
				-1,0,1,		//7 H
				-3,5,-3,	//8 I
				3,5,-3,		//9 J
				3,5,3,		//10 K
				-3,5,3		//11 L
		};
		
		int[] faces = {
				5,0,4,0,7,0,
				7,0,6,0,5,0,
				4,0,5,0,0,0,
				5,0,1,0,0,0,
				5,0,6,0,1,0,
				6,0,2,0,1,0,
				2,0,6,0,7,0,
				2,0,7,0,3,0,
				3,0,4,0,0,0,
				3,0,7,0,4,0,
				0,0,1,0,2,0,
				2,0,3,0,0,0
			
		};*/
		
		
		TriangleMesh mesh = new TriangleMesh();
		mesh.getTexCoords().addAll(0,0);
		mesh.getPoints().addAll(points);
		mesh.getFaces().addAll(faces);
		
		setDrawMode(DrawMode.FILL);
		setVisible(true);

		setMesh(mesh);

		setScaleX(90);
		setScaleY(90);
		setScaleZ(90);
		
		//setCullFace(CullFace.BACK);
		
		setOnMouseEntered(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {

				Frame.startRotate();

			}
		});

		setOnMouseExited(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {

				Frame.stopRotate();
			}
		});

	}

}
