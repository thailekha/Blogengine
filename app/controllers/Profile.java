package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Blog;
import models.Page;
import models.User;
import play.db.jpa.Blob;
import play.mvc.Controller;
import utility.Wiper;

public class Profile extends Controller {

	public static void index()
	{
		User user = Accounts.getLoggedInUser();
		render(user);
	}

	public static void publicProfile(Long id)
	{
		User user = User.findById(id);
		List<Page> pages = new ArrayList<Page>();
		for(Blog blog: user.blogs) {
			pages.addAll(blog.pages);
		}
		int publicview = 0;
		if (session.get("logged_in_userid") == null) {
			publicview = 1;
		}
		render(user,pages,publicview);
	}
	
	public static void editProfile(String firstName, String lastName, String email, Integer age)
	{
		User user = Accounts.getLoggedInUser();
		if(checkValid(firstName)) {
			user.firstName = firstName;
			user.save();
		}
		if(checkValid(lastName)) {
			user.lastName = lastName;
			user.save();
		}
		if(checkValid(email)) {
			user.email = email;
			user.save();
		}
		if(age != null) {
			user.age = age.intValue();
			user.save();
		}
		index();
	}
	
	private static boolean checkValid(String text)
	{
		return (text != null && !text.equals(""));
	}
	
	public static void uploadPicture(Blob picture)
	{
		User user = Accounts.getLoggedInUser();
		user.profilePicture = picture;
		user.save();
		index();
	}
	
	public static void deleteUser(String password)
    {
		User user = Accounts.getLoggedInUser();
		if(user.password.equals(password)) {
			Wiper.removeUser(user.id);
		}
    	Accounts.index();
    }
}
