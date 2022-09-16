package dto;

import java.time.LocalDate;

public class Member {
	
	private String id; 
	private String pwd; 
	private String name; 
	private String phone;
	private LocalDate date; //가입일
	private String level; //이용자 등급
	
	
	
	
	
	public Member(String id, String pwd, String name, String phone) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.phone = phone;
	}


	public Member(String id, String pwd, String name, String phone, LocalDate date, String level) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.phone = phone;
		this.date = date;
		this.level = level;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getPwd() {
		return pwd;
	}


	public void setPwd(String pwd) {
		this.pwd = pwd;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}


	public String getLevel() {
		return level;
	}


	public void setLevel(String level) {
		this.level = level;
	}
	
	
	
	

}
	