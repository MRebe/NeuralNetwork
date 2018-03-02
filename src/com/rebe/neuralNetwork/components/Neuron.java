package com.rebe.neuralNetwork.components;

import com.rebe.neuralNetwork.utils.Utils;

/**
 * Rapresents a neuron of the network
 * 
 * @author Mattia Rebesan
 *
 */
class Neuron implements Comparable<Neuron> {

	/**
	 * Identifier for the neuron
	 */
	private final int id;

	/**
	 * Value of the neuron
	 */
	private double value;

	/**
	 * Not activated value of the neuron
	 */
	private double notActivatedValue;

	/**
	 * Constructor that initialize the neuron
	 */
	public Neuron() {
		id = Utils.getNeuronId();
	}

	/**
	 * Activation function
	 */
	public void activation() {
		value = Utils.activationFunction(value);
	}

	/**
	 * Set a new value in the neuron
	 * 
	 * @param value
	 *            the new value to be set
	 */
	public void setValue(double value) {
		notActivatedValue = value;
		this.value = value;
	}

	/**
	 * Return the current value of the neuron
	 * 
	 * @return current value of the neuron
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Return the current non activated value of the neuron
	 * 
	 * @return current non activated value of the neuron
	 */
	public double getNotActivatedValue() {
		return notActivatedValue;
	}

	/**
	 * Getter for the identifier
	 */
	public int getId() {
		return id;
	}

	/**
	 * Comparator for the Comparable implementation
	 */
	@Override
	public int compareTo(Neuron o) {
		return id - o.getId();
	}

}
