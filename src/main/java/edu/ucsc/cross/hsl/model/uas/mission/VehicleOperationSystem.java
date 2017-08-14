package edu.ucsc.cross.hsl.model.uas.mission;

import com.be3short.jfx.connectors.Linked;

import bs.commons.objects.access.Protected;

public class VehicleOperationSystem implements SubSystem
{

	Protected<SystemContents> contents = null;
	public SimpleUAV uav = null;
	public ProximityNavigationController navigationController = null;
	public SimpleUAVFlightController flightController = null;
	public PathComputer navigationComputer = null;

	public VehicleOperationSystem()
	{
		uav = new SimpleUAV();
		flightController = new SimpleUAVFlightController();
	}

	private void initializeComponents()
	{
		uav = new SimpleUAV();
		flightController = new SimpleUAVFlightController();
	}

	@Override
	public void setup(Protected<SystemContents> contents)
	{
		this.contents = contents;
		this.uav = new SimpleUAV(null);
		this.navigationController = new ProximityNavigationController(uav);
		this.flightController = new SimpleUAVFlightController(contents);
		navigationComputer = new PathComputer(contents);
	}

	@Override
	public SystemContents system()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void prepare()
	{
		// TODO Auto-generated method stub

	}
}
