package flight;

import java.util.HashSet;
import java.util.Set;

import flightmanager.BookingIdManager;

public class GroupBooking extends Booking {

	private Set<String> ssns = new HashSet<>();
	private final float pricePerPerson;
	private float priceTotal;

	public GroupBooking(BookingIdManager idManager, int pricePerPerson) {
		super(idManager);
		this.pricePerPerson = pricePerPerson;
		this.priceTotal = computeCost();
	}

	public void addPerson(String ssn) {
		if (!ssns.contains(ssn)) {
			ssns.add(ssn);
		} else {
			throw new IllegalArgumentException("The person " + ssn + " already exists in this booking!");
		}
	}

	public void removePerson(String ssn) {
		if (ssns.contains(ssn)) {
			ssns.remove(ssn);
		} else {
			throw new IllegalArgumentException("This booking does not contain person " + ssn);
		}
	}

	public boolean containsPerson(String ssn) {
		return ssns.contains(ssn);
	}
	
	private float computeCost() {
		float discount = 0.10f;

		if (ssns.size() > 10) {
			discount = 0.20f;
		}
		return ssns.size() * pricePerPerson * discount;
	}

	public float updateTotalPrice() {
		this.priceTotal = computeCost();
		return this.priceTotal;
	}
	
	public float getPriceTotal() {
		return priceTotal;
	}
}
