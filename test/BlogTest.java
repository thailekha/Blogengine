import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import models.Blog;
import models.Post;
import models.User;
import play.test.UnitTest;

public class BlogTest extends UnitTest {
	
	private User tom;
	private Blog blog1, blog2;
	
	@Before
	public void setup()
	{
		tom = new User("tom","tom@email.com","secret");
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
	public void testBlogThatHasPosts()
	{
//		Post post1 = new Post(blog1,"Post one", "Content one");
//		post1.save();
//		Post post2 = new Post(blog1,"Post two", "Content two");
//		post2.save();
//		
//		Blog blogOne = (Blog) Blog.findAll().get(0);
//		assertEquals(blogOne.posts.size(), 2);	
		
		Blog blog4 = new Blog(tom,"Blog four");
		blog4.save();
		Post post3 = new Post(blog4, "Post three", "Content three");
		post3.save();
		assertEquals(Post.findAll().size(), 1);
		
		Blog blogFour = Blog.find("byBlogTitle", "Blog four").first();
		post3.delete();
		blogFour.delete();
		assertEquals(Blog.findAll().size(),2);
		
//		post1.delete();
//		post2.delete();
	}

}
