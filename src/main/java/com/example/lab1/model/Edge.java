package com.example.lab1.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Edge {

	private Pixel starPixel;
	private Pixel endPixel;
	private List<Pixel> line;
	private boolean horizontal = false;
	private boolean visible = true;

	public Edge() {
		line = new ArrayList<>();
	}

	public Edge(Pixel starPixel, Pixel endPixel) {
		this.starPixel = starPixel;
		this.endPixel = endPixel;
		line = new ArrayList<>();
	}

	public Edge(Pixel starPixel, Pixel endPixel, List<Pixel> pixelList) {
		this.starPixel = starPixel;
		this.endPixel = endPixel;
		this.line = pixelList;

		horizontal = checkHorizontal();
	}

	public Edge(Pixel starPixel, Pixel endPixel, List<Pixel> pixelList, boolean visible) {
		this.starPixel = starPixel;
		this.endPixel = endPixel;
		this.line = pixelList;
		this.visible = visible;

		horizontal = checkHorizontal();
	}

	private boolean checkHorizontal() {
		for (int i = 0; i < line.size(); i++) {
			if (i + 1 == line.size()) {
				return true;
			} else if (line.get(i).getY() != line.get(i + 1).getY()) {
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
		Edge edge = (Edge) o;
		return Objects.equals(starPixel, edge.starPixel) &&
			Objects.equals(endPixel, edge.endPixel);
	}

	@Override
	public int hashCode() {

		return Objects.hash(starPixel, endPixel);
	}

	@Override
	public String toString() {
		return "Edge{" +
			"starPixel=" + starPixel +
			", endPixel=" + endPixel +
			", horizontal=" + horizontal + "\n" +
			", line=" + "\n" + line +
			'}' + "\n";
	}

	public Pixel getStarPixel() {
		return starPixel;
	}

	public void setStarPixel(Pixel starPixel) {
		this.starPixel = starPixel;
	}

	public Pixel getEndPixel() {
		return endPixel;
	}

	public void setEndPixel(Pixel endPixel) {
		this.endPixel = endPixel;
	}

	public List<Pixel> getLine() {
		return line;
	}

	public void setLine(List<Pixel> line) {
		this.line = line;
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
