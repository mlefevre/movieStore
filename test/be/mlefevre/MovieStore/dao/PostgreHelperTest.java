/**
 * 
 */
package be.mlefevre.MovieStore.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import be.mlefevre.MovieStore.dao.PostgreHelper;

/**
 * Test class to validate {@link PostgreHelper} class.
 * @author lefevre
 */
public class PostgreHelperTest {

	private PostgreHelper helper;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		helper = new PostgreHelper();
	}
	
	@After
	public void tearDown() throws Exception{
		helper.destroy();
	}
	
	@Test
	public void createTableTest(){
		// TODO
	}
	@Test
	public void dropTableTest(){
		// TODO
	}
	@Test
	public void insertRowTest(){
		// TODO
	}
	@Test
	public void selectRowTest(){
		// TODO
	}
}
