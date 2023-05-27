package init;

import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import flight.Flight;
import flight.GroupBooking;
import flight.SeatType;
import flight.SingleBooking;
import flightmanager.FlightManager;

public class Program {

	public static void main(String[] args) {
		FlightManager fm = new FlightManager();

		// TEST: read flights from file
//		try {
//			fm.readFlightsFromFile("./resources/flights.csv");
//		} catch (FileNotFoundException e) {
//			System.out.println("Could not find flights file!");
//		}

		// TEST: reading bookings from file
//		try {
//			fm.readBookingsFromFile("./resources/bookings.csv");
//		} catch (FileNotFoundException e) {
//			System.out.println("Could not find bookings file!");
//		}

		Scanner sc = new Scanner(System.in);

		// TEST: read booking from user input
//		System.out.println("Enter new booking: 'S,\"abcdef\",40,CORRIDOR'");
//		String newBooking = sc.nextLine();
//		fm.addBooking(newBooking);

		// TEST: read flight from user input
//		System.out.println("Enter new flight: 'BGY,FCO,01/10/2000,09:30,120'");
//		String newFlight = sc.nextLine();
//		fm.addFlight(newFlight);
//		
//		sc.close();

		// TEST: create flights from String, add flights to flight manager, add bookings
		// to flight, move booking from flight to another flight
		
		fm.addFlight("BGY,FCO,01/10/2000,09:30,120");
		fm.addFlight("FCO,BGY,15/10/2000,12:30,120");

		SingleBooking b1 = new SingleBooking("abc", 15, SeatType.CORRIDOR);
		SingleBooking b2 = new SingleBooking("def", 20, SeatType.NEUTRAL);
		SingleBooking b3 = new SingleBooking("ghi", 30, SeatType.WINDOW);
		SingleBooking b4 = new SingleBooking("jkl", 35, SeatType.NEUTRAL);
		SingleBooking b5 = new SingleBooking("mno", 70, SeatType.CORRIDOR);
		SingleBooking b6 = new SingleBooking("pqr", 85, SeatType.WINDOW);
		GroupBooking b7 = new GroupBooking("AAABBB", 10);

		try {
			fm.addBookingToFlight(b1, 0);
			fm.addBookingToFlight(b2, 0);
			fm.addBookingToFlight(b3, 0);
			fm.addBookingToFlight(b4, 0);
			fm.addBookingToFlight(b5, 0);
			fm.addBookingToFlight(b6, 0);
			fm.addBookingToFlight(b7, 0);

			fm.moveBooking(b2, 0, 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		fm.removeBookingFromFlight(b1, 0);
		
		// TEST: adding too many bookings to a flight causes an exception
		
		fm.addFlight("HNS,BGY,15/02/2000,04:15,90"); // Flight with id 2
		
		try {			
			for (int i = 0; i < 100; i++) {
				fm.addBookingToFlight(new SingleBooking("12a", 15, SeatType.CORRIDOR), 2);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage() + " (this is supposed to appear!)");
		}

		// TEST: adding person to group booking
		fm.addPersonToGroupBooking("Davide", "AAABBB");
		fm.addPersonToGroupBooking("Luca", "AAABBB");
		fm.addPersonToGroupBooking("Francesco", "AAABBB");
		fm.printGroupBookingSsns("AAABBB");
		
		fm.deepPrintFlights();
	}
}
