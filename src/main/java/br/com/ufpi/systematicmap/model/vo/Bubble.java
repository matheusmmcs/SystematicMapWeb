/**
 * 
 */
package br.com.ufpi.systematicmap.model.vo;

/**
 * @author Gleison Andrade
 *
 */
public class Bubble {
	private String q1;
	private String q2;
//	private SubQuestion sub_q1;
//	private SubQuestion sub_q2;
	private String sub_q1;
	private String sub_q2;
	
	private Long qnt;

	/**
	 * @return the qnt
	 */
	public Long getQnt() {
		return qnt;
	}

	/**
	 * @param qnt the qnt to set
	 */
	public void setQnt(Long qnt) {
		this.qnt = qnt;
	}

	/**
	 * @return the q1
	 */
	public String getQ1() {
		return q1;
	}

	/**
	 * @param q1 the q1 to set
	 */
	public void setQ1(String q1) {
		this.q1 = q1;
	}

	/**
	 * @return the q2
	 */
	public String getQ2() {
		return q2;
	}

	/**
	 * @param q2 the q2 to set
	 */
	public void setQ2(String q2) {
		this.q2 = q2;
	}

	/**
	 * @return the sub_q1
	 */
	public String getSub_q1() {
		return sub_q1;
	}

	/**
	 * @param sub_q1 the sub_q1 to set
	 */
	public void setSub_q1(String sub_q1) {
		this.sub_q1 = sub_q1;
	}

	/**
	 * @return the sub_q2
	 */
	public String getSub_q2() {
		return sub_q2;
	}

	/**
	 * @param sub_q2 the sub_q2 to set
	 */
	public void setSub_q2(String sub_q2) {
		this.sub_q2 = sub_q2;
	}
	
}
