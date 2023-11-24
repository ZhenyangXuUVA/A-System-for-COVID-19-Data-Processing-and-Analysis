package edu.upenn.cit594.util;

public class Property {
	
	private final Long zipCode;
	
	private int zipCount = 0;
	
	private  Double marketValue = null;
	
	private int marketCount = 0;
	
	private  Double totalLivableArea = null;
	
	private int LaCount = 0;
	
	
	public Property(long zipCode, Double marketValue, Double totalLivableArea) {
		this.zipCode = zipCode;
		this.marketValue = marketValue;
		this.totalLivableArea = totalLivableArea;
		
		this.zipCount++;
		if (marketValue != null) {marketCount++;}
		if (totalLivableArea != null) {this.LaCount++;}
		
	}
	
	public void updateMarketValue(Double mv) {
		if (this.marketValue == null) {
			this.marketValue =  mv;
			this.marketCount++;
		} else {
			this.marketValue = this.marketValue + mv;
			this.marketCount++;
		}
	}
	
	public void updateTLA(Double tla) {
		if (this.totalLivableArea == null) {
			this.totalLivableArea = tla;
			this.LaCount++;
		} else {
			this.totalLivableArea = this.totalLivableArea + tla;
			this.LaCount++;
			}
	}

	/**
	 * @return the zipCode
	 */
	public Long getZipCode() {
		return zipCode;
	}


	/**
	 * @return the marketValue
	 */
	public Double getMarketValue() {
		return marketValue;
	}


	/**
	 * @return the totalLivableArea
	 */
	public Double getTotalLivableArea() {
		return totalLivableArea;
	}


	/**
	 * @return the zipCount
	 */
	public int getZipCount() {
		return zipCount;
	}


	/**
	 * @param zipCount the zipCount to set
	 */
	public void setZipCount(int zipCount) {
		this.zipCount = zipCount;
	}


	/**
	 * @return the marketCount
	 */
	public int getMarketCount() {
		return marketCount;
	}


	/**
	 * @param marketCount the marketCount to set
	 */
	public void setMarketCount(int marketCount) {
		this.marketCount = marketCount;
	}


	/**
	 * @return the laCount
	 */
	public int getLaCount() {
		return LaCount;
	}


	/**
	 * @param laCount the laCount to set
	 */
	public void setLaCount(int laCount) {
		LaCount = laCount;
	}
	
}
