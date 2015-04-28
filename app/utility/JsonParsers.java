package utility;

import play.Logger;
import models.Comment;
import models.Page;
import models.Post;
import models.SubComment;
import models.Update;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

public class JsonParsers {

	static GsonBuilder builderPost = new GsonBuilder().registerTypeHierarchyAdapter(Post.class, new PostAdapter());
	static GsonBuilder builderUpdates = new GsonBuilder().registerTypeHierarchyAdapter(Update.class, new UpdatesAdapter());
	static Gson gsonPost = builderPost.create();
	static Gson gsonUpdates = builderUpdates.create();
	static Gson gson = new Gson();
	
	public static String postToJson(Object post)
	{
		return gsonPost.toJson(post);
	}
	
	public static String updatesToJson(Object update)
	{
		return gsonUpdates.toJson(update);
	}
	
//	public static String textToJson(Object text)
//	{
//		return gson.toJson(text);
//	}
//	
////---------------------------------------------------------------------------------	
//	
//	public static String pageToJson(Page page)
//	{
//		return gson.toJson(page);
//	}
//	
//	public static String commentToJson(Comment comment)
//	{
//		return gson.toJson(comment);
//	}
//	
//	public static String replyToJson(SubComment reply)
//	{
//		return gson.toJson(reply);
//	}
//
////----------------------------------------------------------------------------------------	
//	
//	public static Post jsonToPost(JsonElement jPost)
//	{
//		return gson.fromJson(jPost, Post.class);
//	}
//	
//	public static Page jsonToPage(JsonElement jPage)
//	{
//		return gson.fromJson(jPage, Page.class);
//	}
}
