package controllers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.JsonElement;

import models.Blog;
import models.Followship;
import models.Update;
import models.User;
import play.mvc.Controller;
import utility.JsonParsers;

public class Home extends Controller {

	public static void index()
	{
		User user = Accounts.getLoggedInUser();
		//boolean needjs = true;
		List<User> others = User.findAll();
		others.remove(user);
		render(user,others);
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
		User followed = User.findById(id);
		User source = Accounts.getLoggedInUser();
		Followship followshipToRemove = null;
		for(Followship follow: source.followings)
		{
			if(follow.source.equals(source) && follow.target.equals(followed)) {
				followshipToRemove = follow;
			}
		}
		source.followings.remove(followshipToRemove);
		source.save();
		followed.followers.remove(followshipToRemove);
		followed.save();
		followshipToRemove.delete();
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
//		String newsFeed = "{\n\"updates\":[\n";
//		for(Update update: news) {
//			newsFeed += JsonParsers.updatesToJson(update) + ",\n";
//		}
//		newsFeed += "]\n}";
		ArrayList<String> newsFeed = new ArrayList<String>();
		for(Update update: news) {
			newsFeed.add(JsonParsers.updatesToJson(update));
		}
		renderJSON(newsFeed);
	}
}
