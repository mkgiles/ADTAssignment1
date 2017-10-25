/*
 * 
 */
package view;

import java.io.PrintStream;
import java.util.Scanner;

import controller.SystemAPI;
import model.Bed;
import model.ItemList;
import model.Property;
import model.Room;
import model.Student;

/**
 * The Class Menu.
 *
 * @author Conor James Giles
 */
class Menu {

	/** The input stream. */
	private static Scanner in = new Scanner(System.in);

	/** The output stream. */
	private static PrintStream out = System.out;

	/** The system API. */
	private static SystemAPI system = new SystemAPI();

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args){
		Menu.menu();
	}

	/**
	 * Main Menu.
	 */
	public static void menu(){
		out.print("Welcome to the WIT Student Accomodation Management Tool.\nPress Enter to begin.");
		in.nextLine();
		while (true) {
			out.print("Please select an option:\n1)Add/Remove entities\n2)Save/Load properties\n3)Assign beds to students\n4)View data\n0)Quit\n>");
			switch (Menu.getInt()) {
			case 0:
				Menu.quit();
				break;
			case 1:
				Menu.addMenu();
				break;
			case 2:
				Menu.saveLoadMenu();
				break;
			case 3:
				Menu.assignmentMenu();
				break;
			case 4:
				Menu.viewMenu();
				break;
			default:
				out.println("Invalid index, please try again.");
				in.nextLine();
			}
		}
	}

	/**
	 * Quit.
	 */
	public static void quit() {
		System.exit(0);
	}

	/**
	 * Menu for adding and removing items.
	 */
	public static void addMenu() {
		Boolean inMenu = true;
		while (inMenu == true) {
			out.print("Please select an option:\n1)Add new Property\n2)Add new Room\n3)Add new Bed\n4)Add new Student\n5)Remove property\n6)Remove room\n7)Remove bed\n8)Remove student\n0)Return to Main Menu\n>");
			switch (Menu.getInt()) {
			case 0:
				inMenu = false;
				break;
			case 1:
				out.println("Please enter the address of your new Property: ");
				String addr = in.nextLine();
				out.println("Please enter the distance (in km) from the college of your property: ");
				int dist = Menu.getInt();
				out.println("Please enter the number of car park spaces of your Property: ");
				int spaces = Menu.getInt();
				system.addProperty(new Property(addr, dist, spaces));
				in.nextLine();
				break;
			case 2:
				out.println("Please enter the address of the property you want to add a room to:");
				String property = in.nextLine();
				Property prop = system.searchProperty(property);
				if (prop == null) {
					out.println("Property does not exist.");
					break;
				}
				out.println("Please enter the floor number of your room:");
				int floor = Menu.getInt();
				out.println("Does this room have an ensuite?[Y/n]");
				Boolean ensuite = Menu.getBool();
				prop.addRoom(new Room(floor, ensuite));
				out.println("Room added.");
				break;
			case 3:
				out.println("Please enter the address of the property you are adding a bed to:");
				String location = in.nextLine();
				Property prp = system.searchProperty(location);
				out.println("Please enter the floor of the room you want to add a bed to:");
				int storey = Menu.getInt();
				ItemList<Room> rooms = prp.listRooms(storey);
				if (rooms == null) {
					out.println("No rooms on that floor");
					break;
				}
				for (int i = 0; i < rooms.length(); i++) {
					out.println("" + i + rooms.get(i));
				}
				out.println("Select a room from the list:");
				int roomno = Menu.getInt();
				Room room = rooms.get(roomno);
				out.println("Please enter the type of bed:\n0)Single\n1)Double\n2)Bunk");
				int type = Menu.getInt();
				out.println("Please enter the cost of the bed:");
				float cost = Menu.getFloat();
				room.addBed(new Bed(type, cost));
				out.println("Bed added");
				break;
			case 4:
				out.println("Please enter the student's name: ");
				String name = in.nextLine();
				out.println("Please enter the student's id: ");
				int sid = Menu.getInt();
				out.println("Does the student have a car? [Y/n]");
				Boolean car = Menu.getBool();
				out.println("Is the student male or female?");
				Boolean sex = true;
				try {
				sex = in.nextLine().toLowerCase().charAt(0) == 'f';
				}catch(Exception e) {
					System.err.println(e);
				}
				Student stud = new Student(name, sex, car, sid);
				system.addStudent(stud);
				break;
			case 5:
				out.println("Please enter the address of the property you want to remove: ");
				system.removeProperty(system.searchProperty(in.nextLine()));
				break;
			case 6:
				out.println("Please enter the address of the property you want to remove a room from: ");
				Property place = system.searchProperty(in.nextLine());
				out.println("Please enter the floor of the room you want to remove: ");
				int flr = Menu.getInt();
				ItemList<Room> rm = place.listRooms(flr);
				for (int i = 0; i < rm.length(); i++) {
					out.println("" + i + rm.get(i));
				}
				out.println("Please select a room from the list: ");
				place.removeRoom(flr, Menu.getInt());
				break;
			case 7:
				out.println("Please enter the ID of the bed you wish to remove: ");
				system.removeBed(system.searchBed(Menu.getInt()));
				break;
			case 8:
				out.println("Please enter the ID of the student you wish to remove: ");
				system.removeStudent(system.searchStudent(Menu.getInt()));
				break;
			default:
				out.println("Invalid index, please try again.");
				in.nextLine();
			}
		}
	}

	/**
	 * Save load menu.
	 */
	public static void saveLoadMenu() {
		Boolean inMenu = true;
		while (inMenu == true) {
			out.print("Please select an option:\n1)Save properties\n2)Save Students\n3)Load properties\n4)Load Students\n0)Return to Main Menu\n>");
			switch (Menu.getInt()) {
			case 0:
				inMenu = false;
				break;
			case 1:
				out.print("Please enter the name of the file you want to save to:\n>");
				try {
					system.writePropertyCSV(in.nextLine());
				}catch(Exception e) {
					out.println("Failed to write save file.");
				}
				break;
			case 2:
				out.print("Please enter the name of the file you want to save to:\n>");
				try {
					system.writeStudentCSV(in.nextLine());
				}catch(Exception e) {
					out.println("Failed to write save file.");
				}
				break;
			case 3:
				out.print("Please enter the name of the file you want to load from:\n>");
				try {
					system.readPropertyCSV(in.nextLine());
				}catch(Exception e) {
					out.println("Failed to read save file. Either malformed or not present.");
				}
				break;
			case 4:
				out.print("Please enter the name of the file you want to load from:\n>");
				try {
					system.readStudentCSV(in.nextLine());
				}catch(Exception e) {
					out.println("Failed to read save file. Either malformed or not present.");
				}
				break;
			default:
				out.println("Invalid index, please try again.");
				in.nextLine();
			}
		}
	}

	/**
	 * Assignment menu.
	 */
	public static void assignmentMenu() {
		Boolean inMenu = true;
		while (inMenu == true) {
			out.print("Please select an option:\n1)Assign Bed to Student\n2)Remove Student from Bed\n0)Return to Main Menu\n>");
			switch (Menu.getInt()) {
			case 1:
				out.println("Please enter Student ID: ");
				int sid = Menu.getInt();
				out.println("please enter bed ID: ");
				int bid = Menu.getInt();
				Student stud = system.searchStudent(sid);
				out.println(stud);
				Bed bed = system.searchBed(bid);
				out.println(bed);
				system.assignStudentBed(stud, bed);
				out.println("Assigned.");
				break;
			case 2:
				out.println("Enter student ID: ");
				int uid = Menu.getInt();
				system.removeStudentBed(system.searchStudent(uid));
				break;
			case 0:
				inMenu = false;
				break;
			default:
				out.println("Invalid index, please try again.");
				in.nextLine();
			}
		}
	}

	/**
	 * View menu.
	 */
	public static void viewMenu() {
		Boolean inMenu = true;
		while (inMenu == true) {
			out.print("Please select an option:\n1)View all properties\n2)View property at an address\n3)View all beds\n4)View all free beds\n5)View all students\n6)Search for beds with particular parameters\n7)Get address of a bed.\n8)Get address of student\n0)Return to Main Menu\n>");
			switch (Menu.getInt()) {
			case 0:
				inMenu = false;
				break;
			case 1:
				out.print(system.viewProperty());
				in.nextLine();
				break;
			case 2:
				out.print("Please enter the address of the property you would like to view\n>");
				out.print(system.viewProperty(in.nextLine()));
				in.nextLine();
				break;
			case 3:
				out.println(system.listBeds());
				in.nextLine();
				break;
			case 4:
				out.println(system.listFreeBeds());
				in.nextLine();
				break;
			case 5:
				out.println(system.listStudents());
				in.nextLine();
				break;
			case 6:
				String queries[] = new String[6];
				out.println("Do you wish to set a max distance from the college?");
				if (Menu.getBool()) {
					out.println("Enter max distance: ");
					queries[0] = "" + Menu.getInt();
				}
				out.println("Do you wish to find a property with available parking space?");
				if (Menu.getBool())
					queries[1] = "true";
				out.println("Do you wish to set a max floor height?");
				if (Menu.getBool()) {
					out.println("Please enter a max height: ");
					queries[2] = "" + Menu.getInt();
				}
				out.println("Do you wish to be in a single sex property/room?");
				if (Menu.getBool()) {
					out.println("Do you want to be in a single sex property?");
					if (Menu.getBool()) {
						out.println("Male or female?");
						if (in.nextLine().toLowerCase().charAt(0) == 'm')
							queries[3] = "0";
						else
							queries[3] = "2";
					} else {
						out.println("Room?");
						if (Menu.getBool()) {
							out.println("Male or female?");
							if (in.nextLine().toLowerCase().charAt(0) == 'm')
								queries[3] = "1";
							else
								queries[3] = "3";
						}
					}
				}
				out.println("Do you need an en-suite?");
				if (Menu.getBool())
					queries[4] = "true";
				out.println("Do you want to set a max cost?");
				if (Menu.getBool()) {
					out.println("Please enter a max cost:");
					queries[5] = "" + Menu.getFloat();
				}
				out.println(system.searchBed(queries));
				in.nextLine();
				break;
			case 7:
				out.println("Please enter the ID of the bed you are searching for: ");
				int bid = Menu.getInt();
				Bed bed = system.searchBed(bid);
				out.println(system.getAddress(bed));
				in.nextLine();
				break;
			case 8:
				out.println("Please enter the student ID of the student you are searching for: ");
				int sid = Menu.getInt();
				Student student = system.searchStudent(sid);
				out.println(system.getAddress(student.getBed()));
				in.nextLine();
				break;
			default:
				out.println("Invalid index, please try again.");
				in.nextLine();
			}
		}
	}

	/**
	 * Gets an int from input.
	 *
	 * @return the int
	 */
	private static int getInt() {
		try {
			int i = in.nextInt();
			in.nextLine();
			return i;
		}catch(Exception e) {
			System.err.println(e);
			return -1;
		}
	}

	/**
	 * Gets a float from input.
	 *
	 * @return the float
	 */
	private static float getFloat() {
		try {
			float f = in.nextFloat();
			in.nextLine();
			return f;
		}catch(Exception e) {
			System.err.println(e);
			return -1;
		}
	}

	/**
	 * Gets a bool from input.
	 *
	 * @return the bool
	 */
	private static Boolean getBool() {
		try{String temp = in.nextLine();
		if (temp.toLowerCase().charAt(0) == 'n')
			return false;
		return true;
		}catch(Exception e) {
			return true;
		}
	}
}
