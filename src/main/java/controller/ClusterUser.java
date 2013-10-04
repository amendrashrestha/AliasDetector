package controller;

import java.awt.List;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import database.FetchDatabase;

import model.Alias;

public class ClusterUser {

	CreateUserObject initUserObj = new CreateUserObject();
	ArrayList<Alias> userObj = new ArrayList<Alias>();
	FetchDatabase getResult = new FetchDatabase();
	CalculateList initCalc = new CalculateList();

	ArrayList firstUserPostTime, secondUserPostTime;
	ArrayList<String> firstActivitycluster0004 = new ArrayList<String>();
	ArrayList<String> firstActivitycluster0408 = new ArrayList<String>();
	ArrayList<String> firstActivitycluster0812 = new ArrayList<String>();
	ArrayList<String> firstActivitycluster1216 = new ArrayList<String>();
	ArrayList<String> firstActivitycluster1620 = new ArrayList<String>();
	ArrayList<String> firstActivitycluster2000 = new ArrayList<String>();
	ArrayList<String> nullcluster = new ArrayList<String>();
	ArrayList<String> users0004 = new ArrayList<String>();
	ArrayList<String> users0408 = new ArrayList<String>();
	ArrayList<String> users0812 = new ArrayList<String>();
	ArrayList<String> users1216 = new ArrayList<String>();
	ArrayList<String> users1620 = new ArrayList<String>();
	ArrayList<String> users2000 = new ArrayList<String>();
	ArrayList<String> secondActivitycluster0004 = new ArrayList<String>();
	ArrayList<String> secondActivitycluster0408 = new ArrayList<String>();
	ArrayList<String> secondActivitycluster0812 = new ArrayList<String>();
	ArrayList<String> secondActivitycluster1216 = new ArrayList<String>();
	ArrayList<String> secondActivitycluster1620 = new ArrayList<String>();
	ArrayList<String> secondActivitycluster2000 = new ArrayList<String>();
	ArrayList<ArrayList<String>> firstActivityCluster;
	ArrayList<ArrayList<String>> totalSleepingUserCluster;
	ArrayList<ArrayList<String>> secondActivityCluster;

	public ClusterUser() {

	}

	public void splitUsers(ArrayList<String> user) throws SQLException {
		initUserObj.returnUserObject(user);
		userObj = initUserObj.aliases;
		int userSize = user.size();

		for (int i = 0; i < userSize; i++) {
			String tempUser = new String();
			tempUser = userObj.get(i).getUser();

			String firstUser = tempUser + "_A";
			String secondUser = tempUser + "_B";

			splitTime(tempUser);
			userObj.remove(i);

			Alias tempAlias = new Alias(tempUser);
			tempAlias = new Alias(firstUser);
			tempAlias.setPostTime(firstUserPostTime);
			double[] tempTimeVectorA = getTimeVector(firstUserPostTime);

			// dividing posts into clusters
			createActiveCluster(tempTimeVectorA, firstUser);
			userObj.add(0, tempAlias);

			// for second user
			tempAlias = new Alias(secondUser);
			tempAlias.setPostTime(secondUserPostTime);
			double[] tempTimeVectorB = getTimeVector(secondUserPostTime);

			createActiveCluster(tempTimeVectorB, secondUser);
			userObj.add(tempAlias);
		}
		userObj.clear();

		firstActivityCluster = new ArrayList<ArrayList<String>>();
		firstActivityCluster.add(firstActivitycluster0004);
		firstActivityCluster.add(firstActivitycluster0408);
		firstActivityCluster.add(firstActivitycluster0812);
		firstActivityCluster.add(firstActivitycluster1216);
		firstActivityCluster.add(firstActivitycluster1620);
		firstActivityCluster.add(firstActivitycluster2000);

		HashMap<String, ArrayList> matchedActiveUsers = new HashMap<String, ArrayList>();
		matchedActiveUsers = initCalc
				.calculateMatchedList(firstActivityCluster);

		System.out.println("\nActive Cluster");

		for (Entry<String, ArrayList> peakMapEntry : matchedActiveUsers
				.entrySet()) {
			System.out.println(peakMapEntry);
		}
		System.out.println("------------------");
		createSleepingCluster(matchedActiveUsers);
	}

