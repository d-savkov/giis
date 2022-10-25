package com.example.lab1.algoritms.interpolation;

import java.util.ResourceBundle;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Interpolation {

	private static final int RADIUS = 2;
	private final double[][] matrixErmit = {{1, 0, 0, 0}, {0, 0, 1, 0}, {-3, 3, -2, -1},
		{2, -2, 1, 1}};
	private final Pane pole;
	private final Circle startNode;
	private final Circle endNode;
	private final Circle firstVector;
	private final Circle secondVector;
	private final TextArea textAreaDebug;
	private final ResourceBundle resourceBundle;
	private boolean ermit = true;


	public Interpolation(Circle startNode, Circle endNode, Circle firstVector, Circle secondVector,
						 Pane pole, TextArea textAreaDebug, ResourceBundle resourceBundle) {
		this.startNode = startNode;
		this.endNode = endNode;
		this.firstVector = firstVector;
		this.secondVector = secondVector;
		this.pole = pole;
		this.textAreaDebug = textAreaDebug;
		this.resourceBundle = resourceBundle;
	}

	public void init() {
		begin();
	}

	private void begin() {
		double[] matrixX = {startNode.getCenterX(), endNode.getCenterX(),
			Math.abs(firstVector.getCenterX() - startNode.getCenterX()),
			Math.abs(secondVector.getCenterX() - endNode.getCenterX())};
		double[] matrixY = {startNode.getCenterY(), endNode.getCenterY(),
			Math.abs(firstVector.getCenterY() - startNode.getCenterY()),
			Math.abs(secondVector.getCenterY() - endNode.getCenterY())};
		double[] xa = matrixMultiplication(matrixX, matrixErmit);
		double[] ya = matrixMultiplication(matrixY, matrixErmit);

		double x = 0;
		double y = 0;
		int step = 0;

		textAreaDebug.setText(
			textAreaDebug.getText() + resourceBundle.getString("step") + " " + step + " x="
				+ startNode.getCenterX() + " y=" + startNode.getCenterY() + "\n");
		for (double i = 0.0; i <= 1; i = i + 0.01) {
			if (ermit) {
				double[] T = {1, i, Math.pow(i, 2), Math.pow(i, 3)};
				x = matrixMultiplication(T, xa);
				y = matrixMultiplication(T, ya);
			} else {
				x = Math.pow((1 - i), 3) * startNode.getCenterX() + 3 * Math.pow((1 - i), 2) * i
					* firstVector.getCenterX() + 3 * (1 - i) * Math.pow(i, 2)
					* secondVector.getCenterX() + Math.pow(i, 3) * endNode.getCenterX();
				y = Math.pow((1 - i), 3) * startNode.getCenterY() + 3 * Math.pow((1 - i), 2) * i
					* firstVector.getCenterY() + 3 * (1 - i) * Math.pow(i, 2)
					* secondVector.getCenterY() + Math.pow(i, 3) * endNode.getCenterY();
			}
			Circle circle = new Circle(x, y, RADIUS);
			circle.setFill(Color.AQUA);
			pole.getChildren().add(circle);
			textAreaDebug.setText(
				textAreaDebug.getText() + resourceBundle.getString("step") + " " + ++step + " x="
					+ x + " y=" + y + "\n");
		}
		textAreaDebug.setText(textAreaDebug.getText() + "\n" + "\n");

	}

	private double matrixMultiplication(double[] matrix1, double[] matrix2) {
		double res = 0;
		res = matrix2[0] * matrix1[0] + matrix2[1] * matrix1[1] + matrix2[2] * matrix1[2]
			+ matrix2[3] * matrix1[3];
		return res;
	}

	private double[] matrixMultiplication(double[] matrix1, double[][] matrix2) {
		double[] a = {0, 0, 0, 0};
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				a[i] = a[i] + matrix1[j] * matrix2[i][j] * 1.;
			}
		}
		return a;
	}

	public void setErmit(boolean ermit) {
		this.ermit = ermit;
	}
}
