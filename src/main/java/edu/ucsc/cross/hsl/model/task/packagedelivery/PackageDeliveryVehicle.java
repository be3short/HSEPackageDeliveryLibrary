package edu.ucsc.cross.hsl.model.task.packagedelivery;

import edu.ucsc.cross.hse.core.framework.component.Component;
import edu.ucsc.cross.hse.core.framework.data.Data;
import edu.ucsc.cross.hse.core.framework.models.HybridSystem;
import edu.ucsc.cross.hse.model.electronics.basic.ConnectedStorageController;
import edu.ucsc.cross.hse.model.electronics.basic.StorageState;
import edu.ucsc.cross.hse.model.electronics.basic.StorageSystem;
import edu.ucsc.cross.hse.model.position.general.Position;
import edu.ucsc.cross.hse.model.position.general.PositionStateData;
import edu.ucsc.cross.hse.model.vehicle.general.Vehicle;
import edu.ucsc.cross.hse.model.vehicle.navigation.DestinationControl;
import edu.ucsc.cross.hse.model.vehicle.navigation.WaypointConroller;
import edu.ucsc.cross.hse.model.vehicle.pointmass.PointMassVehicleSystem;
import edu.ucsc.cross.hse.model.vehicle.pointmass.SimplePointMassVehicleNavigationController;
import edu.ucsc.cross.hse.model.vehicle.pointmass.SimplePointMassVehicleParameters;

public class PackageDeliveryVehicle extends Component implements HybridSystem
{

	public StorageSystem<ConnectedStorageController> storage;
	public Vehicle<DestinationControl> vehicle;
	public Data<PackageDeliveryInstructions> taskStatus;
	public Data<PackageDeliveryVehicleMode> vehicleMode;

	public PackageDeliveryVehicle(Vehicle<DestinationControl> vehicle,
	StorageSystem<ConnectedStorageController> storage)
	{
		instantiateElements(vehicle, storage);
	}

	public PackageDeliveryVehicle()
	{
		storage = new StorageSystem<ConnectedStorageController>(new StorageState(), new ConnectedStorageController());
		PositionStateData position = new PositionStateData();
		SimplePointMassVehicleParameters parameters = new SimplePointMassVehicleParameters(1.0, 1.0);
		SimplePointMassVehicleNavigationController controller = new SimplePointMassVehicleNavigationController(10.0, .1,
		.1);
		PointMassVehicleSystem<DestinationControl> system = new PointMassVehicleSystem<DestinationControl>(position,
		parameters, controller);
		vehicle = system;
		taskStatus = new Data<PackageDeliveryInstructions>("Delivery Instructions", null);
		vehicleMode = new Data<PackageDeliveryVehicleMode>("Vehicle Mode", PackageDeliveryVehicleMode.IDLE);

	}

	private void instantiateElements(Vehicle<DestinationControl> vehicle,
	StorageSystem<ConnectedStorageController> storage)
	{
		this.storage = storage;
		this.vehicle = vehicle;
		taskStatus = new Data<PackageDeliveryInstructions>("Delivery Instructions", null);
		vehicleMode = new Data<PackageDeliveryVehicleMode>("Vehicle Mode", PackageDeliveryVehicleMode.IDLE);
	}

	@Override
	public void flowMap()
	{
		switch (vehicleMode.getValue())
		{
		case IDLE:
		{
			if (deliveryInstructionsReceived())
			{
				vehicleMode.setValue(PackageDeliveryVehicleMode.DELIVERING);
				vehicle.getVehicleController().updateDestination(taskStatus.getValue().deliveryDestination.getValue());
			}
			System.out.println("hwew");
			System.exit(1);
			break;
		}
		case DELIVERING:
		{
			if (vehicle.component().getContent().getObjects(WaypointConroller.class, true).get(0).destinationReached
			.getValue())
			{
				vehicleMode.setValue(PackageDeliveryVehicleMode.RETURNING);
				vehicle.getVehicleController().updateDestination(taskStatus.getValue().returnDestination.getValue());
				System.out.println("returning");
			}
			break;
		}
		case RETURNING:
		{
			if (vehicle.component().getContent().getObjects(WaypointConroller.class, true).get(0).destinationReached
			.getValue())
			{
				vehicleMode.setValue(PackageDeliveryVehicleMode.IDLE);
				taskStatus.setValue(null);
				System.out.println("returned");
				break;
			}
		}
		}

	}

	public void loadInstructions(PackageDeliveryInstructions instructions)
	{
		vehicleMode.setValue(PackageDeliveryVehicleMode.DELIVERING);
		taskStatus.setValue(instructions);
		vehicle.getVehicleController().updateDestination(taskStatus.getValue().deliveryDestination.getValue());
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

	private boolean deliveryInstructionsReceived()
	{
		System.out.println(taskStatus.toString());
		return taskStatus.getValue() != null;
	}
}
