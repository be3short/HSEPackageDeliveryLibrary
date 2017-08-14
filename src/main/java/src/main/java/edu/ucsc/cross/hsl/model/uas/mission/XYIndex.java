package edu.ucsc.cross.hsl.model.uas.mission;

import java.util.HashMap;

public class XYIndex
{

	public static HashMap<Integer, HashMap<Integer, XYIndex>> indicies = new HashMap<Integer, HashMap<Integer, XYIndex>>();
	public Integer xIndex;
	public Integer yIndex;

	public static XYIndex getXYIndex(Integer x, Integer y)
	{
		XYIndex ind = new XYIndex(x, y);
		if (indicies.containsKey(x))
		{
			if (indicies.get(x).containsKey(y))
			{
				ind = indicies.get(x).get(y);
			} else
			{
				indicies.get(x).put(y, ind);
			}
		} else
		{
			HashMap<Integer, XYIndex> yMap = new HashMap<Integer, XYIndex>();
			yMap.put(y, ind);
			indicies.put(x, yMap);
		}
		return ind;
	}

	private XYIndex(Integer x, Integer y)

	{
		xIndex = x;
		yIndex = y;
	}

	public <T> T getElement(T[][] grid)
	{
		T element = null;
		try
		{
			element = grid[xIndex][yIndex];
		} catch (Exception badIndex)
		{

		}
		return element;
	}

}
