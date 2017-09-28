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
	ItemList beds;
	ItemList students;
	ItemList properties;
	ItemList rooms;
	public SystemAPI() {
		this.beds = null;
		this.students = null;
		this.properties = null;
		this.rooms = null;
	}
	public void listBeds() {
		ItemList head = beds;
		while(head!=null) {
			System.out.println(head.retrieve().toString());
			head=head.next();
		}
	}
	public void listFreeBeds() {
		ItemList head = beds;
		while(head!=null) {
			if(((Bed) head.retrieve()).getStudent() == null)
				System.out.println(head.retrieve().toString());
			head=head.next();
		}
	}
	public void searchFreeBeds() {
		
	}
	public void readCSV(String filename) throws Exception {
		BufferedReader file = new BufferedReader(new FileReader(filename));
		String str = "";
		while(str!=null) {
			str=file.readLine();
			String[] params = str.split(",");
			Property prop = new Property(params[0], Integer.parseInt(params[1]), Integer.parseInt(params[2]));
			for(int i=3;i<params.length;) {
				Boolean ensuite = params[i]=="RoomES";
				Room room = new Room(Integer.parseInt(params[++i]),ensuite);
				prop.addRoom(room);
				while(params[i]!="\n" && (params[i]!="RoomNES" || params[i]!="RoomES") && i<params.length) {
					room.addBed(new Bed(params[++i], Float.parseFloat(params[++i])));
				}
			}
		}
		file.close();
	}
	public void writeCSV(String filename) throws Exception {
		FileWriter file = new FileWriter(filename);
		file.write(properties.toString());
		file.close();
	}
	public void assignStudentBed(Student student, Bed bed) {
		bed.setStudent(student);
		student.setBed(bed);
	}
	public void removeStudentBed(Student student) {
		student.getBed().removeStudent(student);
		student.setBed(null);
		
	}
	public void viewProperty() {
		
	}
	public void viewProperty(String address) {
		
	}
	public void addProperty(Property property) {
		ItemList.append(properties, property);
	}
}
