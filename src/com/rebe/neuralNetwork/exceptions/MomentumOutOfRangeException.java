package com.rebe.neuralNetwork.exceptions;

/**
 * Simple exception thrown when momentum value is not in the range [0, 1[
 * 
 * @author Mattia Rebesan
 *
 */
public class MomentumOutOfRangeException extends NeuralNetworkException {

	/**
	 * Generated serial version uid
	 */
	private static final long serialVersionUID = -7421128309956650452L;

	/**
	 * Constructor that throw a new {@link MomentumOutOfRangeException}
	 */
	public MomentumOutOfRangeException() {
		super("Momentum must be in [0, 1[");
	}

}
