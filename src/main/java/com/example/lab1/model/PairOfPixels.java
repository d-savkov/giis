package com.example.lab1.model;

import java.util.ArrayList;
import java.util.List;

public class PairOfPixels {

	private List<Pixel> firstPixelList;
	private List<Pixel> secondPixelList;

	public PairOfPixels() {
		firstPixelList = new ArrayList<>();
		secondPixelList = new ArrayList<>();
	}

	public PairOfPixels(List<Pixel> firstPixelList, List<Pixel> secondPixelList) {
		this.firstPixelList = firstPixelList;
		this.secondPixelList = secondPixelList;
	}

	public List<Pixel> getFirstPixelList() {
		return firstPixelList;
	}

	public void setFirstPixelList(List<Pixel> firstPixelList) {
		this.firstPixelList = firstPixelList;
	}

	public List<Pixel> getSecondPixelList() {
		return secondPixelList;
	}

	public void setSecondPixelList(List<Pixel> secondPixelList) {
		this.secondPixelList = secondPixelList;
	}
}
