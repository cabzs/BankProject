package dto;

import java.time.LocalDate;

public class Trade {
	private int tradeId;
	private String userAccount;
	private Long number;
	private LocalDate tradeDate;
	private int typeId;
	
	
	public Trade(int tradeId, String userAccount, Long number, LocalDate tradeDate, int typeId) {
		super();
		this.tradeId = tradeId;
		this.userAccount = userAccount;
		this.number = number;
		this.tradeDate = tradeDate;
		this.typeId = typeId;
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

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
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
	
	

}
