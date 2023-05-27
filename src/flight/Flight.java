package flight;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import flightmanager.FlightIdManager;

public class Flight {

	private final int id;
	private final Date date;
	private final String departure;
	private final String destination;
	private final int maxSeats;
	private Set<Booking> bookings;

	public Flight(int id, Date date, String departure, String destination, int maxSeats) {
		this.date = date;
		this.departure = departure;
		this.destination = destination;
		this.maxSeats = maxSeats;
		this.bookings = new HashSet<>();

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

	/**
	 * Returns whether all seats in this flight are taken
	 * 
	 * @return
	 */
	public boolean isFull() {
		return seatsTaken() >= maxSeats;
	}

	public int seatsTaken() {
		int unavailabelSeats = 0;

		for (Booking booking : bookings) {
			unavailabelSeats += booking.getNumberOfSeats();
		}

		return unavailabelSeats;
	}

	public void addBooking(Booking b) {
		bookings.add(b);
	}

	public void removeBooking(Booking b) {
		bookings.remove(b);
	}

	public int numberOfSingleBookings() {
		int singleBookings = 0;

		for (Booking booking : bookings) {
			if (booking instanceof SingleBooking) {
				singleBookings++;
			}
		}

		return singleBookings;
	}

	public int numberOfGroupBookings() {
		int groupBookings = 0;

		for (Booking booking : bookings) {
			if (booking instanceof GroupBooking) {
				groupBookings++;
			}
		}

		return groupBookings;
	}

	/**
	 * Returns the total revenue from this flight computed as the sum of the price
	 * of each booking.
	 * 
	 * @return
	 */
	public float getRevenue() {
		int revenue = 0;
		for (Booking booking : bookings) {
			revenue += booking.getPrice();
		}

		return revenue;
	}

	/**
	 * Returns a string representation of this flight complete with each booking
	 * @return
	 */
	public String deepToString() {
		StringBuilder sb = new StringBuilder();

		String flightInfo = this.toString() + ", date&time: " + this.getDate().toLocaleString() + ", isFull: "
				+ this.isFull() + ", seatsTaken: " + this.seatsTaken() + ", single bookings: "
				+ this.numberOfSingleBookings() + ", group bookings: " + this.numberOfGroupBookings();

		sb.append(flightInfo + "\n");

		for (Booking booking : bookings) {
			if (booking instanceof SingleBooking) {
				SingleBooking b = (SingleBooking) booking;
				sb.append("SingleBooking: [" + b.getId() + "]\n");
				sb.append(" SSN        > " + b.getSsn() + "\n");
				sb.append(" Price      > " + b.getPrice() + "\n");
				sb.append(" Seat       > " + b.getPrice() + "\n");
			} else if (booking instanceof GroupBooking) {
				GroupBooking b = (GroupBooking) booking;
				sb.append("GroupBooking: [" + b.getId() + "]\n");
				sb.append(" SSNs       > " + Arrays.toString(b.getSsns()) + "\n");
				sb.append(" Seats      > " + b.getNumberOfSeats() + "\n");				
				sb.append(" Discount   > " + (b.getDiscount()*100) + "%\n");
				sb.append(" Price      > " + b.getPricePerPerson() + "\n");
				sb.append(" TotalPrice > " + b.getPrice() + "\n");
			}
		}

		return sb.toString();
	}

	@Override
	public int hashCode() {
		return Integer.valueOf(id).hashCode();
	}

	@Override
	public String toString() {
		return "Flight: [" + id + "], " + departure + " -> " + destination + ", max seats: " + maxSeats;
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
