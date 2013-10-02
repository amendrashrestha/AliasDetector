package model;

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
		
		for(int j = 0; j < userObj.size(); j++){
			String tempUser = userObj.toString();
			//System.out.println(tempUser);
		}
		for(int i = 0; i < userSize; i++){
			divideUserPost(i);
		}
		
		
	}
	
	public void divideUserPost(int ID){
		
	}
}
