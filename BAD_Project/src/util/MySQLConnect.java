package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

	public class MySQLConnect {
		
		private String HOST = "localhost:3306";
		private String DATABASE = "janji_jywa";
		private String URL = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
		private String USER = "root";
		private String PASS = "";
		public Connection con;
		public Statement statement;
		public ResultSet rs;
		public ResultSetMetaData rsm;
		public PreparedStatement ps;

		public MySQLConnect() {
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				con = DriverManager.getConnection(URL, USER, PASS);
				statement = con.createStatement();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		public ResultSet executeQuery(String query) {
			
			try {
				rs = statement.executeQuery(query);
				rsm = rs.getMetaData();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return rs;
		}
		
		public void executeUpdate(String query) {
			
			try {
				statement.executeUpdate(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public PreparedStatement prepareStatement(String query) {
	    	PreparedStatement ps = null;
	    	try {
				ps = con.prepareStatement(query);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	return ps;
	    }

	}