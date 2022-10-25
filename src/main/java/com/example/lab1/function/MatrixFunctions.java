package com.example.lab1.function;

public final class MatrixFunctions {

	private static MatrixFunctions instance = null;

	private MatrixFunctions() {
	}

	public static synchronized MatrixFunctions getInstance() {
		if (instance == null) {
			instance = new MatrixFunctions();
		}
		return instance;
	}

	public double[] matrixMult(double[] firstMatrix, double[][] secondMatrix) {
		if (firstMatrix.length != secondMatrix[0].length) {
			return null;
		}
		double[] result = new double[firstMatrix.length];
		for (int i = 0; i < firstMatrix.length; i++) {
			for (int j = 0; j < secondMatrix.length; j++) {
				result[i] += firstMatrix[j] * secondMatrix[j][i];
			}
		}

		return result;
	}
}
