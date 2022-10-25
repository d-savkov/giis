package com.example.lab1.algoritms.clipping;

import com.example.lab1.algoritms.segment.AlgorithmDDA;
import com.example.lab1.functions.Functions;
import com.example.lab1.model.Edge;
import com.example.lab1.model.Pixel;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

public class CyrusBeck {

	private final Pixel[][] grid;
	private final TextArea textAreaDebug;
	private final ResourceBundle resourceBundle;
	private final Functions functions = Functions.getInstance();
	private final List<Edge> rebsOfFigure;
	private final List<Pixel> vertexOfFigure;
	private List<Pixel> normalOfEdge;

	private final int X_TILES;
	private final int Y_TILES;

	public CyrusBeck(Pixel[][] grid, int X_TILES, int Y_TILES, TextArea textAreaDebug,
					 ResourceBundle resourceBundle) {
		this.grid = grid;
		this.textAreaDebug = textAreaDebug;
		this.resourceBundle = resourceBundle;
		this.X_TILES = X_TILES;
		this.Y_TILES = Y_TILES;
		rebsOfFigure = new ArrayList<>();
		vertexOfFigure = new ArrayList<>();
		normalOfEdge = new ArrayList<>();

		preparedAlgorithm();
		if (!checkingFigureForConvexity(rebsOfFigure)) {
			//ошибочка. Фигура не является выпуклой
		}

	}

	public void startAlgorithm(List<Edge> inputLines) {
		List<Edge> edgeList = new ArrayList<>(inputLines);
		for (Edge line : edgeList) {
			paintEdge(line);
			clippingLineCyrusBeck(line);
		}
		rebsOfFigure.forEach(reb ->
			reb.getLine().forEach(pixel ->
				pixel.getRectangle().setFill(Color.BLACK)));
	}


	private void clippingLineCyrusBeck(Edge line) {
		Pixel starPixel = line.getStarPixel();
		Pixel endPixel = line.getEndPixel();
		Pixel d = new Pixel(endPixel.getX() - starPixel.getX(), endPixel.getY() - starPixel.getY(),
			grid[0][0].getSize());

		double tn = 0;
		double tv = 1;

		List<Integer> wnList = new ArrayList<>();
		List<Integer> wnDnList = new ArrayList<>();

		a:
		for (int i = 0; i < vertexOfFigure.size(); i++) {
			Pixel f = vertexOfFigure.get(i);
			Pixel normal = normalOfEdge.get(i);

			Pixel w = new Pixel(starPixel.getX() - f.getX(), starPixel.getY() - f.getY(),
				grid[0][0].getSize());

			int wn = w.getX() * normal.getX() + w.getY() * normal.getY();
			int dn = d.getX() * normal.getX() + d.getY() * normal.getY();

			if (dn == 0) {
				if (wn < 0) {
					break;
				}
			} else {
				double t = -(wn * 1.0 / dn);
				if (t >= 0 && t <= 1) {
					if (dn < 0) {
						if (t < tv) {
							tv = t;
						}
						if (t < tn) {
							break;
						}
					}
					if (dn > 0) {
						if (t > tn) {
							tn = t;
						}
						if (t > tv) {
							break;
						}
					}
				} else {
					wnList.add(wn);
					wnDnList.add(wn + dn);
				}
			}
		}

		if (vertexOfFigure.size() == wnDnList.size()) {
			wnList.removeIf(wn -> wn > 0);
			wnDnList.removeIf(wndn -> wndn > 0);
			if (wnList.size() == wnDnList.size() && wnList.size() == vertexOfFigure.size()) {
				new AlgorithmDDA(grid, starPixel, endPixel, X_TILES, Y_TILES, textAreaDebug,
					resourceBundle);
			}
		} else if (tn < tv) {
			wnList.removeIf(wn -> wn < 0);
			wnDnList.removeIf(wndn -> wndn < 0);
			if (!wnList.isEmpty() || !wnDnList.isEmpty()) {
				Pixel s = new Pixel(
					(int) Math.ceil(starPixel.getX() + (endPixel.getX() - starPixel.getX()) * tn),
					(int) Math.ceil(starPixel.getY() + (endPixel.getY() - starPixel.getY()) * tn),
					grid[0][0].getSize());
				Pixel t = new Pixel(
					(int) Math.ceil(starPixel.getX() + (endPixel.getX() - starPixel.getX()) * tv),
					(int) Math.ceil(starPixel.getY() + (endPixel.getY() - starPixel.getY()) * tv),
					grid[0][0].getSize());
				new AlgorithmDDA(grid, s, t, X_TILES, Y_TILES, textAreaDebug, resourceBundle);
			}

		}

	}

