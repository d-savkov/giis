package com.example.lab1.algoritms.segment;

import com.example.lab1.functions.Functions;
import com.example.lab1.model.Pixel;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.TextArea;

public class AlgorithmDDA {

	private final ResourceBundle resourceBundle;

	private final Pixel[][] grid;
	private final int X_TILES;
	private final int Y_TILES;
	private final TextArea textAreaDebug;
	private final Functions functions = Functions.getInstance();
	private final List<Pixel> line = new ArrayList<>();
	private Pixel startPixel;
	private Pixel endPixel;

	// Конструктор для инициализации алгоритма ЦДА
	// grid - поле пикселей, x_Tiles и y_Tiles - размер пикселя по x и y.
	public AlgorithmDDA(Pixel[][] grid, int x_TILES, int y_TILES, TextArea textAreaDebug,
						ResourceBundle resourceBundle) {
		this.grid = grid;
		X_TILES = x_TILES;
		Y_TILES = y_TILES;
		this.resourceBundle = resourceBundle;
		this.textAreaDebug = textAreaDebug;
		initAlgorithm();
	}

	public AlgorithmDDA(Pixel[][] grid, Pixel start, Pixel end, int x_TILES, int y_TILES,
						TextArea textAreaDebug, ResourceBundle resourceBundle) {
		this.grid = grid;
		X_TILES = x_TILES;
		Y_TILES = y_TILES;
		this.resourceBundle = resourceBundle;
		this.textAreaDebug = textAreaDebug;

		initAlgorithm(start, end);
	}

	private void initAlgorithm() {
		textAreaDebug.setText(
			textAreaDebug.getText() + resourceBundle.getString("algoritm_CDA") + "\n");

		//Начальный пиксель
		startPixel = functions.findLeftmostTilePolygon(grid, X_TILES, Y_TILES);
		//Конечный пиксель
		endPixel = functions.findEndTile(grid, X_TILES, Y_TILES);

		int x1 = startPixel.getX();
		int x2 = endPixel.getX();
		int y1 = startPixel.getY();
		int y2 = endPixel.getY();

		//Вычисление разности между конечным и начальным пикселем
		int length = Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2));
		double dx = (Math.abs(x2 - x1) * 1.) / length;
		double dy = (Math.abs(y2 - y1) * 1.) / length;

		double x = x1 + 0.5 * functions.sign(dx);
		double y = y1 + 0.5 * functions.sign(dy);
		boolean checkX = startPixel.getX() > endPixel.getX();
		boolean checkY = startPixel.getY() > endPixel.getY();

		algorithm(length, x, y, dx, dy, checkX, checkY);
		textAreaDebug.setText(textAreaDebug.getText() + "\n");
	}

	private void initAlgorithm(Pixel startPixel, Pixel endPixel) {
		textAreaDebug.setText(
			textAreaDebug.getText() + resourceBundle.getString("algoritm_CDA") + "\n");

		this.startPixel = startPixel;
		this.endPixel = endPixel;

		int x1 = startPixel.getX();
		int x2 = endPixel.getX();
		int y1 = startPixel.getY();
		int y2 = endPixel.getY();

		//Вычисление разности между конечным и начальным пикселем
		int length = Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2));
		double dx = (Math.abs(x2 - x1) * 1.) / length;
		double dy = (Math.abs(y2 - y1) * 1.) / length;

		double x = x1 + 0.5 * functions.sign(dx);
		double y = y1 + 0.5 * functions.sign(dy);
		boolean checkX = startPixel.getX() > endPixel.getX();
		boolean checkY = startPixel.getY() > endPixel.getY();

		algorithm(length, x, y, dx, dy, checkX, checkY);

		textAreaDebug.setText(textAreaDebug.getText() + "\n");
	}

	private void algorithm(int length, double x, double y, double dx, double dy, boolean checkX,
						   boolean checkY) {
		for (int i = 0; i < length + 1; i++) {
			line.add(grid[(int) x][(int) y]);
			functions.plot(grid, (int) x, (int) y, i, textAreaDebug, resourceBundle);
			if (checkX) {
				x = x - dx;
			} else {
				x = x + dx;
			}
			if (checkY) {
				y = y - dy;
			} else {
				y = y + dy;
			}
		}
	}

	public List<Pixel> getLine() {
		return line;
	}

	public Pixel getStartPixel() {
		return startPixel;
	}

	public Pixel getEndPixel() {
		return endPixel;
	}

}
