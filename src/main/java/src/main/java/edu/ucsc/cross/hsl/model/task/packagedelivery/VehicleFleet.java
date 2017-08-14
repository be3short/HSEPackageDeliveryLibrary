package edu.ucsc.cross.hsl.model.task.packagedelivery;

import java.util.ArrayList;

import edu.ucsc.cross.hse.core.framework.data.Data;
import edu.ucsc.cross.hse.model.vehicle.general.Vehicle;

public interface VehicleFleet
{

	public Data<ArrayList<Vehicle>> getAllVehicles();
}
