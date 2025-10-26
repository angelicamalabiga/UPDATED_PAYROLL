package com.JLopez.payroll.model;

import java.time.LocalDate;


public class Holiday {
	
	 int id;
     String holidayName;
     LocalDate holidayDate;
     String type;
     String description;
     
     
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHoliday_name() {
		return holidayName;
	}
	public void setHoliday_name(String holiday_name) {
		this.holidayName = holiday_name;
	}
	public LocalDate getHoliday_date() {
		return holidayDate;
	}
	public void setHoliday_date(LocalDate holiday_date) {
		this.holidayDate = holiday_date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
     
     

}
