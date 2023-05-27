package comparators;

import java.util.Comparator;

import flight.Booking;

public class BookingPriceComparator implements Comparator<Booking> {

	@Override
	public int compare(Booking o1, Booking o2) {
		return o1.comparePrice(o2);
	}

}
