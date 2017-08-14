package edu.ucsc.cross.hse.model.electronics.basic;

import edu.ucsc.cross.hse.core.framework.annotations.LibraryDefinition;
import edu.ucsc.cross.hse.core.framework.component.Component;
import edu.ucsc.cross.hse.core.framework.models.HybridSystem;

public class StorageSystem<T extends StorageController> extends Component implements HybridSystem
{

	public StorageState state;
	public T control;

	public StorageSystem(StorageState state, T control)
	{
		this.state = state;
		this.control = control;
		this.control.storageState = state;
	}

	@Override
	public void flowMap()
	{
		control.performControllerAction();
		Double netRate = computeNetTransferRate();
		if (netRate > 0)
		{
			if (state.currentCapacity.getValue() >= state.maxCapacity.getValue())
			{
				state.currentCapacity.setDerivative(0.0);
				state.overFlow.setDerivative(netRate);
			} else
			{
				state.currentCapacity.setDerivative(netRate);
			}
		} else
		{
			if (state.currentCapacity.getValue() <= 0.0)
			{
			} else
			{
				state.currentCapacity.setDerivative(netRate);
			}
		}

	}

	@Override
	public boolean flowSet()
	{
		boolean generatingData = (control.generatingData.getValue() > 0);
		boolean transferringData = (Math.abs(control.additionalChangeRate.getValue()) > 0);
		return generatingData || transferringData;
	}

	@Override
	public void jumpMap()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean jumpSet()
	{
		// TODO Auto-generated method stub
		return false;
	}

	private Double computeNetTransferRate()
	{
		Double generationRate = control.generatingData.getValue() * state.generationRate.getValue();
		Double transferRate = control.additionalChangeRate.getValue();
		Double netRate = generationRate + transferRate;
		return netRate;
	}

	@LibraryDefinition(label = "Simple Storage Device")
	public static StorageSystem storageDevice()
	{
		StorageState state = new StorageState();
		StorageController controller = new StorageController(state);
		return new StorageSystem(state, controller);
	}
}
