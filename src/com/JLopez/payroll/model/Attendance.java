package com.JLopez.payroll.model;

import java.time.LocalDate;



public class Attendance {
	
	int id;
    int userId;
    LocalDate periodStart;
    LocalDate periodEnd;
    String fileName;
    String filePath;
    LocalDate submittedDate;
    String verifiedBy;
    LocalDate verifiedDate;
    String attendanceStatus;
    
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return userId;
	}
	public void setUser_id(int user_id) {
		this.userId = user_id;
	}
	public LocalDate getPeriod_start() {
		return periodStart;
	}
	public void setPeriod_start(LocalDate period_start) {
		this.periodStart = period_start;
	}
	public LocalDate getPeriod_end() {
		return periodEnd;
	}
	public void setPeriod_end(LocalDate period_end) {
		this.periodEnd = period_end;
	}
	public String getFile_name() {
		return fileName;
	}
	public void setFile_name(String file_name) {
		this.fileName = file_name;
	}
	public String getFile_path() {
		return filePath;
	}
	public void setFile_path(String file_path) {
		this.filePath = file_path;
	}
	public LocalDate getSubmitted_date() {
		return submittedDate;
	}
	public void setSubmitted_date(LocalDate submitted_date) {
		this.submittedDate = submitted_date;
	}
	public String getVerified_by() {
		return verifiedBy;
	}
	public void setVerified_by(String verified_by) {
		this.verifiedBy = verified_by;
	}
	public LocalDate getVerified_date() {
		return verifiedDate;
	}
	public void setVerified_date(LocalDate verified_date) {
		this.verifiedDate = verified_date;
	}
	public String getAttendance_status() {
		return attendanceStatus;
	}
	public void setAttendance_status(String attendance_status) {
		this.attendanceStatus = attendance_status;
	}
    
    
	
}


