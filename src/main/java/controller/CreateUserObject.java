package controller;

import java.util.ArrayList;

import model.Alias;

public class CreateUserObject {

	public ArrayList<Alias> aliases;

	public CreateUserObject() {

	}

	/**
	 * makes the object of users
	 * 
	 * @param userList
	 */
	public void returnUserObject(ArrayList<?> userList) {

		aliases = new ArrayList<Alias>();
		int userSize = userList.size();
		Alias[] tempAlias = new Alias[userSize];

		for (int i = 0; i < userList.size(); i++) {
			String userID = userList.get(i).toString();
			tempAlias[i] = new Alias(userID);
			aliases.add(tempAlias[i]);
		}
	}
}
