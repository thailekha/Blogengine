package controllers;

import models.Blog;
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
    	index();
    }
}
