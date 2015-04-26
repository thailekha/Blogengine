import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import models.Blog;
import models.Comment;
import models.Page;
import models.Post;
import models.SubComment;
import models.User;
import play.test.Fixtures;
import play.test.UnitTest;


public class SubCommentTest extends UnitTest {
	
	private User tom, bob;
	private Blog blog;
	private Post post;
	private Comment comment;
	
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
		post = new Post(blog,"title post","content post",false);
		post.save();
		comment = new Comment(post,tom,"tom comment");
		comment.save();
	}
	
	@After
	public void reset()
	{
		comment.delete();
		post.delete();
		blog.delete();
		bob.delete();
		tom.delete();
	}
	
	@Test
	public void testCreateAndDeleteSubComment()
	{
		SubComment subCmt1 = new SubComment(bob,comment,"bob subcomment");
		subCmt1.save();
		SubComment subCmt2 = new SubComment(tom,comment,"tom reply");
		subCmt2.save();
		
		assertEquals(SubComment.findAll().size(),2);
		
		User bobby = User.find("byFirstName", "bob").first();
		User thomas = User.find("byFirstName", "tom").first();
		SubComment busCmt1 = SubComment.find("bySubCommenter", bobby).first();
		SubComment busCmt2 = SubComment.find("bySubCommenter", thomas).first();
		
		assertNotNull(busCmt1);
		assertNotNull(busCmt2);
		assertEquals(busCmt1.subCommentText,"bob subcomment");
		assertEquals(busCmt2.subCommentText,"tom reply");
		
		Post impliedPost = busCmt1.commentHost.postHost;
		Post deilpmiPost = busCmt2.commentHost.postHost;
		assertEquals(impliedPost.postContent,"content post");
		assertEquals(impliedPost,deilpmiPost);
		
		subCmt2.delete();
		subCmt1.delete();
		
		assertEquals(SubComment.findAll().size(),0);
	}
}
