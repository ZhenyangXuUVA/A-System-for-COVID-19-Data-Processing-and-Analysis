package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;

import edu.upenn.cit594.util.Property;

public class PropertyReader{
	
	private String filename;
	
	public PropertyReader(String name) {
		this.filename = name;
	}
	
	
	public Map<Long, Property> reader() throws ParseException, IOException {
		HashMap<Long, Property> listOfProperty = new HashMap<>();
		
		if (this.filename == null) {return listOfProperty;}
		
		try (BufferedReader br = new BufferedReader(new FileReader(new File(this.filename)))) {
			String[] line;
			
			line = CSVLexer.readRow(br);
			
			if (line == null || line.length < 2) {throw new IOException();}
			
			int count = 0;
			
			int mvFieldNo = -1;
			int totalLivableAreaField = -1;
			int zipCodeField = -1;
			
			for (String s : line) {
				if (s.equals("zip_code")) {
					zipCodeField = count;
				} else if (s.equals("total_livable_area")) {
					totalLivableAreaField = count;
				} else if (s.equals("market_value")) {
					mvFieldNo = count;
				}
				count++;
			}
			
			if (zipCodeField == - 1 || mvFieldNo == -1 || totalLivableAreaField == -1) {throw new IOException();}
			
			long zipCode;
			Double marketValue;
			Double totalLA;
			
			while ( (line = CSVLexer.readRow(br)) != null){
				
				if (line.length < 3 ) {continue;}
				try {
					if (line[zipCodeField].length() < 5) {continue;}
					else { 
						zipCode = Long.parseLong(line[zipCodeField].substring(0,5));
						}
				} catch (NumberFormatException e){
					continue;
				}
				
				try {
					marketValue = Double.parseDouble(line[mvFieldNo]);
				} catch (NumberFormatException e){
					marketValue = null;
				}
				
				try {
					totalLA = Double.parseDouble(line[totalLivableAreaField]);
				} catch (NumberFormatException e) {
					totalLA = null;
				}
				
				
				if (listOfProperty.containsKey(zipCode)) {
					Property toUpdate = listOfProperty.get(zipCode);
					if (marketValue != null) {
						toUpdate.updateMarketValue(marketValue);
					} 
					if (totalLA != null) {
						toUpdate.updateTLA(totalLA);
					}
				} else {
					Property toAdd = new Property(zipCode, marketValue, totalLA);
				
					listOfProperty.put(zipCode, toAdd);
					}
				
				
			}
			
		}
		
		
		return listOfProperty;
	}

}
