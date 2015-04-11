package controllers;

import models.User;
import play.mvc.Controller;

public class Profile extends Controller {

	public static void index()
	{
		User user = Accounts.getLoggedInUser();
		render(user);
	}
}
