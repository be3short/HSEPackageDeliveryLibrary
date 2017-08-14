package edu.ucsc.cross.hsl.model.task.packagedelivery;

import edu.ucsc.cross.hse.core.framework.component.Component;
import edu.ucsc.cross.hse.core.framework.models.HybridSystem;
import edu.ucsc.cross.hse.model.vehicle.general.Vehicle;

public class PackageDeliveryVehicle extends Component implements HybridSystem
{

	public Vehicle vehicle;
	public Equipment equipment;

	public PackageDeliveryInstructions taskStatus;

	@Override
	public void flowMap()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean flowSet()
	{
		// TODO Auto-generated method stub
		return false;
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

}
