// Course:      CIS 228 Java Programming II
// File:        Final/BookStore.java
// Written by:  Christian Loayza
// Written on:  16 May 2023



import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;


public class Final extends Application{
	
	public static void main(String[] args) {
		
		launch(args);
		
	}

	@Override
	public void start(Stage primaryStage){
		// TODO Auto-generated method stub
		
		Button btnAddToCart = new Button("Add to Cart");
		Button btnRemoveFromCart = new Button("Remove from Cart");
		Button btnClearCart = new Button("Clear Cart");		
		ListView<Book> lvPickBook = new ListView<>(getBooks());
		lvPickBook.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		ListView<Book> lvShoppingCart = new ListView<>();
		lvShoppingCart.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		
		
		Label lblSubTotal = new Label("Subtotal: 0");
		Label lblTax = new Label("Tax: 0");
		Label lblTotal = new Label("Total: 0");
		
		
		Label lblPickBook = new Label("Pick a Book");
		Label lblShoppingCart = new Label("Shopping Cart");
		
		VBox vboxPickBook = new VBox(10,lblPickBook, lvPickBook);
		VBox vboxButtons = new VBox(10, btnAddToCart, btnRemoveFromCart,btnClearCart);
		vboxButtons.setAlignment(Pos.CENTER);
		
		VBox vboxCart = new VBox(10,lblShoppingCart, lvShoppingCart);
		HBox hbox1 = new HBox(10, vboxPickBook, vboxButtons,vboxCart);
		HBox hbox2 = new HBox(10, lblSubTotal, lblTax, lblTotal);
		hbox2.setAlignment(Pos.CENTER);
		VBox vbox = new VBox(10, hbox1,hbox2);
		vbox.setPadding(new Insets(10));
		Scene scene = new Scene(vbox);
		
		primaryStage.setTitle("Nick's Books");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		btnAddToCart.setOnAction(new EventHandler <ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				ObservableList<Book> selections = lvPickBook.getSelectionModel().getSelectedItems();
				for(Book selection : selections) {
					lvShoppingCart.getItems().add(selection);
				}
				lblSubTotal.setText("Subtotal: " + String.format("$%.2f",getSubTotal(lvShoppingCart.getItems())));
				lblTax.setText("Tax: " + String.format("$%.2f",getTax(lvShoppingCart.getItems())));
				lblTotal.setText("Total: " + String.format("$%.2f",getTotal(lvShoppingCart.getItems())));
			}
			
			
			
		});
		
		//Button click removes selected items from lvCart.
		btnRemoveFromCart.setOnAction(new EventHandler <ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ObservableList<Integer> selections = lvShoppingCart.getSelectionModel().getSelectedIndices();
				
				for(int selection : selections) {
					
					lvShoppingCart.getItems().remove(selection);
					
				}
				lblSubTotal.setText("Subtotal: " + String.format("$%.2f",getSubTotal(lvShoppingCart.getItems())));
				lblTax.setText("Tax: " + String.format("$%.2f",getTax(lvShoppingCart.getItems())));
				lblTotal.setText("Total: " + String.format("$%.2f",getTotal(lvShoppingCart.getItems())));
			}
			
			
		});
		//Clears all items from lvCart.
		btnClearCart.setOnAction(new EventHandler <ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				lvShoppingCart.getItems().clear();
				lblSubTotal.setText("Subtotal: " + String.format("$%.2f",getSubTotal(lvShoppingCart.getItems())));
				lblTax.setText("Tax: " + String.format("$%.2f",getTax(lvShoppingCart.getItems())));
				lblTotal.setText("Total: " + String.format("$%.2f",getTotal(lvShoppingCart.getItems())));
			}});
		
	}
	//Creates ovbservablelist from data on a text file.
	public ObservableList<Book> getBooks(){
		
		ObservableList<Book> books = FXCollections.observableArrayList();
		try {
		String fileName = "inventory.txt";
		File inventory = new File(fileName);
		Scanner stdFile = new Scanner(inventory);
		while(stdFile.hasNext()) {
			String book = stdFile.nextLine();
			String name = book.substring(0,book.indexOf(","));
			double price = Double.parseDouble(book.substring(book.indexOf(",")+1, book.length()));
			books.add(new Book(name, price));
		}
		stdFile.close();
		}catch(IOException e) {
			System.out.print(e.getMessage());
		}
		return books;
		
	}
	
	public double getSubTotal(ObservableList<Book> books) {
		
		double total = 0;
		
		for(Book book : books) {
			total += book.getPrice();
		}
		
		return total;
	}
	
	
	
	public double getTax(ObservableList<Book> books) {
		
		
		double dTotal = getSubTotal(books);
		
		double tax = dTotal * .0775;
		
		return tax;
		
	}
	
	public double getTotal(ObservableList<Book> books) {
		
		double tax = getTax(books);
		double subTotal = getSubTotal(books);
		
		return tax + subTotal;
		
	}
}
