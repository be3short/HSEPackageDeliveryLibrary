package edu.ucsc.cross.hsl.model.uas.mission;

import edu.ucsc.cross.hse.core.framework.data.Data;

public class UAVProperties implements VehicleProperties
{

	public Data<Double> maximumPlanarSpeed;
	public Data<Double> maximumVerticalSpeed;

	public UAVProperties()
	{
		maximumPlanarSpeed = new Data<Double>("Maximum Planar Speed", 0.0);
		maximumVerticalSpeed = new Data<Double>("Maximum Vertical Speed", 0.0);
	}

	@Override
	public Double maximumPlanarSpeed()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double maximumVerticalSpeed()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public enum UAVMode implements VehicleMode
	{
		STANDBY(
			"Standby",
			false),
		IDLE(
			"Idle",
			false),
		PREPARING(
			"Preparing",
			false),
		TAKEOFF(
			"Takeoff",
			true),
		HOVERING(
			"Hovering",
			false),
		TRAVELLING(
			"Travelling",
			true),
		LANDING(
			"Landing",
			true);

		private String modeName;
		private Boolean movement;

		private UAVMode(String name, Boolean movement)
		{
			this.movement = movement;
			modeName = name;
		}

		@Override
		public VehicleMode[] allModes()
		{
			// TODO Auto-generated method stub
			return UAVMode.values();
		}

		@Override
		public String modeName()
		{
			// TODO Auto-generated method stub
			return modeName;
		}

		@Override
		public Boolean movementAllowed()
		{
			// TODO Auto-generated method stub
			return movement;
		}

	}
}
