package edu.ucsc.cross.hsl.model.task.packagedelivery;

import edu.ucsc.cross.hse.core.framework.component.Component;
import edu.ucsc.cross.hse.model.position.general.PositionData;

public class PackageDeliveryInstructions extends Component
{

	public PositionData deliveryDestination;
	public PositionData returnDestination;

	public PackageDeliveryInstructions(PositionData destination, PositionData return_destination)
	{
		super("Package Delivery Instructions");
		deliveryDestination = destination;
		returnDestination = return_destination;
	}
}
