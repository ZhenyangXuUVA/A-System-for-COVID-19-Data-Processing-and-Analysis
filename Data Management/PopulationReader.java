package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;

public class PopulationReader {
	
	private String filename;
	public PopulationReader(String name) {
		this.filename = name;
	}
	
	public Map<Long, Long> reader() throws ParseException, IOException {
		Map<Long, Long> zipMap = new HashMap<>();
		
		if (this.filename == null) {return zipMap;}
		
		try (BufferedReader br = new BufferedReader(new FileReader(new File(this.filename)))) {
			String[] line;
			
			line = CSVLexer.readRow(br);
			
			if (line == null || line.length < 2) {throw new IOException();}
			
			int count = 0;
			
			int zipFieldNo = -1;
			int popFieldNo = -1;
			
			for (String s: line) {
				if (s.equals("zip_code")) {
					zipFieldNo = count;
				}
				
				if (s.equals("population")) {
					popFieldNo = count;
					
				}
				
				count++;
			}
			
			if (zipFieldNo == - 1 || popFieldNo == -1) {throw new IOException();}
			
			
			while ( (line = CSVLexer.readRow(br)) != null){
				if (line.length < 2) {continue;}
				try {
				Long zipCode = Long.parseLong(line[zipFieldNo]);
				if (zipCode > 99999 || zipCode < 10000) {continue;}
				
				Long populationNo = Long.parseLong(line[popFieldNo]);
				
				zipMap.put(zipCode, populationNo);
				}
				catch (NumberFormatException e) {
					continue;
				}
				
			}

			
		}
		
		
		
		return zipMap;
	}

}
