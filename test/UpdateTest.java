import java.util.List;

import models.Blog;
import models.Post;
import models.Update;
import models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;


public class UpdateTest extends UnitTest {

	private User tom;
	private Blog blog;
	private Post post;
	private Update update1, update2;
	
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
		blog = new Blog(tom,"Blog one");
		blog.save();
		post = new Post(blog,"title","content",false);
		post.save();
		update1 = new Update(tom,post,post.postTitle + " first",post.postContent + " first");
		update1.save();
		update2 = new Update(tom,post,post.postTitle + " second",post.postContent + " second");
		update2.save();
	}
	
	@After
	public void teardown()
	{
		update2.delete();
		update1.delete();
		post.delete();
		blog.delete();
		tom.save();
		
		assertEquals(Update.findAll().size(),0);
	}
	
	@Test
	public void testCreateUpdate()
	{
		List<Update> updates = Update.findAll();
		assertEquals(updates.size(),2);
		User tomCat = User.find("byEmail", "tom@email.com").first();
		List<Update> mirUpdates = Update.find("byUpdater", tomCat).fetch();
		Update mirUpdate1 = mirUpdates.get(0);
		Update mirUpdate2 = mirUpdates.get(1);
		assertEquals(mirUpdate1.belong,post);
		assertEquals(mirUpdate1.nTitle,"title first");
		assertEquals(mirUpdate1.nContent,"content first");
		
		assertEquals(mirUpdate2.belong,post);
		assertEquals(mirUpdate2.nTitle,"title second");
		assertEquals(mirUpdate2.nContent,"content second");
	}
}
