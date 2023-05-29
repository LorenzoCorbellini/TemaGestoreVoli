package init;

import comparators.BookingAlphabetComparator;
import comparators.BookingPriceComparator;
import exceptions.FlightCapacityExceededException;
import flight.GroupBooking;
import flightmanager.FlightManager;

public class Program {

	public static void main(String[] args) {
		FlightManager fm = new FlightManager();
		fm.addFlight("BGY,HNS,10/04/2023,10:00,3");		
		fm.addBooking(new GroupBooking("BBBCCC", 15));
		fm.addBooking(new GroupBooking("AAABBB", 10));
		fm.addBooking(new GroupBooking("CCCDDD", 5));
		
		try {
			fm.addBookingToFlight(fm.getBooking("AAABBB"), 0);
			fm.addBookingToFlight(fm.getBooking("BBBCCC"), 0);
			fm.addBookingToFlight(fm.getBooking("CCCDDD"), 0);
			
			fm.addPersonToGroupBooking("francesco1", "AAABBB");
			fm.addPersonToGroupBooking("francesco2", "BBBCCC");
			fm.addPersonToGroupBooking("francesco3", "CCCDDD");
//			fm.addPersonToGroupBooking("francesco4", "AAABBB"); // Gives error!
		} catch (FlightCapacityExceededException e) {
			e.printStackTrace();
		}
		
		
//		fm.deepPrintFlights();
		fm.deepPrintFlightsSorted(new BookingPriceComparator());
		System.out.println("----------------------\n");
		fm.deepPrintFlightsSorted(new BookingAlphabetComparator());
	}
}
