package com.rebe.neuralNetwork.components;

import com.rebe.neuralNetwork.exceptions.EmptyLayerException;
import com.rebe.neuralNetwork.exceptions.IllegalRandomizerArgumentException;
import com.rebe.neuralNetwork.utils.Utils;
import com.rebe.neuralNetwork.utils.WeightRandomizer;

/**
 * Represents the connection between two layer
 * 
 * @author Mattia Rebesan
 *
 */
public class ConnectionLayers implements Comparable<ConnectionLayers> {

	/**
	 * Identifier for the comparisons
	 */
	private final int id;

	/**
	 * Left {@link Layer} of the connection
	 */
	private final Layer leftLayer;

	/**
	 * Right {@link Layer} of the connection
	 */
	private final Layer rightLayer;

	/**
	 * Weights matrix of the connection layers, used to compute the right layer
	 * neurons values
	 */
	private double[][] weightsMatrix;

	/**
	 * Delta matrix, used to compute new weight, associated with the momentum
	 */
	private double[][] previousDeltaMatrix;

	/**
	 * Value used to avoid to be stuck in local minimal, higher value means higher
	 * variation. Common to all network connection layer
	 */
	private final double momentum;

	/**
	 * Value used to control the network learning speed
	 */
	private final double learningRate;

	/**
	 * Biases array of the connection layers, used to compute the right layer
	 * neurons values
	 */
	private double[] biases;

	/**
	 * Errors array used to optimize the previous layer
	 */
	private double[] errors;

	/**
	 * Constructor that initialize the weights matrix based on the received layers
	 * 
	 * @param id
	 *            Identifier of the connection layer
	 * @param leftLayer
	 *            left layer of the connection layer
	 * @param rightLayer
	 *            right layer of the connection layer
	 * @param momentum
	 *            value of the momentum, to avoid to be stuck in local minimal,
	 *            higher value means higher variation
	 * @param learningRate
	 *            value used to control the network learning speed, lower values
	 *            mean lower speed learning
	 * @throws EmptyLayerException
	 *             Thrown in case of left or right null layer
	 * @throws IllegalRandomizerArgumentException
	 *             Throw in case left layer size less than 1
	 */
	public ConnectionLayers(final Layer leftLayer, final Layer rightLayer, final double momentum,
			final double learningRate) throws EmptyLayerException, IllegalRandomizerArgumentException {

		if (leftLayer == null || rightLayer == null) {
			throw new EmptyLayerException();
		}

		this.id = Utils.getConnectionLayerId();
		this.leftLayer = leftLayer;
		this.rightLayer = rightLayer;
		this.momentum = momentum;
		this.learningRate = learningRate;
		initializeWeights();
		initializeBiases();
		initializeErrors();

	}

	/**
	 * Method that initialize the weights matrix with random values
	 */
	private void initializeWeights() throws IllegalRandomizerArgumentException {

		weightsMatrix = new double[leftLayer.size()][rightLayer.size()];

		WeightRandomizer randomizer = new WeightRandomizer(leftLayer.size());

		for (int rowIndex = 0; rowIndex < leftLayer.size(); rowIndex++) {
			for (int columnIndex = 0; columnIndex < rightLayer.size(); columnIndex++) {
				weightsMatrix[rowIndex][columnIndex] = randomizer.randWeight();
			}
		}

		previousDeltaMatrix = new double[leftLayer.size()][rightLayer.size()];
	}

	/**
	 * Method that initialize the errors array with 0
	 */
	private void initializeErrors() {
		biases = new double[rightLayer.size()];
	}

	private void initializeBiases() {
		errors = new double[leftLayer.size()];
	}

	/**
	 * Compute the right layer values using left layer, weights matrix and biases
	 */
	public void compute() {
		double[] inputs = leftLayer.values();
		double[] result = new double[rightLayer.size()];

		double sum;
		for (int columnIndex = 0; columnIndex < rightLayer.size(); columnIndex++) {
			sum = 0;
			for (int rowIndex = 0; rowIndex < leftLayer.size(); rowIndex++) {
				sum += inputs[rowIndex] * weightsMatrix[rowIndex][columnIndex];
			}
			sum += biases[columnIndex];
			result[columnIndex] = sum;
		}
		rightLayer.setValues(result);
		rightLayer.activeNeurons();
	}

	/**
	 * Optimize the connection layer updating weight and biases
	 * 
	 * @param rightLayerErrors
	 *            errors array of the right layer
	 */
	public void optimize(final double[] rightLayerErrors) {
		computeCurrentLeftLayerError(rightLayerErrors);

		// optimize weights
		double[] leftLayerValues = leftLayer.values();
		double delta;
		for (int columnIndex = 0; columnIndex < rightLayer.size(); columnIndex++) {
			for (int rowIndex = 0; rowIndex < leftLayer.size(); rowIndex++) {
				delta = learningRate * leftLayerValues[rowIndex] * rightLayerErrors[columnIndex];
				weightsMatrix[rowIndex][columnIndex] += delta + momentum * previousDeltaMatrix[rowIndex][columnIndex];
				previousDeltaMatrix[rowIndex][columnIndex] = delta;
			}
		}

		// optimize biases
		for (int index = 0; index < biases.length; index++) {
			biases[index] += rightLayerErrors[index];
		}
	}

	/**
	 * Compute the errors array of the left layer in the connection layer
	 * 
	 * @param rightLayerErrors
	 *            errors array of the right layer
	 */
	private void computeCurrentLeftLayerError(final double[] rightLayerErrors) {
		double[][] weightsMatrixTransposed = Utils.transposeMatrix(weightsMatrix);

		double[] functionGradient = new double[leftLayer.size()];
		double sum;
		for (int rowIndex = 0; rowIndex < leftLayer.size(); rowIndex++) {
			sum = 0;
			for (int columnIndex = 0; columnIndex < rightLayer.size(); columnIndex++) {
				sum += weightsMatrixTransposed[columnIndex][rowIndex] * rightLayerErrors[columnIndex];
			}
			functionGradient[rowIndex] = sum;
		}

		double[] leftLayerValues = leftLayer.values();
		double[] leftLayerNotActivatedValues = leftLayer.notActivatedValues();

		for (int index = 0; index < leftLayerValues.length; index++) {
			errors[index] = functionGradient[index]
					* Utils.activationFunctionDerivate(leftLayerNotActivatedValues[index]);
		}

	}

	/**
	 * Getter of the current errors list
	 * 
	 * @return
	 */
	public double[] getError() {
		return errors;
	}

	/**
	 * Getter for the identifier
	 */
	public int getId() {
		return id;
	}

	/**
	 * Comparator for the Comparable implementation
	 */
	@Override
	public int compareTo(ConnectionLayers o) {
		return id - o.getId();
	}

}
