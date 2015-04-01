import java.util.ArrayList;
import java.util.List;

import org.junit.*;
import play.test.UnitTest;
import models.User;

public class UserTest extends UnitTest {

	private User tom;
	private User jerry;
	
	@Before
	public void setup()
	{
		tom = new User("tom","tom@email.com","secret");
		tom.save();
		jerry = new User("jerry", "jerry@email.com","secret");
		jerry.save();
	}
	
	@After
	public void reset()
	{
		tom.delete();
		jerry.delete();
	}
	
	@Test
	public void testCreateUser()
	{
		List<User> users = User.findAll();
		assertEquals(users.size(), 2);
		
		User checkTom = User.find("email", "tom@email.com").first();
		assertEquals(checkTom.name, "tom");
		User checkJerry = User.find("email", "jerry@email.com").first();
		assertEquals(checkJerry.name, "jerry");
	}
	
	@Test
	public void testDeleteUser()
	{
		User spike = new User("spike", "spike@email.com","secret");
		spike.save();
		
		spike.delete();
		assertEquals(User.find("name", "spike").first(), null);
	}
}
