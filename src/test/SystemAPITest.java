/**
 * 
 */
package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.SystemAPI;
import model.*;

/**
 * @author Conor James Giles
 *
 */
public class SystemAPITest {
	SystemAPI api;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		api = new SystemAPI();
		Property prop = new Property("Address", 10, 5);
		api.addProperty(prop);
		Room room = new Room(0, true);
		prop.addRoom(room);
		room.addBed(new Bed(0, 12.75));
		room.addBed(new Bed(2, 10.75));
		Student student = new Student("John Doe", false, true, 1);
		api.addStudent(student);
		api.assignStudentBed(student, room.getBeds().get(0));
		Room room2 = new Room(12, false);
		prop.addRoom(room2);
		room2.addBed(new Bed(1, 13.50));
		room2.addBed(new Bed(1, 13.50));
		api.addProperty(new Property("Address2", 0, 0));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		api = null;
	}

	/**
	 * Test method for {@link controller.SystemAPI#listBeds()}.
	 */
	@Test
	public final void testListBeds() {
		assertNotNull(api.listBeds());
		assertTrue(api.listBeds().get(0).getCost() == 12.75);
		assertNotNull(api.listBeds().next());
		assertNotNull(api.listBeds().get(0).getStudent());
	}

	/**
	 * Test method for {@link controller.SystemAPI#listFreeBeds()}.
	 */
	@Test
	public final void testListFreeBeds() {
		ItemList<Bed> head = api.listFreeBeds();
		while(head!=null) {
			assertNull(head.retrieve().getStudent());
			assertNull(head.retrieve().getBunkmate());
			head=head.next();
		}
	}

	/**
	 * Test method for {@link controller.SystemAPI#readPropertyCSV(java.lang.String)}.
	 */
	@Test
	public final void testReadPropertyCSV() {
		api = new SystemAPI();
		try {
		api.readPropertyCSV("test.csv");
		}catch(Exception e) {
			fail(e.getMessage());
		}
		assertEquals(api.listBeds().get(0).getType(),"SINGLE");
		
	}

	/**
	 * Test method for {@link controller.SystemAPI#readStudentCSV(java.lang.String)}.
	 */
	@Test
	public final void testReadStudentCSV() {
		api = new SystemAPI();
		try {
			api.readStudentCSV("testStudent.csv");
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertNotNull(api.listStudents());
	}

	/**
	 * Test method for {@link controller.SystemAPI#writePropertyCSV(java.lang.String)}.
	 */
	@Test
	public final void testWritePropertyCSV() {
		api = new SystemAPI();
		Property property = new Property("CSV", 0, 0);
		api.addProperty(property);
		Room room = new Room(0, true);
		property.addRoom(room);
		room.addBed(new Bed(0,12));
		api.addProperty(new Property("Second", 12, 24));
		try {
			api.writePropertyCSV("test.csv");
		}catch(Exception e){
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link controller.SystemAPI#writeStudentCSV(java.lang.String)}.
	 */
	@Test
	public final void testWriteStudentCSV() {
		api = new SystemAPI();
		api.addStudent(new Student("Jane Smith", true, false, 42));
		try {
			api.writeStudentCSV("testStudent.csv");
		}catch(Exception e){
			fail(e.getMessage());
		}
		
	}

	/**
	 * Test method for {@link controller.SystemAPI#assignStudentBed(model.Student, model.Bed)}.
	 */
	@Test
	public final void testAssignStudentBed() {
		Student student = new Student("Jill Jackson", true, true, 100);
		api.addStudent(student);
		Bed bed = api.listFreeBeds().get(0);
		api.assignStudentBed(student, bed);
		assertNotNull(student.getBed());
		assertNotNull(bed.getStudent());
	}

	/**
	 * Test method for {@link controller.SystemAPI#removeStudentBed(model.Student)}.
	 */
	@Test
	public final void testRemoveStudentBed() {
		api.removeStudentBed(api.searchStudent(1));
		assertNull(api.searchStudent(1).getBed());
	}

	/**
	 * Test method for {@link controller.SystemAPI#searchProperty(java.lang.String)}.
	 */
	@Test
	public final void testSearchProperty() {
		assertEquals(api.searchProperty("Address").getAddress(), "Address");
	}

	/**
	 * Test method for {@link controller.SystemAPI#removeProperty(model.Property)}.
	 */
	@Test
	public final void testRemoveProperty() {
		api.removeProperty(api.searchProperty("Address"));
		assertNull(api.searchProperty("Address"));
	}

	/**
	 * Test method for {@link controller.SystemAPI#removeBed(model.Bed)}.
	 */
	@Test
	public final void testRemoveBed() {
		Bed bed = api.listFreeBeds().get(0);
		api.removeBed(bed);
		assertNotEquals(api.listFreeBeds().get(0), bed);
	}

	/**
	 * Test method for {@link controller.SystemAPI#removeStudent(model.Student)}.
	 */
	@Test
	public final void testRemoveStudent() {
		api.removeStudent(api.listStudents().get(0));
		assertNull(api.listStudents());
	}

	/**
	 * Test method for {@link controller.SystemAPI#addProperty(model.Property)}.
	 */
	@Test
	public final void testAddProperty() {
		api.addProperty(new Property("Somewhere", 42, 16));
		assertNotNull(api.searchProperty("Somewhere"));
	}

	/**
	 * Test method for {@link controller.SystemAPI#addStudent(model.Student)}.
	 */
	@Test
	public final void testAddStudent() {
		api.addStudent(new Student("Name Surname", false, false, 20));
		assertNotNull(api.searchStudent(20));
	}

	/**
	 * Test method for {@link controller.SystemAPI#getAddress(model.Bed)}.
	 */
	@Test
	public final void testGetAddress() {
		assertEquals(api.getAddress(api.listBeds().get(0)), "Address");
	}
}
