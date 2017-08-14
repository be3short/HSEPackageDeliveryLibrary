package edu.ucsc.cross.hsl.model.uas.mission;

import java.util.ArrayList;

import bs.commons.objects.access.FieldFinder;
import bs.commons.objects.access.Protected;

public interface SubSystem
{

	public static SubSystem[] getAllSubSystems(Object obj)
	{
		ArrayList<Object> s = FieldFinder.getObjectFieldValues(obj, true, SubSystem.class);
		return s.toArray(new SubSystem[s.size()]);
	}

	public SystemContents system();

	public void setup(Protected<SystemContents> contents);

	public void prepare();
}
