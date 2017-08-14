package edu.ucsc.cross.hsl.model.uas.mission;

import java.util.HashMap;

import bs.commons.objects.access.FieldFinder;
import bs.commons.objects.access.Protected;

public class SystemContents
{

	public SystemContents(ContentIdentifier identifier, Object... objects_to_scan)
	{
		this.identifier = identifier;
		initialize(objects_to_scan);
	}

	private void initialize(Object... objects_to_scan)
	{
		content = new HashMap<Class, Protected<Object>>();
		for (Object object : objects_to_scan)
		{
			System.out.println(object);
			for (Object obj : FieldFinder.getObjectFieldValues(object, true, false))
			{
				System.out.println(obj);
				ContentIdentifier found = identifier.foundObject(obj);
				if (found != null)
				{
					System.out.println("Found" + content);
					content.put(found.contentClass(), new Protected<Object>(obj, true));
				}
			}
		}
		initializeNullValues();
	}

	private void initializeNullValues()
	{
		for (ContentIdentifier id : identifier.allContentIds())
		{
			content.put(id.contentClass(), new Protected<Object>(null, true));

		}
	}

	ContentIdentifier identifier;

	HashMap<Class, Protected<Object>> content;

	public <T> T getContent(ContentIdentifier id)
	{
		Class<T> classT = (Class<T>) id.contentClass();
		return getContent(id, classT);
	}

	public <T> T getContent(ContentIdentifier id, Class<T> classT)
	{
		try
		{
			Class<T> idClas = (Class<T>) id.contentClass();
			return (T) idClas.cast(content.get(id).get());
		} catch (Exception e)
		{
			return null;
		}
	}

	public String getReport()
	{
		String s = "Loaded Contents:";
		for (ContentIdentifier id : identifier.allContentIds())
		{
			s += "\n : " + id + " : " + id.contentClass() + " : " + content.get(id.contentClass());

		}
		return s;
	}
}
