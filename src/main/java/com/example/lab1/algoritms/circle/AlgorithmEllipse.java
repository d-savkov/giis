package com.example.lab1.algoritms.circle;

import com.example.lab1.dialog.Dialogs;
import com.example.lab1.functions.Functions;
import com.example.lab1.model.Pixel;
import java.util.ResourceBundle;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

public class AlgorithmEllipse {

	int aPow2;
	int bPow2;
	private final ResourceBundle resourceBundle;
	private final Pixel[][] grid;
	private final int X_TILES;
	private final int Y_TILES;
	private final TextArea textAreaDebug;
	private final Functions functions = Functions.getInstance();

	// Конструктор для инициализации алгоритма Построения элипса
	// grid - поле пикселей, x_Tiles и y_Tiles - размер пикселя по x и y.
	public AlgorithmEllipse(Pixel[][] grid, int x_TILES, int y_TILES, TextArea textAreaDebug,
							ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
		this.grid = grid;
		X_TILES = x_TILES;
		Y_TILES = y_TILES;
		this.textAreaDebug = textAreaDebug;
		enterAB();
		textAreaDebug.setText(textAreaDebug.getText() + resourceBundle.getString("ellips") + "\n");
		begin();
	}

	//Проверка корректности входных значений
	private void enterAB() {
		// максимальное значение, которое может достигать граница окружности
		int limit = grid[0].length;
		aPow2 = (int) Math.pow(
			Dialogs.getInstance().showDialog(resourceBundle.getString("hyperbolaTitleA"), limit),
			2);
		bPow2 = (int) Math.pow(
			Dialogs.getInstance().showDialog(resourceBundle.getString("hyperbolaTitleB"), limit),
			2);
		while (Math.sqrt(aPow2) > limit || Math.sqrt(aPow2) > limit) {
			Dialogs.getInstance().errorDialog(resourceBundle.getString("error"),
				resourceBundle.getString("not_more"), limit);
			aPow2 = (int) Math.pow(Dialogs.getInstance()
				.showDialog(resourceBundle.getString("hyperbolaTitleA"), limit), 2);
			bPow2 = (int) Math.pow(Dialogs.getInstance()
				.showDialog(resourceBundle.getString("hyperbolaTitleB"), limit), 2);
		}
	}

	private void begin() {
		Pixel startPixel = functions.findLeftmostTilePolygon(grid, X_TILES, Y_TILES);
		try {
			grid[startPixel.getX()][startPixel.getY() + (int) Math.sqrt(bPow2) - 1].getRectangle()
				.setFill(Color.BLACK);
			grid[startPixel.getX()][startPixel.getY() - (int) Math.sqrt(bPow2) + 1].getRectangle()
				.setFill(Color.BLACK);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("bad");
		}

		int x = 0;
		int y = (int) Math.sqrt(bPow2);
		textAreaDebug.setText(
			textAreaDebug.getText() + " a=" + (int) Math.sqrt(aPow2) + "  b=" + (int) Math.sqrt(
				bPow2) + "\n");
		algoritm(x, y, startPixel);
	}

	private void algoritm(int x, int y, Pixel center) {
		int i = 0;
		int limit = 0;
		int error = (2 - 2 * bPow2);
		temp:
		while (y > limit) {
			i++;
			int dz = 2 * error - 2 * y * aPow2;
			if (error > 0 && dz > 0) {
				y--;
				error += aPow2 - 2 * y * aPow2;
				functions.drawCircle(grid, center, i, error, x, y, textAreaDebug, resourceBundle);
				continue temp;
			}
			int d = 2 * error + 2 * y * aPow2 - 1;
			if (error < 0 && d <= 0) {
				x++;
				error += bPow2 + 2 * x * bPow2;
				functions.drawCircle(grid, center, i, error, x, y, textAreaDebug, resourceBundle);
				continue temp;
			}
			x++;
			y--;
			error += bPow2 * (2 * x + 1) + aPow2 * (1 - 2 * y);
			functions.drawCircle(grid, center, i, error, x, y, textAreaDebug, resourceBundle);
		}
	}
}
