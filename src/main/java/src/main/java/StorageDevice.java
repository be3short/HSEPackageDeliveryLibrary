import java.util.ArrayList;

import edu.ucsc.cross.hse.core.framework.component.Component;
import edu.ucsc.cross.hse.core.framework.data.Data;
import edu.ucsc.cross.hse.core.framework.data.State;
import edu.ucsc.cross.hse.core.framework.models.HybridSystem;
import edu.ucsc.cross.hsl.model.uas.mission.StorageDeviceProperties;

public class StorageDevice extends Component implements HybridSystem
{

	public StorageDeviceProperties properties;
	public State currentCapacity;
	public State dataOverflow;
	public StorageDeviceController controller;

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
