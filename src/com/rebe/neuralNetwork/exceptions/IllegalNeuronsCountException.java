package com.rebe.neuralNetwork.exceptions;

/**
 * Simple exception thrown when the neurons count inserted is not acceptable
 * 
 * @author Mattia Rebesan
 *
 */
public class IllegalNeuronsCountException extends NeuralNetworkException {

	/**
	 * Generated serial version uid
	 */
	private static final long serialVersionUID = -7421128309956650452L;

	/**
	 * Constructor that throw a new {@link IllegalNeuronsCountException}
	 */
	public IllegalNeuronsCountException() {
		super("Neurons count inserted must be greater than 0");
	}

}
