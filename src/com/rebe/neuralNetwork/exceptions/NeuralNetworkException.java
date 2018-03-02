package com.rebe.neuralNetwork.exceptions;

/**
 * A based abstract exception
 * 
 * @author Mattia Rebesan
 *
 */
public abstract class NeuralNetworkException extends Exception {

	/**
	 * Generated serial version uid
	 */
	private static final long serialVersionUID = 1976467022512785406L;

	/**
	 * Constructor that call the super constructor with the passed message
	 * @param message
	 */
	public NeuralNetworkException(String message) {
		super(message);
	}

}
