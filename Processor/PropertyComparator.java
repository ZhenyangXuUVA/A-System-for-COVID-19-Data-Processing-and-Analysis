package edu.upenn.cit594.processor;

import java.util.Map;

import edu.upenn.cit594.util.Property;

public interface PropertyComparator {
	
	public long averageValue(long zipCode, Map<Long, Property>listOfProperty);

}
