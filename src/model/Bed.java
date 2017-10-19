package model;

/**
 * The Class Bed.
 *
 * @author Conor James Giles
 */
public class Bed implements CSV {

	/** The uuid. */
	private static int uuid = 0;

	/**
	 * The Enum Type.
	 */
	private static enum Type {

		/** The single. */
		SINGLE,
		/** The double. */
		DOUBLE,
		/** The bunk. */
		BUNK
	}

	/** The type. */
	private Type type;

	/** The cost. */
	private float cost;

	/** The uid. */
	private int uid;

	/** The student. */
	private Student student = null;

	/** The bunkmate. */
	private Student bunkmate = null;

	/**
	 * Instantiates a new bed.
	 *
	 * @param type
	 *            the type
	 * @param cost
	 *            the cost
	 */
	public Bed(int type, float cost) {
		this.cost = cost;
		this.uid = uuid++;
		this.type = Type.values()[type];
	}

	/**
	 * Instantiates a new bed.
	 *
	 * @param type
	 *            the type
	 * @param cost
	 *            the cost
	 */
	public Bed(String type, float cost) {
		this.cost = cost;
		this.uid = uuid++;
		this.type = Type.valueOf(type);
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type.toString();
	}

	/**
	 * Sets the type.
	 *
	 * @param type
	 *            the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * Gets the cost.
	 *
	 * @return the cost
	 */
	public float getCost() {
		return cost;
	}

	/**
	 * Sets the cost.
	 *
	 * @param cost
	 *            the cost to set
	 */
	public void setCost(float cost) {
		this.cost = cost;
	}

	/**
	 * Gets the uid.
	 *
	 * @return the uid
	 */
	public int getUid() {
		return uid;
	}

	/**
	 * Sets the uid.
	 *
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(int uid) {
		this.uid = uid;
	}

	/**
	 * Gets the student.
	 *
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * Sets the student.
	 *
	 * @param student
	 *            the student to set
	 */
	public void setStudent(Student student) {
		if (this.type != Type.BUNK)
			this.student = student;
		else {
			if (this.student != null)
				this.bunkmate = student;
			else
				this.student = student;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Bed no. " + uid + ": " + type + " " + cost
				+ (type == Type.BUNK
						? (" Top Bunk:" + (student == null ? " Free" : (" [" + student + "]")) + "; Bottom Bunk:"
								+ (bunkmate == null ? " Free" : (" [" + bunkmate + "]")))
						: (student == null ? " Free" : (" [" + student + "]")));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.CSV#toCSV()
	 */
	@Override
	public String toCSV() {
		return type.toString() + ", " + cost;
	}

	/**
	 * Removes the student.
	 *
	 * @param student
	 *            the student
	 */
	public void removeStudent(Student student) {
		if (this.student == student)
			this.student = null;
		if (this.bunkmate == student)
			this.bunkmate = null;
	}

	/**
	 * Gets the bunkmate.
	 *
	 * @return the bunkmate
	 */
	public Student getBunkmate() {
		return bunkmate;
	}

}
