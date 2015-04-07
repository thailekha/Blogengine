import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import models.Blog;
import models.Page;
import models.Post;
import models.User;
import play.Logger;
import play.test.Fixtures;
import play.test.UnitTest;

public class BlogTest extends UnitTest {
	
	private User tom;
	private Blog blog1, blog2;
	
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
		blog1 = new Blog(tom,"Blog one");
		blog1.save();
		blog2 = new Blog(tom, "Blog two");
		blog2.save();
	}
	
	@After
	public void reset()
	{
		blog1.delete();
		blog2.delete();
		tom.delete();
	}
	
	@Test
	public void testCreateAndDeleteBlog()
	{
		List<Blog> blogs = Blog.findAll(); 
		assertEquals(blogs.size(), 2);
		Blog blogOne = blogs.get(0);
		assertEquals(blogOne.blogTitle, "Blog one");
		Blog blogTwo = Blog.find("byBlogTitle", "Blog two").first();
		assertEquals(blogTwo.blogOwner, tom);
		
		Blog blog3 = new Blog(tom, "Blog three");
		blog3.save();
		
		blogs = Blog.findAll();
		assertEquals(blogs.size(), 3);
		
		Blog blogThree = blogs.get(2);
		assertEquals(blogThree.blogTitle, "Blog three");
		//tom.blogs.remove(blogThree);			<=== why does this line break the test ?
		blogThree.delete();
		
		assertEquals(Blog.findAll().size(),2);
	}
	
	@Test
	public void testCreateAndDeleteBlogWithPostAndPage()
	{
		Blog blog4 = new Blog(tom,"Blog four");
		blog4.save();
		Post post3 = new Post(blog4, "Post three", "Content three");
		post3.save();
		Post post4 = new Post(blog4,"Post four","Content four");
		post4.save();
		assertEquals(Post.findAll().size(), 2);
		Page page = new Page(blog4, "page", "page content","link");
		page.save();
		
		Blog blogFour = Blog.find("byBlogTitle", "Blog four").first();
		assertNotNull(blogFour);
		assertNotNull(Page.find("byBlogPageHost", blogFour).first());
		
		page.delete();
		post3.delete();
		post4.delete();
		blog4.delete();
		assertEquals(Blog.findAll().size(),2);
	}

	@Test
	public void testUpdateBlog()
	{
		blog1.blogTitle = "Edited title";
		blog1.save();
		blog2.blogTitle = "";
		blog2.save();
		
		Blog blogOne = Blog.find("byBlogTitle", "Edited title").first();
		Blog blogTwo = Blog.find("byBlogTitle", "").first();
		assertNotNull(blogOne);
		assertNotNull(blogTwo);
	}
}
