package edu.upenn.cit594.datamanagement;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.upenn.cit594.util.Vaccination;

public class CovidJSONReader extends Reader{
	
	public CovidJSONReader(String name) {
		this.filename = name;
	}
	
	@Override
	public Map<String, Map<Long, Vaccination>> reader() throws ParseException, IOException {
		Map<String, Map<Long, Vaccination>> listOfCovid = new HashMap<>();
		
		String zipString;
		long zipCode;
		//SimpleDateFormat timestampFormat =  new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");
		//SimpleDateFormat simpleFormat = new SimpleDateFormat("YYYY-MM-DD");
		//Date timestamp_full;
		//String timestamp_part;
		
		LocalDateTime timestamp_full; 
		String timestamp_part;
		
		long partVacc;
		long fullVacc;
		
		
		FileReader fr = new FileReader(this.filename);
		
		Object obj = new JSONParser().parse(fr);
		
		JSONArray jArray = (JSONArray) obj;
		
		if (jArray.isEmpty()) {return listOfCovid;}
		
		Iterator<?> jit = jArray.iterator();
		
		while (jit.hasNext()) {
			JSONObject jObj = (JSONObject) jit.next();
			if (!jObj.containsKey("zip_code") || !jObj.containsKey("etl_timestamp")) {continue;}
			
			zipString = (String) jObj.get("zip_code");
			if (zipString.length() < 5) {continue;}
			try {
				zipCode = Long.parseLong(zipString.substring(0,6));
			} catch(NumberFormatException e) {
				continue;
			}
			try {
				timestamp_full = LocalDateTime.parse((String)jObj.get("etl_timestamp"));
				timestamp_part = timestamp_full.toLocalDate().toString();
			} catch (java.time.format.DateTimeParseException e) {
				continue;
			}
			if (jObj.containsKey("partially_vaccinated")) {
				try {
					partVacc = Long.parseLong((String)jObj.get("partially_vaccinated"));
				} catch (NumberFormatException e) {
					partVacc = 0;
				}
			} else {partVacc = 0;}
			
			if (jObj.containsKey("fully_vaccinated")) {
				try {
					fullVacc = Long.parseLong((String) jObj.get("fully_vaccinated"));
				} catch(NumberFormatException e) {
					fullVacc = 0;
				} 
			} else {fullVacc =0;}
			
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
		
		
		return listOfCovid;
	}

}
