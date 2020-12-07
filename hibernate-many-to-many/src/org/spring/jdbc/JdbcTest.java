package org.spring.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcTest {

	public static void main(String[] args) {
		String dbUrl = "jdbc:mysql://localhost:3306/hb-05-many-to-many?useSSL=false";
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
