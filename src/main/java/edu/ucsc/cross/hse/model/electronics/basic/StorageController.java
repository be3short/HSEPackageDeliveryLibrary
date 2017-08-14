package edu.ucsc.cross.hse.model.electronics.basic;

import edu.ucsc.cross.hse.core.framework.component.Component;
import edu.ucsc.cross.hse.core.framework.data.Data;

public class StorageController extends Component
{

	public StorageState storageState;
	public Data<Integer> generatingData;
	public Data<Double> additionalChangeRate;

	public StorageController(StorageState storage_state, Integer generating, Double transmission_rate)
	{
		super("Storage Controller");
		instantiateElements(storage_state, generating, transmission_rate);
	}

	public StorageController(StorageState storage_state)
	{
		super("Storage Controller");
		instantiateElements(storage_state, 0, 0.0);
	}

	/*
	 * Instantiates state and data elements
	 */
	private void instantiateElements(StorageState storage_state, Integer generating, Double transmission_rate)
	{
		storageState = storage_state;
		generatingData = new Data<Integer>("Generating Data", generating);
		additionalChangeRate = new Data<Double>("Additional Data Transfer", transmission_rate);
	}

	public <T> void performControllerAction()
	{
		// TODO Auto-generated method stub

	}

}
