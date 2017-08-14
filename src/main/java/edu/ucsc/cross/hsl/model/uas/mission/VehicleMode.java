package edu.ucsc.cross.hsl.model.uas.mission;

public interface VehicleMode
{

	public VehicleMode[] allModes();

	public String modeName();

	public Boolean movementAllowed();
}
