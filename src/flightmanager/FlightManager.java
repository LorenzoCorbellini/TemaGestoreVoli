package flightmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import flight.Booking;
import flight.Flight;
import flight.GroupBooking;
import flight.SingleBooking;

public class FlightManager {
	private Set<Flight> flights = new HashSet<>();
	private Set<SingleBooking> singleBookings = new HashSet<>();
	private Set<GroupBooking> groupBookings = new HashSet<>();
	
	public FlightManager() {

	}
	
	public void addFlight(Flight f) {
		
	}
	
	public void addBooking(Booking b) {
		
	}
	
	public void readFlightsFromFile(String path) throws FileNotFoundException {
		File f = new File(path);
		Scanner sc = new Scanner(f);
		
		while(sc.hasNext()) {
			String[] fields = sc.nextLine().split(",");
			
			if(fields[0].charAt(0) == "#".toCharArray()[0]) { // Skip comments (defined with a hashtag)
				continue;
			}
			
			int id = Integer.parseInt(fields[0]);
			Date date = new Date(fields[1]);
			String departure = fields[2];
			String destination = fields[3];
			int maxSeats = Integer.parseInt(fields[4]);
			
			Flight fl = null;
			if(id == 0) {
				fl = new Flight(date, departure, destination, maxSeats);
			} else {
				fl = new Flight(id, date, departure, destination, maxSeats);
			}
			
			flights.add(fl);
		}
	}
	
	public void readBookingsFromFile() throws FileNotFoundException {
		File f = new File("./sources/bookings.csv");
		Scanner sc = new Scanner(f);
		
		while(sc.hasNext()) {

			
			
		}
	}
	
	public void printFlights() {
		for (Flight flight : flights) {
			System.out.println(flight.toString());
		}
	}
}
