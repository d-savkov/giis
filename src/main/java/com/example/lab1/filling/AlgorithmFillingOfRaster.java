package com.example.lab1.filling;

import com.example.lab1.model.Edge;
import com.example.lab1.model.PairOfEdges;
import com.example.lab1.model.Pixel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;


public class AlgorithmFillingOfRaster {

	private final ResourceBundle resourceBundle;


	private final Pixel[][] grid;
	private final int X_TILES;
	private final int Y_TILES;
	private final TextArea textAreaDebug;
	private final Color color;

	public AlgorithmFillingOfRaster(Pixel[][] grid, Color color, int x_TILES, int y_TILES,
									TextArea textAreaDebug, ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
		this.grid = grid;
		X_TILES = x_TILES;
		Y_TILES = y_TILES;
		this.color = color;
		this.textAreaDebug = textAreaDebug;

	}

	public void rideOderOfListOfReb(List<Edge> edgeList) {
		List<Edge> intersectionList = new ArrayList<Edge>();
		int startLine = findStartLine(edgeList);
		int endLine = findEndLine(edgeList);
		int startStroke = findStartStroke(edgeList);
		int endStroke = findEndStroke(edgeList);
		findIntersectingPixels(intersectionList, startLine, endLine, startStroke, endStroke);

		fillPairOfEdges(edgeList, intersectionList);
		for (Edge edge : edgeList) {
			edge.getStarPixel().getRectangle().setFill(Color.BLACK);
		}

	}

	private void fillPairOfEdges(List<Edge> edgeList, List<Edge> intersectionList) {
		Edge tempEdge = null;
		if (!edgeList.get(0).isHorizontal()) {
			tempEdge = edgeList.get(0);
		} else {
			tempEdge = edgeList.get(1);
		}
		List<PairOfEdges> pairOfEdgesList = findPairsOfEdges(edgeList, tempEdge);
		removeExcessPixel(intersectionList);
		findRange(intersectionList, pairOfEdgesList);
	}

	private void findRange(List<Edge> intersectionList, List<PairOfEdges> pairOfEdgesList) {
		for (Edge edge : intersectionList) {
			Iterator<Pixel> pixelIterator = edge.getLine().iterator();
			while (pixelIterator.hasNext()) {
				Pixel pixel = pixelIterator.next();
				for (PairOfEdges pairOfEdges : pairOfEdgesList) {
					Pixel firstStarPixel = pairOfEdges.getFirstEdge().getStarPixel();
					Pixel secondStarPixel1 = pairOfEdges.getSecondEdge().getStarPixel();
					if (firstStarPixel.equals(pixel) || secondStarPixel1.equals(pixel)) {
						try {
							pixelIterator.remove();
						} catch (IllegalStateException e) {
						}
					}
				}
			}
		}
		for (PairOfEdges pairOfEdges : pairOfEdgesList) {
			Edge firstEdge = pairOfEdges.getFirstEdge();
			Edge secondEdge = pairOfEdges.getSecondEdge();
			Pixel firstPixel = firstEdge.getLine().get(0);
			List<Pixel> secondLine = secondEdge.getLine();
			Pixel secondPixel = secondLine.get(secondLine.size() - 1);
			if (firstPixel.getX() > secondPixel.getX()) {
				Edge temp = secondEdge;
				secondEdge = firstEdge;
				firstEdge = temp;
			}
			for (Edge intersectionEdge : intersectionList) {
				List<Pixel> line = intersectionEdge.getLine();
				for (int i = 0; i < line.size(); i++) {
					if (i + 1 != line.size()) {
						if (firstEdge.getLine().contains(line.get(i)) && secondEdge.getLine()
							.contains(line.get(i + 1))) {
							for (int j = line.get(i).getX() + 1; j < line.get(i + 1).getX(); j++) {
								grid[j][line.get(i).getY()].getRectangle().setFill(color);
							}
						}
					}
				}
			}
		}
	}

	private void removeExcessPixel(List<Edge> intersectionList) {
		Iterator<Edge> edgeIterator = intersectionList.iterator();
		while (edgeIterator.hasNext()) {
			Edge edge = edgeIterator.next();
			List<Pixel> line = edge.getLine();
			if (line.size() == 1) {
				edgeIterator.remove();
			} else if (line.size() == 2) {
				int firstX = line.get(0).getX();
				int secondX = line.get(1).getX();
				if (firstX + 1 == secondX) {
					edgeIterator.remove();
				}
			}
		}
	}

