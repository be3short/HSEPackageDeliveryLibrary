package edu.ucsc.cross.hse.model.electronics.basic;

import edu.ucsc.cross.hse.core.framework.component.Component;
import edu.ucsc.cross.hse.core.framework.data.Data;
import edu.ucsc.cross.hse.core.framework.data.State;

public class StorageState extends Component
{

	public State currentCapacity;
	public State overFlow;
	public Data<Double> maxCapacity;
	public Data<Double> generationRate;

	public StorageState(Double generation_rate, Double initial_capacity, Double max_capacity, Double over_flow_initial)
	{
		super("Storage State");
		instantiateElements(generation_rate, initial_capacity, max_capacity, over_flow_initial);
	}

	public StorageState()
	{
		super("Storage State");
		instantiateElements(0.0, 0.0, 0.0, 0.0);
	}

	public Double percentageCapacityUsed()
	{
		return currentCapacity.getValue() / maxCapacity.getValue();
	}

	/*
	 * Instantiates state and data elements
	 */
	private void instantiateElements(Double generation_rate, Double initial_capacity, Double max_capacity,
	Double over_flow_initial)
	{
		currentCapacity = new State("Current Capacity (mB)", initial_capacity);
		overFlow = new State("Data Overflow (mB)", over_flow_initial);
		maxCapacity = new Data<Double>("Max Storage Capacity (mB)", max_capacity);
		generationRate = new Data<Double>("Generation Rate (mB/sec)", generation_rate);
	}
}
