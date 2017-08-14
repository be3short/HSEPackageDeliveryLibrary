package edu.ucsc.cross.hsl.model.uas.mission;

import edu.ucsc.cross.hse.core.framework.component.Component;
import edu.ucsc.cross.hse.core.framework.data.Data;
import edu.ucsc.cross.hse.core.framework.models.HybridSystem;
import java.util.ArrayList;

import bs.commons.objects.access.Protected;

/*
 * A controller that navigates to the next closest objective
 */
public class ProximityNavigationController extends Component implements NavigationalController, HybridSystem
{

	public Data<Geolocation> agentLocation;
	public Data<Coordinate> startingPosition;
	public Data<Coordinate> nextPosition;
	public ArrayList<Coordinate> coordinatesToExplore;
	public PathComputer pathComputer;

	public ProximityNavigationController(Geolocation agent_location)
	{

		agentLocation = new Data<Geolocation>("Agent Location", agent_location);
		startingPosition = new Data<Coordinate>("Starting Coordinate", new Coordinate(0.0, 0.0, 0.0));
		nextPosition = new Data<Coordinate>("Destination Coordinate", new Coordinate(0.0, 0.0, 0.0));
	}

	@Override
	public void initialize()
	{

	}

	@Override
	public Coordinate nextWaypoint()
	{
		return nextPosition.getValue();
	}

	@Override
	public void flowMap()
	{
		if (waypointReached())
		{
			pathComputer.a
		}
	}

	@Override
	public boolean flowSet()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void jumpMap()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean jumpSet()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean waypointReached()
	{
		return nextPosition.getValue().coordinateReached(Geolocation.getCoordinate(agentLocation.getValue()), .2);
	}

}
