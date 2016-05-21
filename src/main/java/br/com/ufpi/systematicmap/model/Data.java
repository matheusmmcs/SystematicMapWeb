package br.com.ufpi.systematicmap.model;

public class Data {
	private String name;
	private Double y;
	private Double percent;
	private String drilldown;
//	private Drilldown drilldown;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Data [name=" + name + ", y=" + y + "]";
	}

	/**
	 * @return the drilldown
	 */
	public String getDrilldown() {
		return drilldown;
	}

	/**
	 * @param drilldown the drilldown to set
	 */
	public void setDrilldown(String drilldown) {
		this.drilldown = drilldown;
	}

	/**
	 * @return the y
	 */
	public Double getY() {
		return y;
	}



	/**
	 * @param y the y to set
	 */
	public void setY(Double y) {
		this.y = y;
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}



	/**
	 * @return the percent
	 */
	public Double getPercent() {
		return percent;
	}

	/**
	 * @param percent the percent to set
	 */
	public void setPercent(Double percent) {
		this.percent = percent;
	}
	
	
}
