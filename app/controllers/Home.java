package controllers;
import models.Blog;
import models.User;
import play.mvc.Controller;

public class Home extends Controller {

	public static void index()
	{
		User user = Accounts.getLoggedInUser();
		boolean needjs = true;
		render(user,needjs);
	}
	
	public static void newBlog(String blogTitle)
	{
		User user = Accounts.getLoggedInUser();
		user.newBlog(blogTitle);
		user.save();
		index();
	}
	
	public static void deleteBlog(Long id)
	{
		Blog blog = Blog.findById(id);
		User blogOwner = blog.blogOwner;
		blogOwner.blogs.remove(blog);
		blogOwner.save();
		blog.delete();
		index();
	}
}
