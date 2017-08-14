package edu.ucsc.cross.hsl.model.uas.mission;

import java.util.HashMap;

import bs.commons.objects.access.FieldFinder;
import edu.ucsc.cross.hse.model.environment.structure.EnvironmentContent;
import edu.ucsc.cross.hse.model.environment.structure.TerrestrialEnvironment;
import edu.ucsc.cross.hse.model.environment.structure.TerrestrialEnvironmentObserver;
import edu.ucsc.cross.hse.model.storage.data.environment.EnvironmentDataStorage;
import edu.ucsc.cross.hse.model.storage.data.environment.TerrestrialDataCollector;

public enum SystemElement implements ContentIdentifier
{
	UAV(
		Vehicle.class),
	FLIGHT_CONTROLLER(
		ManuverController.class),
	NAVIGATION_CONTROLLER(
		NavigationalController.class),
	PATH_COMPUTER(
		getClass(PathComputer.class)),
	DATA_STORAGE(
		EnvironmentDataStorage.class),
	DATA_COLLECTOR(
		TerrestrialDataCollector.class),
	MISSION_CONTROLLER(
		getClass(MissionController.class)),
	TERRESTRIAL_ENVIRONMENT(
		TerrestrialEnvironment.class),
	TERRESTRIAL_ENVIRONMENT_OBSERVER(
		TerrestrialEnvironmentObserver.class),
	ENVIRONMENT_WITH_FEATURES(
		EnvironmentContent.class);

	private Class<?> classType;

	private SystemElement(Class<?> class_type)
	{
		classType = class_type;
	}

	private static HashMap<Class<?>, SystemElement> elementMap = initializeElementMap();

	private static HashMap<Class<?>, SystemElement> initializeElementMap()
	{
		HashMap<Class<?>, SystemElement> elementMap = new HashMap<Class<?>, SystemElement>();
		for (SystemElement element : SystemElement.values())
		{
			elementMap.put(element.classType, element);
		}
		return elementMap;
	}

	@Override
	public ContentIdentifier foundObject(Object obj)
	{
		for (SystemElement element : SystemElement.values())
		{
			if (FieldFinder.containsSuper(obj, element.classType))
			{
				return element;
			}
		}
		return null;
	}

	@Override
	public ContentIdentifier[] allContentIds()
	{
		// TODO Auto-generated method stub
		return SystemElement.values();
	}

	@Override
	public Class<?> contentClass()
	{
		// TODO Auto-generated method stub
		return classType;
	}

	@Override
	public String identifierName()
	{
		// TODO Auto-generated method stub
		return this.identifierName();
	}

	public static Class<?> getClass(Class<?> name)
	{
		String n = name.getName();
		Class<?> real = name;
		try
		{
			real = ClassLoader.getSystemClassLoader().loadClass(n);
			// real = (Class<T>)
			// ClassLoader.getSystemClassLoader().loadClass(n);
		} catch (

		Exception e)
		{

		}
		return real;
	}
}
