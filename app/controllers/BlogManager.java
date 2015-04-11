package controllers;

import java.util.Collections;
import java.util.List;

import models.Blog;
import models.Comment;
import models.Page;
import models.Post;
import models.SubComment;
import models.User;
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
	
	public static void editPost(Long postId,String postTitle,String postContent)
	{
		Post post = Post.findById(postId);
		post.postTitle = postTitle;
		post.save();
		post.postContent = postContent;
		post.save();
		postManager(post.blogPostHost.id);
	}
	
	public static void createPage(Long id, String pageTitle, String pageContent, String pageLink)
	{
		Blog blog = Blog.findById(id);
		blog.newPage(pageTitle, pageContent,pageLink);
		blog.save();
		pageManager(id);
	}
	
	public static void editPage(Long pageId,String pageTitle,String pageContent)
	{
		Page page = Page.findById(pageId);
		page.pageTitle = pageTitle;
		page.save();
		page.pageContent = pageContent;
		page.save();
		pageManager(page.blogPageHost.id);
	}
	
	public static void blogView(Long blogId)
	{
		Blog blog = Blog.findById(blogId);
		Collections.reverse(blog.posts);
		render(blog);
	}
	
	public static void pageView(String pageLink)
	{
		Page page = Page.find("byPageLink", pageLink).first();
		render(page);
	}
	
	public static void postView(Long postId)
	{
		Post post = Post.findById(postId);
		render(post);
	}
	
	public static void commentPost(Long postId, String commentText)
	{
		User commenter = Accounts.getLoggedInUser();
		Post postHost = Post.findById(postId);
		
		Comment comment = new Comment(postHost,commenter,commentText);
		comment.save();
		commenter.commentsUser.add(comment);
		commenter.save();
		postHost.commentsPost.add(comment);
		postHost.save();
		
		postView(postId);
	}
	
	public static void deleteComment(Long commentId)
	{
		Comment comment = Comment.findById(commentId);
		List<SubComment> repliesFromComment = SubComment.find("byCommentHost", comment).fetch();
		for(SubComment reply: repliesFromComment) {
			Comment commentHost = reply.commentHost;
			commentHost.subComments.remove(reply);
			commentHost.save();
			User replier = reply.subCommenter;
			replier.replies.remove(reply);
			replier.save();
			
			reply.delete();
		}
		
		User commenter = comment.commenter;
		commenter.commentsUser.remove(comment);
		commenter.save();
		
		if(comment.postHost != null) {
			Post post = comment.postHost;
			post.commentsPost.remove(comment);
			post.save();
			comment.delete();
			postView(post.id);
		}
		else {
			Page page = comment.pageHost;
			page.commentsPage.remove(comment);
			page.save();
			comment.delete();
			pageView(page.pageLink);
		}
	}
	
	public static void replyComment(Long commentId, String subCommentText)
	{
		User subCommenter = Accounts.getLoggedInUser();
		Comment commentHost = Comment.findById(commentId);
		
		SubComment reply = new SubComment(subCommenter,commentHost,subCommentText);
		reply.save();
		
		subCommenter.replies.add(reply);
		subCommenter.save();
		commentHost.subComments.add(reply);
		commentHost.save();
		
		if(commentHost.postHost != null) {
			postView(commentHost.postHost.id);
		}
		else {
			pageView(commentHost.pageHost.pageLink);
		}
	}
	
	public static void deleteReplyComment(Long replyId)
	{
		SubComment reply = SubComment.findById(replyId);
		
		Comment commentHost = reply.commentHost;
		commentHost.subComments.remove(reply);
		commentHost.save();
		User replier = reply.subCommenter;
		replier.replies.remove(reply);
		replier.save();
		
		reply.delete();
		
		if(commentHost.postHost != null) {
			postView(commentHost.postHost.id);
		}
		else {
			pageView(commentHost.pageHost.pageLink);
		}
	}
}
