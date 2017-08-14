package edu.ucsc.cross.hsl.model.uas.mission;

import edu.ucsc.cross.hse.core.framework.component.Component;
import edu.ucsc.cross.hse.core.framework.data.Data;
import edu.ucsc.cross.hse.core.framework.models.HybridSystem;
import edu.ucsc.cross.hse.model.environment.structure.TerrestrialEnvironment;

public class GridDataCollector extends Component implements HybridSystem
{

	public Data<TerrestrialEnvironment> gridData;
	public Data<Geolocation> currentLocation;

	public GridDataCollector(Geolocation location, TerrestrialEnvironment real_environment)
	{
		gridData = new Data<TerrestrialEnvironment>("Data Collection Environment",
		new TerrestrialEnvironment(real_environment.getGeographySettings()));
		currentLocation = new Data<Geolocation>("Current Location", location);
	}

	@Override
	public void flowMap()
	{
		if (inUndetectedArea())
		{
			Double value = environmentArea().g.getValue(currentLocation.getValue());
			gridData.getValue().storeValue(currentLocation.getValue(), value);
		}

	}

	@Override
	public boolean flowSet()
	{
		// TODO Auto-generated method stub
		return true;
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

	public boolean inUndetectedArea()
	{
		boolean undetected = true;
		if (this.gridData.getValue().getValue(currentLocation.getValue()) != null)
		{
			undetected = false;
		}
		return undetected;
	}

	public TerrestrialEnvironment environmentArea()
	{
		return this.component().getEnvironment().component().getContent()
		.getObjects(TerrestrialEnvironment.class, false).get(0);
	}
}
