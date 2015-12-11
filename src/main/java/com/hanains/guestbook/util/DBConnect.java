package com.hanains.guestbook.util;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {
	public DBConnect() {}
	
	public Connection getConnection() {
		String driverName="oracle.jdbc.driver.OracleDriver";
		String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
		String DB_USER = "webdb";
		String DB_PASSWORD = "webdb";
		
		Connection con = null;
		
		try {
			Class.forName(driverName);
			con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
		}catch(Exception e) {
			System.out.println(e);
		}
		return con;
	}
}
