package controllers;

import models.Post;
import models.User;
import play.mvc.Controller;

public class PostRender extends Controller {

	public static void index(Long postId)
	{
		Post post = Post.findById(postId);
		render(post);
	}
	
	public static void commentPost(Long postId, String commentText)
	{
		User commenter = Accounts.getLoggedInUser();
		Post postHost = Post.findById(postId);
		postHost.newCommentPost(commenter, commentText);
		postHost.save();
		index(postId);
	}
}
