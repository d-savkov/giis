package com.example.lab1.algoritms.polygon;

import com.example.lab1.functions.Functions;
import com.example.lab1.model.Pixel;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.TextArea;

public class AlgorithmJarvis {

	private final List<Pixel> pixelList;
	private final Pixel[][] grid;
	private final TextArea textAreaDebug;
	private final ResourceBundle resourceBundle;
	private final Functions functions = Functions.getInstance();

	public AlgorithmJarvis(List<Pixel> pixelList, Pixel[][] grid, TextArea textAreaDebug,
						   ResourceBundle resourceBundle) {
		this.pixelList = pixelList;
		this.grid = grid;
		this.textAreaDebug = textAreaDebug;
		this.resourceBundle = resourceBundle;
	}

	public List<Pixel> jarvis() {
		List<Pixel> result = new ArrayList<>();
		Pixel startPixel = functions.findLeftmostTilePolygon(pixelList);
		Pixel secondPixel = functions.findRightmostTilePolygon(pixelList);

		textAreaDebug.setText(
			textAreaDebug.getText() + resourceBundle.getString("findStartPoint") + ":" + " x="
				+ startPixel.getX() + " y=" + startPixel.getY() + "\n");
		result.add(startPixel);

		Pixel current = startPixel;

		do {
			double angMin;
			int iMin;
			if (current.equals(pixelList.get(0))) {
				angMin = current.calcAngleR(pixelList.get(1));
				iMin = 1;
			} else {
				angMin = current.calcAngleR(pixelList.get(0));
				iMin = 0;
			}
			for (int i = 0; i < pixelList.size(); i++) {
				if (!current.equals(pixelList.get(i))) {
					Double angCur = current.calcAngleR(pixelList.get(i));
					if ((angCur < angMin) || (angCur == angMin
						&& distance(current, pixelList.get(i)) < distance(current,
						pixelList.get(iMin)))) {
						angMin = angCur;
						iMin = i;
						textAreaDebug.setText(
							textAreaDebug.getText() + resourceBundle.getString("findMin") + ":"
								+ " x=" + pixelList.get(i).getX() + " y=" + pixelList.get(i).getY()
								+ "\n");
					}
				}
			}
			textAreaDebug.setText(
				textAreaDebug.getText() + resourceBundle.getString("findNBO") + ":" + " x="
					+ pixelList.get(iMin).getX() + " y=" + pixelList.get(iMin).getY() + "\n");
			result.add(pixelList.get(iMin));
			current = pixelList.get(iMin);

		} while (!current.equals(secondPixel));

		do {
			double angMin;
			int iMin;

			if (current.equals(pixelList.get(0))) {
				angMin = current.calcAngleL(pixelList.get(1));
				iMin = 1;
			} else {
				angMin = current.calcAngleL(pixelList.get(0));
				iMin = 0;
			}
			for (int i = 0; i < pixelList.size(); i++) {
				if (!current.equals(pixelList.get(i))) {
					Double angCur = current.calcAngleL(pixelList.get(i));
					if ((angCur < angMin) || (angCur == angMin
						&& distance(current, pixelList.get(i)) < distance(current,
						pixelList.get(iMin)))) {
						angMin = angCur;
						iMin = i;
						textAreaDebug.setText(
							textAreaDebug.getText() + resourceBundle.getString("findMin") + ":"
								+ " x=" + pixelList.get(i).getX() + " y=" + pixelList.get(i).getY()
								+ "\n");

					}
				}
			}
			textAreaDebug.setText(
				textAreaDebug.getText() + resourceBundle.getString("findNBO") + ":" + " x="
					+ pixelList.get(iMin).getX() + " y=" + pixelList.get(iMin).getY() + "\n");
			result.add(pixelList.get(iMin));
			current = pixelList.get(iMin);
		} while (!current.equals(startPixel));
		return result;
	}


	private double distance(Pixel current, Pixel pixel) {
		return Math.sqrt(
			Math.pow(pixel.getX() - current.getX(), 2) + Math.pow(pixel.getY() - current.getY(),
				2));
	}


}
