package com.JLopez.payroll.model;


import java.time.LocalDate;
import java.time.LocalDateTime;


public class Payroll {
	
	 int id;
     int userId;
     LocalDate periodStart;
     LocalDate periodEnd;
     double basicPay;
     double totalEarnings;
     double totalDeductions;
     double netPay;
     LocalDateTime dateGenerated;
     
     
     
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
	public double getBasic_pay() {
		return basicPay;
	}
	public void setBasic_pay(double basic_pay) {
		this.basicPay = basic_pay;
	}
	public double getTotal_earnings() {
		return totalEarnings;
	}
	public void setTotal_earnings(double total_earnings) {
		this.totalEarnings = total_earnings;
	}
	public double getTotal_deductions() {
		return totalDeductions;
	}
	public void setTotal_deductions(double total_deductions) {
		this.totalDeductions = total_deductions;
	}
	public double getNet_pay() {
		return netPay;
	}
	public void setNet_pay(double net_pay) {
		this.netPay = net_pay;
	}
	public LocalDateTime getDate_generated() {
		return dateGenerated;
	}
	public void setDate_generated(LocalDateTime date_generated) {
		this.dateGenerated = date_generated;
	}
     
     

}
