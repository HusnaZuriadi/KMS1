package kms.model;

import java.io.Serializable;



public class teacher implements Serializable {
	private static final long serialVersionUID = 1L;
	private int teacherId;
	private String teacherName;
	private String teacherEmail;
	private String teacherPass;
	private String teacherPhone;
	private String teacherRole;
	private String teacherType;
	private Integer adminId;
	private byte[] teacherPhoto;


	
	public teacher () {
		
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherEmail() {
		return teacherEmail;
	}

	public void setTeacherEmail(String teacherEmail) {
		this.teacherEmail = teacherEmail;
	}

	public String getTeacherPass() {
		return teacherPass;
	}

	public void setTeacherPass(String teacherPass) {
		this.teacherPass = teacherPass;
	}

	public String getTeacherPhone() {
		return teacherPhone;
	}

	public void setTeacherPhone(String teacherPhone) {
		this.teacherPhone = teacherPhone;
	}

	public String getTeacherRole() {
		return teacherRole;
	}

	public void setTeacherRole(String teacherRole) {
		this.teacherRole = teacherRole;
	}

	public String getTeacherType() {
		return teacherType;
	}

	public void setTeacherType(String teacherType) {
		this.teacherType = teacherType;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public byte[] getTeacherPhoto() {
		return teacherPhoto;
	}

	public void setTeacherPhoto(byte[] teacherPhoto) {
		this.teacherPhoto = teacherPhoto;
	}
	
	
}
