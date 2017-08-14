package edu.ucsc.cross.hse.model.network.proximity;

import edu.ucsc.cross.hse.model.electronics.basic.ConnectedStorageController;
import edu.ucsc.cross.hse.model.electronics.basic.StorageSystem;
import java.util.HashMap;

public interface NetworkController<T>
{

	public Double transmissionRate(T a, T b);

	public boolean inRange(T a, T b);

	public <S extends ConnectedStorageController> HashMap<T, StorageSystem<S>> getAvailableConnections(T node,
	NetworkState<T> state);

}
