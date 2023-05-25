package flight;

import flightmanager.BookingIdManager;

public abstract class Booking {
	private final String id;
	
	public Booking(BookingIdManager idManager) {
		this.id = idManager.getNewBookingId();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	

	@Override
	public int hashCode() {
		return this.getId().hashCode();
	}
}
