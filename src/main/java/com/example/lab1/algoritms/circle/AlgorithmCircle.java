package com.example.lab1.algoritms.circle;

import com.example.lab1.dialog.Dialogs;
import com.example.lab1.functions.Functions;
import com.example.lab1.model.Pixel;
import java.util.ResourceBundle;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

public class AlgorithmCircle {

	private final ResourceBundle resourceBundle;

	private final Pixel[][] grid;
	private final int X_TILES;
	private final int Y_TILES;
	private final TextArea textAreaDebug;
	private final Functions functions = Functions.getInstance();
	private final int i = 0;

	// Конструктор для инициализации алгоритма Построения окружности
	// grid - поле пикселей, x_Tiles и y_Tiles - размер пикселя по x и y.
	public AlgorithmCircle(Pixel[][] grid, int x_TILES, int y_TILES, TextArea textAreaDebug,
						   ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
		this.grid = grid;
		X_TILES = x_TILES;
		Y_TILES = y_TILES;
		this.textAreaDebug = textAreaDebug;
		begin();
	}


	private void begin() {
		Pixel startPixel = functions.findLeftmostTilePolygon(grid, X_TILES, Y_TILES);

		int x = 0;
		int R = Dialogs.getInstance().showDialog(resourceBundle.getString("circleR"));

		// максимальное значение, которое может достигать граница окружности
		int limitR = grid[0].length;
		while (R > limitR) {
			Dialogs.getInstance().errorDialog(resourceBundle.getString("error"),
				resourceBundle.getString("not_more"), limitR);
			R = Dialogs.getInstance().showDialog(resourceBundle.getString("circleR"));
		}
		int y = R;

		int error = 2 - 2 * R;

		textAreaDebug.setText(textAreaDebug.getText() + resourceBundle.getString("circle") + "\n");
		try {
			grid[startPixel.getX()][startPixel.getY() + R].getRectangle().setFill(Color.BLACK);
			grid[startPixel.getX()][startPixel.getY() - R].getRectangle().setFill(Color.BLACK);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("bad");
		}

		algoritm(startPixel, x, y, error);
	}

	private void algoritm(Pixel center, int x, int y, int error) {
		int i = 0;
		int limit = 0;
		temp:
		while (y > limit) {
			int dz = 2 * error - 2 * x - 1;
			if (error > 0 && dz > 0) {
				y--;
				error += 1 - 2 * y;
				functions.drawCircle(grid, center, i, error, x, y, textAreaDebug, resourceBundle);
				i++;
				continue temp;
			}
			int d = 2 * error + 2 * y - 1;
			if (error < 0 && d <= 0) {
				x++;
				error += 1 + 2 * x;
				functions.drawCircle(grid, center, i, error, x, y, textAreaDebug, resourceBundle);
				i++;
				continue temp;
			}
			x++;
			y--;
			error += 2 * x - 2 * y + 2;
			functions.drawCircle(grid, center, i, error, x, y, textAreaDebug, resourceBundle);
			i++;
		}
	}


}
