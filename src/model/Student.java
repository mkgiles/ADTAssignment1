package model;

/**
 * The Class Student.
 *
 * @author Conor James Giles
 */
public class Student implements CSV {

	/** The name. */
	private String name;

	/** The gender. */
	private Boolean gender;

	/** The car. */
	private Boolean car;

	/** The sid. */
	private int sid;

	/** The bed. */
	private Bed bed;

	/**
	 * Instantiates a new student.
	 *
	 * @param name
	 *            the name
	 * @param gender
	 *            the gender
	 * @param car
	 *            the car
	 * @param sid
	 *            the sid
	 */
	public Student(String name, Boolean gender, Boolean car, int sid) {
		this.name = name;
		this.gender = gender;
		this.car = car;
		this.sid = sid;
	}

	/**
	 * Gets the bed.
	 *
	 * @return the bed
	 */
	public Bed getBed() {
		return bed;
	}

	/**
	 * Sets the bed.
	 *
	 * @param bed
	 *            the bed to set
	 */
	public void setBed(Bed bed) {
		this.bed = bed;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public Boolean getGender() {
		return gender;
	}

	/**
	 * Checks for car.
	 *
	 * @return the car
	 */
	public Boolean hasCar() {
		return car;
	}

	/**
	 * Gets the sid.
	 *
	 * @return the sid
	 */
	public int getSid() {
		return sid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Student no: " + sid + ". " + name + " (" + (gender ? "female" : "male") + " " + (car ? "car" : "no car")
				+ ")";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.CSV#toCSV()
	 */
	@Override
	public String toCSV() {
		return "" + sid + ", " + "name" + ", " + (gender ? "F" : "M") + ", " + (car ? "car" : "ncar") + ", "
				+ (bed == null ? "unassigned" : ("" + bed.getUid() + ", " + bed.toCSV()));
	}
}
