package edu.ucsc.cross.hsl.model.uas.mission;

import java.util.HashMap;

import edu.ucsc.cross.hse.core.framework.component.Component;

public class PackageDeliveryWarehouse extends Component
{

	PackageDeliveryWarehouseParameters parameters;
	HashMap<Integer, Vehicle> vehicleFleet;

	public static enum MissionStage
	{
		TAKEOFF,
		DATA_COLLECTION,
		LANDING;
	}

}
