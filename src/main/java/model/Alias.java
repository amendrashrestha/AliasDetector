package model;


public class Alias {
	
	private String user;

	public Alias(int userID) {
		this.user = Integer.toString(userID);
	}
	
	public String toString(){
		return user;
	}

}
