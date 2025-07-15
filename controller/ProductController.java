package com.jspiders.jdbc_preparedStatement_crud_operation.controller;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import com.jspiders.jdbc_preparedStatement_crud_operation.connection.ProductConnection;

public class ProductController {
	public static void main(String[] args) throws SQLException {

		Connection conn = ProductConnection.getProductConnection();

		int id;
		String name, color, mfd, expd;
		Double price;
		LocalDate lmfd, lexpd;

		if (conn != null) {
			System.out.println(" >> Connection Created to DB Successfully. <<");

			Scanner sc = new Scanner(System.in);
			System.out.println("\n===Enter the Product Details===");
			System.out.println("Enter the product id :");
			id = sc.nextInt();
			System.out.println("Enter the product name :");
			name = sc.next();
			System.out.println("Enter the product color :");
			color = sc.next();
			System.out.println("Enter the product price :");
			price = sc.nextDouble();
			System.out.println("Enter the product MFD(yyyy-mm-dd) :");
			mfd = sc.next();
			System.out.println("Enter the product EXPD(yyyy-mm-dd) :");
			expd = sc.next();

			sc.close();

			// Parsing string to LocalDate(Convert --> String to LocalDate)
			lexpd = LocalDate.parse(expd);
			lmfd = LocalDate.parse(mfd);

			try {
				String insertProductQuery = "insert into product(id,name,color,price,mfd,expd) values(?,?,?,?,?,?)";
				PreparedStatement ps = conn.prepareStatement(insertProductQuery);

				//call the setter method
				ps.setInt(1, id);
				ps.setString(2, name);
				ps.setString(3, color);
				ps.setDouble(4, price);
				ps.setObject(5, lmfd);
				ps.setObject(6, lexpd);

				ps.executeUpdate();

				System.out.println("---------------------------------");
				System.out.println(">> Data for id " + id + " is Inserted SuccessFully. <<");

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(" !! Unable to insert the Data. !!\n Try Again ");
			} finally {
				conn.close();
				System.out.println(">> And Connection closed Successfully. <<");
			}
			
		} else {
			System.out.println(" >> Connection Not Created. Something went wrong !! <<\n Check connection code again. ");
		}

	}
}
