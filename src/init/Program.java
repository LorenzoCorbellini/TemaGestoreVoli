package init;

import java.util.Scanner;

import comparators.BookingAlphabetComparator;
import exceptions.FlightCapacityExceededException;
import flight.GroupBooking;
import flight.SeatType;
import flight.SingleBooking;
import flightmanager.FlightManager;

public class Program {

	public static void main(String[] args) {
		FlightManager fm = new FlightManager();
		fm.addFlight("BGY,HNS,10/04/2023,10:00,3");		
		fm.addBooking(new GroupBooking("AAABBB", 10));
		
		try {
			fm.addBookingToFlight(fm.getBooking("AAABBB"), 0);
			fm.addPersonToGroupBooking("francesco1", "AAABBB");
			fm.addPersonToGroupBooking("francesco2", "AAABBB");
			fm.addPersonToGroupBooking("francesco3", "AAABBB");
			fm.addPersonToGroupBooking("francesco4", "AAABBB"); // Gives error!
		} catch (FlightCapacityExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fm.deepPrintFlights();
	}
}
