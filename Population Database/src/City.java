import java.text.DecimalFormat;

public class City {

	String name;
	Double population;
	static double totalpop = 0;
	static int totalCities = 0;
	
	public City() {
		
		this.name = "";
		this.population = 0.0;
		totalCities +=1;
	}
	
	public City(String name, double population) {
		this.name = name;
		this.population = population;
		totalpop += this.population;
		totalCities +=1;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPopulation() {	
		return population;
	}

	public void setPopulation(Double population) {
		this.population = population;
	}
	
	public String getFormattedPop() {
		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		String formattednum = decimalFormat.format(this.population);
		return formattednum;
		
	}
		
	

	public City(String name, Double population) {
		
		this.name = name;
		this.population = population;
		
	}
	
	public static String getAverage() {
		double average = totalpop / totalCities;
		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		String formattednum = decimalFormat.format(average);
		
		return formattednum;
	}
}
