package edu.ucsc.cross.hse.model.network.proximity;

import edu.ucsc.cross.hse.core.framework.component.Component;
import edu.ucsc.cross.hse.core.framework.models.HybridSystem;
import edu.ucsc.cross.hse.model.electronics.basic.ConnectedStorageController;
import edu.ucsc.cross.hse.model.electronics.basic.StorageSystem;
import edu.ucsc.cross.hse.model.position.general.Position;
import java.util.HashMap;

public class NetworkSystem<T> extends Component implements HybridSystem
{

	public NetworkState<T> state;
	public NetworkController<T> controller;

	public NetworkSystem(NetworkState<T> state, NetworkController<T> controller)
	{
		super("Network System");
		this.state = state;
		this.controller = controller;
	}

	@Override
	public void flowMap()
	{
		determineIfConnectionsAreTerminated();
		computeTransmissionSpeeds();
	}

	private void determineIfConnectionsAreTerminated()
	{
		for (T a : state.connections.keySet())
		{
			if (state.connections.get(a) != null)
			{
				try
				{
					StorageSystem<ConnectedStorageController> storageA = state.network.get(a);
					StorageSystem<ConnectedStorageController> storageB = state.network.get(state.network.get(a));
					Boolean maxSpeed = controller.inRange(a, state.connections.get(a));
					if (!maxSpeed)
					{
						storageA.control.maxTransmissionSpeed.setValue(0.0);
						storageB.control.maxTransmissionSpeed.setValue(0.0);
						storageA.control.connectedSystem.setValue(null);
						storageB.control.connectedSystem.setValue(null);
						state.connections.put(a, null);
					}
				} catch (Exception terminatedAlready)
				{
					state.connections.remove(a);
				}
			}
		}
	}

	private void computeTransmissionSpeeds()
	{
		for (T a : state.connections.keySet())
		{
			StorageSystem<ConnectedStorageController> storageA = state.network.get(a);
			StorageSystem<ConnectedStorageController> storageB = state.network.get(state.network.get(a));
			Double maxSpeed = controller.transmissionRate(a, state.connections.get(a));
			storageA.control.maxTransmissionSpeed.setValue(maxSpeed);
			storageB.control.maxTransmissionSpeed.setValue(maxSpeed);
		}
	}

	public void addItemsToNetwork(T index, StorageSystem<ConnectedStorageController> storage)
	{
		storage.control.connectedNetwork.setValue(this);
		state.network.put(index, storage);
	}

	public HashMap<T, StorageSystem<ConnectedStorageController>> getAvailableConnections(T node)
	{
		HashMap<T, StorageSystem<ConnectedStorageController>> conns = controller.getAvailableConnections(node, state);

		return conns;
	}

	public HashMap<T, StorageSystem<ConnectedStorageController>> getAvailableConnections(
	ConnectedStorageController controller)
	{
		T value = getValueFromController(controller);
		HashMap<T, StorageSystem<ConnectedStorageController>> conns = this.controller.getAvailableConnections(value,
		state);

		return conns;
	}

	public void establishConnection(StorageSystem<ConnectedStorageController> a,
	StorageSystem<ConnectedStorageController> b)
	{
		System.out.println(a + "   " + b);
		state.connections.put(getValueFromController(a.control), getValueFromController(b.control));
	}

	public T getValueFromController(ConnectedStorageController controller)
	{
		T val = null;
		for (T key : this.state.network.keySet())
		{
			if (state.network.get(key).control.equals(controller))
			{
				val = key;
				break;
			}
		}
		return val;
	}

	public StorageSystem<ConnectedStorageController> getStorageValueFromController(
	ConnectedStorageController controller)
	{
		StorageSystem<ConnectedStorageController> val = null;
		for (T key : this.state.network.keySet())
		{
			if (state.network.get(key).control.equals(controller))
			{
				val = state.network.get(key);
				break;
			}
		}
		return val;
	}

	@Override
	public boolean flowSet()
	{

		return true;
	}

	@Override
	public void jumpMap()
	{

	}

	@Override
	public boolean jumpSet()
	{
		return false;
	}

	public static NetworkSystem<Position> getProximityNetwork(Double max_transmission, Double max_dist)
	{
		NetworkState<Position> state = new NetworkState<Position>();
		ProximityNetworkController controller = new ProximityNetworkController(max_dist, max_transmission,
		SignalDistanceSlope.SQRT);
		NetworkSystem<Position> newNetwork = new NetworkSystem<Position>(state, controller);
		return newNetwork;
	}
}
