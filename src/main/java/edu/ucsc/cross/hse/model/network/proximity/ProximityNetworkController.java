package edu.ucsc.cross.hse.model.network.proximity;

import edu.ucsc.cross.hse.core.framework.component.Component;
import edu.ucsc.cross.hse.core.framework.data.Data;
import edu.ucsc.cross.hse.model.electronics.basic.ConnectedStorageController;
import edu.ucsc.cross.hse.model.electronics.basic.StorageSystem;
import edu.ucsc.cross.hse.model.position.general.Position;
import java.util.HashMap;

public class ProximityNetworkController extends Component implements NetworkController<Position>
{

	/*
	 * Maximum euclidean distance that a connection can be established
	 */
	public Data<Double> horizontalProximityThreshold;

	/*
	 * Transfer rate when the distance between the two points is zero
	 */
	public Data<Double> maximumTransferRate;

	/*
	 * Definition of the slope of the signal strength as distance increases
	 */
	public Data<SignalDistanceSlope> currentWaypoint;

	/*
	 * Constructor with thresholds defined and potentially including waypoints
	 */
	public ProximityNetworkController(Double horiz_prox_threshold, Double max_rate, SignalDistanceSlope slope)
	{
		super("Proximity Network Controller");
		instantiateElements(horiz_prox_threshold, max_rate, slope);
	}

	/*
	 * Constructor without thresholds or waypoints defined
	 */
	public ProximityNetworkController()
	{
		super("Proximity Network Controller");
		instantiateElements(0.0, 0.0, SignalDistanceSlope.LINEAR);
	}

	/*
	 * Instantiates state and data elements
	 */
	private void instantiateElements(Double horiz_prox_threshold, Double max_rate, SignalDistanceSlope slope)
	{

		horizontalProximityThreshold = new Data<Double>("Horizontal Proximity Threshold", horiz_prox_threshold);
		maximumTransferRate = new Data<Double>("Maximum Transfer Rate", max_rate);
		currentWaypoint = new Data<SignalDistanceSlope>("Signal Dropoff Slope", slope);
	}

	@Override
	public Double transmissionRate(Position a, Position b)
	{
		Double rate = currentWaypoint.getValue().computeSignal(maximumTransferRate.getValue(),
		Position.computeDirectDistance(a, b), horizontalProximityThreshold.getValue());
		return rate;
	}

	@Override
	public boolean inRange(Position a, Position b)
	{
		boolean outOfRange = Position.computeDirectDistance(a, b) > horizontalProximityThreshold.getValue();
		return outOfRange;
	}

	@Override
	public <S extends ConnectedStorageController> HashMap<Position, StorageSystem<S>> getAvailableConnections(
	Position node, NetworkState<Position> state)
	{
		HashMap<Position, StorageSystem<S>> available = new HashMap<Position, StorageSystem<S>>();
		for (Position pos : state.network.keySet())
		{
			if (!state.network.get(pos).control.isConnected())
			{
				if (inRange(pos, node))
				{
					available.put(pos, (StorageSystem<S>) state.network.get(pos));
				}
			}
		}
		return available;
	}

}
