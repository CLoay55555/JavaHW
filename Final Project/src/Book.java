// Course:      CIS 228 Java Programming II
// File:        Final/BookStore.java
// Written by:  Christian Loayza
// Written on:  16 May 2023
public class Book {
	private String name;
	private double price;
	
	public Book() {
		
		this.name = "";
		this.price = 0;
	}
	
	public Book(String name, double price) {
		
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public String toString() {
		return this.name;
	}
}
