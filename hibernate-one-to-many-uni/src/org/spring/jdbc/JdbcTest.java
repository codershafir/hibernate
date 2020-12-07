package org.spring.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcTest {

	public static void main(String[] args) {
		String dbUrl = "jdbc:mysql://localhost:3306/hb-04-one-to-many-uni?useSSL=false";
		String userName = "hbstudent";
		String password = "hbstudent";
		try {
			Connection con = DriverManager.getConnection(dbUrl, userName, password);
			System.out.println("Success...!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
