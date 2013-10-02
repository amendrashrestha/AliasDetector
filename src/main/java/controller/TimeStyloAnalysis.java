package controller;

import java.util.ArrayList;

import model.Alias;

public class TimeStyloAnalysis {

	public ArrayList<Alias> aliases;

	public TimeStyloAnalysis(ArrayList<?> userList) {

		aliases = new ArrayList<Alias>();
		int userSize = userList.size();
		Alias[] tempAlias = new Alias[userSize];

		for (int i = 0; i < userList.size(); i++) {
			String tempUserID = userList.get(i).toString();
			int userID = Integer.parseInt(tempUserID);
			tempAlias[i] = new Alias(userID);
			aliases.add(tempAlias[i]);
		}
	}
}
