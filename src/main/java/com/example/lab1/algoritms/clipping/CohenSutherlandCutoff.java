package com.example.lab1.algoritms.clipping;

import com.example.lab1.algoritms.segment.AlgorithmDDA;
import com.example.lab1.functions.Functions;
import com.example.lab1.model.CohenSutherland;
import com.example.lab1.model.Edge;
import com.example.lab1.model.Pixel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

public class CohenSutherlandCutoff {

	private final String CENTER = "falsefalsefalsefalse";
	private final String LEFT = "falsefalsefalsetrue";
	private final String RIGH = "falsefalsetruefalse";
	private final String UP = "truefalsefalsefalse";
	private final String UP_LEFT = "truefalsefalsetrue";
	private final String UP_RIGHT = "truefalsetruefalse";
	private final String DOWN = "falsetruefalsefalse";
	private final String DOWN_LEFT = "falsetruefalsetrue";
	private final String DOWN_RIGHT = "falsetruetruefalse";
	private final Pixel[][] grid;
	private final TextArea textAreaDebug;
	private final ResourceBundle resourceBundle;
	private final Functions functions = Functions.getInstance();
	private final int X_TILES;
	private final int Y_TILES;
	private int upBorder;
	private int downBorder;
	private int rightBorder;
	private int leftBorder;


	public CohenSutherlandCutoff(Pixel[][] grid, int X_TILES, int Y_TILES, TextArea textAreaDebug,
								 ResourceBundle resourceBundle) {
		this.grid = grid;
		this.textAreaDebug = textAreaDebug;
		this.resourceBundle = resourceBundle;
		this.X_TILES = X_TILES;
		this.Y_TILES = Y_TILES;
		preparedAlgorithm();
	}

	private void preparedAlgorithm() {
		Pixel firstPixel = new Pixel(7, 2, grid[0][0].getSize());
		Pixel secondPixel = new Pixel(7, 22, grid[0][0].getSize());
		Pixel thirdPixel = new Pixel(27, 22, grid[0][0].getSize());
		Pixel fourthPixel = new Pixel(27, 2, grid[0][0].getSize());

		AlgorithmDDA algorithmDDA = new AlgorithmDDA(grid, firstPixel, secondPixel, X_TILES,
			Y_TILES, textAreaDebug, resourceBundle);
		AlgorithmDDA algorithmDDA1 = new AlgorithmDDA(grid, secondPixel, thirdPixel, X_TILES,
			Y_TILES, textAreaDebug, resourceBundle);
		AlgorithmDDA algorithmDDA2 = new AlgorithmDDA(grid, thirdPixel, fourthPixel, X_TILES,
			Y_TILES, textAreaDebug, resourceBundle);
		AlgorithmDDA algorithmDDA3 = new AlgorithmDDA(grid, fourthPixel, firstPixel, X_TILES,
			Y_TILES, textAreaDebug, resourceBundle);

		fillBorder(algorithmDDA, algorithmDDA1, algorithmDDA2, algorithmDDA3);
	}

	private void fillBorder(AlgorithmDDA algorithmDDA, AlgorithmDDA algorithmDDA1,
							AlgorithmDDA algorithmDDA2, AlgorithmDDA algorithmDDA3) {
		List<Pixel> pixelList = algorithmDDA.getLine();
		List<Pixel> pixelList1 = algorithmDDA1.getLine();
		List<Pixel> pixelList2 = algorithmDDA2.getLine();
		List<Pixel> pixelList3 = algorithmDDA3.getLine();
		int x = pixelList.get(1).getX();
		int x1 = pixelList1.get(1).getX();
		int x2 = pixelList2.get(1).getX();
		int x3 = pixelList3.get(1).getX();
		if (x < x1 && x2 > x3) {
			int y = pixelList1.get(1).getY();
			int y1 = pixelList3.get(1).getY();
			if (y > y1) {
				upBorder = y1;
				downBorder = y;
			} else {
				upBorder = y;
				downBorder = y1;
			}
			if (x < x2) {
				leftBorder = x;
				rightBorder = x2;
			} else {
				leftBorder = x2;
				rightBorder = x;
			}
		} //просмотреть все варианты
	}


	public void startAlgorithm(List<Edge> edgeList) {
		for (Edge edge : edgeList) {
			checkUp(edge);
			checkDown(edge);
			checkRight(edge);
			checkLeft(edge);
		}
		List<Edge> result = findBitwiseAnd(edgeList);
		removePartOfEdge(result);
	}

	private void removePartOfEdge(List<Edge> edgeList) {
		for (Edge edge : edgeList) {
			remove(edge);
		}
	}

