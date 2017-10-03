package view;
import java.io.PrintStream;
import java.util.Scanner;

import controller.SystemAPI;
import model.Property;
/**
 * @author Conor James Giles
 *
 */
class Menu {
	private static Scanner in = new Scanner(System.in);
	private static PrintStream out = System.out;
	private static SystemAPI system = new SystemAPI();
	public static void main(String[] args) throws Exception {
		Menu.menu();
	}
	public static void menu() throws Exception {
		out.println("Welcome to the WIT Student Accomodation Management Tool.\nPress Enter to begin.");
		in.nextLine();
		while(true) {
			out.println("Please select an option:\n1)Add new entities\n2)Save/Load properties\n3)Assign beds to students\n4)View property data\n0)Quit\n>");
			switch(Menu.getInt()) {
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
				Menu.propertyMenu();
				break;
			default:
				out.println("Invalid index, please try again.");
				in.nextLine();
			}
		}
	}
	public static void quit() {
		System.exit(0);
	}
	public static void addMenu() {
		Boolean inMenu = true;
		while(inMenu == true) {
			out.println("Please select an option:\n1)Add new Property\n2)Add new Room\n3)Add new Bed\n4)Add new Student\n0)Return to Main Menu\n>");
			switch(Menu.getInt()) {
			case 0:
				inMenu=false;
				break;
			case 1:
				out.println("Please enter the address of your new Property: ");
				String addr = in.nextLine();
				out.println("Please enter the distance (in km) from the college of your property: ");
				int dist = Menu.getInt();
				out.println("Please enter the number of car park spaces of your Property: ");
				int spaces = Menu.getInt();
				system.addProperty(new Property(addr, dist, spaces));
				out.println("Property added.");
				in.nextLine();
				break;
			default:
				out.println("Invalid index, please try again.");
				in.nextLine();
			}
		}
	}
	public static void saveLoadMenu() throws Exception {
		Boolean inMenu = true;
		while(inMenu == true) {
			out.println("Please select an option:\n1)Save properties\n2)Load properties\n0)Return to Main Menu\n>");
			switch(Menu.getInt()) {
			case 0:
				inMenu=false;
				break;
			case 1:
				out.println("Please enter the name of the file you want to save to:\n>");
				system.writeCSV(in.nextLine());
				break;
			case 2:
				out.println("Please enter the name of the file you want to load from:\n>");
				system.readCSV(in.nextLine());
				break;
			default:
				out.println("Invalid index, please try again.");
				in.nextLine();
			}
		}
	}
	public static void assignmentMenu() {
		Boolean inMenu = true;
		while(inMenu == true) {
			out.println("Please select an option:\n1)Assign Bed to Student\n2)Remove Student from Bed\n0)Return to Main Menu\n>");
			switch(Menu.getInt()) {
			case 0:
				inMenu=false;
				break;
			default:
				out.println("Invalid index, please try again.");
				in.nextLine();
			}
		}
	}
	public static void propertyMenu() {
		Boolean inMenu = true;
		while(inMenu == true) {
			out.println("Please select an option:\n1)View all properties\n2)View property at an address\n0)Return to Main Menu\n>");
			switch(Menu.getInt()) {
			case 0:
				inMenu=false;
				break;
			case 1:
				system.viewProperty();
				break;
			case 2:
				out.println("Please enter the address of the property you would like to view\n>");
				system.viewProperty(in.nextLine());
				break;
			default:
				out.println("Invalid index, please try again.");
				in.nextLine();
			}
		}
	}
	private static int getInt() {
		int i = in.nextInt();
		in.nextLine();
		return i;
	}
}
