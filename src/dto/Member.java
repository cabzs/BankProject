package dto;

import java.time.LocalDate;
import java.util.List;

public class Member {
	
	private String userId; 
	private String userPwd; 
	private String userName; 
	private String phone;
	private LocalDate date; //가입일
	private String levelId; //이용자 등급
	
	private List<Account> acList;
	
	//account list...
	
	
	public Member(String userId) {
		super();
		this.userId = userId;
	}
	
	public Member(String userId, String userPwd) {
		super();
		this.userId = userId;
		this.userPwd = userPwd;
	}

	public Member(String userId, String userPwd, String userName, String phone) {
		super();
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.phone = phone;
	}


	public Member(String userId, String userPwd, String userName, String phone, LocalDate date, String levelId) {
		super();
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.phone = phone;
		this.date = date;
		this.levelId = levelId;
	}

	

	public Member(String userId, String userName, String phone, LocalDate date, String levelId) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.phone = phone;
		this.date = date;
		this.levelId = levelId;
	}


	public Member(String userId, String userPwd, String userName, String phone, LocalDate date, String levelId,
			List<Account> acList) {
		super();
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.phone = phone;
		this.date = date;
		this.levelId = levelId;
		this.acList = acList;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getUserPwd() {
		return userPwd;
	}


	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
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


	public String getLevelId() {
		return levelId;
	}


	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}


	public List<Account> getAcList() {
		return acList;
	}


	public void setAcList(List<Account> acList) {
		this.acList = acList;
	}
	
	


}
	