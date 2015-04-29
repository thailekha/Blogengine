package controllers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.JsonElement;

import models.Blog;
import models.Followship;
import models.Page;
import models.Post;
import models.Update;
import models.User;
import play.db.jpa.Blob;
import play.mvc.Controller;
import utility.JsonParsers;
import utility.Wiper;

public class Home extends Controller {

	public static void index()
	{
		User user = Accounts.getLoggedInUser();
		//boolean needjs = true;
		List<User> others = User.findAll();
		others.remove(user);
		render(user,others);
	}
	
	public static void getPicture(Long id) 
	  {
	    User user = User.findById(id);
	    Blob picture = user.profilePicture;
	    if (picture.exists())
	    {
	      response.setContentTypeIfNotSet(picture.type());
	      renderBinary(picture.get());
	    }
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
		Wiper.removeBlog(id);
		index();
	}
	
	public static void follow(Long id)
	{
		User source = Accounts.getLoggedInUser();
		User target = User.findById(id);
		boolean followable = true;
		for(Followship followshipFromSource: source.followings) {
			if(followshipFromSource.target.equals(target)) {
				followable = false;
			}
		}
		if(followable) {
			Followship followship = new Followship(source,target);
			followship.save();
			source.followings.add(followship);
			source.save();
			target.followers.add(followship);
			target.save();
		}
		index();
	}
	
	public static void unfollow(Long id)
	{
		Wiper.removeFollowship(id);
		index();
	}
	
	public static void newsFeed()
	{
		User user = Accounts.getLoggedInUser();
		List<Update> news = new ArrayList<Update>();
		for(Followship follow: user.followings)
		{
			news.addAll(follow.target.newsFeed);
		}
		ArrayList<String> newsFeed = new ArrayList<String>();
		for(Update update: news) {
			newsFeed.add(JsonParsers.updatesToJson(update));
		}
		Collections.reverse(newsFeed);
		renderJSON(newsFeed);
	}
}
