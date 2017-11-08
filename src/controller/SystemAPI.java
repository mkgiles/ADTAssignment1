package controller;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;

import model.*;

/**
 * The Class SystemAPI.
 *
 * @author Conor James Giles
 */
public class SystemAPI {

	/** The students. */
	ItemList<Student> students;

	/** The properties. */
	ItemList<Property> properties;

	/**
	 * Instantiates a new system API.
	 */
	public SystemAPI() {
		this.students = null;
		this.properties = null;
	}

	/**
	 * List beds.
	 *
	 * @return the item list
	 */
	public ItemList<Bed> listBeds() {
		ItemList<Property> property = properties;
		ItemList<Bed> beds = null;
		while (property != null) {
			ItemList<Room> room = property.retrieve().listRooms();
			while (room != null) {
				ItemList<Bed> head = room.retrieve().getBeds();
				while (head != null) {
					if(beds == null)
						beds = new ItemList<Bed>(head.retrieve());
					else
						beds.append(head.retrieve());
					head = head.next();
				}
				room = room.next();
			}
			property = property.next();
		}
		return beds;
	}

	/**
	 * List free beds.
	 *
	 * @return the item list
	 */
	public ItemList<Bed> listFreeBeds() {
		ItemList<Bed> beds = listBeds();
		ItemList<Bed> head = beds;
		for (int i = 0; head != null; i++) {
			if (head.retrieve().getStudent() != null) {
				if (head.retrieve().getType().equals("BUNK")) {
					if (head.retrieve().getBunkmate() != null) {
						head = head.next();
						if(beds.length() == 1)
							beds = null;
						else
							beds.remove(i);
					}
				} else {
					head = head.next();
					if(beds.length() == 1)
						beds = null;
					else
						beds.remove(i);
				}
			}
			else
				head = head.next();
			i++;
		}
		return beds;
	}

	/**
	 * Search free beds.
	 *
	 * @param bid
	 *            the bid
	 * @return the item list
	 */
	public ItemList<Bed> searchFreeBeds(int bid) {
		ItemList<Bed> beds = listFreeBeds();
		return beds;
	}

	/**
	 * Read CSV containing properties.
	 *
	 * @param filename
	 *            the filename
	 * @throws Exception
	 *             the exception
	 */
	public void readPropertyCSV(String filename) throws Exception {
		BufferedReader file = new BufferedReader(new FileReader(filename));
		String str = "";
		str = file.readLine();
		while (str != null) {
			String[] params = str.split(", ");
			Property prop = new Property(params[0].replaceAll("%", ","), Integer.parseInt(params[1]), Integer.parseInt(params[2]));
			for (int i = 3; i < params.length;) {
				Boolean ensuite = params[i++] == "RoomES";
				Room room = new Room(Integer.parseInt(params[i++]), ensuite);
				prop.addRoom(room);
				while (i < params.length && (!params[i].equals("RoomNES")) && !(params[i].equals("RoomES"))) {
					room.addBed(new Bed(params[i++], Float.parseFloat(params[i++])));
				}
			}
			addProperty(prop);
			str = file.readLine();
		}
		file.close();
	}

	/**
	 * Read CSV containing students.
	 *
	 * @param filename
	 *            the filename
	 * @throws Exception
	 *             the exception
	 */
	public void readStudentCSV(String filename) throws Exception {
		BufferedReader file = new BufferedReader(new FileReader(filename));
		String str = "";
		str = file.readLine();
		while (str != null) {
			String[] params = str.split(", ");
			Student stud = new Student(params[1], params[2] == "F", params[3] == "car", Integer.parseInt(params[0]));
			if (!params[4].equals("unassigned")) {
				Bed bed = searchBed(Integer.parseInt(params[4]));
				if (bed != null && bed.getType().equals(params[5]) && bed.getCost() == Float.parseFloat(params[6])) {
					assignStudentBed(stud, bed);
				}
			}
			addStudent(stud);
			str = file.readLine();
		}
		file.close();
	}

	/**
	 * Write property CSV.
	 *
	 * @param filename
	 *            the filename
	 * @throws Exception
	 *             the exception
	 */
	public void writePropertyCSV(String filename) throws Exception {
		FileWriter file = new FileWriter(filename);
		file.write(properties.toCSV("\n"));
		file.close();
	}

	/**
	 * Write student CSV.
	 *
	 * @param filename
	 *            the filename
	 * @throws Exception
	 *             the exception
	 */
	public void writeStudentCSV(String filename) throws Exception {
		FileWriter file = new FileWriter(filename);
		file.write(students.toCSV("\n"));
		file.close();
	}

	/**
	 * Assign student to bed.
	 *
	 * @param student
	 *            the student
	 * @param bed
	 *            the bed
	 */
	public void assignStudentBed(Student student, Bed bed) {
		bed.setStudent(student);
		student.setBed(bed);
	}

