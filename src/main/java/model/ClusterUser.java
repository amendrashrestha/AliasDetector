package model;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import controller.TimeStyloAnalysis;

import database.FetchDatabase;

public class ClusterUser {

	FetchDatabase getResult = new FetchDatabase();

	public void calculateFusionEquality() {

		ArrayList<Integer> users = getResult.getUsers();
		int userSize = users.size();

		ArrayList<Alias> userObj = new ArrayList<Alias>();

		TimeStyloAnalysis timeStylo = new TimeStyloAnalysis(users);
		userObj = timeStylo.aliases;
		int[] tempUser = new int[userSize * 2];

		for (int j = 0; j < userObj.size(); j++) {
			tempUser[j] = userObj.get(j).getUser();
			divideUserPost(tempUser[j]);
		}
	}

	public void divideUserPost(int ID) {

		ArrayList<?> userPost = new ArrayList<Object>();
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
				firstUserPostTime.add(userPost.get(i));
			} else {
				secondUserPost.add(userPost.get(i));
				secondUserPostTime.add(userPost.get(i));
			}
		}

	}
}
