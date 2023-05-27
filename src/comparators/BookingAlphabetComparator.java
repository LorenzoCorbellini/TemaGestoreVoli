package comparators;

import java.util.Comparator;

import flight.Booking;

public class BookingAlphabetComparator implements Comparator<Booking> {

	@Override
	public int compare(Booking o1, Booking o2) {
		return o1.compareIdAlphabetically(o2);
	}

}
