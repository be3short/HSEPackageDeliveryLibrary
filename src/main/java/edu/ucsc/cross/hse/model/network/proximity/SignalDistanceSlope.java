package edu.ucsc.cross.hse.model.network.proximity;

public enum SignalDistanceSlope
{
	SQRT(),
	LINEAR();

	public Double computeSignal(Double max_signal, Double current_distance, Double max_distance)
	{
		Double slope = (max_distance - current_distance) / max_distance;
		;
		if (this.equals(SQRT))
		{
			slope = Math.sqrt(slope);
		}
		return slope * max_signal;
	}
}
