package com.rebe.neuralNetwork.exceptions;

/**
 * Simple exception thrown when the bound for the randomizer is less than 1
 * 
 * @author Mattia Rebesan
 *
 */
public class IllegalRandomizerArgumentException extends NeuralNetworkException {

	/**
	 * Generated serial version uid
	 */
	private static final long serialVersionUID = -2229574055888604708L;

	/**
	 * Constructor that throw a new {@link IllegalRandomizerArgumentException}
	 */
	public IllegalRandomizerArgumentException() {
		super("Bound inserted must be greater than 1");
	}

}
