package edu.upenn.cit594.util;

public class Population {
	
	/**
	 * To store the zip-code
	 */
	private final Long zip;
	
	/**
	 * To store the population
	 */
	private final Long pop;
	
	
	/**
	 * Constructor for Population object when needed.
	 * @param zip
	 * @param pop
	 */
	public Population(Long zip, Long pop) {
		this.zip = zip;
		this.pop = pop;
	}


	/**
	 * @return the zip
	 */
	public Long getZip() {
		return zip;
	}


	/**
	 * @return the pop
	 */
	public Long getPop() {
		return pop;
	}
	
	
	

}
