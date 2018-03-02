package com.rebe.neuralNetwork.exceptions;

/**
 * Simple exception thrown when the neurons count for each hidden layer is less than the count of the hidden layers
 * 
 * @author Mattia Rebesan
 *
 */
public class NotEnoughLayersException extends NeuralNetworkException {

	/**
	 * Generated serial version uid
	 */
	private static final long serialVersionUID = -7421128309956650452L;

	/**
	 * Constructor that throw a new {@link NotEnoughLayersException}
	 */
	public NotEnoughLayersException() {
		super("Not enough neurons to fill every hidden layer");
	}

}
