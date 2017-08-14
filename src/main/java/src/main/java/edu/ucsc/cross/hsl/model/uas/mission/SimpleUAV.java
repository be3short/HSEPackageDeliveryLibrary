package edu.ucsc.cross.hsl.model.uas.mission;

import edu.ucsc.cross.hse.core.framework.component.Component;
import edu.ucsc.cross.hse.core.framework.data.Data;
import edu.ucsc.cross.hse.core.framework.data.State;
import edu.ucsc.cross.hse.core.framework.models.HybridSystem;
import edu.ucsc.cross.hsl.model.uas.mission.UAVProperties.UAVMode;

public class SimpleUAV extends Component implements HybridSystem, Vehicle
{

	public ManuverController controlInput;
	public State xPosition;
	public State yPosition;
	public State zPosition;
	public Data<Double> dataCollectionRate;
	public StorageDevice storageDevice;

	public UAVProperties uavProperties;
	public Data<VehicleMode> mode;

	public SimpleUAV(ManuverController controller)
	{
		loadController(controller);
		initializeStates();
	}

	public SimpleUAV()
	{

		initializeStates();
	}

	private void initializeStates()
	{
		orientation = new Data<Double>("Orientation (rad)", 0.0);
		verticalVelocity = new Data<Double>("Vertical Velocity (m/s)", 0.0);
		planarVelocity = new Data<Double>("Planar Velocity (m/s)", 0.0);
		xPosition = new State("X Position");
		yPosition = new State("Y Position");
		zPosition = new State("Z Position");
		mode = new Data<VehicleMode>("UAV Mode", UAVMode.STANDBY);
	}

	public void loadController(ManuverController controller)
	{
		controlInput = controller;
	}

	@Override
	public void flowMap()
	{
		orientation.setValue(controlInput.getOrientation());
		planarVelocity.setValue(controlInput.getPlanarVelocity());
		xPosition.setDerivative(planarVelocity.getValue() * Math.cos(orientation.getValue()));
		yPosition.setDerivative(planarVelocity.getValue() * Math.sin(orientation.getValue()));
		zPosition.setDerivative(controlInput.getVerticalVelocity());
	}

	@Override
	public boolean flowSet()
	{
		// TODO Auto-generated method stub
		return mode.getValue().movementAllowed();
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

	@Override
	public Double getXPosition()
	{
		// TODO Auto-generated method stub
		return xPosition.getValue();
	}

	@Override
	public Double getYPosition()
	{
		// TODO Auto-generated method stub
		return yPosition.getValue();
	}

	@Override
	public Double getZPosition()
	{
		// TODO Auto-generated method stub
		return zPosition.getValue();
	}

	@Override
	public VehicleMode currentMode()
	{
		// TODO Auto-generated method stub
		return mode.getValue();
	}

	@Override
	public void changeMode(VehicleMode new_mode)
	{
		mode.setValue(new_mode);

	}

	@Override
	public VehicleProperties vehicleProperties()
	{
		// TODO Auto-generated method stub
		return uavProperties;
	}

}
