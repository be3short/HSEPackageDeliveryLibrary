package edu.ucsc.cross.hsl.model.task.packagedelivery;

import edu.ucsc.cross.hse.core.framework.data.Data;
import edu.ucsc.cross.hse.model.position.general.Position;

public interface PackageDeliveryInstructions
{

	public Position getDeliveryDestination();

	public Position getReturnDestination();

}
