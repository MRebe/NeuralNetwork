package com.rebe.neuralNetwork.exceptions;

/**
 * Simple exception thrown when the layer passed is null
 * 
 * @author Mattia Rebesan
 *
 */
public class EmptyLayerException extends NeuralNetworkException {

	/**
	 * Generated serial version uid
	 */
	private static final long serialVersionUID = 139410045289094788L;

	/**
	 * Constructor that throw a new {@link EmptyLayerException}
	 */
	public EmptyLayerException() {
		super("Layer must not be null");
	}

}
