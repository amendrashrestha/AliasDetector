package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculateList {

	HashMap calculateMatchedList(ArrayList<ArrayList<String>> ActivityPeakUsers) {
		HashMap matchedUserMap = new HashMap<String, ArrayList>();
		ArrayList<String> matchedUser = new ArrayList<String>();

		for (int a = 0; a < ActivityPeakUsers.size(); a++) {
			ArrayList userA = new ArrayList();
			ArrayList userB = new ArrayList();
			ArrayList bUsers = ActivityPeakUsers.get(a);

			for (int b = 0; b < bUsers.size(); b++) {
				String tempUsers = bUsers.get(b).toString();

				if (tempUsers.contains("_")) {
					String realUser = returnUser(tempUsers);
					if (tempUsers.contains("_A")) {
						userA.add(realUser);
					} else {
						userB.add(realUser);
					}
				}

			} // end for
			if (userA.size() > 0 && userB.size() > 0) {
				ArrayList tempList = new ArrayList();

				for (int i = 0; i < userA.size(); i++) {
					for (int j = 0; j < userB.size(); j++) {
						if (userA.get(i).equals(userB.get(j))) {
							tempList.add(userA.get(i));
							matchedUserMap.put(String.valueOf(a + 1), tempList);
							break;
						}
					}
				}
				userA.clear();
				userB.clear();
			}
		}
		return matchedUserMap;
	}

	/**
	 * return userID by removing _A or _B
	 * 
	 * @param userID
	 * @return
	 */
	public static String returnUser(String userID) {
		String num = "";
		String regex = "(\\d+)";
		Matcher matcher = Pattern.compile(regex).matcher(userID);
		while (matcher.find()) {
			num = matcher.group();
			// System.out.println(num);
		}
		return num;
	}

}
