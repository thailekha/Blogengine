import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import models.Followship;
import models.User;
import play.test.Fixtures;
import play.test.UnitTest;

public class FollowshipTest extends UnitTest {

	private User tom, jerry, spike, fish;
	
	@BeforeClass
	  public static void loadDB()
	  {
	    Fixtures.deleteAllModels();
	  }
	
	@Before
	public void setup()
	{
		tom = new User("tom","cat", "tom@email.com",10,"secret");
		tom.save();
		jerry = new User("jerry","mouse", "jerry@email.com",10,"secret");
		jerry.save();
		spike = new User("spike","dog", "spike@email.com",10,"secret");
		spike.save();
		fish = new User("fish","fish", "fish@email.com",10,"secret");
		fish.save();
	}
	
	@Test
	public void testCreateAndDeleteFollowship()
	{
		Followship tomToJerry = new Followship(tom,jerry);
		tomToJerry.save();
		Followship jerryToTom = new Followship(jerry,tom);
		jerryToTom.save();
		Followship tomToSpike = new Followship(tom,spike);
		tomToSpike.save();
		Followship jerryToFish = new Followship(jerry,fish);
		jerryToFish.save();
		Followship tomToTom = new Followship(tom,tom);
		tomToTom.save();
		
		assertEquals(Followship.findAll().size(),5);
		
		User mirrorTom = User.find("byEmail", "tom@email.com").first();
		User mirrorJerry = User.find("byEmail", "jerry@email.com").first();
		User mirrorSpike = User.find("byEmail", "spike@email.com").first();
		User mirrorFish = User.find("byEmail", "fish@email.com").first();
		
		List<Followship> fromToms = Followship.find("bySource", mirrorTom).fetch();
		assertEquals(fromToms.get(0).target,mirrorJerry);
		assertEquals(fromToms.get(1).target,mirrorSpike);
		assertEquals(fromToms.get(2).target,mirrorTom);
		
		Followship toTom = Followship.find("bySource", mirrorJerry).first();
		assertEquals(toTom.target,mirrorTom);
		Followship toFish = Followship.find("byTarget", mirrorFish).first();
		assertEquals(toFish.source,mirrorJerry);
		
		tomToJerry.delete();
		jerryToTom.delete();
		tomToSpike.delete();
		jerryToFish.delete();
		tomToTom.delete();
		
		assertEquals(Followship.findAll().size(),0);
	}
}
