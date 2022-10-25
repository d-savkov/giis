package com.example.lab1.function;

import com.example.lab1.model.Pixel;
import java.util.ResourceBundle;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

public final class Functions {

	private static Functions instance = null;

	private Functions() {
	}

	public static synchronized Functions getInstance() {
		if (instance == null) {
			instance = new Functions();
		}
		return instance;
	}

	public Pixel findLeftmostTilePolygon(Pixel[][] grid, int X_TILES, int Y_TILES) {
		for (int i = 0; i < X_TILES; i++) {
			for (int j = 0; j < Y_TILES; j++) {
				Pixel pixel = grid[i][j];
				if (pixel.isStart()) {
					return pixel;
				}
			}
		}
		return null;
	}

	public Pixel findEndTile(Pixel[][] grid, int X_TILES, int Y_TILES) {
		for (int i = 0; i < X_TILES; i++) {
			for (int j = 0; j < Y_TILES; j++) {
				Pixel pixel = grid[i][j];
				if (pixel.isEnd()) {
					return pixel;
				}
			}
		}
		return null;
	}

	public int sign(double a) {
		return (a > 0) ? 1 : (a < 0) ? -1 : 0;
	}

	public void plot(Pixel[][] grid, int x, int y, int step, TextArea textAreaDebug,
					 ResourceBundle resourceBundle) {
		grid[x][y].getRectangle().setFill(Color.BLACK);
		textAreaDebug.setText(
			textAreaDebug.getText() + resourceBundle.getString("step") + " " + step + " x=" + x
				+ " y=" + y);
		textAreaDebug.setText(textAreaDebug.getText() + "\n");

	}

	public void drawCircle(Pixel[][] grid, Pixel center, int step, int error, int x, int y,
						   TextArea textAreaDebug, ResourceBundle resourceBundle) {
		try {
			grid[x + center.getX()][y + center.getY()].getRectangle().setFill(Color.BLACK);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("bad");
		}
		try {
			grid[-x + center.getX()][-y + center.getY()].getRectangle().setFill(Color.BLACK);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("bad");
		}
		try {
			grid[x + center.getX()][-y + center.getY()].getRectangle().setFill(Color.BLACK);

		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("bad");
		}
		try {
			grid[-x + center.getX()][y + center.getY()].getRectangle().setFill(Color.BLACK);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("bad");
		}

		textAreaDebug.setText(
			textAreaDebug.getText() + resourceBundle.getString("step") + " " + step + " x=" + x
				+ " y=" + y + " error=" + error);
		textAreaDebug.setText(textAreaDebug.getText() + "\n");
	}

	public void plot(Pixel[][] grid, int x, int y, int step, int error, TextArea textAreaDebug,
					 ResourceBundle resourceBundle) {
		grid[x][y].getRectangle().setFill(Color.BLACK);
		textAreaDebug.setText(
			textAreaDebug.getText() + resourceBundle.getString("step") + " " + step + " x=" + x
				+ " y=" + y + " error=" + error);
		textAreaDebug.setText(textAreaDebug.getText() + "\n");

	}

	public void plot(Pixel[][] grid, int x, int y, int changeX, int changeY, double intencivity,
					 int step, TextArea textAreaDebug, ResourceBundle resourceBundle) {
		double temp = Math.abs(intencivity);
		grid[x][y].getRectangle().setFill(new Color(0, 0, 0, Math.abs(1 - temp)));

		if (intencivity < 0) {
			grid[x - changeX][y - changeY].getRectangle().setFill(new Color(0, 0, 0, temp));
			textAreaDebug.setText(
				textAreaDebug.getText() + resourceBundle.getString("step") + " " + step + ".5"
					+ " x=" + x + " y=" + (y + changeY) + " i=" + Math.abs(temp - 1));
			textAreaDebug.setText(textAreaDebug.getText() + "\n");

		} else {
			grid[x + changeX][y + changeY].getRectangle().setFill(new Color(0, 0, 0, temp));
			textAreaDebug.setText(
				textAreaDebug.getText() + resourceBundle.getString("step") + " " + step + ".5"
					+ " x=" + x + " y=" + (y - changeY) + " i=" + Math.abs(temp - 1));
			textAreaDebug.setText(textAreaDebug.getText() + "\n");
		}

		textAreaDebug.setText(
			textAreaDebug.getText() + resourceBundle.getString("step") + " " + step + " x=" + x
				+ " y=" + y + " i=" + temp);
		textAreaDebug.setText(textAreaDebug.getText() + "\n");
	}
}
