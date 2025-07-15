package com.jspiders.jdbc_preparedStatement_crud_operation.connection;

import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.cj.jdbc.Driver;

public class ProductConnection {
	
	public static Connection getProductConnection(){
		try {
			//STEP-1 Load/Register the Driver Class
			
			Driver driver=new Driver();
			DriverManager.registerDriver(driver);
			
//			DriverManager.registerDriver(new Driver());
			
			//STEP-2 create connection
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc-a7","root","Shakya");
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
