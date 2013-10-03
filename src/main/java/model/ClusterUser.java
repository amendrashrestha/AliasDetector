package model;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import controller.AnalyzePost;
import controller.TimeStyloAnalysis;

import database.FetchDatabase;

public class ClusterUser {

     
    
	FetchDatabase getResult = new FetchDatabase();
	AnalyzePost init = new AnalyzePost();

	public void calculateFusionEquality() {

		ArrayList<String> users = getResult.getUsers();
		
		try {
			init.splitUsers(users);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		int userSize = users.size();
		TimeStyloAnalysis timeStylo = new TimeStyloAnalysis();
		timeStylo.returnUserObject(users);		
		
		
		
		
//		int[] tempUser = new int[userSize * 2];
//
//		for (int j = 0; j < userObj.size(); j++) {
//			tempUser[j] = userObj.get(j).getUser();
//			divideUserPostandTime(tempUser[j]);
//		}
	}

	public void divideUserPostandTime(String ID) {

		ArrayList userPost = new ArrayList();
		ArrayList<?> userPostTime = new ArrayList<Object>();
		ArrayList firstUserPost = new ArrayList<Object>();
		ArrayList secondUserPost = new ArrayList<Object>();
		ArrayList firstUserPostTime = new ArrayList<Object>();
		ArrayList secondUserPostTime = new ArrayList<Object>();

		try {
			userPostTime = getResult.getPostTime(ID);
			userPost = getResult.getPost(ID);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < userPost.size(); i++) {
			if (i % 2 == 0) {
				firstUserPost.add(userPost.get(i));
				firstUserPostTime.add(userPostTime.get(i));
			} else {
				secondUserPost.add(userPost.get(i));
				secondUserPostTime.add(userPostTime.get(i));
			}
		}

	}
}
