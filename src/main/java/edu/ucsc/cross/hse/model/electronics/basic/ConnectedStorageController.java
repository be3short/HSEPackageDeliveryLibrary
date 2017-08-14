package edu.ucsc.cross.hse.model.electronics.basic;

import edu.ucsc.cross.hse.core.framework.data.Data;
import edu.ucsc.cross.hse.model.network.proximity.NetworkSystem;
import java.util.HashMap;

public class ConnectedStorageController extends StorageController
{

	public Data<NetworkSystem<?>> connectedNetwork;

	public Data<StorageSystem<ConnectedStorageController>> connectedSystem;

	public Data<Double> maxTransmissionSpeed;

	public Data<Double> currentTransmissionSpeed;

	public Data<Double> receiveThreshold;

	public Data<Double> connectThreshold;

	public ConnectedStorageController(StorageState storage_state)
	{
		super(storage_state);
		initializeElements();
	}

	public ConnectedStorageController()
	{
		super(null);
		initializeElements();
	}

	private void initializeElements()
	{
		maxTransmissionSpeed = new Data<Double>("Maximum Transmission Speed", 0.0);
		currentTransmissionSpeed = new Data<Double>("Current Transmission Speed", 0.0);
		receiveThreshold = new Data<Double>("Maximum Transmission Speed", 0.3);
		connectThreshold = new Data<Double>("Maximum Transmission Speed", 0.6);
		connectedNetwork = new Data<NetworkSystem<?>>("Connected Network", null);
		connectedSystem = new Data<StorageSystem<ConnectedStorageController>>("Connected System", null);
	}

	@Override
	public void performControllerAction()
	{
		if (!isConnected())
		{
			if (this.storageState.percentageCapacityUsed() >= connectThreshold.getValue())
			{
				Object ownIndex = (Object) connectedNetwork.getValue().getValueFromController(this);
				Class<?> classT = ownIndex.getClass();
				HashMap<?, StorageSystem<ConnectedStorageController>> connections = connectedNetwork.getValue()
				.getAvailableConnections(this);
				Double minSize = Double.MAX_VALUE;
				StorageSystem<ConnectedStorageController> conn = null;
				for (StorageSystem<ConnectedStorageController> avail : connections.values())
				{
					if (avail.state.currentCapacity.getValue() < minSize)
					{
						conn = avail;
						minSize = avail.state.currentCapacity.getValue();
					}
				}
				if (conn != null)
				{
					connectedNetwork.getValue()
					.establishConnection(connectedNetwork.getValue().getStorageValueFromController(this), conn);
				}
			}
		}
	}

	public void terminateConnection()
	{
		connectedSystem.setValue(null);
		maxTransmissionSpeed.setValue(0.0);
	}

	public void newConnectionEstablished(StorageSystem<ConnectedStorageController> connection, Double transmission_rate)
	{
		connectedSystem.setValue(connection);
		maxTransmissionSpeed.setValue(transmission_rate);
	}

	public boolean isConnected()
	{
		return connectedSystem.getValue() != null;
	}

	public void updateRates()
	{
		currentTransmissionSpeed.setValue(transmitSpeed());
		this.additionalChangeRate
		.setValue(-currentTransmissionSpeed.getValue() + connectedSystem.getValue().control.transmitSpeed());
	}

	public Double transmitSpeed()
	{
		Double transmitSpeed = 0.0;

		if (connectedSystem.getValue().state.percentageCapacityUsed() + receiveThreshold.getValue() < this.storageState
		.percentageCapacityUsed())
		{
			transmitSpeed = maxTransmissionSpeed.getValue();
		}
		return transmitSpeed;
	}
}
