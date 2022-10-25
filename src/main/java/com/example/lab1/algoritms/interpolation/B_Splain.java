package com.example.lab1.algoritms.interpolation;

import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class B_Splain {

	private static final double RADIUS = 2;
	private final List<Circle> circleList;
	private final Pane pole;
	private final TextArea textAreaDebug;
	private final ResourceBundle resourceBundle;
	private final double[][] bSplainMatrix = {{-1, 3, -3, 1}, {3, -6, 3, 0}, {-3, 0, 3, 0},
		{1, 4, 1, 0}};


	public B_Splain(List<Circle> circleList, Pane pole, TextArea textAreaDebug,
					ResourceBundle resourceBundle) {
		this.circleList = circleList;
		this.pole = pole;
		this.textAreaDebug = textAreaDebug;
		this.resourceBundle = resourceBundle;

		begin();
	}

	private void begin() {
		int step = 0;

		for (int i = 0; i < circleList.size() - 2; i++) {
			Circle circle0;
			if (i == 0) {
				circle0 = new Circle(0, 0, 2);
			} else {
				circle0 = circleList.get(i - 1);
			}

			Circle circle1 = circleList.get(i);
			Circle circle2 = circleList.get(i + 1);
			Circle circle3 = circleList.get(i + 2);

			double[][] P = {{circle0.getCenterX(), circle0.getCenterY()},
				{circle1.getCenterX(), circle1.getCenterY()},
				{circle2.getCenterX(), circle2.getCenterY()},
				{circle3.getCenterX(), circle3.getCenterY()}};

			double[][] tempXY = matrixMultiplication(bSplainMatrix, P);

			for (double j = 0.0; j <= 1; j = j + 0.01) {
				double[] matrixT = {Math.pow(j, 3), Math.pow(j, 2), j, 1};
				double[] xy = matrixMultiplication(matrixT, tempXY);

				double x = xy[0] * 0.1666666667;
				double y = xy[1] * 0.1666666667;
				pole.getChildren().add(new Circle(x, y, RADIUS, Color.AQUA));
				textAreaDebug.setText(
					textAreaDebug.getText() + resourceBundle.getString("step") + " " + ++step
						+ " x=" + x + " y=" + y + "\n");
			}
		}
		textAreaDebug.setText(textAreaDebug.getText() + "\n" + "\n");
	}

	public double[] matrixMultiplication(double[] matrix1, double[][] matrix2) {
		double[] a = {0, 0};
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 4; j++) {
				a[i] = a[i] + matrix1[j] * matrix2[j][i];
			}
		}
		return a;
	}

	private double[][] matrixMultiplication(double[][] matrix1, double[][] matrix2) {
		double[][] a = {{0, 0}, {0, 0}, {0, 0}, {0, 0}};
		for (int q = 0; q < 4; q++) {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 4; j++) {
					a[q][i] = a[q][i] + matrix1[q][j] * matrix2[j][i] * 1.;
				}
			}
		}
		return a;
	}


}
