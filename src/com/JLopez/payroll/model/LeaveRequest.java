package com.JLopez.payroll.model;

import java.time.LocalDate;


public class LeaveRequest {
	
	 int id;
     int userId;
     LocalDate startDate;
     LocalDate endDate;
     String status;
     LocalDate dateRequest;
     String reason;
     
     
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
	public LocalDate getStart_date() {
		return startDate;
	}
	public void setStart_date(LocalDate start_date) {
		this.startDate = start_date;
	}
	public LocalDate getEnd_date() {
		return endDate;
	}
	public void setEnd_date(LocalDate end_date) {
		this.endDate = end_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDate getDate_request() {
		return dateRequest;
	}
	public void setDate_request(LocalDate date_request) {
		this.dateRequest = date_request;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
     
     

}