	private List<PairOfEdges> findPairsOfEdges(List<Edge> edgeList, Edge tempEdge) {
		List<PairOfEdges> pairOfEdgesList = new ArrayList<>();
		for (int i = 0; i < edgeList.size(); i++) {
			if (i + 1 != edgeList.size()) {
				if (!edgeList.get(i).isHorizontal()) {
					if (!edgeList.get(i + 1).isHorizontal()) {
						if (edgeList.get(i).getEndPixel()
							.equals(edgeList.get(i + 1).getStarPixel())) {
							PairOfEdges pairOfEdges = new PairOfEdges();
							pairOfEdges.setFirstEdge(edgeList.get(i));
							pairOfEdges.setSecondEdge(edgeList.get(i + 1));
							pairOfEdgesList.add(pairOfEdges);
							i++;
						}
					} else if (edgeList.get(i + 1).isHorizontal()) {
						if (i + 2 != edgeList.size()) {
							PairOfEdges pairOfEdges = new PairOfEdges();
							pairOfEdges.setFirstEdge(edgeList.get(i));
							pairOfEdges.setSecondEdge(edgeList.get(i + 2));
							pairOfEdgesList.add(pairOfEdges);
							i++;
						}
					}
				}
			}
		}
		if (!edgeList.get(edgeList.size() - 1).isHorizontal()) {
			PairOfEdges pairOfEdges = new PairOfEdges();
			pairOfEdges.setFirstEdge(edgeList.get(edgeList.size() - 1));
			pairOfEdges.setSecondEdge(tempEdge);
			pairOfEdgesList.add(pairOfEdges);
		} else {
			PairOfEdges pairOfEdges = new PairOfEdges();
			pairOfEdges.setFirstEdge(edgeList.get(edgeList.size() - 2));
			pairOfEdges.setSecondEdge(tempEdge);
			pairOfEdgesList.add(pairOfEdges);
		}
		return pairOfEdgesList;
	}

	private void findIntersectingPixels(List<Edge> intersectionList, int startLine, int endLine,
										int startStroke, int endStroke) {
		for (int i = startLine; i < endLine + 1; i++) {
			Edge edge = new Edge();
			for (int j = startStroke; j < endStroke + 1; j++) {
				if (grid[j][i].getRectangle().getFill() == Color.BLACK) {
					edge.getLine().add(grid[j][i]);
				}
			}
			addToIntersectionList(intersectionList, startStroke, endStroke, edge);
		}
	}

	private void addToIntersectionList(List<Edge> intersectionList, int startStroke, int endStroke,
									   Edge edge) {
		if (checkHorizontal(edge, endStroke - startStroke + 1) && edge.getLine().size() != 0) {
			edge.getLine().sort(Pixel.PixelComparator);
			intersectionList.add(edge);
		}
	}

	private boolean checkHorizontal(Edge edge, int range) {
		List<Pixel> line = edge.getLine();
		int y = line.get(0).getY();
		int count = 0;
		for (Pixel pixel : line) {
			if (pixel.getY() != y) {
				return false;
			} else if (pixel.getRectangle().getFill() == Color.BLACK) {
				count++;
			}
		}
		return count != range;
	}

	private int findStartStroke(List<Edge> edgeList) {
		int result = 999999999;
		for (Edge edge : edgeList) {
			if (edge.getStarPixel().getX() < result) {
				result = edge.getStarPixel().getX();
			}
		}
		return result;
	}

	private int findEndStroke(List<Edge> edgeList) {
		int result = 0;
		for (Edge edge : edgeList) {
			if (edge.getStarPixel().getX() > result) {
				result = edge.getStarPixel().getX();
			}
		}
		return result;
	}

	private int findStartLine(List<Edge> edgeList) {
		int result = 999999999;
		for (Edge edge : edgeList) {
			if (edge.getStarPixel().getY() < result) {
				result = edge.getStarPixel().getY();
			}
		}
		return result;
	}

	private int findEndLine(List<Edge> edgeList) {
		int result = 0;
		for (Edge edge : edgeList) {
			if (edge.getStarPixel().getY() > result) {
				result = edge.getStarPixel().getY();
			}
		}
		return result;
	}
}
