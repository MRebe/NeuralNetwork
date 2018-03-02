package com.rebe.neuralNetwork.utils;

/**
 * Collection of utility methods
 * 
 * @author Mattia Rebesan
 *
 */
public class Utils {

	private static int neuronIdCounter = 0;
	private static int connectionLayerIdCounter = 0;

	/**
	 * Sigmoid function
	 * 
	 * @param value
	 *            given to the function
	 * @return computed result
	 */
	public static double activationFunction(double value) {
		return (1 / (1 + Math.pow(Math.E, (-1 * value))));
	}

	/**
	 * Inverse of sigmoid function
	 * 
	 * @param value
	 *            given to the function
	 * @return computed result
	 */
	public static double inverseActivationFunction(double value) {
		return Math.log(value / (1 - value));
	}

	/**
	 * Sigmoid function derivate
	 * 
	 * @param value
	 *            given to the function
	 * @return computed result
	 */
	public static double activationFunctionDerivate(double value) {
		double sigmoid = activationFunction(value);
		return sigmoid * (1 - sigmoid);
	}

	/**
	 * Return a sequential id for the neurons list
	 * 
	 * @return a sequential identifier
	 */
	public static int getNeuronId() {
		return neuronIdCounter++;
	}

	/**
	 * Return a sequential id for the connection layer list
	 * 
	 * @return a sequential identifier
	 */
	public static int getConnectionLayerId() {
		return connectionLayerIdCounter++;
	}

	/**
	 * Method that transpose a Matrix
	 * 
	 * @param weightsMatrix
	 *            matrix to be transposed
	 * @return a new matrix that is the transposed of the received one
	 */
	public static double[][] transposeMatrix(double[][] weightsMatrix) {
		double[][] transposedMatrix = new double[weightsMatrix[0].length][weightsMatrix.length];
		for (int i = 0; i < weightsMatrix.length; i++) {
			for (int j = 0; j < weightsMatrix[0].length; j++) {
				transposedMatrix[j][i] = weightsMatrix[i][j];
			}
		}

		return transposedMatrix;
	}

	/**
	 * Compute the norm of the array, which is the sum of the absolute values
	 * 
	 * @param array
	 *            to calculate the norm
	 * @return the norm value
	 */
	public static double norm(double[] array) {

		double norm = 0;

		for (int index = 0; index < array.length; index++) {
			norm += Math.abs(array[index]);
		}

		return norm;
	}

}
