package com.example.lab1.model;

public class PairOfPixel {

	private Pixel firstPixel;
	private Pixel secondPixel;

	public PairOfPixel() {
	}

	public PairOfPixel(Pixel firstPixel, Pixel secondPixel) {
		this.firstPixel = firstPixel;
		this.secondPixel = secondPixel;
	}

	public Pixel getFirstPixel() {
		return firstPixel;
	}

	public void setFirstPixel(Pixel firstPixel) {
		this.firstPixel = firstPixel;
	}

	public Pixel getSecondPixel() {
		return secondPixel;
	}

	public void setSecondPixel(Pixel secondPixel) {
		this.secondPixel = secondPixel;
	}
}
