package flightmanager;

import java.util.HashSet;
import java.util.Set;

// Singleton
public class FlightIdManager {
	private static FlightIdManager instance = null;
	private int lastId = 0;
	private Set<Integer> unavailableIds = new HashSet<>();
	
	private FlightIdManager() { }
	
	public static FlightIdManager getInstance() {
		if(instance == null) {
			instance = new FlightIdManager();
		}
		
		return instance;
	}
	
	public void makeIdUnavailable(int id) {
		if(isIdAvailable(id)) {
			unavailableIds.add(id);
		} else {
			throw new IllegalArgumentException("id " +id+ " is unavailable!");
		}
	}
	
	public boolean isIdAvailable(int id) {
		return !this.unavailableIds.contains(id);
	}
	
	/**
	 * Returns a new unique id and marks it as unavailable
	 * @return
	 */
	public int getNewFlightId() {
		while(!isIdAvailable(lastId)) {
			this.lastId = lastId + 1;
		}
		
		makeIdUnavailable(lastId);
		return this.lastId;
	}
}
