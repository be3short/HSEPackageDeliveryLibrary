package edu.ucsc.cross.hsl.model.uas.mission;

import com.be3short.jfx.connectors.Linked;

import bs.commons.objects.access.Protected;

public class SimpleUAVFlightController implements ManuverController
{

	public Protected<NavigationalController> navigation;
	public Protected<Geolocation> location;
	public Protected<VehicleProperties> vehicleProperties;

	public SimpleUAVFlightController()
	{
		navigation = new Protected<NavigationalController>(null, true);
		location = new Protected<Geolocation>(null, true);
		vehicleProperties = new Protected<VehicleProperties>(null, true);
	}

	public void loadNavigationController(NavigationalController navigation)
	{
		this.navigation.set(navigation);
	}

	public void loadVehicleLocation(Geolocation location)
	{
		this.location.set(location);
	}

	public void loadVehicleProperties(VehicleProperties properties)
	{
		this.vehicleProperties.set(properties);
	}

	@Override
	public Double getOrientation()
	{
		Coordinate target = navigation.get().nextWaypoint();
		Double orientation = computeTargetAngle(target);
		return orientation;
	}

	@Override
	public Double getPlanarVelocity()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getVerticalVelocity()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Double computeTargetAngle(Coordinate target)
	{
		Double angle = Math.atan2(target.yPosition - location.get().getYPosition(),
		target.xPosition - location.get().getXPosition());

		return angle;
	}
}
