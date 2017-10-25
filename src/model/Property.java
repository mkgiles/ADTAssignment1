package model;

/**
 * The Class Property.
 *
 * @author Conor James Giles
 */
public class Property implements CSV {

	/** The address. */
	private String address;

	/** The distance from the college. */
	private int distance;

	/** The number of parking spaces. */
	private int spaces;

	/** The rooms. */
	private ItemList<Room> rooms;

	/**
	 * Instantiates a new property.
	 *
	 * @param address
	 *            the address
	 * @param distance
	 *            the distance
	 * @param spaces
	 *            the spaces
	 */
	public Property(String address, int distance, int spaces) {
		this.address = address;
		this.distance = distance;
		this.spaces = spaces;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the distance.
	 *
	 * @return the distance
	 */
	public int getDistance() {
		return distance;
	}

	/**
	 * Sets the distance.
	 *
	 * @param distance
	 *            the distance to set
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}

	/**
	 * Gets the number of spaces.
	 *
	 * @return the number of spaces
	 */
	public int getSpaces() {
		return spaces;
	}

	/**
	 * Sets the number of spaces.
	 *
	 * @param spaces
	 *            the number of spaces to set
	 */
	public void setSpaces(int spaces) {
		this.spaces = spaces;
	}

	/**
	 * Adds a room.
	 *
	 * @param room
	 *            the room
	 */
	public void addRoom(Room room) {
		if (rooms == null)
			rooms = new ItemList<Room>(room);
		else
			rooms.append(room);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.CSV#toCSV()
	 */
	@Override
	public String toCSV() {
		String str = address + ", " + distance + ", " + spaces;
		if (rooms != null) {
			str += ", ";
			str += rooms.toCSV(", ");
		}
		return str;
	}

	/**
	 * List rooms.
	 *
	 * @param storey
	 *            the storey
	 * @return the item list
	 */
	public ItemList<Room> listRooms(int storey) {
		ItemList<Room> temp = null;
		ItemList<Room> head = rooms;
		while (head != null) {
			if (head.retrieve().getFloor() == storey) {
				if (temp == null) {
					temp = new ItemList<Room>(head.retrieve());
				} else
					temp.append(head.retrieve());
			}
			head = head.next();
		}
		return temp;
	}

	/**
	 * Removes a room.
	 *
	 * @param flr
	 *            the flr
	 * @param room
	 *            the room
	 */
	public void removeRoom(int flr, int room) {
		listRooms().remove(listRooms().getIndexOf(listRooms(flr).get(room)));
	}

	/**
	 * List rooms.
	 *
	 * @return the item list
	 */
	public ItemList<Room> listRooms() {
		return rooms;
	}
}
