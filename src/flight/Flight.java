package flight;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import flightmanager.FlightIdManager;

public class Flight {

	private final int id;
	private final Date date;
	private final String departure;
	private final String destination;
	private final int maxSeats;
	private List<Booking> bookings;

	public Flight(int id, Date date, String departure, String destination, int maxSeats) {
		this.date = date;
		this.departure = departure;
		this.destination = destination;
		this.maxSeats = maxSeats;
		this.bookings = new ArrayList<>();

		if (!isAirportCorrectFormat(departure)) {
			throw new IllegalArgumentException("Wrong depature format! " + departure);
		}

		if (!isAirportCorrectFormat(destination)) {
			throw new IllegalArgumentException("Wrong destination format! " + destination);
		}

		// Check if id is already taken: if that's the case we create a new unique id
		if (id == -1) {
			this.id = FlightIdManager.getInstance().getNewFlightId();
		} else if (FlightIdManager.getInstance().isIdAvailable(id)) {
			this.id = id;
			FlightIdManager.getInstance().makeIdUnavailable(id);
		} else {
			this.id = FlightIdManager.getInstance().getNewFlightId();
			throw new IllegalArgumentException("Id " + id + " is already taken! Flight from " + departure + " to "
					+ destination + " was created with id " + this.id);
		}
	}

	public Flight(Date date, String departure, String destination, int maxSeats) {
		this(-1, date, departure, destination, maxSeats);
	}

	// Check if the departure / destination is in the right format
	private boolean isAirportCorrectFormat(String a) {
		return a.length() == 3 && a.equals(a.toUpperCase()); // Regex to check if string is uppercase only
	}

	@Override
	public int hashCode() {
		return Integer.valueOf(id).hashCode();
	}

	@Override
	public String toString() {
		return "Flight: [" + id + "], " + departure + " -> " + destination + ", max seats: " + maxSeats+ ", date & time: " +date.toLocaleString();
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @return the departure
	 */
	public String getDeparture() {
		return departure;
	}

	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @return the maxSeats
	 */
	public int getMaxSeats() {
		return maxSeats;
	}
}
