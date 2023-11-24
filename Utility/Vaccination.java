package edu.upenn.cit594.util;



public class Vaccination {

	private final long zipCode;

	private final String timestamp;

	// private long partialVacc;

	// private long fullyVacc;

	private long[] vaccStatus = new long[2];

	private int count;

	public Vaccination(long zipCode, String timestamp, long partialVacc, long fullyVacc) {
		this.zipCode = zipCode;
		this.timestamp = timestamp;
		// this.partialVacc = partialVacc;
		// this.fullyVacc = fullyVacc;
		this.vaccStatus[0] = partialVacc;
		this.vaccStatus[1] = fullyVacc;
	}

	public void updateVacRecords(long pVacc, long fVacc) {
		// this.partialVacc += pVacc;
		this.vaccStatus[0] += pVacc;
		// this.fullyVacc += fVacc;
		this.vaccStatus[1] += fVacc;
		this.count++;
	}

	/**
	 * @return the vaccStatus
	 */
	public long[] getVaccStatus() {
		return this.vaccStatus;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	
	
	
	
	
	/*	
		*//**
			 * @return the partialVacc
			 */
	/*
	 * public long getPartialVacc() { return partialVacc; }
	 * 
	 * 
	 *//**
		 * @return the fullyVacc
		 *//*
			 * public long getFullyVacc() { return fullyVacc; }
			 */

}
