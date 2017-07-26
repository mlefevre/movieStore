/**
 * 
 */
package be.mlefevre.MovieStore.model;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author lefevre
 *
 */
public class CycleTest {

	private static final String DEFAULT_CYCLE_NAME = "default cycle name";
	private static final String DEFAULT_ORIGINAL_TITLE = "Default Original title test";
	private static final String DEFAULT_ORIGINAL_TITLE_2 = "Second default Original title test";
	private static final String DEFAULT_ORIGINAL_TITLE_3 = "Third default Original title test";
	private static final String ORIGINAL_TITLE_1 = "Original title test 1";
	private static final String ORIGINAL_TITLE_2 = "Original title test 2";

	private Movie default_movie_1;
	private Movie default_movie_2;
	private Movie default_movie_3;
	private Movie movie1;
	private Movie movie2;
	
	private Title movieDefaultTitle1;
	private Title movieDefaultTitle2;
	private Title movieDefaultTitle3;
	private Title movieTitle1;
	private Title movieTitle2;

	private Cycle cycleToTest;

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
		cycleToTest = new Cycle();
		cycleToTest.setName(DEFAULT_CYCLE_NAME);

		movieTitle1 = new Title();
		movieTitle1.setOriginal(ORIGINAL_TITLE_1);
		movieTitle2 = new Title();
		movieTitle2.setOriginal(ORIGINAL_TITLE_2);
		movieDefaultTitle1 = new Title();
		movieDefaultTitle1.setOriginal(DEFAULT_ORIGINAL_TITLE);
		movieDefaultTitle2 = new Title();
		movieDefaultTitle2.setOriginal(DEFAULT_ORIGINAL_TITLE_2);
		movieDefaultTitle3 = new Title();
		movieDefaultTitle3.setOriginal(DEFAULT_ORIGINAL_TITLE_3);

		movie1 = new Movie();
		movie1.setTitle(movieTitle1);
		movie2 = new Movie();
		movie2.setTitle(movieTitle2);
		default_movie_1 = new Movie();
		default_movie_1.setTitle(movieDefaultTitle1);
		default_movie_1.setGenre(Genre.COMEDY);
		default_movie_2 = new Movie();
		default_movie_2.setTitle(movieDefaultTitle2);
		default_movie_1.setGenre(Genre.WAR);
		default_movie_3 = new Movie();
		default_movie_3.setTitle(movieDefaultTitle3);
		default_movie_1.setGenre(Genre.COMEDY);

