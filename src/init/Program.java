package init;

import java.io.FileNotFoundException;

import flightmanager.FlightManager;

public class Program {

	public static void main(String[] args) {
		FlightManager fm = new FlightManager();
		
		try {
			fm.readFlightsFromFile("./resources/flights.csv");
		} catch (FileNotFoundException e) {
			System.out.println("Could not find file!");
		}
		
		fm.printFlights();
	}

}
