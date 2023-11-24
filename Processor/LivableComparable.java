package edu.upenn.cit594.processor;

import java.util.Map;

import edu.upenn.cit594.util.Property;

public class LivableComparable implements PropertyComparator {

	@Override
	public long averageValue(long zipCode, Map<Long, Property> listOfProperty) {
		long val;
		double avg = 0.0;
		
		if (listOfProperty.containsKey(zipCode)) {
			avg = listOfProperty.get(zipCode).getTotalLivableArea() / (double) listOfProperty.get(zipCode).getLaCount();
		}
		
		val = (long) avg;
		return val;
	}

}
