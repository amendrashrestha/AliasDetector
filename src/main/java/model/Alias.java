package model;

import java.util.ArrayList;

public class Alias {

	private String userID;
	private ArrayList<String> postTime;


	public Alias(String user) {
		this.userID = user;
	}

	public String getUser() {
		return userID;
	}

	public void setUser(String user) {
		this.userID = user;
	}
	
	public void setPostTime(ArrayList<String> postTime) {
        this.postTime = postTime;
    }

    public ArrayList<String> getPostTime() {
        return postTime;
    }

}
