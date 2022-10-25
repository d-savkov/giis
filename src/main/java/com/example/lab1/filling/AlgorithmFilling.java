package com.example.lab1.filling;

import com.example.lab1.model.Pixel;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

public class AlgorithmFilling {

	private final ResourceBundle resourceBundle;

	private final Pixel[][] grid;
	private final int X_TILES;
	private final int Y_TILES;
	private final TextArea textAreaDebug;
	private final Color color;

	public AlgorithmFilling(Pixel[][] grid, Pixel startPixel, Color color, int x_TILES, int y_TILES,
							TextArea textAreaDebug, ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
		this.grid = grid;
		X_TILES = x_TILES;
		Y_TILES = y_TILES;
		this.color = color;
		this.textAreaDebug = textAreaDebug;
	}

	public void simplePrimer(Pixel startPixel) {
		textAreaDebug.setText(textAreaDebug.getText() + "\n");
		textAreaDebug.setText(
			textAreaDebug.getText() + resourceBundle.getString("simple_primer") + "\n" + "\n");
		textAreaDebug.setText(textAreaDebug.getText() + resourceBundle.getString("start_point")
			+ startPixel.toString() + "\n");
		Stack<Pixel> pixelStack = new Stack<>();
		pixelStack.add(startPixel);
		while (!pixelStack.empty()) {
			Pixel temp = pixelStack.pop();
			if (checkPixel(temp)) {
//                textAreaDebug.setText(textAreaDebug.getText() + resourceBundle.getString("paint_pixel") + temp.toString() + "\n" );
				paintPixel(temp);
				addPixelsToStack(pixelStack, temp);
			} else {
//                textAreaDebug.setText(textAreaDebug.getText() + temp.toString() + "-" + resourceBundle.getString("wrong_pixel") + "\n" );

			}
		}
	}

	public void lineSeedFilling(Pixel startPixel) {
		textAreaDebug.setText(textAreaDebug.getText() + "\n");
		textAreaDebug.setText(
			textAreaDebug.getText() + resourceBundle.getString("simple_primer") + "\n" + "\n");
		textAreaDebug.setText(textAreaDebug.getText() + resourceBundle.getString("start_point")
			+ startPixel.toString() + "\n");
		Stack<Pixel> pixelStack = new Stack<>();
		pixelStack.add(startPixel);
		while (!pixelStack.empty()) {
			Pixel temp = pixelStack.pop();
			int rightX = temp.getX();
			int leftX = temp.getX();
			if (checkBoundsOfGrid(rightX, temp.getY())) {
				while (checkPixel(grid[rightX][temp.getY()])) {
					rightX--;
				}
			}
			if (checkBoundsOfGrid(leftX, temp.getY())) {
				while (checkPixel(grid[leftX][temp.getY()])) {
					leftX++;
				}
			}
			int y = temp.getY();
			paintPixelsInInterval(leftX, rightX, y);
			if (checkPixelsInInterval(leftX, rightX, y + 1)) {
				pixelStack.add(grid[temp.getX()][y + 1]);
			}
			if (checkPixelsInInterval(leftX, rightX, y - 1)) {
				pixelStack.add(grid[temp.getX()][y - 1]);
			}
		}
	}

	private boolean checkPixelsInInterval(int leftX, int rightX, int y) {
		if (leftX > rightX) {
			int temp = rightX;
			rightX = leftX;
			leftX = temp;
		}
		for (int i = leftX + 1; i < rightX; i++) {
			if (checkPixel(grid[i][y])) {
				return true;
			}
		}
		return false;
	}

	private void paintPixelsInInterval(int leftX, int rightX, int y) {
		if (leftX > rightX) {
			int temp = rightX;
			rightX = leftX;
			leftX = temp;
		}
		for (int i = leftX + 1; i < rightX; i++) {
			paintPixel(grid[i][y]);
		}
	}

	private void addPixelsToStack(Stack<Pixel> pixelStack, Pixel temp) {
		Pixel pixel1 = null;
		Pixel pixel2 = null;
		Pixel pixel3 = null;
		Pixel pixel4 = null;

		if (checkBoundsOfGrid(temp.getX() + 1, temp.getY())) {
			pixel1 = grid[temp.getX() + 1][temp.getY()];
		}
		if (checkBoundsOfGrid(temp.getX(), temp.getY() + 1)) {
			pixel2 = grid[temp.getX()][temp.getY() + 1];
		}
		if (checkBoundsOfGrid(temp.getX() - 1, temp.getY())) {
			pixel3 = grid[temp.getX() - 1][temp.getY()];
		}
		if (checkBoundsOfGrid(temp.getX(), temp.getY() - 1)) {
			pixel4 = grid[temp.getX()][temp.getY() - 1];
		}
		if (pixel1 != null) {
			pixelStack.add(pixel1);
		}
		if (pixel2 != null) {
			pixelStack.add(pixel2);
		}
		if (pixel3 != null) {
			pixelStack.add(pixel3);
		}
		if (pixel4 != null) {
			pixelStack.add(pixel4);
		}
	}

	private void paintPixel(Pixel pixel) {
		pixel.getRectangle().setFill(color);
	}

	private boolean checkPixel(Pixel pixel) {
		return pixel.getRectangle().getFill() == Color.WHITE;
	}

	private boolean checkBoundsOfGrid(int x, int y) {
		return !(x > grid.length - 1 || y > grid[1].length - 1 || x < 0 || y < 0);
	}


}
