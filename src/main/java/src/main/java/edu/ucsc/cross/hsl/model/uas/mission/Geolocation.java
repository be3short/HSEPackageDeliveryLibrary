package edu.ucsc.cross.hsl.model.uas.mission;

public interface Geolocation
{

	public Double getXPosition();

	public Double getYPosition();

	public Double getZPosition();

	public static Coordinate getCoordinate(Geolocation location)
	{
		return new Coordinate(location.getXPosition(), location.getYPosition(), location.getZPosition());
	}
}
