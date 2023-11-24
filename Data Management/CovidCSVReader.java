package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;




import edu.upenn.cit594.util.Vaccination;

public class CovidCSVReader extends Reader{
	
	public CovidCSVReader(String name) {
		this.filename = name;
	}

	@Override
	public Map<String, Map<Long,Vaccination>> reader() throws IOException {
		Map<String, Map<Long, Vaccination>> listOfCovid = new HashMap<>();
		
		if (this.filename == null) {return listOfCovid;}
		
		try (BufferedReader br = new BufferedReader(new FileReader(new File(this.filename)))) {
			String[] line;
			
			line = this.readRow(br);
			
			if (line == null || line.length < 4) {throw new IOException();}
			
			int count = 0;
			
			int zipCodeField = -1;
			int timeField = -1;
			int partField = -1;
			int fullField = -1;
			
			for (String s : line) {
				if (s.equals("zip_code")) {
					zipCodeField = count;
				} else if (s.equals("etl_timestamp")) {
					timeField = count;
				} else if (s.equals("partially_vaccinated")) {
					partField = count;
				} else if (s.equals("fully_vaccinated")) {
					fullField = count;
				}
				count++;
			}
			
			long zipCode;
			//SimpleDateFormat timestampFormat =  new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");
			//SimpleDateFormat simpleFormat = new SimpleDateFormat("YYYY-MM-DD");
			//Date timestamp_full;
			//String timestamp_part;
			
			LocalDateTime timestamp_full; 
			String timestamp_part;
			
			long partVacc;
			long fullVacc;
			//String pattern = "YYYY-MM-DD hh:mm:ss";
			
			if (zipCodeField == - 1 || timeField == -1 || partField == -1 || fullField == -1) {throw new IOException();}
			
			while ( (line = this.readRow(br)) != null){
				if (line.length != count) {continue;}
				try {
					if (line[zipCodeField].length() < 5) {continue;}
					else { 
						zipCode = Long.parseLong(line[zipCodeField].substring(0,5));
						}
				} catch (NumberFormatException e){
					continue;
				}
				try {
					if (line[timeField].length() != 19) {continue;}
					else {
						
						//System.out.println(line[timeField]);
						timestamp_full = LocalDateTime.parse(line[timeField], DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss"));
						//System.out.println(timestamp_full);
						timestamp_part = timestamp_full.toLocalDate().toString();
						//System.out.println(timestamp_part);
						
					} 
				} catch (DateTimeParseException e) {
					e.printStackTrace();
					continue;
				}
				try {
					partVacc = Long.parseLong(line[partField]); 
				} catch (NumberFormatException e) {
					partVacc = 0;
				}
				try {
					fullVacc= Long.parseLong(line[fullField]);
				} catch (NumberFormatException e) {
					fullVacc = 0;
				}
				
				if (listOfCovid.containsKey(timestamp_part)) {
					Map<Long, Vaccination>  listOfZips = listOfCovid.get(timestamp_part);
					if (listOfZips.containsKey(zipCode)) {
						listOfZips.get(zipCode).updateVacRecords(partVacc, fullVacc);
					} else {
						Vaccination card = new Vaccination(zipCode, timestamp_part, partVacc,fullVacc);
						listOfZips.put(zipCode, card);
					}
				} else {
					Vaccination newCard = new Vaccination(zipCode, timestamp_part, partVacc,fullVacc);
					HashMap<Long, Vaccination>  newZipMap = new HashMap<>();
					newZipMap.put(zipCode, newCard);
					listOfCovid.put(timestamp_part, newZipMap);
				}
				
			}
		}
		return listOfCovid;
	}

}
