package edu.upenn.cit594.processor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.json.simple.parser.ParseException;

import edu.upenn.cit594.datamanagement.PopulationReader;
import edu.upenn.cit594.datamanagement.PropertyReader;
import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.util.Property;
import edu.upenn.cit594.util.Vaccination;

public class Processor {
	
	protected PropertyReader pReader;
	
	protected PopulationReader popReader;
	
	protected Reader cReader;
	
	protected Map<Long, Long> listOfPopulation;
	
	protected Map<Long, Property> listOfProperty;
	
	protected Map<String, Map<Long, Vaccination>> listOfVac;
	
	public Processor(PropertyReader pReader, PopulationReader popReader, Reader cReader ) throws ParseException, IOException {
		this.pReader = pReader;
		this.popReader = popReader;
		this.cReader = cReader;
		
		this.listOfProperty = this.pReader.reader();
		this.listOfPopulation = this.popReader.reader();
		this.listOfVac = this.cReader.reader();
	}
	
	
	public int totalPopulationCount() {
		
		int total = 0;
		
		for (long l: this.listOfPopulation.values()) {
			total += l;
		}
		
		return total;
		
	}
	
	public TreeMap<Long, Double> vacPerCapital(String type, String date){
		
		TreeMap<Long, Double> tally = new TreeMap<>();
		
		//boolean flag;
		
		int flag;
		
		if (type.equals("full")) {
			//flag = true;
			flag = 1;
		} else {
			//flag = false;
			flag = 0;
		}
		Map<Long, Vaccination> listOfZip;
		
		if (this.listOfVac.containsKey(date)) {
			listOfZip = this.listOfVac.get(date);
		} else { listOfZip = new HashMap<>();}
		double avg = 0;
		
		for (long z: listOfZip.keySet()) {
			if (listOfZip.get(z).getVaccStatus()[flag] == 0) {continue;}
			if (!this.listOfPopulation.containsKey(z)) {continue;}
			 avg = (double) listOfZip.get(z).getVaccStatus()[flag]/(double)this.listOfPopulation.get(z);
			 tally.put(z, avg);
			
		}
		
		
		/*
		if (flag) {
			for (long z: listOfZip.keySet()) {
				if (listOfZip.get(z).getFullyVacc() == 0) {continue;}
				if (!this.listOfPopulation.containsKey(z)) {continue;}
				 avg = (double) listOfZip.get(z).getFullyVacc()/(double)this.listOfPopulation.get(z);
				 tally.put(z, avg);
			}
		} else {
			for (long z : listOfZip.keySet()) {
				if (listOfZip.get(z).getPartialVacc() == 0) {continue;}
				if (!this.listOfPopulation.containsKey(z)) {continue;}
				avg = (double) listOfZip.get(z).getPartialVacc()/(double)this.listOfPopulation.get(z);
				tally.put(z, avg);
			} 
		}*/
		
		return tally;
	}
	
	private long getAvgValue(long zipCode, PropertyComparator comp) {
		long val = comp.averageValue(zipCode, this.listOfProperty);
		return val;
	}
	
	public long getAvgMV(long zipCode) {
		return getAvgValue(zipCode, new MarketComparable());
	}
	
	public long getAvgLV(long zipCode) {
		return getAvgValue(zipCode, new LivableComparable());
	}
	
	public long mvPerCapita(long zipCode) {
		long val = 0;
	
		if (!this.listOfProperty.containsKey(zipCode) || !this.listOfPopulation.containsKey(zipCode)) {return val;}
		
		double totalMV = this.listOfProperty.get(zipCode).getMarketValue();
		
		val = (long) (totalMV/this.listOfPopulation.get(zipCode));
		
		return val;
		
	}
	
	public boolean checkPopData() {
		if (this.listOfPopulation.isEmpty()) {
			return false;
		}
		return true; 
	}
	
	public boolean checkCovData() {
		if (this.listOfVac.isEmpty()) {
			return false;
		}
		return true;
	}
	
	public boolean checkPropData() {
		if (this.listOfProperty.isEmpty()) {
			return false;
		}
		return true;
	}
}
