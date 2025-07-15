package com.jspiders.jdbc_preparedStatement_crud_operation.controller;

import com.jspiders.jdbc_preparedStatement_crud_operation.connection.ProductConnection;
import com.jspiders.jdbc_preparedStatement_crud_operation.entity.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;

public class InsertProductController {
	// USe prepared statement when passing the values through run time
	public static void main(String[] args) throws SQLException {

		Connection conn = ProductConnection.getProductConnection();
		System.out.println(conn);
		Scanner sc = new Scanner(System.in);

		while (conn != null) {
			
			System.out.println(">> Connection Created Successfully <<");
			
			boolean b = true;

			System.out.println();
			while (b) {
				System.out.println(
						"Select your choice for- \n1.INSERT \n2.DISPLAY ALL PRODUCT \n3.UPDATE BY ID\n4.DELETE BY ID \n5.DISPLAY PRODUCT BY ID");
				System.out.println("Enter your choice :");
				int choice = sc.nextInt();

				switch (choice) {
				case 1: {
					System.out.println("\n===Enter the Product Details===");
					System.out.println("Enter the product id :");
					Integer id = sc.nextInt();
					System.out.println("Enter the product name :");
					String name = sc.next();
					System.out.println("Enter the product color :");
					String color = sc.next();
					System.out.println("Enter the product price :");
					Double price = sc.nextDouble();
					System.out.println("Enter the product MFD(yyyy-mm-dd) :");
					String mfd = sc.next();
					System.out.println("Enter the product EXPD(yyyy-mm-dd) :");
					String expd = sc.next();

					// Parsing string to LocalDate(Convert --> String to LocalDate)
					LocalDate lexpd = LocalDate.parse(expd);
					LocalDate lmfd = LocalDate.parse(mfd);

					try {
						String insertProductQuery = "insert into product(id,name,color,price,mfd,expd) values(?,?,?,?,?,?)";
						PreparedStatement ps = conn.prepareStatement(insertProductQuery);

						// call the setter method
						ps.setInt(1, id);
						ps.setString(2, name);
						ps.setString(3, color);
						ps.setDouble(4, price);
						ps.setObject(5, lmfd);
						ps.setObject(6, lexpd);

						int a = ps.executeUpdate();

						System.out.println("---------------------------------");
						String msg = a != 0 ? "Data Inserted/Stored Successfully." : "Something Went Wrong...";
						System.out.println(msg);

						System.out.println(">> Data for id " + id + " is Inserted SuccessFully. <<");

					} catch (SQLException e) {
						e.printStackTrace();
						System.out.println(" !! Unable to insert the Data. !!\n Try Again ");
					}

					System.out.println("===============================================================");
					System.out.println("Press Y/y for continue else press anyother letter for stop :");
					char ask = sc.next().charAt(0);
					if (ask == 'y' || ask == 'Y') {
						b = true;
					} else {
						b = false;
					}

				}
					break;
				case 2: {

					String displayProductQuery = "Select*from product";
					try {
						Statement stm = conn.createStatement();
						ResultSet set = stm.executeQuery(displayProductQuery);

						while (set.next()) {

							int id = set.getInt("id");
							String name = set.getString("name");
							String color = set.getString("color");
							Double price = set.getDouble("price");

							LocalDate mfd = set.getDate("mfd").toLocalDate();
							LocalDate expd = set.getDate("expd").toLocalDate();

							Product pd = new Product();
							pd.setId(id);
							pd.setName(name);
							pd.setColor(color);
							pd.setPrice(price);
							pd.setMfd(mfd);
							pd.setExpd(expd);

							System.out.println(pd);

						}

					} catch (Exception e) {
						e.printStackTrace();
					}

					System.out.println("===============================================================");
					System.out.println("Press Y/y for continue else press anyother letter for stop :");
					char ask = sc.next().charAt(0);
					if (ask == 'y' || ask == 'Y') {
						b = true;
					} else {
						b = false;
					}
				}
					break;

				case 3: {
					System.out.print("Enter the Product ID to update: ");
					int updateId = sc.nextInt();

					// Check if product exists first (optional but recommended)
					String checkQuery = "SELECT * FROM product WHERE id = ?";
					PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
					checkStmt.setInt(1, updateId);
					ResultSet rs = checkStmt.executeQuery();

					if (rs.next()) {
						// Prompt for new data
						System.out.println("\n=== Enter New Details for Product ID " + updateId + " ===");
						System.out.print("Enter the new name: ");
						String newName = sc.next();
						System.out.print("Enter the new color: ");
						String newColor = sc.next();
						System.out.print("Enter the new price: ");
						Double newPrice = sc.nextDouble();
						System.out.print("Enter the new MFD (yyyy-mm-dd): ");
						String newMfd = sc.next();
						System.out.print("Enter the new EXPD (yyyy-mm-dd): ");
						String newExpd = sc.next();

						LocalDate newMfdDate = LocalDate.parse(newMfd);
						LocalDate newExpdDate = LocalDate.parse(newExpd);

						// Perform the update
						String updateQuery = "UPDATE product SET name=?, color=?, price=?, mfd=?, expd=? WHERE id=?";
						PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
						updateStmt.setString(1, newName);
						updateStmt.setString(2, newColor);
						updateStmt.setDouble(3, newPrice);
						updateStmt.setObject(4, newMfdDate);
						updateStmt.setObject(5, newExpdDate);
						updateStmt.setInt(6, updateId);

						int rowsUpdated = updateStmt.executeUpdate();
						if (rowsUpdated > 0) {
							System.out.println(">> Product ID " + updateId + " updated successfully.");
						} else {
							System.out.println("!! Failed to update product. Try again.");
						}
					} else {
						System.out.println("!! No product found with ID " + updateId);
					}

					System.out.println("===============================================================");
					System.out.println("Press Y/y for continue else press anyother letter for stop :");
					char ask = sc.next().charAt(0);
					if (ask == 'y' || ask == 'Y') {
						b = true;
					} else {
						b = false;
					}
				}
					break;

				case 4: {
					System.out.println("Enter the Product ID to delete:");
					int deleteId = sc.nextInt();

					String deleteQuery = "DELETE FROM product WHERE id = ?";
					try {
						PreparedStatement ps = conn.prepareStatement(deleteQuery);
						ps.setInt(1, deleteId); // set the ID into the query

						int result = ps.executeUpdate(); // run the delete query

						if (result != 0) {
							System.out.println(">> Product with ID " + deleteId + " deleted successfully. <<");
						} else {
							System.out.println("!! No product found with ID " + deleteId + ". Nothing was deleted.");
						}

					} catch (SQLException e) {
						e.printStackTrace();
						System.out.println("!! Something went wrong while deleting the product. Try again.");
					}

					System.out.println("===============================================================");
					System.out.println("Press Y/y for continue else press anyother letter for stop :");
					char ask = sc.next().charAt(0);
					if (ask == 'y' || ask == 'Y') {
						b = true;
					} else {
						b = false;
					}
				}
					break;
				case 5: {
					System.out.print("Enter the Product ID to search: ");
					int searchId = sc.nextInt();

					// Query to fetch product details
					String searchQuery = "SELECT * FROM product WHERE id = ?";
					PreparedStatement searchStmt = conn.prepareStatement(searchQuery);
					searchStmt.setInt(1, searchId);
					ResultSet rs = searchStmt.executeQuery();

					if (rs.next()) {
						System.out.println("\n=== Product Details ===");
						System.out.println("ID     : " + rs.getInt("id"));
						System.out.println("Name   : " + rs.getString("name"));
						System.out.println("Color  : " + rs.getString("color"));
						System.out.println("Price  : " + rs.getDouble("price"));
						System.out.println("MFD    : " + rs.getDate("mfd"));
						System.out.println("EXPD   : " + rs.getDate("expd"));
					} else {
						System.out.println("!! No product found with ID " + searchId);
					}

					System.out.println("===============================================================");
					System.out.println("Press Y/y to continue or any other key to stop:");
					char ask = sc.next().charAt(0);
					if (ask == 'y' || ask == 'Y') {
						b = true;
					} else {
						b = false;
					}
				}
					break;

				default:
					sc.close();
					System.out.println("!! Insert the correct choice !!");
				}
			}
		}
	}

}
