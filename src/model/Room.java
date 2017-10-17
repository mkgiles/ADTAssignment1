package model;
/**
 * @author Conor James Giles
 *
 */
public class Room implements CSV{
	private int floor;
	private Boolean ensuite;
	private ItemList<Bed> beds;
	/**
	 * @param floor
	 * @param ensuite
	 * @param beds
	 */
	public Room(int floor, Boolean ensuite) {
		this.floor = floor;
		this.ensuite = ensuite;
	}
	/**
	 * @return the floor
	 */
	public int getFloor() {
		return floor;
	}
	/**
	 * @param floor the floor to set
	 */
	public void setFloor(int floor) {
		this.floor = floor;
	}
	/**
	 * @return the ensuite
	 */
	public Boolean hasEnsuite() {
		return ensuite;
	}
	/**
	 * @param ensuite the ensuite to set
	 */
	public void setEnsuite(Boolean ensuite) {
		this.ensuite = ensuite;
	}
	/**
	 * @return the beds
	 */
	public ItemList<Bed> getBeds() {
		return beds;
	}
	/**
	 * @param beds the beds to set
	 */
	public void addBed(Bed bed) {
		if(beds == null)
			beds = new ItemList<Bed>(bed);
		else
			beds.append(bed);
	}
	@Override
	public String toCSV() {
		String str = "";
		str += ensuite?"RoomES, ":"RoomNES, ";
		str += floor;
		if(beds != null) {
			str += ", ";
			str += beds.toCSV(", ");
		}
		return str;
	}

}
