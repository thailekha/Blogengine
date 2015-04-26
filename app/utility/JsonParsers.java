package utility;

import play.Logger;
import models.Comment;
import models.Page;
import models.Post;
import models.SubComment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

public class JsonParsers {

	static GsonBuilder builder = new GsonBuilder().registerTypeHierarchyAdapter(Post.class, new PointAdapter());
	static Gson gson = builder.create();
	
	public static String postToJson(Object post)
	{
		return gson.toJson(post);
	}
	
	public static String pageToJson(Page page)
	{
		return gson.toJson(page);
	}
	
	public static String commentToJson(Comment comment)
	{
		return gson.toJson(comment);
	}
	
	public static String replyToJson(SubComment reply)
	{
		return gson.toJson(reply);
	}

//----------------------------------------------------------------------------------------	
	
	public static Post jsonToPost(JsonElement jPost)
	{
		return gson.fromJson(jPost, Post.class);
	}
	
	public static Page jsonToPage(JsonElement jPage)
	{
		return gson.fromJson(jPage, Page.class);
	}
}
