package controller;

import java.awt.List;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;

import database.FetchDatabase;

import model.Alias;

public class AnalyzePost {

	TimeStyloAnalysis timeStylo = new TimeStyloAnalysis();
	ArrayList<Alias> userObj = new ArrayList<Alias>();
	FetchDatabase getResult = new FetchDatabase();
	CalculateList initCalc = new CalculateList();
	
	ArrayList firstUserPostTime, secondUserPostTime;
	ArrayList<String> cluster0004 = new ArrayList<String>();
    ArrayList<String> cluster0408 = new ArrayList<String>();
    ArrayList<String> cluster0812 = new ArrayList<String>();
    ArrayList<String> cluster1216 = new ArrayList<String>();
    ArrayList<String> cluster1620 = new ArrayList<String>();
    ArrayList<String> cluster2000 = new ArrayList<String>();
    ArrayList<String> nullcluster = new ArrayList<String>();
    ArrayList<String> users0004 = new ArrayList<String>();
    ArrayList<String> users0408 = new ArrayList<String>();
    ArrayList<String> users0812 = new ArrayList<String>();
    ArrayList<String> users1216 = new ArrayList<String>();
    ArrayList<String> users1620 = new ArrayList<String>();
    ArrayList<String> users2000 = new ArrayList<String>();
    ArrayList<String> firstActivityPeak0004 = new ArrayList<String>();
    ArrayList<String> firstActivityPeak0408 = new ArrayList<String>();
    ArrayList<String> firstActivityPeak0812 = new ArrayList<String>();
    ArrayList<String> firstActivityPeak1216 = new ArrayList<String>();
    ArrayList<String> firstActivityPeak1620 = new ArrayList<String>();
    ArrayList<String> firstActivityPeak2000 = new ArrayList<String>();
    ArrayList<ArrayList<String>> totalUserCluster;
    ArrayList<ArrayList<String>> totalSleepingUserCluster;

	public AnalyzePost() {

	}

	public void splitUsers(ArrayList<String> user) throws SQLException {
		timeStylo.returnUserObject(user);
		userObj = timeStylo.aliases;
		int userSize = user.size();

		Alias[] tempAlias = new Alias[userSize + 1];
		String[] tempUser = new String[userSize + 1];

		for (int i = 0; i < userSize; i++) {

			tempUser[i] = userObj.get(i).getUser();

			String firstUser = tempUser[i] + "_A";
			String secondUser = tempUser[i] + "_B";

			splitTime(tempUser[i]);
			userObj.remove(i);

			tempAlias[i] = new Alias(firstUser);
			tempAlias[i].setPostTime(firstUserPostTime);
			double[] tempTimeVectorA = getTimeVector(firstUserPostTime);

			// dividing posts into clusters
			createCluster(tempTimeVectorA, firstUser);
			userObj.add(0, tempAlias[i]);

			// for second user
			tempAlias[i + 1] = new Alias(secondUser);
			tempAlias[i + 1].setPostTime(secondUserPostTime);
			double[] tempTimeVectorB = getTimeVector(secondUserPostTime);

			createCluster(tempTimeVectorB, secondUser);
			userObj.add(tempAlias[i + 1]);
		}
		
		totalUserCluster = new ArrayList<ArrayList<String>>();
        totalUserCluster.add(cluster0004);
        totalUserCluster.add(cluster0408);
        totalUserCluster.add(cluster0812);
        totalUserCluster.add(cluster1216);
        totalUserCluster.add(cluster1620);
        totalUserCluster.add(cluster2000);

        totalSleepingUserCluster = new ArrayList<ArrayList<String>>();
        totalSleepingUserCluster.add(users0004);
        totalSleepingUserCluster.add(users0408);
        totalSleepingUserCluster.add(users0812);
        totalSleepingUserCluster.add(users1216);
        totalSleepingUserCluster.add(users1620);
        totalSleepingUserCluster.add(users2000);
        totalSleepingUserCluster.add(nullcluster);
        
        initCalc.calculateMatchedList(totalUserCluster);
	}

	private void createCluster(double[] timeVector, String user) {
		double[] post_count = timeVector;
        double totalPost = 0.0;

        for (double i : post_count) {
            totalPost += i;
        }

        double threshHold = (20 * totalPost) / 100;
        double firstClusterSize = post_count[0] + post_count[1] + post_count[2] + post_count[3];
        double secondClusterSize = post_count[4] + post_count[5] + post_count[6] + post_count[7];
        double thirdClusterSize = post_count[8] + post_count[9] + post_count[10] + post_count[11];
        double fourthClusterSize = post_count[12] + post_count[13] + post_count[14] + post_count[15];
        double fifthClusterSize = post_count[16] + post_count[17] + post_count[18] + post_count[19];
        double sixthClusterSize = post_count[20] + post_count[21] + post_count[22] + post_count[23];

        if (firstClusterSize > threshHold) {
            cluster0004.add(user);
        }
        if (secondClusterSize > threshHold) {
            cluster0408.add(user);
        }
        if (thirdClusterSize > threshHold) {
            cluster0812.add(user);
        }
        if (fourthClusterSize > threshHold) {
            cluster1216.add(user);
        }
        if (fifthClusterSize > threshHold) {
            cluster1620.add(user);
        }
        if (sixthClusterSize > threshHold) {
            cluster2000.add(user);
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

}
