package edu.ucsc.cross.hsl.model.task.packagedelivery;

import edu.ucsc.cross.hse.core.framework.component.Component;
import edu.ucsc.cross.hse.core.framework.data.Data;
import edu.ucsc.cross.hse.core.framework.models.HybridSystem;
import edu.ucsc.cross.hse.model.electronics.basic.ConnectedStorageController;
import edu.ucsc.cross.hse.model.electronics.basic.StorageState;
import edu.ucsc.cross.hse.model.electronics.basic.StorageSystem;
import edu.ucsc.cross.hse.model.position.general.Position;
import edu.ucsc.cross.hse.model.vehicle.general.Vehicle;
import edu.ucsc.cross.hse.model.vehicle.navigation.DestinationControl;
import edu.ucsc.cross.hse.model.vehicle.pointmass.TestSystem;

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
		vehicle = TestSystem.getSimplePointMassVehicleSystem();
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

			break;
		}
		case DELIVERING:
		{
			if (Position.computeDirectDistance(vehicle, taskStatus.getValue().deliveryDestination.getValue()) <= .1)
			{
				vehicleMode.setValue(PackageDeliveryVehicleMode.RETURNING);
				vehicle.getVehicleController().updateDestination(taskStatus.getValue().returnDestination.getValue());

			}
			break;
		}
		case RETURNING:
		{
			if (Position.computeDirectDistance(vehicle, taskStatus.getValue().returnDestination.getValue()) <= .1)
			{
				vehicleMode.setValue(PackageDeliveryVehicleMode.IDLE);
				taskStatus.setValue(null);

			}
		}
		}

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
		return taskStatus.getValue() != null;
	}
}
