package com.example.lab1.algoritm.segment;

import com.example.lab1.function.Functions;
import com.example.lab1.model.Pixel;
import java.util.ResourceBundle;
import javafx.scene.control.TextArea;

public class AlgorithmBR {

	private final ResourceBundle resourceBundle;

	private final Pixel[][] grid;
	private final int xTiles;
	private final int yTiles;
	private final TextArea textAreaDebug;
	private final Functions functions = Functions.getInstance();

	public AlgorithmBR(Pixel[][] grid, int xTiles, int yTiles, TextArea textAreaDebug,
					   ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
		this.grid = grid;
		this.xTiles = xTiles;
		this.yTiles = yTiles;
		this.textAreaDebug = textAreaDebug;

		begin();
	}

	private void begin() {
		textAreaDebug.setText(
			textAreaDebug.getText() + resourceBundle.getString("algoritm_BR") + "\n");

		//Начальный пиксель
		Pixel startPixel = functions.findLeftmostTilePolygon(grid, xTiles, yTiles);
		//Конечный пиксель
		Pixel endPixel = functions.findEndTile(grid, xTiles, yTiles);

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
