package model;
/**
 * @author Conor James Giles
 *
 */
public class Property {
	private String address;
	private int distance;
	private int spaces;
	private ItemList<Room> rooms;
	/**
	 * @param address
	 * @param distance
	 * @param spaces
	 * @param rooms
	 */
	public Property(String address, int distance, int spaces) {
		this.address = address;
		this.distance = distance;
		this.spaces = spaces;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the distance
	 */
	public int getDistance() {
		return distance;
	}
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}
	/**
	 * @return the spaces
	 */
	public int getSpaces() {
		return spaces;
	}
	/**
	 * @param spaces the spaces to set
	 */
	public void setSpaces(int spaces) {
		this.spaces = spaces;
	}
	/**
	 * @param rooms the rooms to set
	 */
	public void addRoom(Room room) {
		rooms.append(room);				
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "$(member.value}, $(member.value}, $(member.value}";
	}
}
