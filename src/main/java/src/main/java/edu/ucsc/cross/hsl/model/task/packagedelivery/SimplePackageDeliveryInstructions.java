package edu.ucsc.cross.hsl.model.task.packagedelivery;

import edu.ucsc.cross.hse.core.framework.component.Component;
import edu.ucsc.cross.hse.core.framework.data.Data;
import edu.ucsc.cross.hse.model.position.general.Position;

public class SimplePackageDeliveryInstructions extends Component implements PackageDeliveryInstructions
{

	public Data<Position> deliveryDestination;
	public Data<Position> returnDestination;

	@Override
	public Position getDeliveryDestination()
	{
		// TODO Auto-generated method stub
		return deliveryDestination.getValue();
	}

	@Override
	public Position getReturnDestination()
	{
		// TODO Auto-generated method stub
		return returnDestination.getValue();
	}
}
