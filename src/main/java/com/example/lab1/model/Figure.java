package com.example.lab1.model;

import java.util.List;

public class Figure {

	private List<Pixel> pixelList;
	private List<Edge> edgeList;
	private double size;

	public Figure() {
	}

	public Figure(List<Pixel> pixelList, List<Edge> edgeList, double size) {
		this.pixelList = pixelList;
		this.edgeList = edgeList;
		this.size = size;
	}

	public List<Edge> getEdgeList() {
		return edgeList;
	}

	public void setEdgeList(List<Edge> edgeList) {
		this.edgeList = edgeList;
	}

	public List<Pixel> getPixelList() {
		return pixelList;
	}

	public void setPixelList(List<Pixel> pixelList) {
		this.pixelList = pixelList;
	}

	public double getSize() {
		return size;
	}
}
