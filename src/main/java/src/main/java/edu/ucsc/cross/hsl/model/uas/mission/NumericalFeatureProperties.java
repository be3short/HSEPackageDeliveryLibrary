package edu.ucsc.cross.hsl.model.uas.mission;

import edu.ucsc.cross.hse.core.framework.data.Data;
import edu.ucsc.cross.hsl.model.environment.featurea.FeatureSpecification;
import edu.ucsc.cross.hsl.model.environment.featurea.GeographicFeature;

public class NumericalFeatureProperties implements FeatureSpecification
{

	private Data<Double> minimumValue;
	private Data<Double> maximumValue;
	private GeographicFeature feature;

	public NumericalFeatureProperties(GeographicFeature feature, Double minimum_value, Double maximum_value)
	{
		minimumValue = new Data<Double>("Minimum Feature Value", minimum_value);
		maximumValue = new Data<Double>("Maximum Grid Section Value", maximum_value);
		this.feature = feature;
	}

	@Override
	public String name()
	{
		// TODO Auto-generated method stub
		return feature.toString();
	}

	@Override
	public Double getValue(Coordinate location)
	{

		Double value = Math.random() * (maximumValue.getValue() - minimumValue.getValue()) + minimumValue.getValue();
		return value;
	}

	@Override
	public GeographicFeature featureType()
	{
		// TODO Auto-generated method stub
		return feature;
	}

	@Override
	public Class<Double> featureClass()
	{
		// TODO Auto-generated method stub
		return Double.class;
	}
}
