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
	
	/**
	 * Returns 1, -1 or 0 if the price of this booking is greater, lesser or equal respectively
	 * @param b
	 * @return
	 */
	public int comparePrice(Booking b) {
		if(this.getPrice() > b.getPrice()) {
			return 1;
		}
		
		if(this.getPrice() < b.getPrice()) {
			return -1;
		}
		
		return 0;
	}
	
	/**
	 * Returns 1, -1 or 0 if the id of this booking is greater, lesser or equal respectively
	 * @param b
	 * @return
	 */
	public int compareIdAlphabetically(Booking b) {
		return this.getId().compareTo(b.getId());
	}
	
	@Override
	public int hashCode() {
		return this.getId().hashCode();
	}
}
