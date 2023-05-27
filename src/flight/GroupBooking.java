package flight;

import java.util.HashSet;
import java.util.Set;

public class GroupBooking extends Booking {

	private Set<String> ssns = new HashSet<>();
	private final float pricePerPerson;
	private float priceTotal;
	private float discount;

	public GroupBooking(int pricePerPerson) {
		this(null, pricePerPerson);
	}
	
	public GroupBooking(String id, int pricePerPerson) {
		super(id);
		this.pricePerPerson = pricePerPerson;
		this.priceTotal = 0;
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
	
	private float computePrice() {		
		return ssns.size() * pricePerPerson * getDiscount();
	}

	public float getPrice() {
		this.priceTotal = computePrice();
		return this.priceTotal;
	}
	
	/**
	 * Returns the number of seats taken by this booking
	 * @return
	 */
	public int getNumberOfOccupiedSeats() {
		return ssns.size();
	}
	
	@Override
	public String toString() {
		return "Group booking:  [" +this.getId()+"], price: "+this.pricePerPerson;
	}
	
	public String[] getSsns() {
		return ssns.toArray(new String[0]);
	}
	
	public float getPricePerPerson() {
		return this.pricePerPerson;
	}
	
	public float getDiscount() {
		this.discount = 0.10f;

		if (ssns.size() > 10) {
			this.discount = 0.20f;
		}
		
		return this.discount;
	}
}
