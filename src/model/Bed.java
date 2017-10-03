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
	private ItemList<Student> student = null;
	/**
	 * @param type
	 * @param cost
	 * @param uid
	 * @param student
	 */
	public Bed(String type, float cost) {
		this.cost = cost;
		this.uid = uuid++;
		switch(type.toLowerCase()) {
			case "single":
				this.type = Type.SINGLE;
				break;
			case "double":
				this.type = Type.DOUBLE;
				break;
			case "bunk":
				this.type = Type.BUNK;
				break;
			default:
				this.type = Type.SINGLE;
		}
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
		return (Student) student.retrieve();
	}
	/**
	 * @param student the student to set
	 */
	public void setStudent(Student student) {
		if(type == Type.BUNK && this.student != null)
			this.student.append(student);
		else
			this.student = new ItemList<Student>(student);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return type.toString() + ", " + cost;
	}
	public void removeStudent(Student student2) {
		// TODO Auto-generated method stub
		
	}
	
	

}
