package flight;

import flightmanager.BookingIdManager;

public abstract class Booking {
	private final String id;

	public Booking() {
		this.id = BookingIdManager.getInstance().getNewBookingId();
	}

	public Booking(String id) {
		if (id == null) {
			this.id = BookingIdManager.getInstance().getNewBookingId();
		} else if (BookingIdManager.getInstance().isIdAvailable(id)) {
			this.id = id;
			BookingIdManager.getInstance().makeIdUnavailable(id);
		} else {
			this.id = BookingIdManager.getInstance().getNewBookingId();
		}
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the number of seats taken by this booking
	 * 
	 * @return
	 */
	public abstract int getNumberOfSeats();
	
	public abstract float getPrice();

	@Override
	public int hashCode() {
		return this.getId().hashCode();
	}
}
