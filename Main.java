package edu.upenn.cit594;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.parser.ParseException;

import edu.upenn.cit594.datamanagement.CovidCSVReader;
import edu.upenn.cit594.datamanagement.CovidJSONReader;
import edu.upenn.cit594.datamanagement.PopulationReader;
import edu.upenn.cit594.datamanagement.PropertyReader;
import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.ui.CommandLineUserInterface;
import edu.upenn.cit594.util.Property;
import edu.upenn.cit594.util.Vaccination;

public class Main {

	public static void main(String[] args) {
		String regex = "^--(?<name>.+?)=(?<value>.+)$";

		Pattern p = Pattern.compile(regex);
		Matcher m;
		
		if (args.length == 0 || args.length > 4 ) {return;}
		
		String cDataFile = null;
		String propDataFile = null;
		String popDataFile = null;
		String logDataFile = null;
		
		for (String a: args) {
			m = p.matcher(a);
			if (m.find()) {
				String type = m.group("name");
				if (type.equals("covid")) {
					if (cDataFile == null) {
						cDataFile = m.group("value");
					} else {
						System.out.println("Error: parameter used more than once");
						return;
					}
				} else if (type.equals("properties")) {
					if (propDataFile == null) {
						propDataFile = m.group("value");
					} else {
						System.out.println("Error: parameter used more than once");
						return;
					}
				} else if (type.equals("population")) {
					if (popDataFile == null) {
						popDataFile = m.group("value");
					} else {
						System.out.println("Error: parameter used more than once");
						return;
					}
				} else if (type.equals("log")) {
					if (logDataFile == null) {
						logDataFile = m.group("value");
					} else {
						System.out.println("Error: parameter used more than once");
						return;
					}
				}
			} else {System.out.println("Error: Invalid argument type"); return;}
		}
		
		Reader covidReader;
		PropertyReader propertyReader;
		PopulationReader popReader;
		
		if (cDataFile != null) {
			String cDLC = cDataFile.toLowerCase();
			if (cDLC.endsWith("json")) {
				covidReader = new CovidJSONReader(cDataFile);
			} else if (cDLC.endsWith(".csv")){
				covidReader = new CovidCSVReader(cDataFile);
			} else {
				System.out.println("Error:Wrong file extension");
				return;
			}
		} else {
			covidReader = new CovidJSONReader(null);
		}
		
		if (propDataFile != null) {
			String propDLC = propDataFile.toLowerCase();
			if (propDLC.endsWith("csv")) {
				propertyReader = new PropertyReader(propDataFile);
			} else {
				System.out.println("Error:Wrong file extension");
				return;
			}
		} else {
			propertyReader = new PropertyReader(null);
		}
		
		if (popDataFile != null) {
			String popDLC = popDataFile.toLowerCase();
			if (popDLC.endsWith("csv")) {
				popReader = new PopulationReader(popDataFile);
			} else {
				System.out.println("Error:Wrong file extension");
				return;
			}
		} else {
			popReader = new PopulationReader(null);
		}
		
		if (logDataFile == null) {System.out.println("Error: no log file specified"); return;}
		Logger l = Logger.getInstance();
		try {
			l.setOutputDestination(logDataFile);
		
			Processor processor = new Processor(propertyReader, popReader, covidReader);
			
			CommandLineUserInterface ui = new CommandLineUserInterface(processor);
			
			ui.start();
		}
		catch (FileNotFoundException e) {
			System.out.println("Error: File not found Exception");
			return;
		} catch (ParseException e) {
			System.out.println("Error: ParseException");
			return;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: IOException");
			return;
		}
	}

}