	private void createSleepingCluster(HashMap<String, ArrayList> matchedUsers) {

		for (Entry<String, ArrayList> MatrixEntry : matchedUsers.entrySet()) {
			ArrayList values = MatrixEntry.getValue();
			splitActiveUser(values);
		}
	}

	@SuppressWarnings("unchecked")
	private void splitActiveUser(ArrayList<String> users) {
		try {
			initUserObj.returnUserObject(users);
			userObj = initUserObj.aliases;
			int userSize = userObj.size();

			for (int i = 0; i < userSize; i++) {

				String tempUser = new String();
				tempUser = userObj.get(i).getUser();

				Alias tempAlias = new Alias(tempUser);
				splitTime(tempUser);
				userObj.remove(i);

				String firstUser = tempUser + "_A";
				String secondUser = tempUser + "_B";

				tempAlias = new Alias(firstUser);
				tempAlias.setPostTime(firstUserPostTime);

				double[] tempTimeVectorA = getTimeVector(firstUserPostTime);
				inActivityCluster(tempTimeVectorA, firstUser);
				userObj.add(0, tempAlias);

				// for second user
				tempAlias = new Alias(secondUser);
				tempAlias.setPostTime(secondUserPostTime);

				double[] tempTimeVectorB = getTimeVector(secondUserPostTime);

				inActivityCluster(tempTimeVectorB, secondUser);
				userObj.add(tempAlias);
			}

		} catch (SQLException ex) {
			System.out.println(ex);
		}

		totalSleepingUserCluster = new ArrayList<ArrayList<String>>();
		totalSleepingUserCluster.add(users0004);
		totalSleepingUserCluster.add(users0408);
		totalSleepingUserCluster.add(users0812);
		totalSleepingUserCluster.add(users1216);
		totalSleepingUserCluster.add(users1620);
		totalSleepingUserCluster.add(users2000);
		totalSleepingUserCluster.add(nullcluster);

		// System.out.println("\nSleeping Cluster" + totalSleepingUserCluster);

		HashMap<String, ArrayList> matchedSleepingUsers = new HashMap<String, ArrayList>();
		matchedSleepingUsers = initCalc
				.calculateMatchedList(totalSleepingUserCluster);

		System.out.println("\nInactive Users: ");
		for (Entry<String, ArrayList> peakMapEntry : matchedSleepingUsers
				.entrySet()) {
			System.out.println(peakMapEntry);
		}
		System.out.println("------------------");
		secondActivityCluster(matchedSleepingUsers);

		secondActivityCluster = new ArrayList<ArrayList<String>>();
		secondActivityCluster.add(secondActivitycluster0004);
		secondActivityCluster.add(secondActivitycluster0408);
		secondActivityCluster.add(secondActivitycluster0812);
		secondActivityCluster.add(secondActivitycluster1216);
		secondActivityCluster.add(secondActivitycluster1620);
		secondActivityCluster.add(secondActivitycluster2000);

		HashMap<String, ArrayList> matchedsecondActivityClusterUsers = new HashMap<String, ArrayList>();
		matchedsecondActivityClusterUsers = initCalc.calculateMatchedList(secondActivityCluster);
		
		System.out.println("\n Second Activity Cluster Users: ");
		for (Entry<String, ArrayList> peakMapEntry : matchedsecondActivityClusterUsers
				.entrySet()) {
			System.out.println(peakMapEntry);
		}
		
		clearList();
		System.out.println("******************");
	}

