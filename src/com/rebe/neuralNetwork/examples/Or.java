package com.rebe.neuralNetwork.examples;

import java.util.stream.IntStream;

import com.rebe.neuralNetwork.NeuralNetwork;
import com.rebe.neuralNetwork.exceptions.IllegalNeuronsCountException;
import com.rebe.neuralNetwork.exceptions.NeuralNetworkException;

/**
 * Simple OR circuit simulated by the network
 * 
 * @author Mattia Rebesan
 *
 */
public class Or {

	private final static int inputCount = 2;
	private final static int outputCount = 1;
	private final static int hiddenLayerLayersCount = 1;
	private final static int[] hiddenLayersNeuronCount = { 3 };
	private final static double momentum = 0.8;
	private final static double learningRate = 0.9;

	private static NeuralNetwork neuralNetwork;

	private final static double[][] inputs = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } };
	private final static double[] expectedOutput = { 0, 0, 0, 1 };

	public static void main(String[] args) {

		try {

			neuralNetwork = new NeuralNetwork(inputCount, hiddenLayerLayersCount, hiddenLayersNeuronCount, outputCount,
					momentum, learningRate);

			int cicle = 0;

			while (!neuralNetwork.isTrained()) {
				try {
					System.out.print("Cycle " + cicle + " ");
					System.out.print("Input: " + inputs[cicle % 4][0] + ", " + inputs[cicle % 4][1] + " ");
					double[] result = neuralNetwork.computeResult(inputs[cicle % 4]);
					IntStream.range(0, result.length).forEach(indexResults -> {
						System.out.print("Result: " + result[indexResults] + " ");
					});
					double[] expected = new double[1];
					expected[0] = expectedOutput[cicle % 4];
					System.out.println("Expected: " + expected[0] + " ");
					neuralNetwork.learn(expected);

				} catch (IllegalNeuronsCountException e) {
					e.printStackTrace();
				}
				cicle++;
			}

			System.out.println("Network error coefficient: " + neuralNetwork.getNetworkErrorCoefficent());
		} catch (NeuralNetworkException e) {
			e.printStackTrace();
		}
	}

}
