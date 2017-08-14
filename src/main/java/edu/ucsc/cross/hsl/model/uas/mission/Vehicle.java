package edu.ucsc.cross.hsl.model.uas.mission;

public interface Vehicle extends Geolocation
{

	public VehicleMode currentMode();

	public void changeMode(VehicleMode new_mode);

	public VehicleProperties vehicleProperties();
}
