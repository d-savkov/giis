package com.example.lab1.algoritm.circle;

import com.example.lab1.dialog.Dialogs;
import com.example.lab1.function.Functions;
import com.example.lab1.model.Pixel;
import java.util.ResourceBundle;
import javafx.scene.control.TextArea;

public class AlgorithmHyperbola {

	int a;
	int b;
	private final ResourceBundle resourceBundle;
	private final Pixel[][] grid;
	private final int X_TILES;
	private final int Y_TILES;
	private final TextArea textAreaDebug;
	private final Functions functions = Functions.getInstance();

	// Конструктор для инициализации алгоритма Построения гиперболы
	// grid - поле пикселей, x_Tiles и y_Tiles - размер пикселя по x и y.
	public AlgorithmHyperbola(Pixel[][] grid, int x_TILES, int y_TILES, TextArea textAreaDebug,
							  ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
		this.grid = grid;
		X_TILES = x_TILES;
		Y_TILES = y_TILES;
		this.textAreaDebug = textAreaDebug;
		enterAB();
		textAreaDebug.setText(
			textAreaDebug.getText() + resourceBundle.getString("hyperbola") + "\n");
		begin();
	}

	//Проверка корректности входных значений
	private void enterAB() {
		int length = grid[0].length;
		a = Math.abs(
			Dialogs.getInstance().showDialog(resourceBundle.getString("hyperbolaTitleA"), length));
		b = Math.abs(
			Dialogs.getInstance().showDialog(resourceBundle.getString("hyperbolaTitleB"), length));
		while (a > length || b > length) {
			Dialogs.getInstance().errorDialog(resourceBundle.getString("error"),
				resourceBundle.getString("more_than_25"));
			a = Dialogs.getInstance().showDialog(resourceBundle.getString("hyperbolaTitleA"));
			b = Dialogs.getInstance().showDialog(resourceBundle.getString("hyperbolaTitleB"));
		}
	}


	private void begin() {
		int x = a;
		int y = 0;
		int error = (2 * a * a) - (b * b) - (2 * a * b * b);
		int i = 0;

		textAreaDebug.setText(textAreaDebug.getText() + " a=" + a + "  b=" + b + "\n");

		while (y < grid[x].length) {
			if (error <= (-1 * b * b * 0.5)) {
				error += 2 * a * a * (2 * y + 3);
				functions.plot(grid, x, y, ++i, textAreaDebug, resourceBundle);
				y++;
			} else {
				error = (2 * a * a * (3 * y + 3) - 4 * b * b * (x + 1));
				functions.plot(grid, x, y, ++i, textAreaDebug, resourceBundle);
				x++;
				y++;
			}
		}
	}
}
