package audiotests;

import java.lang.reflect.Field;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
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

	private CheckMenuItem toggle;
	private Menu menu;
	private ToggleGroup tog;
	
	private boolean fillable;

	public Visual(double radius) {

		this.rad = radius;

		this.setStrokeWidth(5.6);
		li = new Lighting();
		l = new Light.Distant(-135, 50, Color.WHITESMOKE);
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

		switch (animCount) {
		case 0: {
			for (int i = 0; i < 96; i++) {
				double m = 90 + buff1[i];
				subAnimate1(i, m, width, height);

			}

			for (int i = 0; i < 96; i++) {
				double m = 90 + buff1[96 - i];
				subAnimate2(i, m, width, height);
			}
			int i = 0;
			double m = 90 + buff1[i];
			subAnimate1(i, m, width, height);
			animCount = 1;
		}

			break;
		case 1: {
			for (int i = 0; i < 96; i++) {
				double m = 90 + buff1[i];
				subAnimate1(i, m, width, height);

			}

			for (int i = 0; i < 96; i++) {
				double m = 90 + buff1[96 - i];
				subAnimate2(i, m, width, height);
			}
			int i = 0;
			double m = 90 + buff1[i];
			subAnimate1(i, m, width, height);
			animCount = 2;
		}
			break;
		case 2: {
			for (int i = 0; i < 96; i++) {

				double m = 90 + buff1[i];
				subAnimate1(i, m, width, height);

			}

			for (int i = 0; i < 96; i++) {
				double m = 90 + buff1[96 - i];
				subAnimate2(i, m, width, height);
			}
			int i = 0;
			double m = 90 + buff1[i];
			subAnimate1(i, m, width, height);
			animCount = 0;
		}
			break;
		}
	}

	public void subAnimate1(int i, double m, double width, double height) {
		double x = Math.cos((Math.PI) / 96 * (i + 1) + Math.PI / 2) * m * (5) + width / 2
				+ Math.cos((Math.PI) / 96 * (i + 1) + Math.PI / 2) * 80;
		double y = Math.sin(Math.PI / 96 * (i + 1) + Math.PI / 2) * m * (5) + height / 2
				+ Math.sin(Math.PI / 96 * (i + 1) + Math.PI / 2) * 80;
		getPoints().addAll(x, y);
	}

	public void subAnimate2(int i, double m, double width, double height) {
		double x = Math.cos((Math.PI) / 96 * (i + 1) + Math.PI / 2) * m * (-5) + width / 2
				+ Math.cos((Math.PI) / 96 * (i + 1) + Math.PI / 2) * (-1) * 80;
		double y = Math.sin(Math.PI / 96 * (i + 1) + Math.PI / 2) * m * (-5) + height / 2
				+ Math.sin(Math.PI / 96 * (i + 1) + Math.PI / 2) * (-1) * 80;
		getPoints().addAll(x, y);
	}

	public CheckMenuItem toggleVis(String id) {
		toggle = new CheckMenuItem("Polygon " + id);
		toggle.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (toggle.isSelected()) {
					setVisible(true);
				} else {
					setVisible(false);
				}
			}

		});
		toggle.setSelected(true);
		return toggle;
	}

	public Menu men(String id, String[] colors) {
		menu = new Menu("Color Polygon " + id);
		tog = new ToggleGroup();
		for (int i = 0; i < colors.length; i++) {
			menu.getItems().add(changeColor(colors[i]));
		}
		menu.getItems().add(toggleFillable());
		
		return menu;
	}
	
	public CheckMenuItem toggleFillable() {
		CheckMenuItem item = new CheckMenuItem("Fillable");
		item.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				fillable = item.isSelected();
				
				if(!fillable) {
					setFill(Color.TRANSPARENT);
				}
				else {
					setFill(((Color) getStroke()));
				}
			}
			
		});
		
		item.setSelected(true);
		
		return item;
	}

	public RadioMenuItem changeColor(String color) {
		
		Color c = Color.web(color);
		
		RadioMenuItem item = new RadioMenuItem(color);
		item.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (fillable) {
				setFill(c);
				}
				
				setStroke(c.brighter());
			}

		});

		item.setToggleGroup(tog);
		return item;
	}

}
