package com.jspiders.jdbc_preparedStatement_crud_operation.entity;

import java.time.LocalDate;

//Java Been / POJO Class
public class Product {

	private int id;
	private String name;
	private String color;
	private Double price;
	private LocalDate mfd;
	private LocalDate expd;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public LocalDate getMfd() {
		return mfd;
	}
	public void setMfd(LocalDate mfd) {
		this.mfd = mfd;
	}
	
	public LocalDate getExpd() {
		return expd;
	}
	public void setExpd(LocalDate expd) {
		this.expd = expd;
	}
	
	@Override
	public String toString() {
		
		return "Product [ ID = "+id+", Name = "+name+", Color = "+color+", Price = "+price+", MagnifiedDAte = "+mfd+", ExpirayDate = "+expd;
	}
}
