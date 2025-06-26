package kms.model;

import java.io.Serializable;



public class parent implements Serializable {
	private static final long serialVersionUID = 1L;
	private int parentId;
	private String parentName;
	private String parentEmail;
	private String parentPass;
	private String parentPhone;
	private byte[] parentPhoto;

	
	public parent () {
		
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getParentEmail() {
		return parentEmail;
	}

	public void setParentEmail(String parentEmail) {
		this.parentEmail = parentEmail;
	}

	public String getParentPass() {
		return parentPass;
	}

	public void setParentPass(String parentPass) {
		this.parentPass = parentPass;
	}

	public String getParentPhone() {
		return parentPhone;
	}

	public void setParentPhone(String parentPhone) {
		this.parentPhone = parentPhone;
	}

	public byte[] getParentPhoto() {
		return parentPhoto;
	}

	public void setStudPhoto(byte[] parentPhoto) {
		this.parentPhoto = parentPhoto;
	}
	
	

}
