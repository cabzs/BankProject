package dto;

import java.util.Date;

public class Account {

	
	private String userAccount;	//계좌번호
	private String accountPwd;
	private String userId;
	private Date startDate; //계좌 개설일
	private Long balance;	//잔액
	
	public Account() {}
	
	
	public Account(String userAccount) {
		super();
		this.userAccount = userAccount;
	}


	public Account(String userAccount, String accountPwd) {
		super();
		this.userAccount = userAccount;
		this.accountPwd = accountPwd;
	}

	public Account(String userAccount, String accountPwd, String userId, Date startDate, Long balance) {
		super();
		this.userAccount = userAccount;
		this.accountPwd = accountPwd;
		this.userId = userId;
		this.startDate = startDate;
		this.balance = balance;
	}


	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getAccountPwd() {
		return accountPwd;
	}

	public void setAccountPwd(String accountPwd) {
		this.accountPwd = accountPwd;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}
	
	
}
