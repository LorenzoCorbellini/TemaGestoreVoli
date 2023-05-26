package flightmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

import exceptions.FlightCapacityExceededException;
import flight.Booking;
import flight.Flight;
import flight.GroupBooking;
import flight.SeatType;
import flight.SingleBooking;

public class FlightManager {
	private Set<Flight> flights = new HashSet<>();
	private Set<Booking> bookings = new HashSet<>();

	public FlightManager() {

	}

	/**
	 * Creates a new booking and adds it to the bookings<br>
	 * Single booking: 'S,ssn,price,seatPreference' | Example:
	 * 'S,"abcdef",40,CORRIDOR' <br>
	 * Group booking: 'G,pricePerPerson' | Example: 'G,20'
	 * 
	 * @param bookingInfo
	 */
	public void addBooking(String bookingInfo) {
		if (bookingInfo.charAt(0) == "S".toCharArray()[0]) {
			addBooking(parseSingleBooking(bookingInfo.split(",")));
		} else if (bookingInfo.charAt(0) == "G".toCharArray()[0]) {
			addBooking(parseGroupBooking(bookingInfo.split(",")));
		} else {
			throw new IllegalArgumentException("String booking not formatted the correct way!");
		}
	}

	/**
	 * Creates a new flight from the format
	 * "departure,destination,gg/mm/yyyy,hh:mm,maxSeats" Example:
	 * "BGY,FCO,01/10/2000,09:30,120"
	 * 
	 * @param flightInfo
	 */
	public void addFlight(String flightInfo) {
		String[] fields = flightInfo.split(",");

		String dep = fields[0];
		String dest = fields[1];
		Date date = new Date(fields[3] + " " + fields[2]);
		int maxSeats = Integer.parseInt(fields[4]);

		addFlight(new Flight(date, dep, dest, maxSeats));
	}

	/**
	 * Returns the flight with the specified id
	 * 
	 * @param id
	 * @return
	 */
	private Flight getFlight(int id) {
		for (Flight flight : flights) {
			if (flight.getId() == id) {
				return flight;
			}
		}
		throw new NoSuchElementException("Flight with id [" + id + "] does not exist!");
	}

	/**
	 * Returns the booking with the specified id
	 * 
	 * @param id
	 * @return
	 */
	private Booking getBooking(String id) {
		for (Booking booking : bookings) {
			if (booking.getId().equals(id)) {
				return booking;
			}
		}
		throw new NoSuchElementException("Booking with id [" + id + "] does not exist!");
	}

	public void addFlight(Flight f) {
		flights.add(f);
	}

	public void addBooking(Booking b) {
		bookings.add(b);
	}

	public void addBookingToFlight(Booking b, int flightId) throws FlightCapacityExceededException {
		Flight f = getFlight(flightId);

		if (f.isFull()) {
			throw new FlightCapacityExceededException("Flight [" + flightId + "] has run out of seats!");
		}

		f.addBooking(b);
	}

	public void removeBookingFromFlight(Booking b, int flightId) {
		Flight f = getFlight(flightId);

		f.removeBooking(b);
	}

	public void readFlightsFromFile(String path) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(path));

		while (sc.hasNext()) {
			String[] fields = sc.nextLine().split(",");

			if (fields[0].charAt(0) == "#".toCharArray()[0]) { // Skip comments (defined with a hashtag)
				continue;
			}

			int id = Integer.parseInt(fields[0]);
			Date date = new Date(fields[1]);
			String departure = fields[2];
			String destination = fields[3];
			int maxSeats = Integer.parseInt(fields[4]);

			Flight fl = null;
			if (id == 0) {
				fl = new Flight(date, departure, destination, maxSeats);
			} else {
				fl = new Flight(id, date, departure, destination, maxSeats);
			}

			flights.add(fl);
		}
	}

	public void readBookingsFromFile(String path) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(path));

		while (sc.hasNext()) {
			String[] fields = sc.nextLine().split(",");

			if (fields[0].charAt(0) == "#".toCharArray()[0]) { // Skip comments (defined with a hashtag)
				continue;
			}

			if (fields[0].charAt(0) == "S".toCharArray()[0]) { // Read single booking
				bookings.add(parseSingleBooking(fields));
				continue;
			}

			if (fields[0].charAt(0) == "G".toCharArray()[0]) { // Read group booking
				bookings.add(parseGroupBooking(fields));
				continue;
			}

			throw new IllegalArgumentException("File not formatted the correct way!");
		}
	}

	private SingleBooking parseSingleBooking(String[] fields) {
		String ssn = fields[1];
		int price = Integer.parseInt(fields[2]);
		SeatType seat = SeatType.valueOf(fields[3]);

		return new SingleBooking(ssn, price, seat);
	}

	private GroupBooking parseGroupBooking(String[] fields) {
		int price = Integer.parseInt(fields[1]);

		return new GroupBooking(price);
	}

	public void printFlights() {
		for (Flight flight : flights) {
			System.out.println(flight.toString());
		}
	}

	public void printBookings() {
		for (Booking booking : bookings) {
			System.out.println(booking.toString());
		}
	}

	public void deepPrintFlights() {
		for (Flight flight : flights) {
			System.out.println(flight.toString() + ", date&time: " + flight.getDate().toLocaleString() + ", isFull: "
					+ flight.isFull() + ", seatsTaken: " + flight.seatsTaken() + ", single bookings: "
					+ flight.numberOfSingleBookings() + ", group bookings: " + flight.numberOfGroupBookings());
		}
	}
}
