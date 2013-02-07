package org;



import java.io.Serializable;

public class StAppInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String getAddName() {
		return addName;
	}
	public void setAddName(String addName) {
		this.addName = addName;
	}
	public String getAddEmail() {
		return addEmail;
	}
	public void setAddEmail(String addEmail) {
		this.addEmail = addEmail;
	}
	private String addName;
	private String addEmail;
	
}