package edu.ucsc.cross.hsl.model.task.packagedelivery;

import bs.commons.objects.manipulation.ObjectCloner;
import edu.ucsc.cross.hse.core.framework.annotations.LibraryDefinition;
import edu.ucsc.cross.hse.core.framework.component.Component;
import edu.ucsc.cross.hse.core.framework.data.Data;
import edu.ucsc.cross.hse.core.framework.models.HybridSystem;
import edu.ucsc.cross.hse.model.network.proximity.NetworkSystem;
import edu.ucsc.cross.hse.model.position.general.Position;
import edu.ucsc.cross.hse.model.position.general.PositionData;
import java.util.ArrayList;
import java.util.HashMap;

public class PackageDeliveryWarehouse extends Component implements HybridSystem
{

	public Data<PositionData> warehouseLocation;
	public NetworkSystem<Position> localNetwork;
	public HashMap<Data<PackageDeliveryVehicleMode>, PackageDeliveryVehicle> vehicleFleet;
	public PackageDeliveryParameters deliveryParams;
	public ArrayList<PackageDeliveryInstructions> packagesToDeliver;
	public PackageDeliveryVehicle vehicleConfig;
	public Data<Integer> vehicleQuantity;

	public PackageDeliveryWarehouse(NetworkSystem<Position> network,
	HashMap<Data<PackageDeliveryVehicleMode>, PackageDeliveryVehicle> fleet, PackageDeliveryParameters delivery_params,
	ArrayList<PackageDeliveryInstructions> packages)
	{
		instantiateElements(network, fleet, delivery_params, packages);
	}

	public PackageDeliveryWarehouse()
	{
		instantiateElements(NetworkSystem.getProximityNetwork(0.0, 0.0), null, new PackageDeliveryParameters(), null);
	}

	@Override
	public void initialize()
	{
		if (vehicleFleet == null)
		{
			vehicleFleet = createFleet(vehicleConfig, vehicleQuantity.setValue());
		}
		if (packagesToDeliver == null)
		{
			packagesToDeliver = createPackagesFleet();
		}
	}

	private void instantiateElements(NetworkSystem<Position> network,
	HashMap<Data<PackageDeliveryVehicleMode>, PackageDeliveryVehicle> fleet, PackageDeliveryParameters delivery_params,
	ArrayList<PackageDeliveryInstructions> packages)
	{
		this.localNetwork = network;
		this.vehicleFleet = fleet;
		this.deliveryParams = delivery_params;
		this.packagesToDeliver = packages;
		warehouseLocation = new Data<PositionData>("Warehouse Location", new PositionData(0.0, 0.0, 0.0));
		vehicleConfig = new PackageDeliveryVehicle();
		vehicleQuantity = new Data<Integer>("Fleet Size", 0);
	}

	private HashMap<Data<PackageDeliveryVehicleMode>, PackageDeliveryVehicle> createFleet(
	PackageDeliveryVehicle vehicle_config, Integer vehicle_quantity)
	{
		HashMap<Data<PackageDeliveryVehicleMode>, PackageDeliveryVehicle> vehicles = new HashMap<Data<PackageDeliveryVehicleMode>, PackageDeliveryVehicle>();
		for (Integer i = 0; i < vehicle_quantity; i++)
		{
			PackageDeliveryVehicle newVehicle = (PackageDeliveryVehicle) ObjectCloner.xmlClone(vehicle_config);
			vehicles.put(newVehicle.vehicleMode, newVehicle);
		}
		return vehicles;
	}

	private ArrayList<PackageDeliveryInstructions> createPackagesFleet()
	{
		ArrayList<PackageDeliveryInstructions> vehicles = new ArrayList<PackageDeliveryInstructions>();
		for (Integer i = 0; i < deliveryParams.numberOfPackagesToDeliver.getValue(); i++)
		{
			PositionData dest = generateNewDestination();
			PackageDeliveryInstructions newPackage = new PackageDeliveryInstructions(dest,
			warehouseLocation.getValue());
			vehicles.add(newPackage);
			System.out.println(dest.getXPosition() + " " + dest.getYPosition() + " " + dest.getZPosition());
		}
		return vehicles;
	}

	public PositionData generateNewDestination()
	{
		Double x = (Math.random() * deliveryParams.xDeliveryRange.getValue() * Math.signum(.5 - Math.random()))
		+ warehouseLocation.getValue().getXPosition();
		Double y = (Math.random() * deliveryParams.yDeliveryRange.getValue() * Math.signum(.5 - Math.random()))
		+ warehouseLocation.getValue().getYPosition();
		return new PositionData(x, y, 0.0);
	}

	@Override
	public void flowMap()
	{
		if (packagesToDeliver())
		{
			int i = 0;
			ArrayList<PackageDeliveryVehicle> idleVehicles = idleVehicles();
			for (PackageDeliveryVehicle veh : idleVehicles)
			{
				if (packagesToDeliver())
				{
					System.out.println("Assigning " + packagesToDeliver.get(i).deliveryDestination.getXPosition() + " "
					+ packagesToDeliver.get(i).deliveryDestination.getYPosition() + " "
					+ packagesToDeliver.get(i).deliveryDestination.getZPosition());
					veh.loadInstructions(ObjectCloner.cloner.deepClone(packagesToDeliver.get(i)));
					packagesToDeliver.remove(i);
				}
			}
		}
	}

	@Override
	public boolean flowSet()
	{
		// TODO Auto-generated method stub
		return true;
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

	private ArrayList<PackageDeliveryVehicle> idleVehicles()
	{
		ArrayList<PackageDeliveryVehicle> idle = new ArrayList<PackageDeliveryVehicle>();
		for (Data<PackageDeliveryVehicleMode> mode : vehicleFleet.keySet())
		{
			if (mode.getValue().equals(PackageDeliveryVehicleMode.IDLE))
			{
				idle.add(vehicleFleet.get(mode));

			}
		}
		return idle;
	}

	private boolean packagesToDeliver()
	{
		return packagesToDeliver.size() > 0;
	}

	@LibraryDefinition(label = "Package Delivery Warehouse")
	public static PackageDeliveryWarehouse getPackageDeliveryWarehouse()
	{
		return new PackageDeliveryWarehouse();
	}
}
