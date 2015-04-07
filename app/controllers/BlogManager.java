package controllers;

import models.Blog;
import play.mvc.Controller;

public class BlogManager extends Controller {

	public static void index(Long id)
	{
		Blog blog = Blog.findById(id);
		render(blog);
	}
	
	public static void postManager(Long id)
	{
		Blog blog = Blog.findById(id);
		render(blog);
	}
	
	public static void pageManager(Long id)
	{
		Blog blog = Blog.findById(id);
		render(blog);
	}
	
	public static void createPost(Long id,String postTitle, String postContent)
	{
		Blog blog = Blog.findById(id);
		blog.newPost(postTitle, postContent);
		blog.save();
		postManager(id);
	}
	
	public static void createPage(Long id, String pageTitle, String pageContent, String pageLink)
	{
		Blog blog = Blog.findById(id);
		blog.newPage(pageTitle, pageContent,pageLink);
		blog.save();
		pageManager(id);
	}
}
