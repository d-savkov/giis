package com.example.lab1.functions;

import com.example.lab1.model.Edge;
import com.example.lab1.model.Figure;
import com.example.lab1.model.Pixel;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;

public final class TransformFigure {

	private static TransformFigure instance = null;
	private final MatrixFunctions matrixFunctions = MatrixFunctions.getInstance();

	private TransformFigure() {
	}

	public static TransformFigure getInstance() {
		if (instance == null) {
			instance = new TransformFigure();
		}
		return instance;
	}

	public Figure moveFigure(Figure oldFigure, final int MIGRATION_CONSTANT_X,
							 final int MIGRATION_CONSTANT_Y, int limitX, int limitY) {
		double[][] moveMatrix = {{1, 0, 0}, {0, 1, 0},
			{MIGRATION_CONSTANT_X, MIGRATION_CONSTANT_Y, 1}};
		return changeFigure(oldFigure, moveMatrix, limitX, limitY);
	}

	public Figure changeSizeOfFigure(Figure oldFigure, double changeSize, int limitX, int limitY) {
		double size = oldFigure.getSize() + changeSize;
		double[][] changeMatrix = {{1, 0, 0}, {0, 1, 0}, {0, 0, size}};
		return changeFigure(oldFigure, changeMatrix, limitX, limitY);

	}

	public Figure rotationFigure(Figure oldFigure, double alpha, double m, double n, int limitX,
								 int limitY) {
		double cosAlpha = Math.cos(alpha);
		double sinAlpha = Math.sin(alpha);
		double[][] rotateMatrix = {{cosAlpha, sinAlpha, 0}, {-sinAlpha, cosAlpha, 0},
			{-m * (cosAlpha - 1) + n * sinAlpha, -m * sinAlpha - n * (cosAlpha - 1), 1}};

		return changeFigure(oldFigure, rotateMatrix, limitX, limitY);
	}

	private Figure changeFigure(Figure oldFigure, double[][] matrix, int limitX, int limitY) {
		List<Edge> edgeList = new ArrayList<>();
		List<Pixel> pixelList = new ArrayList<>();
		for (Edge edge : oldFigure.getEdgeList()) {
			double[] xyStart = {edge.getStarPixel().getX(), edge.getStarPixel().getY(), 1};
			double[] xyEnd = {edge.getEndPixel().getX(), edge.getEndPixel().getY(), 1};

			xyStart = matrixFunctions.matrixMult(xyStart, matrix);
			xyEnd = matrixFunctions.matrixMult(xyEnd, matrix);

			Pixel startPixel = new Pixel((int) (Math.round(xyStart[0] / xyStart[2])),
				(int) (Math.ceil(xyStart[1] / xyStart[2])), edge.getStarPixel().getSize());
			Pixel endPixel = new Pixel((int) (Math.round(xyEnd[0] / xyEnd[2])),
				(int) (Math.ceil(xyEnd[1] / xyEnd[2])), edge.getEndPixel().getSize());

			if (checkBounds(xyStart[0] / xyStart[2], xyStart[1] / xyStart[2], limitX, limitY)
				&& checkBounds(xyEnd[0] / xyEnd[2], xyEnd[1] / xyEnd[2], limitX, limitY)) {
				if (!pixelList.contains(startPixel)) {
					pixelList.add(startPixel);
				}
				if (!pixelList.contains(endPixel)) {
					pixelList.add(endPixel);
				}
				edgeList.add(new Edge(startPixel, endPixel));
			} else {
				return oldFigure;
			}
		}
		return new Figure(pixelList, edgeList, 1);
	}

	public void temp(List<Edge> edgeList, Pixel[][] grid) {
		edgeList.forEach(edge -> {
			if (!edge.isVisible()) {
				edge.getLine().forEach(
					pixel -> grid[pixel.getX()][pixel.getY()].getRectangle().setFill(Color.WHITE));
			}
		});
		edgeList.forEach(edge -> {
			if (edge.isVisible()) {
				edge.getLine().forEach(
					pixel -> grid[pixel.getX()][pixel.getY()].getRectangle().setFill(Color.BLACK));
			}
		});
	}

	private boolean checkBounds(double x, double y, int limitX, int limitY) {
		return !(x < 0 || y < 0 || x > limitX - 1 || y > limitY - 1);
	}
}
