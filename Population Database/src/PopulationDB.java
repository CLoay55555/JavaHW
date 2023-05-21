import java.sql.*;
import java.text.DecimalFormat;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

public class PopulationDB extends Application{

	public static void main(String[] args) {
		
		launch(args);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
	
		final String DB_URL = "jdbc:derby:CityDB";
		
	Connection conn = DriverManager.getConnection(DB_URL);
	Statement stmt = conn.createStatement();
	String sqlStatement = "Select *  from city";
	ResultSet result = stmt.executeQuery(sqlStatement);
		

	TableColumn<City, String> nameColumn = new TableColumn<>("City");
	nameColumn.setMinWidth(200);
	nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
		
	TableColumn<City, String> populationColumn = new TableColumn<>("Population");
	populationColumn.setMinWidth(200);
	populationColumn.setCellValueFactory(new PropertyValueFactory<>("FormattedPop"));
		
		
	TableView<City> table = new TableView<>();
	table.setPrefHeight(525);
	
	table.setItems(getCity(result));
	
	table.getColumns().addAll(nameColumn, populationColumn);
	
	Label lblText = new Label("");
	Button btnSortAcsending = new Button("Sort Ascending");
	Button btnSortDescending = new Button("Sort Descending");
	Button btnSortName = new Button("Sort Name");
	Button btnGetTotal = new Button("Get Total");
	Button btnGetAverage = new Button("Get Average");
	Button btnGetHighest = new Button("Get Highest");
	Button btnGetLowest = new Button("Get Lowest");
	Button btnExit = new Button("Exit");
	 
	HBox hbox1 = new HBox(10,btnSortAcsending, btnSortDescending, btnSortName, btnGetTotal);
	hbox1.setAlignment(Pos.CENTER);
	HBox hbox2 = new HBox(10,btnGetAverage, btnGetHighest, btnGetLowest,btnExit);
	hbox2.setAlignment(Pos.CENTER);
	HBox hbox3 = new HBox(lblText);
	hbox3.setAlignment(Pos.CENTER);
	VBox vbox = new VBox(10,table,hbox3,hbox1,hbox2);
	vbox.setPadding(new Insets(10));
	Scene scene = new Scene(vbox);
	 
	primaryStage.setTitle("Population Database");
	primaryStage.setScene(scene);
	 
	primaryStage.show();
		
	 
	 btnExit.setOnAction(new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent arg0) {
			// TODO Auto-generated method stub
			primaryStage.close();
		}
		 
	 });
		
	 btnGetAverage.setOnAction(new EventHandler<ActionEvent>(){
		 
		 public void handle(ActionEvent arg0) {
			  try {
			   lblText.setText("Average: " + getAverage(conn));
			  }catch(Exception e) {
				  System.out.println(e.getMessage());
			  }
		 }
	 });
	 
	 btnGetTotal.setOnAction(new EventHandler<ActionEvent>(){

		@Override
		public void handle(ActionEvent arg0) {
			// TODO Auto-generated method stub
			try {
				lblText.setText("Total: " + getTotal(conn));
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
			}
		
		 
	 });
	}
	
	public ObservableList<City> getCity(ResultSet result) throws Exception{
		
		ObservableList<City> citys = FXCollections.observableArrayList();
		
		while(result.next()) {
			
			citys.add(new City(result.getString(1), result.getDouble(2)));
			
		}
		
		return citys;
	}
	
	public String getAverage(Connection conn) throws Exception{
		Statement stmt = conn.createStatement();
		String sqlStatement = "Select AVG(Population) as average_value from City";
		ResultSet result = stmt.executeQuery(sqlStatement);
		double average = 0;
		if(result.next())
		average = result.getDouble("average_value");
		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		String formattednum = decimalFormat.format(average);
		return formattednum;
	}
	
	public String getTotal(Connection conn) throws Exception{
		Statement stmt = conn.createStatement();
		String sqlStatement = "Select SUM(Population) as total_value from city";
		ResultSet result = stmt.executeQuery(sqlStatement);
		double total = 0;
		if(result.next())
			total = result.getDouble("total_value");
		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		String formattednum = decimalFormat.format(total);
		return formattednum;
		
	}
}
