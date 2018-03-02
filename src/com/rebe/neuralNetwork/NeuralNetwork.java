package com.rebe.neuralNetwork;

import java.util.Iterator;
import java.util.TreeSet;

import com.rebe.neuralNetwork.components.ConnectionLayers;
import com.rebe.neuralNetwork.components.Layer;
import com.rebe.neuralNetwork.exceptions.IllegalNeuronsCountException;
import com.rebe.neuralNetwork.exceptions.LearningRateOutOfRangeException;
import com.rebe.neuralNetwork.exceptions.MomentumOutOfRangeException;
import com.rebe.neuralNetwork.exceptions.NeuralNetworkException;
import com.rebe.neuralNetwork.exceptions.NotEnoughLayersException;
import com.rebe.neuralNetwork.utils.Utils;

/**
 * Represents the actual neural network
 * 
 * @author Mattia Rebesan
 *
 */
public class NeuralNetwork {

	/**
	 * The count of the neurons of the input layer
	 */
	private final int inputNeuronsCount;

	/**
	 * The count of the neurons of the output layer
	 */
	private final int outputNeuronsCount;

	/**
	 * Input Layer
	 */
	private Layer inputLayer;

	/**
	 * outputLayer
	 */
	private Layer outputLayer;

	/**
	 * The actual network, an ordered list of {@link ConnectionLayers}
	 */
	private TreeSet<ConnectionLayers> network;

	/**
	 * List of the network error coefficients, used to compute the current network
	 * error coefficient
	 */
	private double networkErrorCoefficientsHistory;

	/**
	 * Count of the training cycles, used to compute the current network error
	 * coefficient
	 */
	private int trainingCycle;

	/**
	 * Value that represent the error coefficient of the network. The lower the
	 * coefficient the more the network is reliable
	 */
	private double networkErrorCoefficient;

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
	 * Trainer threshold used to control the network efficiency, set by default at
	 * 0.001
	 */
	private double trainerThreshold;

	/**
	 * Indicates if the network is sufficiently trained
	 */
	private boolean isTrained;

	/**
	 * Constructor that build the neural network. The neural network will have
	 * inputNeuronsCount input neurons, outputNeuronsCount output neurons and
	 * hiddenLayerLayersCount layers in the hidden layer. Each layer will have
	 * hiddenLayersNeuronsCount neurons (retrieved by position). If there are more
	 * hiddenLayersNeuronsCount than hiddenLayerLayersCount, the extra ones simply
	 * will be ignored
	 * 
	 * @param inputNeuronsCount
	 *            count of the neurons of the input layer
	 * @param hiddenLayerLayersCount
	 *            count of the layers that compose the hidden layer
	 * @param hiddenLayersNeuronsCount
	 *            array representing the neurons count for each layer of the hidden
	 *            layer
	 * @param outputNeuronsCount
	 *            count of the neurons of the input layer
	 * @param momentum
	 *            value of the momentum, to avoid to be stuck in local minimal,
	 *            higher value means higher variation, selected between [0, 1[
	 * @param learningRate
	 *            value used to control the network learning speed, lower values
	 *            mean lower speed learning, selected between ]0, 1]
	 * @throws IllegalNeuronsCountException
	 *             Thrown if inputNeuronsCount or outputNeuronsCount are less than 1
	 * @throws NotEnoughLayersException
	 *             Throw if the hiddenLayersNeuronsCount length is less than the
	 *             hiddenLayerLayersCount
	 * @throws MomentumOutOfRangeException
	 *             Thrown if the momentum value is out of range
	 */
	public NeuralNetwork(final int inputNeuronsCount, final int hiddenLayerLayersCount,
			final int[] hiddenLayersNeuronsCount, final int outputNeuronsCount, final double momentum,
			final double learningRate) throws IllegalNeuronsCountException, NotEnoughLayersException,
			MomentumOutOfRangeException, LearningRateOutOfRangeException, NeuralNetworkException {

		if (inputNeuronsCount == 0 || outputNeuronsCount == 0) {
			throw new IllegalNeuronsCountException();
		}
		if (hiddenLayersNeuronsCount.length < hiddenLayerLayersCount) {
			throw new NotEnoughLayersException();
		}
		if (momentum < 0 || momentum >= 1) {
			throw new MomentumOutOfRangeException();
		}
		if (learningRate <= 0 || learningRate > 1) {
			throw new LearningRateOutOfRangeException();
		}

		this.inputNeuronsCount = inputNeuronsCount;
		this.outputNeuronsCount = outputNeuronsCount;
		this.momentum = momentum;
		this.learningRate = learningRate;

		this.trainerThreshold = 0.001;
		this.networkErrorCoefficientsHistory = 0;
		this.trainingCycle = 1;
		this.isTrained = false;

		buildNetwork(hiddenLayerLayersCount, hiddenLayersNeuronsCount);

	}