	/**
	 * creating second activity cluster
	 * 
	 * @param matchedSleepingUsers
	 */
	private void secondActivityCluster(
			HashMap<String, ArrayList> matchedSleepingUsers) {

		for (Entry<String, ArrayList> peakMapEntry : matchedSleepingUsers
				.entrySet()) {
			ArrayList values = peakMapEntry.getValue();
			try {
				splitUserForSecondActivityPeak(values);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void splitUserForSecondActivityPeak(ArrayList user)
			throws SQLException {

		initUserObj.returnUserObject(user);
		userObj = initUserObj.aliases;
		int userSize = user.size();

		for (int i = 0; i < userSize; i++) {
			String tempUser = new String();
			tempUser = userObj.get(i).getUser();

			String firstUser = tempUser + "_A";
			String secondUser = tempUser + "_B";

			splitTime(tempUser);
			userObj.remove(i);

			Alias tempAlias = new Alias(tempUser);
			tempAlias = new Alias(firstUser);
			tempAlias.setPostTime(firstUserPostTime);
			double[] tempTimeVectorA = getTimeVector(firstUserPostTime);

			// dividing posts into clusters
			createSecondActiveCluster(tempTimeVectorA, firstUser);
			userObj.add(0, tempAlias);

			// for second user
			tempAlias = new Alias(secondUser);
			tempAlias.setPostTime(secondUserPostTime);
			double[] tempTimeVectorB = getTimeVector(secondUserPostTime);

			createSecondActiveCluster(tempTimeVectorB, secondUser);
			userObj.add(tempAlias);
		}
		userObj.clear();
	}

	private void createSecondActiveCluster(double[] timeVector, String user) {
		double[] post_count = timeVector;
		double totalPost = 0.0;

		for (double i : post_count) {
			totalPost += i;
		}

		double threshHold = (20 * totalPost) / 100;
		double firstClusterSize = post_count[0] + post_count[1] + post_count[2]
				+ post_count[3];
		double secondClusterSize = post_count[4] + post_count[5]
				+ post_count[6] + post_count[7];
		double thirdClusterSize = post_count[8] + post_count[9]
				+ post_count[10] + post_count[11];
		double fourthClusterSize = post_count[12] + post_count[13]
				+ post_count[14] + post_count[15];
		double fifthClusterSize = post_count[16] + post_count[17]
				+ post_count[18] + post_count[19];
		double sixthClusterSize = post_count[20] + post_count[21]
				+ post_count[22] + post_count[23];

		if (firstClusterSize > threshHold) {
			secondActivitycluster0004.add(user);
		}
		if (secondClusterSize > threshHold) {
			secondActivitycluster0408.add(user);
		}
		if (thirdClusterSize > threshHold) {
			secondActivitycluster0812.add(user);
		}
		if (fourthClusterSize > threshHold) {
			secondActivitycluster1216.add(user);
		}
		if (fifthClusterSize > threshHold) {
			secondActivitycluster1620.add(user);
		}
		if (sixthClusterSize > threshHold) {
			secondActivitycluster2000.add(user);
		}

	}

	private void inActivityCluster(double[] tempTimeVector, String user) {
		double[] post_count = tempTimeVector;
		boolean contains = false;

		if (post_count[0] + post_count[1] + post_count[2] + post_count[3] == 0) {
			users0004.add(user);
			contains = true;
		}
		if (post_count[4] + post_count[5] + post_count[6] + post_count[7] == 0) {
			users0408.add(user);
			contains = true;
		}
		if (post_count[8] + post_count[9] + post_count[10] + post_count[11] == 0) {
			users0812.add(user);
			contains = true;
		}
		if (post_count[12] + post_count[13] + post_count[14] + post_count[15] == 0) {
			users1216.add(user);
			contains = true;
		}
		if (post_count[16] + post_count[17] + post_count[18] + post_count[19] == 0) {
			users1620.add(user);
			contains = true;
		}
		if (post_count[20] + post_count[21] + post_count[22] + post_count[23] == 0) {
			users2000.add(user);
			contains = true;
		}
		if (contains == false) {
			nullcluster.add(user);
		}
	}

	private void createActiveCluster(double[] timeVector, String user) {
		double[] post_count = timeVector;
		double totalPost = 0.0;

		for (double i : post_count) {
			totalPost += i;
		}

		double threshHold = (20 * totalPost) / 100;
		double firstClusterSize = post_count[0] + post_count[1] + post_count[2]
				+ post_count[3];
		double secondClusterSize = post_count[4] + post_count[5]
				+ post_count[6] + post_count[7];
		double thirdClusterSize = post_count[8] + post_count[9]
				+ post_count[10] + post_count[11];
		double fourthClusterSize = post_count[12] + post_count[13]
				+ post_count[14] + post_count[15];
		double fifthClusterSize = post_count[16] + post_count[17]
				+ post_count[18] + post_count[19];
		double sixthClusterSize = post_count[20] + post_count[21]
				+ post_count[22] + post_count[23];

		if (firstClusterSize > threshHold) {
			firstActivitycluster0004.add(user);
		}
		if (secondClusterSize > threshHold) {
			firstActivitycluster0408.add(user);
		}
		if (thirdClusterSize > threshHold) {
			firstActivitycluster0812.add(user);
		}
		if (fourthClusterSize > threshHold) {
			firstActivitycluster1216.add(user);
		}
		if (fifthClusterSize > threshHold) {
			firstActivitycluster1620.add(user);
		}
		if (sixthClusterSize > threshHold) {
			firstActivitycluster2000.add(user);
		}
	} //

	public void splitTime(String ID) throws SQLException {

		ArrayList<?> userPostTime = new ArrayList<Object>();
		firstUserPostTime = new ArrayList();
		secondUserPostTime = new ArrayList();

		userPostTime = getResult.getPostTime(ID);

		for (int i = 0; i < userPostTime.size(); i++) {
			if (i % 2 == 0) {
				firstUserPostTime.add(userPostTime.get(i));
			} else {
				secondUserPostTime.add(userPostTime.get(i));
			}
		}
	}

	public double[] getTimeVector(ArrayList time) {

		double[] rr = new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		Iterator itr = time.iterator();

		while (itr.hasNext()) {
			Timestamp key = (Timestamp) itr.next();
			int hr = key.getHours();
			rr[hr]++;
		}
		return rr;
	}

	private void clearList() {
		if (!nullcluster.isEmpty()) {
			nullcluster.clear();
		}
		if (!users0004.isEmpty()) {
			users0004.clear();
		}
		if (!users0408.isEmpty()) {
			users0408.clear();
		}
		if (!users0812.isEmpty()) {
			users0812.clear();
		}
		if (!users1216.isEmpty()) {
			users1216.clear();
		}
		if (!users1620.isEmpty()) {
			users1620.clear();
		}
		if (!users2000.isEmpty()) {
			users2000.clear();
		}
		if (!firstUserPostTime.isEmpty()) {
			firstUserPostTime.clear();
		}
		if (!secondUserPostTime.isEmpty()) {
			secondUserPostTime.clear();
		}
		if (!firstActivitycluster0004.isEmpty()) {
			firstActivitycluster0004.clear();
		}
		if (!firstActivitycluster0408.isEmpty()) {
			firstActivitycluster0408.clear();
		}
		if (!firstActivitycluster0812.isEmpty()) {
			firstActivitycluster0812.clear();
		}
		if (!firstActivitycluster1216.isEmpty()) {
			firstActivitycluster1216.clear();
		}
		if (!firstActivitycluster1620.isEmpty()) {
			firstActivitycluster1620.clear();
		}
		if (!firstActivitycluster2000.isEmpty()) {
			firstActivitycluster2000.clear();
		}
	}

}
