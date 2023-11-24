package edu.upenn.cit594.processor;

import java.util.Map;

import edu.upenn.cit594.util.Property;

public class MarketComparable implements PropertyComparator {

	@Override
	public long averageValue(long zipCode, Map<Long, Property> listOfProperty) {
		long val = 0;
		
		double avg = 0.0;
		
		if (listOfProperty.containsKey(zipCode)) {
			avg = listOfProperty.get(zipCode).getMarketValue() / (double) listOfProperty.get(zipCode).getMarketCount();
		}
		
		val = (long) avg;
		
		return val;
	}



}
