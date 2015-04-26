import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import play.test.Fixtures;
import play.test.UnitTest;
import play.Logger;
import models.Blog;
import models.Comment;
import models.Page;
import models.Post;
import models.SubComment;
import models.User;

public class UserTest extends UnitTest {

	private User tom;
	private User jerry;
	
	@BeforeClass
	  public static void loadDB()
	  {
	    Fixtures.deleteAllModels();
	  }
	
	@Before
	public void setup()
	{
		Logger.info("Running setup");
		tom = new User("tom","cat", "tom@email.com",10,"secret");
		tom.save();
		Logger.info("Tom's id: " + tom.id.intValue());
		jerry = new User("jerry","mouse", "jerry@email.com",10,"secret");
		jerry.save();
		
		List<User> users = User.findAll();
		for(User user: users) {
			Logger.info("User after created : " + user.email);
		}
		Logger.info("Finished setup");
	}
	
	@After
	public void reset()
	{
		Logger.info("Running tear down");
		tom.delete();
		jerry.delete();
	}
	
	@Test
	public void testCreateUser()
	{
		List<User> users = User.findAll();		
		assertEquals(users.size(), 2);
		
		User checkTom = User.find("email", "tom@email.com").first();
		assertEquals(checkTom.firstName, "tom");
		User checkJerry = User.find("email", "jerry@email.com").first();
		assertEquals(checkJerry.firstName, "jerry");
	}
	
	@Test
	public void testUpdateUser()
	{
		tom.firstName = "thomas";
		tom.email = "thomas@email.com";
		tom.save();
		
		User nTom = User.find("byFirstName", "thomas").first();
		assertNotNull(nTom);
		assertNotNull(User.find("byEmail", "thomas@email.com").first());
	}
	
	@Test
	public void testDeleteUser()
	{
		User spike = new User("spike","dog", "spike@email.com",10,"secret");
		spike.save();
		
		spike.delete();
		assertEquals(User.find("byFirstName", "spike").first(), null);
	}
	
	@Test
	public void testCreateUserWithBlogPagePostCommentSubComment()
	{
		Logger.info("Running big test");
		User spike = new User("spike","dog", "spike@email.com",10,"secret");
		spike.save();
		
		Blog blog = new Blog(spike, "spike's blog");
		blog.save();
		Post post = new Post(blog,"spike's post","post content",false);
		post.save();
		Logger.info("Posts: " + Post.find("byBlogPostHost", blog).fetch().size());
		Page page = new Page(blog, "spike's page", "page content","link");
		page.save();
		
		Comment commentPost = new Comment(post,spike,"post comment");
		commentPost.save();
		Comment commentPage = new Comment(page,spike,"page comment");
		commentPage.save();
		
		SubComment subCommentPost = new SubComment(spike,commentPost,"subcomment in post");
		subCommentPost.save();
		SubComment subCommentPage = new SubComment(spike,commentPage,"subCommentInPage");
		subCommentPage.save();
		
		User nSpike = User.find("byEmail", "spike@email.com").first();
		Blog nBlog = Blog.find("byBlogOwner", nSpike).first();
		
		Post nPost = Post.find("byBlogPostHost", nBlog).first();
		assertEquals(nPost.blogPostHost, nBlog);
		
		Page nPage = Page.find("byBlogPageHost", nBlog).first();
		assertEquals(nPage.blogPageHost, nBlog);
		
		Comment nComment = Comment.find("byCommenter", nSpike).first();		
		assertEquals(nComment.commenter, nSpike);
		
		List<SubComment> subComments = SubComment.findAll();
		assertEquals(subComments.size(), 2);
		
		subCommentPage.delete();
		subCommentPost.delete();
		commentPage.delete();
		commentPost.delete();
		page.delete();
		post.delete();
		blog.delete();
		
		spike.delete();
	}
}