	private void paintEdge(Edge line) {
		line.getLine().forEach(pixel -> {
			pixel.getRectangle().setFill(Color.WHITE);
		});
	}


	private void preparedAlgorithm() {
		Pixel first = new Pixel(8, 6, grid[0][0].getSize());
		Pixel second = new Pixel(8, 12, grid[0][0].getSize());
		Pixel third = new Pixel(12, 16, grid[0][0].getSize());
		Pixel fourth = new Pixel(20, 16, grid[0][0].getSize());
		Pixel fifth = new Pixel(24, 12, grid[0][0].getSize());
		Pixel sixth = new Pixel(24, 6, grid[0][0].getSize());
		Pixel seventh = new Pixel(20, 2, grid[0][0].getSize());
		Pixel eighth = new Pixel(12, 2, grid[0][0].getSize());

		vertexOfFigure.add(first);
		vertexOfFigure.add(second);
		vertexOfFigure.add(third);
		vertexOfFigure.add(fourth);
		vertexOfFigure.add(fifth);
		vertexOfFigure.add(sixth);
		vertexOfFigure.add(seventh);
		vertexOfFigure.add(eighth);

		AlgorithmDDA algorithmDDA = new AlgorithmDDA(grid, first, second, X_TILES, Y_TILES,
			textAreaDebug, resourceBundle);
		AlgorithmDDA algorithmDDA1 = new AlgorithmDDA(grid, second, third, X_TILES, Y_TILES,
			textAreaDebug, resourceBundle);
		AlgorithmDDA algorithmDDA2 = new AlgorithmDDA(grid, third, fourth, X_TILES, Y_TILES,
			textAreaDebug, resourceBundle);
		AlgorithmDDA algorithmDDA3 = new AlgorithmDDA(grid, fourth, fifth, X_TILES, Y_TILES,
			textAreaDebug, resourceBundle);
		AlgorithmDDA algorithmDDA4 = new AlgorithmDDA(grid, fifth, sixth, X_TILES, Y_TILES,
			textAreaDebug, resourceBundle);
		AlgorithmDDA algorithmDDA5 = new AlgorithmDDA(grid, sixth, seventh, X_TILES, Y_TILES,
			textAreaDebug, resourceBundle);
		AlgorithmDDA algorithmDDA6 = new AlgorithmDDA(grid, seventh, eighth, X_TILES, Y_TILES,
			textAreaDebug, resourceBundle);
		AlgorithmDDA algorithmDDA7 = new AlgorithmDDA(grid, eighth, first, X_TILES, Y_TILES,
			textAreaDebug, resourceBundle);
		initRebOfFigure(algorithmDDA, algorithmDDA1, algorithmDDA2, algorithmDDA3, algorithmDDA4,
			algorithmDDA5, algorithmDDA6, algorithmDDA7);
		normalOfEdge = findInternalNormals();
	}

	private void initRebOfFigure(AlgorithmDDA algorithmDDA, AlgorithmDDA algorithmDDA1,
								 AlgorithmDDA algorithmDDA2, AlgorithmDDA algorithmDDA3,
								 AlgorithmDDA algorithmDDA4, AlgorithmDDA algorithmDDA5,
								 AlgorithmDDA algorithmDDA6, AlgorithmDDA algorithmDDA7) {
		rebsOfFigure.add(new Edge(algorithmDDA.getStartPixel(), algorithmDDA.getEndPixel(),
			algorithmDDA.getLine()));
		rebsOfFigure.add(new Edge(algorithmDDA1.getStartPixel(), algorithmDDA1.getEndPixel(),
			algorithmDDA1.getLine()));
		rebsOfFigure.add(new Edge(algorithmDDA2.getStartPixel(), algorithmDDA2.getEndPixel(),
			algorithmDDA2.getLine()));
		rebsOfFigure.add(new Edge(algorithmDDA3.getStartPixel(), algorithmDDA3.getEndPixel(),
			algorithmDDA3.getLine()));
		rebsOfFigure.add(new Edge(algorithmDDA4.getStartPixel(), algorithmDDA4.getEndPixel(),
			algorithmDDA4.getLine()));
		rebsOfFigure.add(new Edge(algorithmDDA5.getStartPixel(), algorithmDDA5.getEndPixel(),
			algorithmDDA5.getLine()));
		rebsOfFigure.add(new Edge(algorithmDDA6.getStartPixel(), algorithmDDA6.getEndPixel(),
			algorithmDDA6.getLine()));
		rebsOfFigure.add(new Edge(algorithmDDA7.getStartPixel(), algorithmDDA7.getEndPixel(),
			algorithmDDA7.getLine()));
	}

