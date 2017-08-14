package edu.ucsc.cross.hsl.model.task.packagedelivery;

import edu.ucsc.cross.hse.core.framework.component.Component;
import edu.ucsc.cross.hse.core.framework.data.Data;

public class PackageDeliveryParameters extends Component
{

	public Data<Integer> numberOfPackagesToDeliver;
	public Data<Double> xDeliveryRange;
	public Data<Double> yDeliveryRange;

	public PackageDeliveryParameters()
	{
		numberOfPackagesToDeliver = new Data<Integer>("Num Packages", 0);
		xDeliveryRange = new Data<Double>("X Range", 0.0);
		yDeliveryRange = new Data<Double>("Y Range", 0.0);
	}

}
