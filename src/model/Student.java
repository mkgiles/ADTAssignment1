package model;

/**
 * @author Conor James Giles
 *
 */
public class Student {

	private String name;
	private Boolean gender;
	private Boolean car;
	private int sid;
	private Bed bed;
	/**
	 * @param name
	 * @param gender
	 * @param car
	 * @param sid
	 */
	public Student(String name, Boolean gender, Boolean car, int sid) {
		this.name = name;
		this.gender = gender;
		this.car = car;
		this.sid = sid;
	}
	/**
	 * @return the bed
	 */
	public Bed getBed() {
		return bed;
	}
	/**
	 * @param bed the bed to set
	 */
	public void setBed(Bed bed) {
		this.bed = bed;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the gender
	 */
	public Boolean getGender() {
		return gender;
	}
	/**
	 * @return the car
	 */
	public Boolean getCar() {
		return car;
	}
	/**
	 * @return the sid
	 */
	public int getSid() {
		return sid;
	}
	public String toString() {
		return "Student no: " + sid + ". " + name + " (" + (gender?"female":"male") + " " + (car?"car":"no car") + ")";
	}
}
