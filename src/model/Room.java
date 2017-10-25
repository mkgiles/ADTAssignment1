package model;

/**
 * The Class Room.
 *
 * @author Conor James Giles
 */
public class Room implements CSV {

	/** The floor. */
	private int floor;

	/** The ensuite check. */
	private Boolean ensuite;

	/** The beds. */
	private ItemList<Bed> beds;

	/**
	 * Instantiates a new room.
	 *
	 * @param floor
	 *            the floor
	 * @param ensuite
	 *            the ensuite
	 */
	public Room(int floor, Boolean ensuite) {
		this.floor = floor;
		this.ensuite = ensuite;
	}

	/**
	 * Gets the floor.
	 *
	 * @return the floor
	 */
	public int getFloor() {
		return floor;
	}

	/**
	 * Sets the floor.
	 *
	 * @param floor
	 *            the floor to set
	 */
	public void setFloor(int floor) {
		this.floor = floor;
	}

	/**
	 * Checks for ensuite.
	 *
	 * @return the ensuite
	 */
	public Boolean hasEnsuite() {
		return ensuite;
	}

	/**
	 * Sets the ensuite check.
	 *
	 * @param ensuite
	 *            the value to set
	 */
	public void setEnsuite(Boolean ensuite) {
		this.ensuite = ensuite;
	}

	/**
	 * Gets the beds.
	 *
	 * @return the beds
	 */
	public ItemList<Bed> getBeds() {
		return beds;
	}

	/**
	 * Adds a bed.
	 *
	 * @param bed
	 *            the bed
	 */
	public void addBed(Bed bed) {
		if (beds == null)
			beds = new ItemList<Bed>(bed);
		else
			beds.append(bed);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.CSV#toCSV()
	 */
	@Override
	public String toCSV() {
		String str = "";
		str += ensuite ? "RoomES, " : "RoomNES, ";
		str += floor;
		if (beds != null) {
			str += ", ";
			str += beds.toCSV(", ");
		}
		return str;
	}
	@Override
	public String toString() {
		String str = "";
		str += "Room with" + (ensuite ? " ": "out ") + "ensuite, ";
		if(beds != null)
			str += "and " + beds.length() + "beds";
		return str;
	}
}
