/**
 * 
 */
package br.com.ufpi.systematicmap.model.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gleison Andrade
 *
 */
public class Column {
	private String title;
	private String subTitle;
	private List<String> categories = new ArrayList<String>();
	private String yAxis;
	private List<Double> data = new ArrayList<Double>();
	private String name;
	
	
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
	 * @return the subTitle
	 */
	public String getSubTitle() {
		return subTitle;
	}
	/**
	 * @param subTitle the subTitle to set
	 */
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	/**
	 * @return the data
	 */
	public List<Double> getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(List<Double> data) {
		this.data = data;
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
	 * @return the categories
	 */
	public List<String> getCategories() {
		return categories;
	}
	/**
	 * @param categories the categories to set
	 */
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	/**
	 * @return the yAxis
	 */
	public String getyAxis() {
		return yAxis;
	}
	/**
	 * @param yAxis the yAxis to set
	 */
	public void setyAxis(String yAxis) {
		this.yAxis = yAxis;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Column [title=" + title + ", subTitle=" + subTitle
				+ ", categories=" + categories + ", yAxis=" + yAxis + ", data="
				+ data + ", name=" + name + "]";
	}
}
