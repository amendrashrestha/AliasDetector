package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class FetchDatabase {
	
	DBConnection dbConnect = new DBConnection();
	
	/**
	 * returns the users from database
	 * @return
	 */
	public ArrayList<Integer> getUsers(){
		ArrayList<Integer> databaseUsersID = new ArrayList<Integer>();
        PreparedStatement ps;
        ResultSet rs;
        Connection con;

        String selectUserQuery = 
                " SELECT distinct MakerID FROM tbl_posts " +
                " group by MakerID " + 
                " order by MakerID" +
               " limit 10";              
        try {
			con = dbConnect.getDBConnection();
			System.out.println(selectUserQuery);
	        ps = con.prepareStatement(selectUserQuery);
	        rs = ps.executeQuery();
	        while (rs.next()) {
	            databaseUsersID.add(rs.getInt(1));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        return databaseUsersID;		
	}
	
	 /*
     * This method returns year and time posted by any users.
     */
    public ArrayList getPostTime(int ID) {
        ArrayList<Timestamp> time = new ArrayList<Timestamp>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        try {
        	con = dbConnect.getDBConnection();
            String queryTime = "select created from tbl_posts where `MakerID` = \'" + ID + "\'";
            ps = con.prepareStatement(queryTime);
            rs = ps.executeQuery();
            while (rs.next()) {
                time.add(rs.getTimestamp(1));
            }
        } catch (java.lang.Throwable t) {
             System.out.println(t.getClass().getName()); //this'll tell you what class has been thrown
        } finally {
            try {
                if (con != null) {                
                    con.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
            }
        }
        return time;
    }
    
    /**
     * return time of post of ID
     */
    public ArrayList<String> getPost(int ID) throws SQLException {
        ArrayList<String> messages = new ArrayList<String>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;

        try {
            con = dbConnect.getDBConnection();
            String querypostID = "SELECT `Content` FROM `tbl_posts` WHERE `MakerID` =\'" + ID + "\'";
            ps = con.prepareStatement(querypostID);
            rs = ps.executeQuery();

            while (rs.next()) {
                messages.add(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
            }
        }
        return messages;
    }

}
