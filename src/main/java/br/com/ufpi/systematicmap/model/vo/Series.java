/**
 * 
 */
package br.com.ufpi.systematicmap.model.vo;

import java.util.HashMap;

/**
 * @author Gleison Andrade
 *
 */
public class Series {
	private String name;
	private String id;
	private HashMap<String, Double> data;
	
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
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the data
	 */
	public HashMap<String, Double> getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(HashMap<String, Double> data) {
		this.data = data;
	}

}
