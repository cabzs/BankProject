package dto;

public class UserLevel {
	
	private int levelId;
	private String levelName;
	private long interest;
	
	
	public UserLevel(int levelId, String levelName, long interest) {
		super();
		this.levelId = levelId;
		this.levelName = levelName;
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


	public long getInterest() {
		return interest;
	}


	public void setInterest(long interest) {
		this.interest = interest;
	}
	
	
	

}
