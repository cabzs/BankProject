package dto;

public class UserLevel {
	
	private int levelId;
	private String levelName;
	private double interest;
	
	
	public UserLevel(int levelId, String levelName, double interest) {
		super();
		this.levelId = levelId;
		this.levelName = levelName;
		this.interest = interest;
	}



	public UserLevel(double interest) {
		super();
		this.interest = interest;
	}


	public int getLevelId() {
		return levelId;
	}


	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}


	public String getLevelName() {
		return levelName;
	}


	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}



	public double getInterest() {
		return interest;
	}



	public void setInterest(double interest) {
		this.interest = interest;
	}
	

}