		cycleToTest.addMovie(default_movie_1, 0, 0);
		cycleToTest.addMovie(default_movie_2, 1, 1);
		cycleToTest.addMovie(default_movie_3, 2, 2);
	}

	/**
	 * Test method for {@link be.mlefevre.MovieStore.model.Cycle#getName()}.
	 */
	@Test
	public void testGetName() {
		Assert.assertEquals(DEFAULT_CYCLE_NAME, cycleToTest.getName());
	}

	/**
	 * Test method for {@link be.mlefevre.MovieStore.model.Cycle#getGenres()}.
	 */
	@Test
	public void testGetGenres() {
		Assert.assertEquals(2, cycleToTest.getGenres().size());
		Assert.assertEquals(3, cycleToTest.getElements().values().size());
	}
	/**
	 * Test method for {@link be.mlefevre.MovieStore.model.Cycle#getGenres()}.
	 * The cycle is empty.
	 */
	@Test
	public void testGetGenresNoElements() {
		cycleToTest = new Cycle();
		Assert.assertEquals(0, cycleToTest.getGenres().size());
		Assert.assertEquals(0, cycleToTest.getElements().values().size());
	}

	/**
	 * Test method for {@link be.mlefevre.MovieStore.model.Cycle#getLength()}.
	 */
	@Test
	public void testGetLength(){
		Assert.assertEquals(3, cycleToTest.getLength());
	}
	/**
	 * Test method for {@link be.mlefevre.MovieStore.model.Cycle#getLength()}.
	 * The cycle is empty.
	 */
	@Test
	public void testGetLengthNoElements(){
		cycleToTest = new Cycle();
		Assert.assertEquals(0, cycleToTest.getLength());
	}
	
	@Test
	public void testaddMovie(){
		cycleToTest = new Cycle();
		cycleToTest.addMovie(default_movie_1, 0, 0);

		Assert.assertEquals(1, cycleToTest.getElements().size());
		String key = cycleToTest.getStoryTellingChronology().get(0);		
		Assert.assertEquals(DEFAULT_ORIGINAL_TITLE, cycleToTest.getElements().get(key).getTitle().getOriginal());
	}
	/**
	 * Test the addMovie method.
	 * Add a movie after an existing movie in the story order.
	 * <p>
	 * Storytelling order should then be :
	 * <ul>
	 * <li>default1</li>
	 * <li>default2</li>
	 * <li>default3</li>
	 * <li>addedMovie</li>
	 * </ul>
	 */
	@Test
	public void testAddMovieAfterStory(){
		cycleToTest.addMovie(movie1, 3, 0);

		Assert.assertEquals(4, cycleToTest.getElements().size());
		
		String key = cycleToTest.getStoryTellingChronology().get(1);		
		Assert.assertEquals(DEFAULT_ORIGINAL_TITLE_2, cycleToTest.getElements().get(key).getTitle().getOriginal());
		key = cycleToTest.getStoryTellingChronology().get(3);		
		Assert.assertEquals(ORIGINAL_TITLE_1, cycleToTest.getElements().get(key).getTitle().getOriginal());
	}
	/*
	 * Test the addMovie method.
	 * Add a movie before an existing movie in the story order.
	 * <p>
	 * Storytelling order should then be :
	 * <ul>
	 * <li>addedMovie</li>
	 * <li>default1</li>
	 * <li>default2</li>
	 * <li>default3</li>
	 * </ul>
	 */
	@Test
	public void testAddMovieBeforeStory(){
		cycleToTest.addMovie(movie1, 0, 0);

		Assert.assertEquals(4, cycleToTest.getElements().size());
		
		String key = cycleToTest.getStoryTellingChronology().get(3);		
		Assert.assertEquals(DEFAULT_ORIGINAL_TITLE_3, cycleToTest.getElements().get(key).getTitle().getOriginal());
		key = cycleToTest.getStoryTellingChronology().get(0);		
		Assert.assertEquals(ORIGINAL_TITLE_1, cycleToTest.getElements().get(key).getTitle().getOriginal());
	}
	/*
	 * Test the addMovie method.
	 * Add a movie at the same index than an existing movie in the story order.
	 * <p>
	 * Storytelling order should then be :
	 * <ul>
	 * <li>default1</li>
	 * <li>default2</li>
	 * <li>addedMovie2</li>
	 * <li>addedMovie1</li>
	 * <li>default3</li>
	 * </ul>
	 */
	@Test
	public void testAddMovieTwiceSamePlaceStory(){
		cycleToTest.addMovie(movie1, 2, 0);
		cycleToTest.addMovie(movie2, 2, 0);

		Assert.assertEquals(5, cycleToTest.getElements().size());
		
		String key = cycleToTest.getStoryTellingChronology().get(4);		
		Assert.assertEquals(DEFAULT_ORIGINAL_TITLE_3, cycleToTest.getElements().get(key).getTitle().getOriginal());
		key = cycleToTest.getStoryTellingChronology().get(1);		
		Assert.assertEquals(DEFAULT_ORIGINAL_TITLE_2, cycleToTest.getElements().get(key).getTitle().getOriginal());
		key = cycleToTest.getStoryTellingChronology().get(2);		
		Assert.assertEquals(ORIGINAL_TITLE_2, cycleToTest.getElements().get(key).getTitle().getOriginal());
		key = cycleToTest.getStoryTellingChronology().get(3);		
		Assert.assertEquals(ORIGINAL_TITLE_1, cycleToTest.getElements().get(key).getTitle().getOriginal());
	}
	/*
	 * Test the addMovie method.
	 * Add a movie after an existing movie in the production order.
	 * <p>
	 * Production order should then be :
	 * <ul>
	 * <li>default1</li>
	 * <li>default2</li>
	 * <li>default3</li>
	 * <li>addedMovie</li>
	 * </ul>
	 */
	@Test
	public void testAddMovieAfterProduction(){
		cycleToTest.addMovie(movie1, 0, 3);

		Assert.assertEquals(4, cycleToTest.getElements().size());
		
		String key = cycleToTest.getProductionChronology().get(1);		
		Assert.assertEquals(DEFAULT_ORIGINAL_TITLE_2, cycleToTest.getElements().get(key).getTitle().getOriginal());
		key = cycleToTest.getProductionChronology().get(3);		
		Assert.assertEquals(ORIGINAL_TITLE_1, cycleToTest.getElements().get(key).getTitle().getOriginal());
	}
	/*
	 * Test the addMovie method.
	 * Add a movie before an existing movie in the production order.
	 * <p>
	 * Production order should then be :
	 * <ul>
	 * <li>addedMovie</li>
	 * <li>default1</li>
	 * <li>default2</li>
	 * <li>default3</li>
	 * </ul>
	 */
	@Test
	public void testAddMovieBeforeProduction(){

		cycleToTest.addMovie(movie1, 0, 0);

		Assert.assertEquals(4, cycleToTest.getElements().size());
		
		String key = cycleToTest.getProductionChronology().get(3);		
		Assert.assertEquals(DEFAULT_ORIGINAL_TITLE_3, cycleToTest.getElements().get(key).getTitle().getOriginal());
		key = cycleToTest.getProductionChronology().get(0);		
		Assert.assertEquals(ORIGINAL_TITLE_1, cycleToTest.getElements().get(key).getTitle().getOriginal());
	}
	/*
	 * Test the addMovie method.
	 * Add a movie at the same index than an existing movie in the production order.
	 * <p>
	 * Storytelling order should then be :
	 * <ul>
	 * <li>default1</li>
	 * <li>default2</li>
	 * <li>addedMovie2</li>
	 * <li>addedMovie1</li>
	 * <li>default3</li>
	 * </ul>
	 */
	@Test
	public void testAddMovieTwiceSamePlaceProduction(){
		cycleToTest.addMovie(movie1, 0, 2);
		cycleToTest.addMovie(movie2, 0, 2);

		Assert.assertEquals(5, cycleToTest.getElements().size());
		
		String key = cycleToTest.getProductionChronology().get(4);		
		Assert.assertEquals(DEFAULT_ORIGINAL_TITLE_3, cycleToTest.getElements().get(key).getTitle().getOriginal());
		key = cycleToTest.getProductionChronology().get(1);		
		Assert.assertEquals(DEFAULT_ORIGINAL_TITLE_2, cycleToTest.getElements().get(key).getTitle().getOriginal());
		key = cycleToTest.getProductionChronology().get(2);		
		Assert.assertEquals(ORIGINAL_TITLE_2, cycleToTest.getElements().get(key).getTitle().getOriginal());
		key = cycleToTest.getProductionChronology().get(3);		
		Assert.assertEquals(ORIGINAL_TITLE_1, cycleToTest.getElements().get(key).getTitle().getOriginal());
	}

	/*
	 * Test the addMovie method.
	 * the given storyOrder is too big for the current element set.
	 * <p>
	 * Expected result : the movie is added in the map and at the end of the story chronology.
	 */
	@Test
	public void testAddMovieStoryOrderTooBig(){
		cycleToTest = new Cycle();
		cycleToTest.addMovie(default_movie_1, 0, 0);
		cycleToTest.addMovie(default_movie_2, 5, 1);

		Assert.assertEquals(2, cycleToTest.getElements().size());
		String key = cycleToTest.getStoryTellingChronology().get(1);		
		Assert.assertEquals(DEFAULT_ORIGINAL_TITLE_2, cycleToTest.getElements().get(key).getTitle().getOriginal());
		key = cycleToTest.getStoryTellingChronology().get(0);		
		Assert.assertEquals(DEFAULT_ORIGINAL_TITLE, cycleToTest.getElements().get(key).getTitle().getOriginal());
	}
	/*
	 * Test the addMovie method.
	 * the given storyOrder is too big for the current element set.
	 * <p>
	 * Expected result : the movie is added in the map and at the end of the story chronology.
	 */
	@Test
	public void testAddMovieProductionOrderTooBig(){
		cycleToTest = new Cycle();
		cycleToTest.addMovie(default_movie_1, 0, 0);
		cycleToTest.addMovie(default_movie_2, 1, 5);

		Assert.assertEquals(2, cycleToTest.getElements().size());
		String key = cycleToTest.getProductionChronology().get(1);		
		Assert.assertEquals(DEFAULT_ORIGINAL_TITLE_2, cycleToTest.getElements().get(key).getTitle().getOriginal());
		key = cycleToTest.getProductionChronology().get(0);		
		Assert.assertEquals(DEFAULT_ORIGINAL_TITLE, cycleToTest.getElements().get(key).getTitle().getOriginal());
	}
	
	@Test
	public void testRemoveMovie(){
		cycleToTest.removeMovie(default_movie_2);
		
		Assert.assertEquals(2, cycleToTest.getElements().size());
	}
}
