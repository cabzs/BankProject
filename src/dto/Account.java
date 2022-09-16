package dto;

import java.util.Date;

public class Account {
	
	Member member;
	
	private String account;	//계좌번호
	private String accPwd;
	private Date startDate; //계좌 개설일
	private double interest;	//이자율
	private int balance;	//잔액
	
	
	public Account(Member member, String account, String accPwd, int balance) {
		super();
		this.member = member;
		this.account = account;
		this.accPwd = accPwd;
		this.balance = balance;
	}

	public Account(Member member, String account, String accPwd, Date startDate, double interest,
			int balance) {
		super();
		this.member = member;
		this.account = account;
		this.accPwd = accPwd;
		this.startDate = startDate;
		this.interest = interest;
		this.balance = balance;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccPwd() {
		return accPwd;
	}

	public void setAccPwd(String accPwd) {
		this.accPwd = accPwd;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	
}
