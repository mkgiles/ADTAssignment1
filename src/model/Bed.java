package model;
/**
 * @author Conor James Giles
 *
 */
public class Bed {
	private static int uuid = 0;
	private static enum Type {
		SINGLE, DOUBLE, BUNK
	}
	private Type type;
	private float cost;
	private int uid;
	private Student student = null;
	/**
	 * @param type
	 * @param cost
	 * @param uid
	 * @param student
	 */
	public Bed(int type, float cost) {
		this.cost = cost;
		this.uid = uuid++;
		this.type = Type.values()[type];
	}
	public Bed(String type, float cost) {
		this.cost = cost;
		this.uid = uuid++;
		this.type = Type.valueOf(type);
	}
	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}
	/**
	 * @return the cost
	 */
	public float getCost() {
		return cost;
	}
	/**
	 * @param cost the cost to set
	 */
	public void setCost(float cost) {
		this.cost = cost;
	}
	/**
	 * @return the uid
	 */
	public int getUid() {
		return uid;
	}
	/**
	 * @param uid the uid to set
	 */
	public void setUid(int uid) {
		this.uid = uid;
	}
	/**
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}
	/**
	 * @param student the student to set
	 */
	public void setStudent(Student student) {
		this.student = student;
	}
	
	public String toCSV() {
		return type.toString() + ", " + cost;
	}
	public String toString() {
		return "Bed no. " + uid + ": " + type + " " + cost + (student==null?" Free":(" [" + student + "]"));
	}
	public void removeStudent() {
		this.student = null;
	}
	

}