	private void remove(Edge edge) {
		Pixel starPixel = edge.getStarPixel();
		Pixel endPixel = edge.getEndPixel();
		if (getCohenSutherlandByInteger(starPixel).equals(UP) || getCohenSutherlandByInteger(
			endPixel).equals(UP)) {
			removeUp(edge);
		}
		if (getCohenSutherlandByInteger(starPixel).equals(DOWN) || getCohenSutherlandByInteger(
			endPixel).equals(DOWN)) {
			removeDown(edge);
		}
		if ((getCohenSutherlandByInteger(starPixel).equals(LEFT) || getCohenSutherlandByInteger(
			endPixel).equals(LEFT)) ||
			(getCohenSutherlandByInteger(starPixel).equals(UP_LEFT) || getCohenSutherlandByInteger(
				endPixel).equals(UP_LEFT)) ||
			(getCohenSutherlandByInteger(starPixel).equals(DOWN_LEFT)
				|| getCohenSutherlandByInteger(endPixel).equals(DOWN_LEFT))) {
			removeLeft(edge);
		}
		if ((getCohenSutherlandByInteger(starPixel).equals(RIGH) || getCohenSutherlandByInteger(
			endPixel).equals(RIGH)) ||
			(getCohenSutherlandByInteger(starPixel).equals(UP_RIGHT) || getCohenSutherlandByInteger(
				endPixel).equals(UP_RIGHT)) ||
			(getCohenSutherlandByInteger(starPixel).equals(DOWN_RIGHT)
				|| getCohenSutherlandByInteger(endPixel).equals(DOWN_RIGHT))) {
			removeRight(edge);
		}
	}

	private void removeRight(Edge edge) {
		for (Pixel pixel : edge.getLine()) {
			if (pixel.getX() > rightBorder) {
				pixel.getRectangle().setFill(Color.WHITE);
			}
		}
	}

	private void removeLeft(Edge edge) {
		for (Pixel pixel : edge.getLine()) {
			if (pixel.getX() < leftBorder) {
				pixel.getRectangle().setFill(Color.WHITE);
			}
		}
	}

	private void removeDown(Edge edge) {
		for (Pixel pixel : edge.getLine()) {
			if (pixel.getY() > downBorder) {
				pixel.getRectangle().setFill(Color.WHITE);
			}
		}
	}

	private void removeUp(Edge edge) {
		for (Pixel pixel : edge.getLine()) {
			if (pixel.getY() < upBorder) {
				pixel.getRectangle().setFill(Color.WHITE);
			}
		}
	}

	private void checkUp(Edge edge) {
		Pixel starPixel = edge.getStarPixel();
		Pixel endPixel = edge.getEndPixel();

		upBorder(starPixel);
		upBorder(endPixel);
	}

	private void upBorder(Pixel pixel) {
		if (pixel.getY() < upBorder) {
			pixel.getCohenSutherland().setUp(true);
		}
	}

	private void checkDown(Edge edge) {
		Pixel starPixel = edge.getStarPixel();
		Pixel endPixel = edge.getEndPixel();

		downBorder(starPixel);
		downBorder(endPixel);
	}

	private void downBorder(Pixel pixel) {
		if (pixel.getY() > downBorder) {
			pixel.getCohenSutherland().setDown(true);
		}
	}

	private void checkRight(Edge edge) {
		Pixel starPixel = edge.getStarPixel();
		Pixel endPixel = edge.getEndPixel();

		rightBorder(starPixel);
		rightBorder(endPixel);
	}

	private void rightBorder(Pixel pixel) {
		if (pixel.getX() > rightBorder) {
			pixel.getCohenSutherland().setRight(true);
		}
	}

	private void checkLeft(Edge edge) {
		Pixel starPixel = edge.getStarPixel();
		Pixel endPixel = edge.getEndPixel();

		leftBorder(starPixel);
		leftBorder(endPixel);
	}

	private void leftBorder(Pixel pixel) {
		if (pixel.getX() < leftBorder) {
			pixel.getCohenSutherland().setLeft(true);
		}
	}

	private List<Edge> findBitwiseAnd(List<Edge> edgeList) {
		List<Edge> result = new ArrayList<>(edgeList);
		Iterator<Edge> edgeIterator = result.iterator();
		while (edgeIterator.hasNext()) {
			Edge edge = edgeIterator.next();
			String res = bitwiseAnd(edge);
			if (res.equals(LEFT)) {
				String startCohenSutherlandByString = getCohenSutherlandByInteger(
					edge.getStarPixel());
				String endCohenSutherlandByString = getCohenSutherlandByInteger(edge.getEndPixel());
				if (startCohenSutherlandByString.equals(LEFT) && endCohenSutherlandByString.equals(
					LEFT)) {
					edgeIterator.remove();
				}
			}
		}
		return result;
	}

	private String bitwiseAnd(Edge edge) {
		CohenSutherland startCohenSutherland = edge.getStarPixel().getCohenSutherland();
		CohenSutherland endCohenSutherland = edge.getEndPixel().getCohenSutherland();
		boolean up = (startCohenSutherland.isUp() && endCohenSutherland.isUp());
		boolean down = (startCohenSutherland.isDown() && endCohenSutherland.isDown());
		boolean right = (startCohenSutherland.isRight() && endCohenSutherland.isRight());
		boolean left = (startCohenSutherland.isLeft() && endCohenSutherland.isLeft());

		return Boolean.toString(up) + down + right
			+ left;
	}

	private String getCohenSutherlandByInteger(Pixel pixel) {
		CohenSutherland cohenSutherland = pixel.getCohenSutherland();
		boolean up = cohenSutherland.isUp();
		boolean down = cohenSutherland.isDown();
		boolean right = cohenSutherland.isRight();
		boolean left = cohenSutherland.isLeft();

		return Boolean.toString(up) + down + right
			+ left;
	}
}
