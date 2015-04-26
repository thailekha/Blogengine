import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import models.Blog;
import models.Draft;
import models.User;
import play.test.Fixtures;
import play.test.UnitTest;


public class DraftTest extends UnitTest {
	
	private User tom;
	private Blog blog;
	
	@BeforeClass
	  public static void loadDB()
	  {
	    Fixtures.deleteAllModels();
	  }
	
	@Before
	public void setup()
	{
		tom = new User("tom","thomas","tom@email.com",10,"secret");
		tom.save();
		blog = new Blog(tom,"Blog one");
		blog.save();	
	}
	
	@After
	public void reset()
	{
		blog.delete();
		tom.delete();
	}
	
	@Test
	public void testCreateDraft()
	{
		Draft draft = new Draft(blog,"title","content");
		draft.save();
		
		List<Draft> drafts = Draft.findAll();
		Draft mirrorDraft = drafts.get(0);
		
		assertEquals(drafts.size(),1);
		assertEquals(draft.title,"title");
		assertEquals(draft.content,"content");
		assertEquals(draft.blogDraftHost,blog);
		
		draft.delete();
	}
}
