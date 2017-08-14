package edu.ucsc.cross.hsl.model.uas.mission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class NavigationComputer
{

	public SquareGridArea<Coordinate> coordinateGrid;
	ArrayList<Coordinate> destinations;
	public ArrayList<XYIndex> reached;
	public HashMap<XYIndex, Coordinate> coordinateIndexMap;

	public NavigationComputer(SquareGridArea area, ArrayList<Coordinate> destinations)
	{
		this.destinations = destinations;
		initializeMappings(area);
	}

	public Coordinate getNearestDestination(Coordinate current_location)
	{
		XYIndex gridSectionIndex = coordinateGrid.getGridIndex(current_location);
		Coordinate nearest = null;// computeClosestNeighbor(gridSectionIndex,
									// current_location);
		Coordinate current = current_location;
		addNewCoordsReached(gridSectionIndex);
		ArrayList<XYIndex> checked = new ArrayList<XYIndex>(reached);
		ArrayList<XYIndex> unchecked = new ArrayList<XYIndex>(Arrays.asList(new XYIndex[]
		{ gridSectionIndex }));
		int i = 0;
		while (nearest == null)
		{
			addIfNew(checked, unchecked);

			unchecked = new ArrayList<XYIndex>(
			GridNeighbor.getNewNeighbors(unchecked, new ArrayList<XYIndex>(coordinateIndexMap.keySet())));
			System.out.println(unchecked.size());
			nearest = computeClosestNeighbor(unchecked, current);
			// unchecked.clear();
			if (reached.size() == (coordinateIndexMap.size()))
			{
				break;
			}
			i++;
			if (i > 50)
			{
				break;
			}
		}
		return nearest;
	}

	public static void addIfNew(ArrayList<XYIndex> list, ArrayList<XYIndex> new_list)
	{
		for (XYIndex ind : new_list)
		{
			if (!list.contains(ind))
			{
				list.add(ind);
			}
		}
	}

	public void addNewCoordsReached(XYIndex ind)
	{

		if (!reached.contains(ind))
		{
			reached.add(ind);
		}

	}

	public void addNewCoordsReached(ArrayList<XYIndex> reach)
	{
		for (XYIndex ind : reach)
		{
			if (!reached.contains(ind))
			{
				reached.add(ind);
			}
		}
	}

	// public Coordinate getNearestNeighborDestination(Coordinate
	// current_location)
	// {
	// XYIndex gridSectionIndex = coordinateGrid.getGridIndex(current_location);
	// Coordinate nearest = computeClosestNeighbor(gridSectionIndex,
	// current_location);
	// while (nearest == null)
	// {
	//
	// }
	// }
	public Coordinate computeClosestNeighbor(XYIndex index, Coordinate current_location)
	{
		Coordinate nearest = null;
		Double minDist = Double.MAX_VALUE;
		for (GridNeighbor n : GridNeighbor.values())
		{
			try
			{
				Coordinate neib = coordinateIndexMap.get(n.index(index));
				if (destinations.contains(neib))
				{
					Double dist = current_location.linearDistance(neib);
					if (dist < minDist)
					{
						nearest = neib;
					}
				}
			} catch (Exception noCorrelation)
			{

			}
		}
		return nearest;
	}

	public Coordinate computeClosestNeighbor(ArrayList<XYIndex> neighbor_list, Coordinate current_location)
	{
		Coordinate nearest = null;

		Double minDist = Double.MAX_VALUE;
		for (XYIndex n : neighbor_list)
		{
			if (!reached.contains(n))
			{
				try
				{

					Coordinate neib = coordinateIndexMap.get(n);

					if (!neib.equals(current_location))
					{
						System.out.println("Checking " + neib.print());
						// if (!reached.contains(n))
						{
							Double dist = current_location.linearDistance(neib);
							if (dist < minDist)
							{
								minDist = dist;
								nearest = neib;
							}
						}
					}
				} catch (Exception noCorrelation)
				{
					noCorrelation.printStackTrace();
				}
			}
		}
		return nearest;
	}

	private void initializeMappings(SquareGridArea exploreArea)
	{
		reached = new ArrayList<XYIndex>();
		coordinateIndexMap = new HashMap<XYIndex, Coordinate>();
		Coordinate[][] coordinatesToExplore = new Coordinate[exploreArea.getResolution()][exploreArea.getResolution()];
		Double gridInterval = (exploreArea.getDimension() / exploreArea.getResolution());
		for (int x = 0; x < exploreArea.getResolution(); x++)
		{
			for (int y = 0; y < exploreArea.getResolution(); y++)
			{
				Coordinate newCoord = new Coordinate((x * gridInterval) + (gridInterval / 2.0),
				(y * gridInterval) + (gridInterval / 2.0), 0.0);
				coordinatesToExplore[x][y] = newCoord;
				coordinateIndexMap.put(XYIndex.getXYIndex(x, y), newCoord);
			}
		}
		coordinateGrid = new SquareGridArea<Coordinate>(coordinatesToExplore, exploreArea.getDimension());
	}

	public static enum GridNeighbor
	{
		TOP(
			1,
			0),
		TOP_RIGHT(
			1,
			1),
		TOP_LEFT(
			-1,
			1),
		LEFT(
			-1,
			0),
		RIGHT(
			1,
			0),
		BOTTOM(
			0,
			-1),
		BOTTOM_LEFT(
			-1,
			-1),
		BOTTOM_RIGHT(
			-1,
			1);

		public final Integer deltaX;
		public final Integer deltaY;

		private GridNeighbor(Integer x_change, Integer y_change)
		{
			deltaX = x_change;
			deltaY = y_change;
		}

		public XYIndex index(XYIndex current)
		{
			XYIndex neighbor = null;
			Integer x = current.xIndex + deltaX;
			Integer y = current.yIndex + deltaY;
			if (x >= 0 && y >= 0)
			{
				neighbor = XYIndex.getXYIndex(x, y);
			}
			return neighbor;
		}

		public static ArrayList<XYIndex> getAllNeighbors(XYIndex current)
		{
			ArrayList<XYIndex> neibs = new ArrayList<XYIndex>();
			for (GridNeighbor neib : GridNeighbor.values())
			{
				XYIndex ind = neib.index(current);
				if (ind != null)
				{
					neibs.add(ind);
				}

			}
			return neibs;
		}

		public static ArrayList<XYIndex> getNewNeighbors(ArrayList<XYIndex> existing, ArrayList<XYIndex> total)
		{
			ArrayList<XYIndex> neibs = new ArrayList<XYIndex>();
			for (XYIndex i : existing)
			{

				for (GridNeighbor neib : GridNeighbor.values())
				{
					XYIndex ind = neib.index(i);
					if (ind != null && !existing.contains(ind) && !neibs.contains(ind) && total.contains(ind))
					{
						neibs.add(ind);
					}

				}
			}
			return neibs;
		}
	}

	public static void main(String args[])
	{
		NavigationComputer comp = new NavigationComputer(SquareGridArea.randomGrid(), new ArrayList<Coordinate>());
		XYIndex currentIndex = comp.coordinateIndexMap.keySet().toArray(
		new XYIndex[comp.coordinateIndexMap.size()])[(int) Math.round(Math.random() * comp.coordinateIndexMap.size())];

		// new XYIndex(
		// (int) Math.floor(Math.abs(Math.round(Math.random() *
		// comp.coordinateGrid.getResolution() - .0001))),
		// (int) Math.floor(Math.abs(Math.round(Math.random() *
		// comp.coordinateGrid.getResolution() - .0001))));
		System.out.println(currentIndex.xIndex + " " + currentIndex.yIndex);
		Coordinate currentCoord = comp.coordinateIndexMap.get(currentIndex);
		System.out.println(currentCoord.print());
		// comp.reached.add(currentIndex);
		int i = 0;
		while (currentCoord != null)
		{
			comp.addNewCoordsReached(comp.coordinateGrid.getGridIndex(currentCoord));
			Coordinate nextCoord = comp.getNearestDestination(currentCoord);
			// addNewCoordsReached(coordinateGrid.getGridIndex(nextCoord));
			if (nextCoord != null)
			{
				System.out.println(currentCoord.print() + " -> " + nextCoord.print());
				i++;
			}
			currentCoord = nextCoord;

		}
		System.out.println(comp.reached.size() + " " + i);
	}
}
