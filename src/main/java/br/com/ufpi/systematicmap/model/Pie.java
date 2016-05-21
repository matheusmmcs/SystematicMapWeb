package br.com.ufpi.systematicmap.model;

import java.util.List;

import br.com.ufpi.systematicmap.model.vo.Series;

public class Pie {
	private String title;
	private String name;
	private boolean colorByPoint;
	private List<Data> data;
	private Series series;
	
	/**
	 * @return the series
	 */
	public Series getSeries() {
		return series;
	}
	/**
	 * @param series the series to set
	 */
	public void setSeries(Series series) {
		this.series = series;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * @return the colorByPoint
	 */
	public boolean isColorByPoint() {
		return colorByPoint;
	}
	/**
	 * @param colorByPoint the colorByPoint to set
	 */
	public void setColorByPoint(boolean colorByPoint) {
		this.colorByPoint = colorByPoint;
	}
	/**
	 * @return the data
	 */
	public List<Data> getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(List<Data> data) {
		this.data = data;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Pie [title=" + title + ", name=" + name + ", colorByPoint="
				+ colorByPoint + ", data=" + data + "]";
	}
	
	
}
