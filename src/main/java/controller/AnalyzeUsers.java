package controller;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;


import database.FetchDatabase;

public class AnalyzeUsers {

     
    
	FetchDatabase getResult = new FetchDatabase();
	ClusterUser init = new ClusterUser();

	public void calculateFusionEquality() {

		ArrayList<String> users = getResult.getUsers();
		
		try {
			init.createCluster(users);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
//		int userSize = users.size();
//		CreateUserObject timeStylo = new CreateUserObject();
//		timeStylo.returnUserObject(users);		
		
		
		
		
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
