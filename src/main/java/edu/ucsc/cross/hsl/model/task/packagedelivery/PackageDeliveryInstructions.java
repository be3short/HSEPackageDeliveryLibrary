package edu.ucsc.cross.hsl.model.task.packagedelivery;

import edu.ucsc.cross.hse.core.framework.component.Component;
import edu.ucsc.cross.hse.core.framework.data.Data;
import edu.ucsc.cross.hse.model.position.general.Position;

public class PackageDeliveryInstructions extends Component
{

	public Data<Position> deliveryDestination;
	public Data<Position> returnDestination;

	public PackageDeliveryInstructions(Position destination, Position return_destination)
	{
		super("Package Delivery Instructions");
		deliveryDestination = new Data<Position>("Delivery Destination", destination);
		returnDestination = new Data<Position>("Return Destination", return_destination);
	}
}
