package com.example.lab1.algoritms.loaders;

import com.example.lab1.model.Edge;
import com.example.lab1.model.Figure;
import com.example.lab1.model.Pixel;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoaderFigureFromFile {

	private final Properties properties = new Properties();
	private final Pattern patternPixel = Pattern.compile("[A-Z]");
	private final Pattern patternEdge = Pattern.compile("[A-Z]{2}");
	private Map<String, Pixel> stringPixelMap;

	public LoaderFigureFromFile() {
		stringPixelMap = null;
		stringPixelMap = new HashMap<>();
	}

	public Figure loadFigure(File file, boolean figure2d, int tileSize) {
		try (InputStream inputStream = new FileInputStream(file)) {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Figure figure = null;

		if (figure2d) {
			figure = figure2D(tileSize);
		} else {
			figure = figure3D(tileSize);
		}
		return figure;
	}

	private Figure figure2D(int tileSize) {
		List<Pixel> pixels = fill2DPixelList(tileSize);
		List<Edge> edgeList = fillEdgeList();

		return new Figure(pixels, edgeList, 1);
	}

	private Figure figure3D(int tileSize) {
		List<Pixel> pixels = fill3DPixelList(tileSize);
		List<Edge> edgeList = fillEdgeList();

		return new Figure(pixels, edgeList, 1);
	}


	private List<Pixel> fill2DPixelList(int tileSize) {
		List<Pixel> pixels = new ArrayList<>();
		//такой себе подход, нужно что другое посмотреть
		for (String name : properties.stringPropertyNames()) {
			String property = properties.getProperty(name);
			if (doMatcher(patternPixel, name)) {
				// проверка на то, что x, y - положительные числа и вообще числа
				int x = Integer.parseInt(property.substring(0, property.indexOf(" ")));
				int y = Integer.parseInt(property.substring(property.indexOf(" ")).trim());
				Pixel pixel = new Pixel(x, y, tileSize);
				pixels.add(pixel);
				stringPixelMap.put(name, pixel);
			}

		}
		return pixels;
	}

	private List<Pixel> fill3DPixelList(int tileSize) {
		List<Pixel> pixels = new ArrayList<>();
		//такой себе подход, нужно что другое посмотреть
		for (String name : properties.stringPropertyNames()) {
			String property = properties.getProperty(name);
			if (doMatcher(patternPixel, name)) {
				// проверка на то, что x, y - положительные числа и вообще числа
				int x = Integer.parseInt(property.substring(0, property.indexOf(" ")));
				int y = Integer.parseInt(
					property.substring(property.indexOf(" ") + 1, property.indexOf(",")));
				int z = Integer.parseInt(property.substring(property.indexOf(",") + 1).trim());
				Pixel pixel = new Pixel(x, y, z, tileSize);
				pixels.add(pixel);
				stringPixelMap.put(name, pixel);
			}

		}
		return pixels;
	}

	private List<Edge> fillEdgeList() {
		//такой себе подход, нужно что другое посмотреть
		List<Edge> edgeList = new ArrayList<>();
		for (String name : properties.stringPropertyNames()) {
			if (name.length() < 3 && name.length() > 1) {
				if (doMatcher(patternEdge, name)) {
					String startPixel = name.substring(0, 1);
					String endPixel = name.substring(1, 2);
					if (!startPixel.equals(endPixel)) {
						Edge edge = new Edge(stringPixelMap.get(startPixel),
							stringPixelMap.get(endPixel));
						edgeList.add(edge);
					} else {
						//генерация ошибки, тип не оч правильный формат данных потшел на вход
					}
				}
			} else {
				//генерация ошибки, тип не оч правильный формат данных пошел на вход
			}
		}
		return edgeList;
	}

	private boolean doMatcher(Pattern pattern, String s) {
		Matcher matcher = pattern.matcher(s);
		return matcher.matches();
	}


}
