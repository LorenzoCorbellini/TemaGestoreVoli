package flight;

import flightmanager.BookingIdManager;

public class SingleBooking extends Booking {

	private final String ssn;
	private final float price;
	private final SeatType seat;
	
	public SingleBooking(String ssn, int price, SeatType seat) {
		super();
		this.ssn = ssn;
		this.price = price;
		this.seat = seat;
	}

	/**
	 * @return the ssn
	 */
	public String getSsn() {
		return ssn;
	}

	/**
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * @return the seat
	 */
	public SeatType getSeat() {
		return seat;
	}
	
	@Override
	public String toString() {
		return "Group booking: [" +this.getId()+"], price: "+this.price+ ", seat: "+this.seat;
	}
}
