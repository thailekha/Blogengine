package controllers;
import models.User;
import play.mvc.Controller;

public class Home extends Controller {

	public static void index()
	{
		User user = Accounts.getLoggedInUser();
		render(user);
	}
	
	public static void newBlog(String blogTitle)
	{
		User user = Accounts.getLoggedInUser();
		user.newBlog(blogTitle);
		user.save();
		index();
	}
}
