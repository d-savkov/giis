package com.example.lab1.model;

import java.util.List;

public class Triangle {

	private Pixel A;
	private Pixel B;
	private Pixel C;
	private List<Edge> edgeList;

	public Triangle() {
	}

	public Triangle(Pixel a, Pixel b, Pixel c) {
		A = a;
		B = b;
		C = c;
		fillEdge();
	}

	public Triangle(Pixel a, Pixel b, Pixel c, List<Edge> edgeList) {
		A = a;
		B = b;
		C = c;
		this.edgeList = edgeList;
	}

	private void fillEdge() {
		edgeList.add(new Edge(A, B));
		edgeList.add(new Edge(A, C));
		edgeList.add(new Edge(B, C));
	}

	public Pixel getA() {
		return A;
	}

	public void setA(Pixel a) {
		A = a;
	}

	public Pixel getB() {
		return B;
	}

	public void setB(Pixel b) {
		B = b;
	}

	public Pixel getC() {
		return C;
	}

	public void setC(Pixel c) {
		C = c;
	}

	public List<Edge> getEdgeList() {
		return edgeList;
	}

	public void setEdgeList(List<Edge> edgeList) {
		this.edgeList = edgeList;
	}

	@Override
	public String toString() {
		return "Triangle{" +
			"A=" + A +
			", B=" + B +
			", C=" + C +
			", edgeList=" + edgeList +
			'}';
	}
}
