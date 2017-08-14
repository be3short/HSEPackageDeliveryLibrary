package edu.ucsc.cross.hsl.model.task.packagedelivery;

import edu.ucsc.cross.hse.core.framework.annotations.LibraryDefinition;
import edu.ucsc.cross.hse.core.framework.component.Component;
import edu.ucsc.cross.hse.core.framework.environment.HybridEnvironment;
import edu.ucsc.cross.hse.model.electronics.basic.ConnectedStorageController;
import edu.ucsc.cross.hse.model.electronics.basic.StorageState;
import edu.ucsc.cross.hse.model.electronics.basic.StorageSystem;
import edu.ucsc.cross.hse.model.network.proximity.NetworkSystem;
import edu.ucsc.cross.hse.model.position.general.Position;
import edu.ucsc.cross.hse.model.vehicle.general.Vehicle;
import edu.ucsc.cross.hse.model.vehicle.navigation.DestinationControl;
import edu.ucsc.cross.hse.model.vehicle.pointmass.TestSystem;

public class VehicleWithStorage extends Component
{

	public StorageSystem<ConnectedStorageController> storage;
	public Vehicle<DestinationControl> vehicle;

	public VehicleWithStorage()
	{
		super("Vehicle With Storage");
		storage = new StorageSystem<ConnectedStorageController>(new StorageState(), new ConnectedStorageController());
		vehicle = TestSystem.getSimplePointMassRandomVehicleSystem();
	}

	@LibraryDefinition(label = "10 Vehicles with Storage and Network")
	public static HybridEnvironment getLib()
	{

		HybridEnvironment env = new HybridEnvironment();
		NetworkSystem<Position> net = NetworkSystem.getProximityNetwork(20.0, 40.0);
		env.component().configure().addComponent(net);
		for (int i = 0; i < 3; i++)
		{
			VehicleWithStorage veh = new VehicleWithStorage();
			veh.storage.state.maxCapacity.setValue(500.0);
			veh.storage.state.generationRate.setValue(Math.random() * 5.0 * (1 - Math.round(Math.random())));
			veh.storage.control.generatingData.setValue(1);
			net.addItemsToNetwork(veh.vehicle, veh.storage);
			env.component().configure().addComponent(veh);
		}
		return env;

	}
}
