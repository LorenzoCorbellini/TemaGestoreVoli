package flightmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

import comparators.BookingAlphabetComparator;
import comparators.BookingPriceComparator;
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
	public Flight getFlight(int id) {
		for (Flight flight : flights) {
			if (flight.getId() == id) {
				return flight;
			}
		}
		throw new NoSuchElementException("Flight with id [" + id + "] does not exist!");
	}

	/**
	 * Returns the flight that contains the specified booking
	 * 
	 * @param id
	 * @return
	 */
	public Flight getFlight(Booking b) {
		for (Flight flight : flights) {
			if (flight.containsBooking(b)) {
				return flight;
			}
		}
		return null;
	}

	/**
	 * Returns the flight with the specified departure if it's not full
	 * 
	 * @param departure with format "BGY" (ignores case)
	 * @return
	 */
	public Flight getFlightFromDeparture(String departure) {
		for (Flight flight : flights) {
			if (!flight.isFull() && flight.getDeparture().equalsIgnoreCase(departure)) {
				return flight;
			}
		}
		throw new NoSuchElementException("Flight with departure <" + departure + "> that isn't full does not exist!");
	}

	/**
	 * Returns the flight with the specified destination if it's not full
	 * 
	 * @param destination with format "BGY" (ignores case)
	 * @return
	 */
	public Flight getFlightFromDestination(String destination) {
		for (Flight flight : flights) {
			if (!flight.isFull() && flight.getDestination().equalsIgnoreCase(destination)) {
				return flight;
			}
		}
		throw new NoSuchElementException(
				"Flight with destination <" + destination + "> that isn't full does not exist!");
	}

	/**
	 * Returns an array of bookings sorted by price in ascending order
	 * 
	 * @return
	 */
	public Booking[] getBookingsSortedByPrice() {
		Booking[] bk = bookings.toArray(new Booking[0]);
		Arrays.sort(bk, new BookingPriceComparator());
		return bk;
	}

	/**
	 * Returns an array of bookings sorted alphabetically from A to Z
	 * 
	 * @return
	 */
	public Booking[] getBookingsSortedByAlphabet() {
		Booking[] bk = bookings.toArray(new Booking[0]);
		Arrays.sort(bk, new BookingAlphabetComparator());
		return bk;
	}

	/**
	 * Returns the booking with the specified id. Returns null if no booking is
	 * found.
	 * 
	 * @param id
	 * @return
	 */
	public Booking getBooking(String id) {
		for (Booking booking : bookings) {
			if (booking.getId().equals(id)) {
				return booking;
			}
		}
		return null;
	}

	public void addFlight(Flight f) {
		flights.add(f);
	}

	public void addBooking(Booking b) {
		bookings.add(b);
	}

	public void addBookingToFlight(Booking b, int flightId) throws FlightCapacityExceededException {
		Flight f = getFlight(flightId);
		
		if (!bookings.contains(b)) {
			addBooking(b);
		} else {			
			// Check if another flight already has this booking
			Flight flightWithBooking = getFlight(b);
			if(flightWithBooking != null && !flightWithBooking.equals(f)) {
				throw new IllegalArgumentException("Flight [" +flightWithBooking.getId()+"] already has this booking!");
			}
		}


		if (f.isFull()) {
			throw new FlightCapacityExceededException("Flight [" + flightId + "] has run out of seats!");
		}

		f.addBooking(b);
	}

	public void removeBookingFromFlight(Booking b, int flightId) {
		Flight f = getFlight(flightId);

		f.removeBooking(b);
	}

	public void addPersonToGroupBooking(String ssn, String bookingId) throws FlightCapacityExceededException {
		GroupBooking b = (GroupBooking) getBooking(bookingId);
		if (b == null) {
			throw new NoSuchElementException(
					"Could not add " + ssn + " to booking [" + bookingId + "]: booking with such id doesn't exist!");
		}

		// If there's a flight with this booking, we need to check whether the flight
		// has enough free seats
		Flight f = getFlight(b);
		
		if(f != null && f.isFull()) {
			throw new FlightCapacityExceededException("Flight ["+f.getId()+"] is full!");
		}

		b.addPerson(ssn);
	}

	/**
	 * Moves the specified booking from a flight to another. Throws exception if the
	 * destinatin flight is full.
	 * 
	 * @param b            Booking to move
	 * @param fromFlightId Flight to move the booking from
	 * @param toFlightId   Flight to move the booking to
	 * @throws FlightCapacityExceededException if the destination flight is full
	 */
	public void moveBooking(Booking b, int fromFlightId, int toFlightId) throws FlightCapacityExceededException {
		Flight from = getFlight(toFlightId);
		Flight to = getFlight(toFlightId);

		if (!bookings.contains(b)) {
			addBooking(b);
		}

		if (!to.canAccomodateBooking(b)) {
			throw new FlightCapacityExceededException("Flight [" + to.getId() + "] is full!");
		}

		from.removeBooking(b);
		to.addBooking(b);
	}

	public void moveBooking(String bookingId, int fromFlightId, int toFlightId) throws FlightCapacityExceededException {
		moveBooking(getBooking(bookingId), fromFlightId, toFlightId);
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
	
	public void printFlight(int id) {
		System.out.println(getFlight(2).deepToString());
	}

	public void printBookings() {
		for (Booking booking : bookings) {
			System.out.println(booking.toString());
		}
	}

	/**
	 * Prints each flight with its revenue and bookings
	 */
	public void deepPrintFlights() {
		for (Flight flight : flights) {
			System.out.println(flight.deepToString());
		}
	}

	/**
	 * Prints each flight with its revenue and bookings, bookings are sorted
	 */
	public void deepPrintFlightsSorted(Comparator<Booking> comparator) {
		for (Flight flight : flights) {
			System.out.println(flight.deepToString(comparator));
		}
	}

	public void printGroupBookingSsns(String id) {
		GroupBooking b = (GroupBooking) getBooking(id);
		System.out.println(Arrays.toString(b.getSsns()));
	}
}
