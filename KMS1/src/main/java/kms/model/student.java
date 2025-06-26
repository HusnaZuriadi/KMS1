package kms.model;

import java.io.Serializable;
import java.sql.Date;
import java.io.InputStream;

public class student implements Serializable {
	private static final long serialVersionUID = 1L;
	private int studId;
	private String studName;
	private int studAge;
	private String studGender;
	private String studAddress;
	private Date studBirthDate;
	private int parentId;
	private byte[] studPhoto;
	private byte[] studBirthCert;


	public student() {

	}

	public int getStudId() {
		return studId;
	}

	public void setStudId(int studId) {
		this.studId = studId;
	}

	public String getStudName() {
		return studName;
	}

	public void setStudName(String studName) {
		this.studName = studName;
	}

	public int getStudAge() {
		return studAge;
	}

	public void setStudAge(int studAge) {
		this.studAge = studAge;
	}

	public String getStudGender() {
		return studGender;
	}

	public void setStudGender(String studGender) {
		this.studGender = studGender;
	}

	public String getStudAddress() {
		return studAddress;
	}

	public void setStudAddress(String studAddress) {
		this.studAddress = studAddress;
	}

	public Date getStudBirthDate() {
		return studBirthDate;
	}

	public void setStudBirthDate(Date studBirthDate) {
		this.studBirthDate = studBirthDate;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public byte[] getStudPhoto() {
		return studPhoto;
	}

	public void setStudPhoto(byte[] studPhoto) {
		this.studPhoto = studPhoto;
	}

	public byte[] getStudBirthCert() {
		return studBirthCert;
	}

	public void setStudBirthCert(byte[] studBirthCert) {
		this.studBirthCert = studBirthCert;
	}
	
}
