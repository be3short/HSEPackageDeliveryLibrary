package edu.ucsc.cross.hsl.model.uas.mission;

import com.be3short.jfx.connectors.Linked;

import edu.ucsc.cross.hse.core.framework.component.Component;
import edu.ucsc.cross.hse.core.framework.data.Data;
import edu.ucsc.cross.hse.core.framework.models.HybridSystem;

public class MissionController extends Component implements HybridSystem
{

	public Linked<Vehicle> vehicle;
	public Linked<NavigationalController> navigator;
	public Linked<SimpleUAVFlightController> flightController;
	public Data<MissionStage> missionStage;

	public MissionController()
	{
		missionStage = new Data<MissionStage>("Mission Stage", MissionStage.PREPARATION);
	}

	public static enum MissionStage
	{
		PREPARATION,
		TAKEOFF,
		DATA_COLLECTION,
		DATA_ANALYSIS,
		APPROACH_DESTINATION,
		LANDING;
	}

	@Override
	public void flowMap()
	{
		// TODO Auto-generated method stub

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

}
