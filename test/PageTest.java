import models.Blog;
import models.Page;
import models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;


public class PageTest extends UnitTest {

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
	public void testCreateAndDeletePage()
	{
		Page page1 = new Page(blog,"title 1","content 1","link 1");
		page1.save();
		Page page2 = new Page(blog,"title 2","content 2","link 2");
		page2.save();
		assertEquals(Page.findAll().size(),2);		
		
		Page egap1 = Page.find("byPageTitle", "title 1").first();
		Page egap2 = Page.find("byPageContent", "content 2").first();
		assertNotNull(egap1);
		assertNotNull(egap2);
		assertEquals(egap1.pageContent,"content 1");
		assertEquals(egap2.pageContent,"content 2");
		
		User impliedPageOwner = egap1.blogPageHost.blogOwner;
		assertEquals(impliedPageOwner.email,"tom@email.com");
		
		page2.delete();
		page1.delete();
		
		assertEquals(Page.findAll().size(),0);
	}
	
	@Test 
	public void testUpdatePage()
	{
		Page page = new Page(blog,"title","content","link");
		page.save();
		
		Blog golb = Blog.find("byBlogTitle", "Blog one").first();
		assertNotNull(golb);
		Page egap = Page.find("byBlogPageHost", golb).first();
		assertNotNull(egap);
		egap.pageTitle = "another title";
		egap.save();
		egap.pageContent = "annother content";
		egap.save();
		
		Page apge = Page.find("byPageTitle", "another title").first();
		assertNotNull(apge);
		//assertEquals(apge.pageContent,"another content");
		
		page.delete();
	}
}
