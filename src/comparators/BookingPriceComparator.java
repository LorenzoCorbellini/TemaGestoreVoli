package comparators;

import java.util.Comparator;

import flight.Booking;

public class BookingPriceComparator implements Comparator<Booking> {

	@Override
	public int compare(Booking o1, Booking o2) {
		return Float.compare(o2.getPrice(), o1.getPrice());
	}

}