	/**
	 * Removes student from bed.
	 *
	 * @param student
	 *            the student
	 */
	public void removeStudentBed(Student student) {
		student.getBed().removeStudent(student);
		student.setBed(null);
	}

	/**
	 * View all properties.
	 *
	 * @return the string
	 */
	public String viewProperty() {
		ItemList<Property> list = properties;
		String str = "";
		while (list != null) {
			str += viewProperty(list.retrieve().getAddress());
			str += "----------------" + "\n";
			list = list.next();
		}
		return str;
	}

	/**
	 * Search for a property.
	 *
	 * @param address
	 *            the address
	 * @return the property
	 */
	public Property searchProperty(String address) {
		ItemList<Property> head = properties;
		while (head != null) {
			if (head.retrieve().getAddress().equals(address))
				return head.retrieve();
			head = head.next();
		}
		return null;
	}

	/**
	 * Removes a property.
	 *
	 * @param property
	 *            the property
	 */
	public void removeProperty(Property property) {
		if(properties.length()>1)
			properties.remove(properties.getIndexOf(property));
		else
			properties = null;
	}

	/**
	 * Removes a bed.
	 *
	 * @param bed
	 *            the bed
	 */
	public void removeBed(Bed bed) {
		Property prop = searchProperty(getAddress(bed));
		ItemList<Room> rooms = prop.listRooms();
		while (rooms != null) {
			if (rooms.retrieve().getBeds().getIndexOf(bed) != -1) {
				rooms.retrieve().getBeds().remove(rooms.retrieve().getBeds().getIndexOf(bed));
				break;
			}
			rooms = rooms.next();
		}
	}

	/**
	 * Removes a student.
	 *
	 * @param student
	 *            the student
	 */
	public void removeStudent(Student student) {
		if(students.length() > 1)
			students.remove(students.getIndexOf(student));
		else
			students = null;
	}

	/**
	 * View property.
	 *
	 * @param address
	 *            the address
	 * @return the string
	 */
	public String viewProperty(String address) {
		Property prop = searchProperty(address);
		String str = "";
		str += "Address: " + prop.getAddress() + "\n";
		str += "Distance (km) from WIT: " + prop.getDistance() + "\n";
		str += "Available car park spaces: " + prop.getSpaces() + "\n";
		str += "Available rooms: " + "\n";
		float profit = 0;
		for (int i = 0; i < 100; i++) {
			ItemList<Room> list = prop.listRooms(i);
			if (list != null)
				str += "Floor " + i + "\n";
			while (list != null) {
				String beds = list.retrieve().getBeds() == null ? "": (" and " + list.retrieve().getBeds().length() + " beds.");
				str += "Room with" + (list.retrieve().hasEnsuite() ? "" : "out") + " ensuite" + beds + "\n";
				if (list.retrieve().getBeds() != null) {
					str += "Beds:\n";
					for (ItemList<Bed> head = list.retrieve().getBeds(); head != null; head = head.next()) {
						str += head.retrieve() + "\n";
						if(head.retrieve().getStudent() != null)
							profit += head.retrieve().getCost();
						if(head.retrieve().getBunkmate()!=null)
							profit += head.retrieve().getCost();
					}
				}
				list = list.next();
			}
		}
		str += "Total profit: " + profit + "\n";
		return str;
	}

	/**
	 * Add a property.
	 *
	 * @param property
	 *            the property
	 */
	public void addProperty(Property property) {
		if (searchProperty(property.getAddress()) == null) {
			if (properties == null)
				properties = new ItemList<Property>(property);
			else
				properties.append(property);
		}
	}

	/**
	 * Add a student.
	 *
	 * @param student
	 *            the student
	 */
	public void addStudent(Student student) {
		if (searchStudent(student.getSid()) == null) {
			if (students == null)
				students = new ItemList<Student>(student);
			else
				students.append(student);
		}
	}

	/**
	 * Search for a student.
	 *
	 * @param sid
	 *            the sid
	 * @return the student
	 */
	public Student searchStudent(int sid) {
		ItemList<Student> head = students;
		while (head != null) {
			if (head.retrieve().getSid() == sid)
				return head.retrieve();
			head = head.next();
		}
		return null;
	}

	/**
	 * Search for a bed.
	 *
	 * @param bid
	 *            the bid
	 * @return the bed
	 */
	public Bed searchBed(int bid) {
		ItemList<Bed> beds = listBeds();
		while (beds != null) {
			if (beds.retrieve().getUid() == bid)
				return beds.retrieve();
			beds = beds.next();
		}
		return null;
	}

