package com.example.lab1.algoritms.segment;

import com.example.lab1.functions.Functions;
import com.example.lab1.model.Pixel;
import java.util.ResourceBundle;
import javafx.scene.control.TextArea;

public class AlgorithmBR {

	private final ResourceBundle resourceBundle;

	private final Pixel[][] grid;
	private final int X_TILES;
	private final int Y_TILES;
	private final TextArea textAreaDebug;
	private final Functions functions = Functions.getInstance();

	// Конструктор для инициализации алгоритма Брезенхема
	// grid - поле пикселей, x_Tiles и y_Tiles - размер пикселя по x и y.
	public AlgorithmBR(Pixel[][] grid, int x_TILES, int y_TILES, TextArea textAreaDebug,
					   ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
		this.grid = grid;
		X_TILES = x_TILES;
		Y_TILES = y_TILES;
		this.textAreaDebug = textAreaDebug;

		begin();
	}

	//Начало алгоритма
	private void begin() {
		textAreaDebug.setText(
			textAreaDebug.getText() + resourceBundle.getString("algoritm_BR") + "\n");

		//Начальный пиксель
		Pixel startPixel = functions.findLeftmostTilePolygon(grid, X_TILES, Y_TILES);
		//Конечный пиксель
		Pixel endPixel = functions.findEndTile(grid, X_TILES, Y_TILES);

		//Проверка расположения пикселей
		boolean checkX = startPixel.getX() > endPixel.getX();
		boolean checkY = startPixel.getY() > endPixel.getY();

		int x1 = startPixel.getX();
		int y1 = startPixel.getY();

		int x2 = endPixel.getX();
		int y2 = endPixel.getY();

		//Вычисление разности между конечным пикселем и начальным
		int dx = Math.abs(endPixel.getX() - x1);
		int dy = Math.abs(endPixel.getY() - y1);

		//Вычисление ошибки
		int e = 2 * dy - dx;
		int i = 1;
		// шаг
		int step = 0;
        if (dx == 0) {
            int length = Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2));
            for (int j = 0; j < length + 1; j++) {
                if (checkY) {
                    functions.plot(grid, x1, y1--, j + 1, textAreaDebug, resourceBundle);
                } else {
                    functions.plot(grid, x1, y1++, j + 1, textAreaDebug, resourceBundle);
                }
            }
        } else {
            do {
                functions.plot(grid, x1, y1, step, textAreaDebug, resourceBundle);
                if (e >= 0) {
                    if (checkX && checkY) {
                        y1--;
                    } else if (!checkX && !checkY) {
                        y1++;
                    }
                    if (checkX && !checkY) {
                        y1++;
                    } else if (!checkX && checkY) {
                        y1--;
                    }
                    e = e - 2 * dx;
                    step++;
                } else {
                    if (checkX && checkY) {
                        x1--;
                    } else if (!checkX && !checkY) {
                        x1++;
                    }
                    if (checkX && !checkY) {
                        x1--;
                    } else if (!checkX && checkY) {
                        x1++;
                    }
                    e = e + 2 * dy;
                    i = i + 1;
                    step++;
                }
            } while (i < dx + 1);
        }
		textAreaDebug.setText(textAreaDebug.getText() + "\n");

	}
}
