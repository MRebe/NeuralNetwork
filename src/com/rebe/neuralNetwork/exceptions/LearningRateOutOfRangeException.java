package com.rebe.neuralNetwork.exceptions;

/**
 * Simple exception thrown when learning rate value is not in the range ]0, 1]
 * 
 * @author Mattia Rebesan
 *
 */
public class LearningRateOutOfRangeException extends NeuralNetworkException {

	/**
	 * Generated serial version uid
	 */
	private static final long serialVersionUID = -7421128309956650452L;

	/**
	 * Constructor that throw a new {@link LearningRateOutOfRangeException}
	 */
	public LearningRateOutOfRangeException() {
		super("Learning rate must be in ]0, 1]");
	}

}
