package com.rebe.neuralNetwork.components;

import java.util.Iterator;
import java.util.TreeSet;

import com.rebe.neuralNetwork.exceptions.IllegalNeuronsCountException;

/**
 * Represents a layer of the network
 * 
 * @author Mattia Rebesan
 *
 */
public class Layer {

	/**
	 * List of {@link Neuron} of the layer
	 */
	private TreeSet<Neuron> neurons;

	/**
	 * Count of the {@link Neuron} that compose the layer
	 */
	private final int neuronsCount;

	/**
	 * Constructor that initialize the list of the neuron of the layer
	 * 
	 * @param neuronsCount
	 *            count of the {@link Neuron} that compose the layer
	 * @throws IllegalNeuronsCountException
	 *             Thrown if the neurons count inserted is less than 1
	 */
	public Layer(final int neuronsCount) throws IllegalNeuronsCountException {
		if (neuronsCount < 1) {
			throw new IllegalNeuronsCountException();
		}

		this.neuronsCount = neuronsCount;

		neurons = new TreeSet<Neuron>();

		// initializing the list with neuronsCount neuron
		for (int i = 0; i < neuronsCount; i++) {
			neurons.add(new Neuron());
		}
	}

	/**
	 * Return the count of the {@link Neuron} of this layer
	 * 
	 * @return
	 */
	public int size() {
		return neuronsCount;
	}

	public void setValues(double[] values) {
		int index = 0;
		Iterator<Neuron> it = neurons.iterator();
		while (it.hasNext()) {
			it.next().setValue(values[index++]);
		}

	}

	/**
	 * Method that return the values in an array form
	 * 
	 * @return the array of values
	 */
	public double[] values() {
		double[] result = new double[size()];

		int index = 0;
		Iterator<Neuron> it = neurons.iterator();
		while (it.hasNext()) {
			result[index++] = it.next().getValue();
		}

		return result;
	}

	/**
	 * Method that compute the activation value for the neurons of the layer
	 */
	public void activeNeurons() {
		neurons.forEach(neuron -> neuron.activation());
	}

	/**
	 * Method that return the non activated values array
	 * 
	 * @return the array of non activated values
	 */
	public double[] notActivatedValues() {
		double[] result = new double[size()];

		int index = 0;
		Iterator<Neuron> it = neurons.iterator();
		while (it.hasNext()) {
			result[index++] = it.next().getNotActivatedValue();
		}

		return result;
	}

}