	/**
	 * Search for a bed.
	 *
	 * @param queries
	 *            the list of queries
	 * @return the item list
	 */
	public ItemList<Bed> searchBed(String[] queries) {
		ItemList<Bed> beds = listFreeBeds();
		for (int i = 0; i < queries.length; i++) {
			if (beds == null)
				return null;
			if (queries[i] == null)
				continue;
			ItemList<Property> head = properties;
			switch (i) {
			case 0:
				while (head != null) {
					if (head.retrieve().getDistance() > Integer.parseInt(queries[i])) {
						ItemList<Room> room = head.retrieve().listRooms();
						while (room != null) {
							ItemList<Bed> bed = room.retrieve().getBeds();
							while (bed != null) {
								if (beds.length() > 0)
									beds.remove(beds.getIndexOf(bed.retrieve()));
								else
									beds = null;
								bed = bed.next();
							}
							room = room.next();
						}
					}
					head = head.next();
				}
				break;
			case 1:
				for (; head != null; head = head.next()) {
					ItemList<Room> room = head.retrieve().listRooms();
					int j = 0;
					for (; room != null; room = room.next()) {
						ItemList<Bed> bed = room.retrieve().getBeds();
						for (; bed != null; bed = bed.next()) {
							if (bed.retrieve().getStudent() != null)
								if (bed.retrieve().getStudent().hasCar())
									j++;
						}
					}
					if (head.retrieve().getSpaces() <= j) {
						while (room != null) {
							ItemList<Bed> bed = room.retrieve().getBeds();
							while (bed != null) {
								if (beds.length() > 0)
									beds.remove(beds.getIndexOf(bed.retrieve()));
								else
									beds = null;
								bed = bed.next();
							}
							room = room.next();
						}
					}
				}
				break;
			case 2:
				while (head != null) {
					ItemList<Room> room = head.retrieve().listRooms();
					while (room != null) {
						if (room.retrieve().getFloor() > Integer.parseInt(queries[i])) {
							ItemList<Bed> bed = room.retrieve().getBeds();
							while (bed != null) {
								if (beds.length() > 0)
									beds.remove(beds.getIndexOf(bed.retrieve()));
								else
									beds = null;
								bed = bed.next();
							}
						}
						room = room.next();
					}
					head = head.next();
				}
				break;
			case 3:
				int check = Integer.parseInt(queries[i]);
				for (; head != null; head = head.next()) {
					Boolean failprop = false;
					ItemList<Room> room = head.retrieve().listRooms();
					for (; room != null; room = room.next()) {
						Boolean failroom = false;
						ItemList<Bed> bed = room.retrieve().getBeds();
						for (; bed != null; bed = bed.next()) {
							if (failprop || failroom) {
								if (beds.length() > 0)
									beds.remove(beds.getIndexOf(bed.retrieve()));
								else
									beds = null;
							} else {
								Boolean failbed = false;
								if (bed.retrieve().getStudent() != null)
									if (bed.retrieve().getStudent().getGender())
										if (check % 2 != 0)
											failbed = true;
										else if (check % 2 == 0)
											failbed = true;
								if (failbed) {
									if (check >= 2)
										failprop = true;
									else
										failbed = true;
									if (beds.length() > 0)
										beds.remove(beds.getIndexOf(bed.retrieve()));
									else
										beds = null;
								}
							}
						}
					}
				}
				break;
			case 4:
				//Began to miss SQL here.
				for (; head != null; head = head.next()) {
					ItemList<Room> room = head.retrieve().listRooms();
					for (; room != null; room = room.next()) {
						if (!room.retrieve().hasEnsuite()) {
							ItemList<Bed> bed = room.retrieve().getBeds();
							for (; bed != null; bed = bed.next()) {
								if (beds.length() > 0)
									beds.remove(beds.getIndexOf(bed.retrieve()));
								else
									beds = null;
							}
						}
					}
				}
				break;
			case 5:
				ItemList<Bed> search = beds;
				for (int j = 0; search != null; j++, search = search.next()) {
					if (search.retrieve().getCost() > Float.parseFloat(queries[i]))
						beds.remove(j);
				}
				break;
			}
		}
		return beds;
	}

	/**
	 * Gets the address of a bed.
	 *
	 * @param bed
	 *            the bed
	 * @return the address
	 */
	public String getAddress(Bed bed) {
		ItemList<Property> prop = properties;
		for (; prop != null; prop = prop.next()) {
			ItemList<Room> room = prop.retrieve().listRooms();
			for (; room != null; room = room.next()) {
				ItemList<Bed> beds = room.retrieve().getBeds();
				for (; beds != null; beds = beds.next()) {
					if (beds.retrieve().equals(bed))
						return prop.retrieve().getAddress();
				}
			}
		}
		return "Failed to locate bed.";
	}

	/**
	 * List students.
	 *
	 * @return the item list
	 */
	public ItemList<Student> listStudents() {
		return students;
	}
}