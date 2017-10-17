package model;
/**
 * @author Conor James Giles
 *
 */
public class Property implements CSV{
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
		if(rooms == null)
			rooms = new ItemList<Room>(room);
		else
			rooms.append(room);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toCSV() {
		String str = address + ", " + distance + ", " + spaces;
		if(rooms!=null) {
			str += ", ";
			str += rooms.toCSV(", ");
		}
		return str;
	}
	public ItemList<Room> listRooms(int storey) {
		ItemList<Room> temp = null;
		ItemList<Room> head = rooms;
		while(head != null) {
			if(head.retrieve().getFloor() == storey) {
				if(temp == null) {
					temp = new ItemList<Room>(head.retrieve());
				}
				else
					temp.append(head.retrieve());
			}
			head = head.next();
		}
		return temp;
	}
	public ItemList<Room> listRooms(){
		return rooms;
	}
}
