package com.example.lab1.model;

import java.util.Comparator;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Pixel extends StackPane {

	public static Comparator<Pixel> PixelComparator = new Comparator<Pixel>() {
		@Override
		public int compare(Pixel pixel1, Pixel pixel2) {
			if (pixel2.getY() < pixel1.getY() || (pixel2.getY() < pixel1.getY()
				&& pixel1.getX() <= pixel2.getX())) {
				return -1;
			} else if (pixel2.getY() == pixel1.getY() && pixel1.getX() == pixel2.getX()) {
				return 0;
			}
			return 1;
		}
	};

	private static boolean visibleStoke = true;
	private int x;
	private int y;
	private int z;
	private int size;
	private boolean start = false;
	private boolean end = false;
	private CohenSutherland cohenSutherland;
	private Rectangle rectangle = new Rectangle(20, 20);

	public Pixel() {
		cohenSutherland = new CohenSutherland();
	}

	public Pixel(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;

		if (visibleStoke) {
			rectangle.setStroke(Color.LIGHTGRAY);
		} else {
			rectangle.setStroke(Color.WHITE);
		}

		rectangle.setFill(Color.WHITE);

		getChildren().addAll(rectangle);
		setTranslateX(x * size);  // размер клетки
		setTranslateY(y * size);
		cohenSutherland = new CohenSutherland();

	}

	public Pixel(int x, int y, int z, int size) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.size = size;

		if (visibleStoke) {
			rectangle.setStroke(Color.LIGHTGRAY);
		} else {
			rectangle.setStroke(Color.WHITE);
		}

		rectangle.setFill(Color.WHITE);

		getChildren().addAll(rectangle);
		setTranslateX(x * size);  // размер клетки
		setTranslateY(y * size);
		cohenSutherland = new CohenSutherland();

	}

	public static boolean isVisibleStoke() {
		return visibleStoke;
	}

	public static void setVisibleStoke(boolean visibleStoke) {
		Pixel.visibleStoke = visibleStoke;
	}

	public static Comparator<Pixel> getPixelComparator() {
		return PixelComparator;
	}

	public static void setPixelComparator(Comparator<Pixel> pixelComparator) {
		PixelComparator = pixelComparator;
	}

	public Double[] calcVect(Pixel a) {
		Double[] result = new Double[2];
		result[0] = a.getX() - x * 1.0;
		result[1] = a.getY() - y * 1.0;
		return result;
	}

	public Double calcAngleR(Pixel b) {
		Double x = this.calcVect(b)[0];
		Double y = this.calcVect(b)[1];
		Double cos = x / (Math.sqrt(x * x + y * y));
		if (this.getY() <= b.getY()) {
			return Math.acos(cos);
		} else {
			return Math.acos(cos) + Math.PI;
		}
	}

	public Double calcAngleL(Pixel b) {
		Double x = this.calcVect(b)[0];
		Double y = this.calcVect(b)[1];
		Double cos = -x / (Math.sqrt(x * x + y * y));
		if (this.getY() >= b.getY()) {
			return Math.acos(cos);
		} else {
			return Math.acos(cos) + Math.PI;
		}
	}

	@Override
	public String toString() {
		return " {" +
			"x=" + x +
			", y=" + y +
			'}' + "\n";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Pixel pixel = (Pixel) o;

		if (x != pixel.x) {
			return false;
		}
		return y == pixel.y;
	}

	@Override
	public int hashCode() {
		int result = x;
		result = 31 * result + y;
		return result;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public boolean isEnd() {
		return end;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	public CohenSutherland getCohenSutherland() {
		return cohenSutherland;
	}

	public void setCohenSutherland(CohenSutherland cohenSutherland) {
		this.cohenSutherland = cohenSutherland;
	}
}
