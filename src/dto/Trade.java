package dto;

import java.time.LocalDate;

public class Trade {
	private int tradeId;
	private String userAccount;
	private Long amount;
	private LocalDate tradeDate;
	private int typeId;
	private Long tradeBal;
	private String otherAccount;
	


	public Trade(int tradeId, String userAccount, Long amount, LocalDate tradeDate, int typeId) {
		super();
		this.tradeId = tradeId;
		this.userAccount = userAccount;
		this.amount = amount;
		this.tradeDate = tradeDate;
		this.typeId = typeId;
	}

	

	public Trade(int tradeId, String userAccount, Long amount, LocalDate tradeDate, int typeId, Long tradeBal) {
		super();
		this.tradeId = tradeId;
		this.userAccount = userAccount;
		this.amount = amount;
		this.tradeDate = tradeDate;
		this.typeId = typeId;
		this.tradeBal = tradeBal;
	}



	public Trade(int tradeId, String userAccount, Long amount, LocalDate tradeDate, int typeId, Long tradeBal,
			String otherAccount) {
		super();
		this.tradeId = tradeId;
		this.userAccount = userAccount;
		this.amount = amount;
		this.tradeDate = tradeDate;
		this.typeId = typeId;
		this.tradeBal = tradeBal;
		this.otherAccount = otherAccount;
	}



	public String getOtherAccount() {
		return otherAccount;
	}



	public void setOtherAccount(String otherAccount) {
		this.otherAccount = otherAccount;
	}



	public int getTradeId() {
		return tradeId;
	}



	public void setTradeId(int tradeId) {
		this.tradeId = tradeId;
	}



	public String getUserAccount() {
		return userAccount;
	}



	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}



	public Long getAmount() {
		return amount;
	}



	public void setAmount(Long amount) {
		this.amount = amount;
	}



	public LocalDate getTradeDate() {
		return tradeDate;
	}



	public void setTradeDate(LocalDate tradeDate) {
		this.tradeDate = tradeDate;
	}



	public int getTypeId() {
		return typeId;
	}



	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}



	public Long getTradeBal() {
		return tradeBal;
	}



	public void setTradeBal(Long tradeBal) {
		this.tradeBal = tradeBal;
	}

	

}
