package com.freetravelapp.findyourride.search;

public class SearchData {
	
    private String seeker_name;
    private String seeker_date;
    private String seeker_time;
    private String ride_type;
    private String seeker_start_point;
    private String seeker_end_point;
    private String seeker_partner;
    private String seeker_contact;
    private String vehicle_no;
    private String vehicle_type;
    
	public SearchData(String seeker_name, String seeker_date,
			String seeker_time, String ride_type, String seeker_start_point,
			String seeker_end_point, String seeker_partner,
			String seeker_contact, String vehicle_no, String vehicle_type) {
		super();
		this.seeker_name = seeker_name;
		this.seeker_date = seeker_date;
		this.seeker_time = seeker_time;
		this.ride_type = ride_type;
		this.seeker_start_point = seeker_start_point;
		this.seeker_end_point = seeker_end_point;
		this.seeker_partner = seeker_partner;
		this.seeker_contact = seeker_contact;
		this.vehicle_no = vehicle_no;
		this.vehicle_type = vehicle_type;
	}
	public String getSeeker_name() {
		return seeker_name;
	}
	public void setSeeker_name(String seeker_name) {
		this.seeker_name = seeker_name;
	}
	public String getSeeker_date() {
		return seeker_date;
	}
	public void setSeeker_date(String seeker_date) {
		this.seeker_date = seeker_date;
	}
	public String getSeeker_time() {
		return seeker_time;
	}
	public void setSeeker_time(String seeker_time) {
		this.seeker_time = seeker_time;
	}
	public String getRide_type() {
		return ride_type;
	}
	public void setRide_type(String ride_type) {
		this.ride_type = ride_type;
	}
	public String getSeeker_start_point() {
		return seeker_start_point;
	}
	public void setSeeker_start_point(String seeker_start_point) {
		this.seeker_start_point = seeker_start_point;
	}
	public String getSeeker_end_point() {
		return seeker_end_point;
	}
	public void setSeeker_end_point(String seeker_end_point) {
		this.seeker_end_point = seeker_end_point;
	}
	public String getSeeker_partner() {
		return seeker_partner;
	}
	public void setSeeker_partner(String seeker_partner) {
		this.seeker_partner = seeker_partner;
	}
	public String getSeeker_contact() {
		return seeker_contact;
	}
	public void setSeeker_contact(String seeker_contact) {
		this.seeker_contact = seeker_contact;
	}
	public String getVehicle_no() {
		return vehicle_no;
	}
	public void setVehicle_no(String vehicle_no) {
		this.vehicle_no = vehicle_no;
	}
	public String getVehicle_type() {
		return vehicle_type;
	}
	public void setVehicle_type(String vehicle_type) {
		this.vehicle_type = vehicle_type;
	}
    
	
    
}
