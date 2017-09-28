package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BedTest.class, ItemListTest.class, PropertyTest.class, RoomTest.class, StudentTest.class })
public class ModelTest {

}
