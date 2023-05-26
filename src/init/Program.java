package init;

import java.io.FileNotFoundException;

import flightmanager.FlightManager;

public class Program {

	public static void main(String[] args) {
		FlightManager fm = new FlightManager();
		
//		try {
//			fm.readFlightsFromFile("./resources/flights.csv");
//		} catch (FileNotFoundException e) {
//			System.out.println("Could not find flights file!");
//		}
//		
//		fm.printFlights();

		try {
			fm.readBookingsFromFile("./resources/bookings.csv");
		} catch (FileNotFoundException e) {
			System.out.println("Could not find bookings file!");
		}
		
		fm.printSingleBookings();
//		fm.printGroupBookings();
	}

}
