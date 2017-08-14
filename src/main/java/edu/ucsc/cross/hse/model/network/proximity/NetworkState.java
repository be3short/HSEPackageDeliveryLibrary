package edu.ucsc.cross.hse.model.network.proximity;

import edu.ucsc.cross.hse.model.electronics.basic.ConnectedStorageController;
import edu.ucsc.cross.hse.model.electronics.basic.StorageSystem;
import java.util.HashMap;

public class NetworkState<T>
{

	public HashMap<T, StorageSystem<ConnectedStorageController>> network;

	public HashMap<T, T> connections;

	public NetworkState(HashMap<T, StorageSystem<ConnectedStorageController>> networked_components)
	{
		network = networked_components;
		connections = new HashMap<T, T>();
	}

	public NetworkState()
	{
		network = new HashMap<T, StorageSystem<ConnectedStorageController>>();
		connections = new HashMap<T, T>();
	}

}
