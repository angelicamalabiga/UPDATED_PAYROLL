package com.JLopez.payroll.model;

import java.time.LocalDateTime;


public class CashAdvance {
	
	 int id;
     int userId;
     double amount;
     String purpose;
     String status;
     LocalDateTime requestDate;
     
     
     
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
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getRequest_date() {
		return requestDate;
	}
	public void setRequest_date(LocalDateTime request_date) {
		this.requestDate = request_date;
	}
     
     

}
