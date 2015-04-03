import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import models.Blog;
import models.Comment;
import models.Page;
import models.Post;
import models.User;
import play.test.Fixtures;
import play.test.UnitTest;


public class CommentTest extends UnitTest {

	private User tom;
	private User bob;
	private Blog blog;
	private Post post;
	private Page page;
	
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
		bob = new User("bob","bie","bob@email.com",10,"secret");
		bob.save();
		blog = new Blog(tom,"Blog one");
		blog.save();
		page = new Page(blog,"title page","content page");
		page.save();
		post = new Post(blog,"title post","content post");
		post.save();
	}
	
	@After
	public void reset()
	{
		post.delete();
		page.delete();
		blog.delete();
		bob.delete();
		tom.delete();
	}
	
	@Test
	public void testCreateAndDeleteComment()
	{
		Comment commentPage = new Comment(page,bob,"page comment");
		commentPage.save();
		Comment commentPost = new Comment(post,tom,"post comment");
		commentPost.save();
		
		assertEquals(Comment.findAll().size(),2);
		
		Page egap = Page.find("byPageTitle", "title page").first();
		Comment egapComment = Comment.find("byPageHost", egap).first();
		assertNotNull(egapComment);
		assertEquals(egapComment.commentText,"page comment");
		
		Post tsop = Post.find("byPostTitle", "title post").first();
		Comment tsopComment = Comment.find("byPostHost", tsop).first();
		assertNotNull(tsopComment);
		assertEquals(tsopComment.commentText, "post comment");
		
		Blog impliedBlogFromPost = tsopComment.postHost.blogPostHost;
		Blog impliedBlogFromPage = egapComment.pageHost.blogPageHost;
		
		assertNotNull(impliedBlogFromPost);
		assertNotNull(impliedBlogFromPage);
		assertEquals(impliedBlogFromPost,impliedBlogFromPage);
		
		commentPost.delete();
		commentPage.delete();
		
		assertEquals(Comment.findAll().size(),0);
	}
}
