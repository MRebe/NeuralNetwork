package com.rebe.neuralNetwork.utils;

import java.util.Random;

import com.rebe.neuralNetwork.exceptions.IllegalRandomizerArgumentException;

/**
 * Values generator for the weights matrixes of a neural network from the range
 * of (−1√x,1√x)
 * 
 * @author Mattia Rebesan
 *
 */
public class WeightRandomizer {

	private Random rand;

	/**
	 * The x value in the function (−1√x,1√x)
	 */
	private final int bound;

	/**
	 * Constructor that initialize the generator with the bound for the values range
	 * 
	 * @param bound
	 *            bound of the interval
	 * @throws IllegalRandomizerArgumentException
	 *             Thorwn in case of bound is less than 1
	 */
	public WeightRandomizer(final int bound) throws IllegalRandomizerArgumentException {
		if (bound < 1) {
			throw new IllegalRandomizerArgumentException();
		}
		rand = new Random();
		this.bound = bound;
	}

	/**
	 * Method that create a new random value included in the range (−1√x,1√x)
	 * 
	 * @return the computed random value
	 */
	public double randWeight() {
		// generate a random value between 0 and 2
		float result = rand.nextFloat() + rand.nextFloat();
		// translate the value between -1 and 1
		result -= 1;
		// narrow the range between −1√x and 1√x
		result /= Math.sqrt(bound);
		
		return result;
	}

}
