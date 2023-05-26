package init;

import java.io.FileNotFoundException;
import java.util.Scanner;

import flightmanager.FlightManager;

public class Program {

	public static void main(String[] args) {
		FlightManager fm = new FlightManager();
		
		try {
			fm.readFlightsFromFile("./resources/flights.csv");
		} catch (FileNotFoundException e) {
			System.out.println("Could not find flights file!");
		}
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter new flight: 'BGY,FCO,01/10/2000,09:30,120'");
		String newFlight = sc.nextLine();
		fm.addFlight(newFlight);
		
		fm.printFlights();
		
		sc.close();
		
//		try {
//			fm.readBookingsFromFile("./resources/bookings.csv");
//		} catch (FileNotFoundException e) {
//			System.out.println("Could not find bookings file!");
//		}
//		
//		fm.printSingleBookings();
//		fm.printGroupBookings();
	}

}
