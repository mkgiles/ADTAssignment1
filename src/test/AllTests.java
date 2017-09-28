package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BedTest.class, ControllerTest.class, ItemListTest.class, ModelTest.class, PropertyTest.class,
		RoomTest.class, StudentTest.class, SystemAPITest.class })
public class AllTests {

}
