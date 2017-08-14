package edu.ucsc.cross.hsl.model.uas.mission;

public class ExplorationUAVSystem
{

	public SimpleUAV uav;
	public ProximityNavigationController navigationController;
	public SimpleUAVFlightController flightController;
	public GridDataCollector dataCollector;

	public ExplorationUAVSystem(SimpleUAV uav)
	{
		this.uav = new SimpleUAV(null);
		this.navigationController = new ProximityNavigationController(uav);
		this.flightController = new SimpleUAVFlightController(uav, navigationController);
	}
}
