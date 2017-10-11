package controller;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;

import model.*;

/**
 * @author Conor James Giles
 *
 */
public class SystemAPI {
	ItemList<Student> students;
	ItemList<Property> properties;
	public SystemAPI() {
		this.students = null;
		this.properties = null;
	}
	public ItemList<Bed> listBeds() {
		ItemList<Property> property = properties;
		ItemList<Bed> beds = null;
		while(property != null) {
			ItemList<Room> room = property.retrieve().listRooms();
			while(room != null) {
				if(beds == null)
					beds = new ItemList<Bed>(room.retrieve().getBeds().retrieve(), room.retrieve().getBeds().next());
				else {
					ItemList<Bed> head = room.retrieve().getBeds();
					while(head != null) {
						beds.append(head.retrieve());
						head = head.next();
					}
				}
				room = room.next();
			}
			property = property.next();
		}
		return beds;
	}
	public ItemList<Bed> listFreeBeds() {
		ItemList<Bed> beds = listBeds();
		ItemList<Bed> head = beds;
		int i = 1;
		while(beds != null) {
			if(beds.retrieve().getStudent()!=null)
				beds.remove(i);
			i++;
			beds = beds.next();
		}
		if(head.retrieve().getStudent() != null)
			head = head.next();
		return head;
	}
	public ItemList<Bed> searchFreeBeds(int bid) {
		ItemList<Bed> beds = listFreeBeds();
		return beds;
	}
	public void readCSV(String filename) throws Exception {
		BufferedReader file = new BufferedReader(new FileReader(filename));
		String str = "";
		str = file.readLine();
		while(str!=null) {
			String[] params = str.split(", ");
			Property prop = new Property(params[0], Integer.parseInt(params[1]), Integer.parseInt(params[2]));
			for(int i = 3;i<params.length;) {
				Boolean ensuite = params[i++]=="RoomES";
				Room room = new Room(Integer.parseInt(params[i++]),ensuite);
				prop.addRoom(room);
				while(i<params.length &&  (!params[i].equals("RoomNES")) && !(params[i].equals("RoomES"))) {
					room.addBed(new Bed(params[i++], Float.parseFloat(params[i++])));
				}
			}
			addProperty(prop);
			str=file.readLine();
		}
		file.close();
	}
	public void writeCSV(String filename) throws Exception {
		FileWriter file = new FileWriter(filename);
		file.write(properties.toCSV("\n"));
		file.close();
	}
	public void assignStudentBed(Student student, Bed bed) {
		bed.setStudent(student);
		student.setBed(bed);
	}
	public void removeStudentBed(Student student) {
		student.getBed().removeStudent();
		student.setBed(null);
	}
	public String viewProperty() {
		ItemList<Property> list = properties;
		String str = "";
		while(list != null) {
			str += viewProperty(list.retrieve().getAddress());
			str += "----------------" + "\n";
			list = list.next();
		}
		return str;
	}
	public Property searchProperty(String address) {
		ItemList<Property> head = properties;
		while(head != null) {
			if(head.retrieve().getAddress().equals(address))
				return head.retrieve();
			head = head.next();
		}
		return null;
	}
	public String viewProperty(String address) {
		Property prop = searchProperty(address);
		String str = "";
		str += "Address: " + prop.getAddress() + "\n";
		str += "Distance (km) from WIT: " + prop.getDistance() + "\n";
		str += "Available car park spaces: " + prop.getSpaces() + "\n";
		str += "Available rooms: " + "\n";
		for(int i = 0; i<100; i++) {
			ItemList<Room> list = prop.listRooms(i);
			if(list != null)
				str += "Floor " + i + "\n";
			while(list != null) {
				String beds = list.retrieve().getBeds()==null?"":(" and " + list.retrieve().getBeds().length() + " beds.");
				str += "Room with" + (list.retrieve().hasEnsuite()?"":"out") + " ensuite" + beds + "\n";
				list = list.next();
			}
		}
		return str;
	}
	public void addProperty(Property property) {
		if(searchProperty(property.getAddress())==null)
		{
			if(properties == null)
				properties= new ItemList<Property>(property);
			else
				properties.append(property);
		}
	}
	public void addStudent(Student student) {
		if(searchStudent(student.getSid())==null)
		{
			if(students == null)
				students = new ItemList<Student>(student);
			else
				students.append(student);
	}
}
	public Student searchStudent(int sid) {
		ItemList<Student> head = students;
		while(head != null) {
			if(head.retrieve().getSid() == sid)
				return head.retrieve();
			head = head.next();
		}
		return null;
	}
	public Bed searchBed(int bid) {
		ItemList<Bed> beds = listBeds();
		while (beds != null) {
			if(beds.retrieve().getUid() == bid)
				return beds.retrieve();
			beds = beds.next();
		}
		return null;
	}
	public ItemList<Student> listStudents() {
		return students;
	}
}