import java.util.List;

import models.Blog;
import models.Post;
import models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import play.Logger;
import play.test.Fixtures;
import play.test.UnitTest;

public class PostTest extends UnitTest {

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
	public void testCreatePost()
	{
		Post post = new Post(blog,"title","content",false);
		post.save();
		
		Post tsop = Post.find("byPostContent", "content").first();
		Blog golb = Blog.find("byBlogTitle", "Blog one").first();
		assertNotNull(tsop);
		assertEquals(tsop.blogPostHost,golb);
		
		Post aPost = new Post(blog,"title 2", "content 2",false);
		aPost.save();
		List<Post> posts = Post.findAll();
		assertEquals(posts.size(), 2);
		
		post.delete();
		aPost.delete();
	}
	
	@Test
	public void testUpdatePost()
	{
		Post post = new Post(blog,"title","content",false);
		post.save();
		
		Post tsop = Post.find("byPostContent", "content").first();
		tsop.postTitle = "new title";
		tsop.save();
		
		Post mirrorPost = Post.find("byPostTitle", "new title").first();
		assertNotNull(mirrorPost);
		assertEquals(mirrorPost.postContent, "content");
		assertEquals(mirrorPost.blogPostHost,blog);
		
		post.delete();
	}
	
	@Test
	public void testDeletePost()
	{
		Post post = new Post(blog,"title","content",false);
		post.save();
		Post aPost = new Post(blog,"title 2", "content 2",false);
		aPost.save();
		
		assertEquals(Post.findAll().size(),2);
		
		aPost.delete();
		post.delete();
		
		assertEquals(Post.findAll().size(),0);
	}
}