	private boolean checkingFigureForConvexity(List<Edge> listOfReb) {
		Edge reb = null;
		Edge next = null;
		List<Integer> multOfEdgeList = fillVectorMultiplicationList(listOfReb);
		if (multOfEdgeList.get(0) >= 0) {
			for (Integer mult : multOfEdgeList) {
				if (mult < 0) {
					return false;
				}
			}
		} else {
			for (Integer mult : multOfEdgeList) {
				if (mult > 0) {
					return false;
				}
			}
		}
		return true;
	}

	private List<Integer> fillVectorMultiplicationList(List<Edge> listOfReb) {
		Edge reb;
		Edge next;
		List<Integer> vectorMultiplicationList = new ArrayList<>();
		for (int i = 0; i < listOfReb.size(); i++) {
			reb = listOfReb.get(i);
			if (i + 1 != listOfReb.size()) {
				next = listOfReb.get(i + 1);
			} else {
				next = listOfReb.get(0);
			}
			int mult = findMultOfEdges(reb, next);
			vectorMultiplicationList.add(mult);
		}
		return vectorMultiplicationList;
	}

	private int findMultOfEdges(Edge reb, Edge next) {
		double res = 0;
		Pixel starPixelOfReb = reb.getStarPixel();
		Pixel endPixelOfReb = reb.getEndPixel();
		Pixel starPixelOfNext = next.getStarPixel();
		Pixel endPixelOfNext = next.getEndPixel();
		int a = (int) Math.sqrt(
			Math.pow(starPixelOfReb.getX() - endPixelOfReb.getX(), 2) + Math.pow(
				starPixelOfReb.getY() - endPixelOfReb.getY(), 2));
		int b = (int) Math.sqrt(
			Math.pow(starPixelOfNext.getX() - endPixelOfNext.getX(), 2) + Math.pow(
				starPixelOfNext.getY() - endPixelOfNext.getY(), 2));
		int c = (int) Math.sqrt(
			Math.pow(endPixelOfReb.getX() - endPixelOfNext.getX(), 2) + Math.pow(
				endPixelOfReb.getY() - endPixelOfNext.getY(), 2));
		res = Math.acos((Math.pow(b, 2) + Math.pow(c, 2) - Math.pow(a, 2)) / 2 * b * c);
		return (int) Math.sin(res) * a * b;
	}

	private List<Pixel> findInternalNormals() {
		List<Pixel> vectorsPerpendicular = new ArrayList<>();
		for (Edge reb : rebsOfFigure) {
			Pixel starPixelOfReb = reb.getStarPixel();
			Pixel endPixelOfReb = reb.getEndPixel();

			vectorsPerpendicular.add(new Pixel(-(starPixelOfReb.getY() - endPixelOfReb.getY()),
				(starPixelOfReb.getX() - endPixelOfReb.getX()), grid[0][0].getSize()));
		}
		List<Pixel> internalNormals = new ArrayList<>();
		for (int i = 0; i < vertexOfFigure.size(); i++) {
			Pixel v0 = vertexOfFigure.get(i);
			Pixel v1 = vertexOfFigure.get(((i + 1) % vertexOfFigure.size()));
			Pixel v2 = vertexOfFigure.get((i + 2) % vertexOfFigure.size());

			if (Math.signum(
				-(v1.getY() - v0.getY()) * (v2.getX() - v0.getX()) + (v1.getX() - v0.getX()) * (
					v2.getY() - v0.getY())) == -1) {
				internalNormals.add(vectorsPerpendicular.get(i));
			} else {
				internalNormals.add(new Pixel((-1) * vectorsPerpendicular.get(i).getX(),
					(-1) * (-1) * vectorsPerpendicular.get(i).getY(), grid[0][0].getSize()));
			}
		}

		return internalNormals;
	}


}
