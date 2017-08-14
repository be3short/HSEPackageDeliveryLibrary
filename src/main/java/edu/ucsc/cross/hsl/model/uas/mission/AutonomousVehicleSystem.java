package edu.ucsc.cross.hsl.model.uas.mission;

public class AutonomousVehicleSystem
{

	VehicleOperationSystem vehicleOperation = new VehicleOperationSystem();
	NavigationSystem navigation = new NavigationSystem();
	MissionControlSystem misionControl = new MissionControlSystem();;
	SystemContents contents;

	public static enum DataCollection
	{
		DATA_COLLECTION(
			DataCollectionSystem.class),
		MISSION_CONTROL(
			MissionControlSystem.class);

		HashMap<String, String> links;
		Class<?> itemClass;
		static HashMap<String, Object> components = new HashMap<String, Object>();

		private <T> DataCollection(T item_class)
		{
			links = new HashMap<String, String>();
			itemClass = item_class;
		}

		public void linkInstance(String data, String access)
		{
			links.put(access, data);
		}

		public <T, itemClass> T getItem()
		{

			return itemClass.cast(new MissionControlSystem());
		}

	}

	public AutonomousVehicleSystem()
	{
		initializeSystemContents();
	}

	private void initializeSystemContents()
	{
		contents = new SystemContents(SystemElement.UAV, environment, dataCollection, vehicleOperation, navigation,
		misionControl);
		System.out.println(contents.getReport());
	}

	private void initializeSystems()
	{

	}

	public static void main(String args[])
	{
		AutonomousVehicleSystem sys = new AutonomousVehicleSystem();
	}
}
