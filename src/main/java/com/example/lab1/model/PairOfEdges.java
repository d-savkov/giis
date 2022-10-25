package com.example.lab1.model;

public class PairOfEdges {

	private Edge firstEdge;
	private Edge secondEdge;
	private PairOfPixels pairOfPixels;

	public PairOfEdges() {
	}

	public PairOfEdges(Edge firstEdge, Edge secondEdge, PairOfPixels pairOfPixels) {
		this.firstEdge = firstEdge;
		this.secondEdge = secondEdge;
		this.pairOfPixels = pairOfPixels;
	}

	public Edge getFirstEdge() {
		return firstEdge;
	}

	public void setFirstEdge(Edge firstEdge) {
		this.firstEdge = firstEdge;
	}

	public Edge getSecondEdge() {
		return secondEdge;
	}

	public void setSecondEdge(Edge secondEdge) {
		this.secondEdge = secondEdge;
	}

	@Override
	public String toString() {
		return "PairOfEdges{" +
			"firstEdge=" + firstEdge +
			", secondEdge=" + secondEdge +
			'}';
	}
}
