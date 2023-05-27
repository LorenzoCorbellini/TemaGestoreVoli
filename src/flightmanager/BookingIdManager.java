package flightmanager;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BookingIdManager {
	private static BookingIdManager instance = null;
	private final int idLength = 6;
	private Set<String> unavailableIds = new HashSet<>();
	private Character[] idChars = new Character[] { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73,
			74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90 };

	private BookingIdManager() { }

	public static BookingIdManager getInstance() {
		if (instance == null) {
			instance = new BookingIdManager();
		}

		return instance;
	}

	public void makeIdUnavailable(String id) {
		if (isIdAvailable(id)) {
			unavailableIds.add(id);
		} else {
			throw new IllegalArgumentException("id " + id + " is unavailable!");
		}
	}

	public boolean isIdAvailable(String id) {
		return !this.unavailableIds.contains(id);
	}

	public String getNewBookingId() {
		String id = null;

		do {
			id = generateRandomId();
		} while (!isIdAvailable(id));
		
		makeIdUnavailable(id);
		
		return id;
	}
	
	private String generateRandomId() {
		String newId = "";
		Random rd = new Random();
		int randomIndex = rd.nextInt(idChars.length);
		
		for (int i = 0; i < idLength; i++) {
			newId = newId + String.valueOf(idChars[randomIndex]);
			randomIndex = rd.nextInt(idChars.length);
		}
		
		return newId;
	}
}
