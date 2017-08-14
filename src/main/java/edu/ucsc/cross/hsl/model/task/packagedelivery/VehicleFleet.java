package edu.ucsc.cross.hsl.model.task.packagedelivery;

import edu.ucsc.cross.hse.core.framework.data.Data;
import edu.ucsc.cross.hsl.model.uas.mission.Vehicle;
import java.util.ArrayList;

public interface VehicleFleet
{

	public Data<ArrayList<Vehicle>> getAllVehicles();
}