	/**
	 * Method that build the actual network
	 * 
	 * @param hiddenLayerLayersCount
	 *            count of the layers that compose the hidden layer
	 * @param hiddenLayersNeuronsCount
	 *            array representing the neurons count for each layer of the hidden
	 *            layer
	 * @throws NeuralNetworkException
	 */
	private void buildNetwork(final int hiddenLayerLayersCount, final int[] hiddenLayersNeuronsCount)
			throws NeuralNetworkException {

		network = new TreeSet<ConnectionLayers>();

		inputLayer = new Layer(inputNeuronsCount);

		Layer temp1 = inputLayer;
		Layer temp2;

		for (int hiddenLayerIndex = 0; hiddenLayerIndex < hiddenLayerLayersCount; hiddenLayerIndex++) {
			temp2 = new Layer(hiddenLayersNeuronsCount[hiddenLayerIndex]);
			network.add(new ConnectionLayers(temp1, temp2, momentum, learningRate));
			temp1 = temp2;
		}

		outputLayer = new Layer(outputNeuronsCount);

		network.add(new ConnectionLayers(temp1, outputLayer, momentum, learningRate));

	}

	/**
	 * Compute the outputs using the received inputs. If the inputs array length is
	 * greater than the inputNeuronsCount inserted when created the network, the
	 * extra ones will be ignored
	 * 
	 * @param inputs
	 *            an array of values
	 * @return the array of computed values
	 * @throws IllegalNeuronsCountException
	 *             Thrown if inputs count not equals to inputNeuronsCount
	 */
	public double[] computeResult(final double[] inputs) throws IllegalNeuronsCountException {
		if (inputs.length != inputNeuronsCount) {
			throw new IllegalNeuronsCountException();
		}

		inputLayer.setValues(inputs);

		network.forEach(connLayer -> connLayer.compute());

		return outputLayer.values();
	}

	/**
	 * Method that make the network learn based on the expected results and update
	 * its error coefficient. Also set isTrained value if error is less than 0.001
	 * 
	 * @param expectedResult
	 *            array of the expected output
	 * @throws IllegalNeuronsCountException
	 *             Thrown if results count not equals to outputNeuronsCount
	 */
	public void learn(final double[] expectedResult) throws IllegalNeuronsCountException {
		if (expectedResult.length != outputNeuronsCount) {
			throw new IllegalNeuronsCountException();
		}

		double[] currentLayerCost = computeOutputCost(expectedResult);

		updateNetworkErrorCoefficient(currentLayerCost);

		Iterator<ConnectionLayers> descIterator = network.descendingIterator();
		ConnectionLayers currentConnectionLayer;
		while (descIterator.hasNext()) {
			currentConnectionLayer = descIterator.next();
			currentConnectionLayer.optimize(currentLayerCost);
			currentLayerCost = currentConnectionLayer.getError();
		}

		this.isTrained = networkErrorCoefficient < trainerThreshold;
		this.trainingCycle++;
	}

	/**
	 * Compute the error of the output layer
	 * 
	 * @param expectedResult
	 *            array of the expected result
	 * @return array of the computed errors
	 */
	private double[] computeOutputCost(double[] expectedResult) {

		double[] actualResult = outputLayer.values();
		double[] result = new double[outputLayer.size()];

		for (int index = 0; index < outputLayer.size(); index++) {
			result[index] = (expectedResult[index] - actualResult[index])
					* Utils.activationFunctionDerivate(actualResult[index]);
		}

		return result;
	}

	/**
	 * Method that compute the error coefficient of the network based on the output
	 * layer errors array and all the old errors. To compute the error is used a
	 * quadratic cost function.
	 * 
	 * @param outputLayerErrors
	 *            the output layer errors array
	 */
	private void updateNetworkErrorCoefficient(double[] outputLayerErrors) {
		// computed the norm of the errors variation array
		double squaredNorm = Math.pow(Utils.norm(outputLayerErrors), 2);

		// add to history
		networkErrorCoefficientsHistory += squaredNorm;

		// current coefficient is equals to
		networkErrorCoefficient = networkErrorCoefficientsHistory / (2 * trainingCycle);

	}

	/**
	 * Return a value that represent the error coefficient of the network. The lower
	 * the coefficient the more the network is reliable
	 * 
	 * @return
	 */
	public double getNetworkErrorCoefficent() {
		return networkErrorCoefficient;
	}

	/**
	 * Trainer threshold setter. You can set the threshold to adjust the precision
	 * of the network. Low level threshold mean more precision, but obviously more
	 * computational cycles. Set by default to 0.001
	 * 
	 * @param trainerThreshold
	 */
	public void setTrainerThreshold(double trainerThreshold) {
		this.trainerThreshold = trainerThreshold;
	}

	/**
	 * Indicates if the network is sufficiently trained
	 * 
	 * @return true if network is trained, false otherwise
	 */
	public boolean isTrained() {
		return isTrained;
	}

	/**
	 * Return the current training cycle
	 * 
	 * @return the current training cycle
	 */
	public int trainingCycle() {
		return trainingCycle;
	}
}
