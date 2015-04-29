
package controllers;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import models.Blog;
import models.Comment;
import models.Page;
import models.Post;
import models.SubComment;
import models.Update;
import models.User;
import play.Logger;
import play.mvc.Controller;
import utility.JsonParsers;
import utility.Wiper;

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
		blog.newPost(postTitle, postContent,false);
		blog.save();
		postManager(id);
	}
	

	public static void newPostDraft(Long id, String postTitle,String postContent)
	{
		Blog blog = Blog.findById(id);
		blog.newPost(postTitle, postContent,true);
		blog.save();
		postManager(id);
	}
	
	public static void editPost(Long idToEdit,String nTitle,String nContent)
	{
		Post post = Post.findById(idToEdit);
		Logger.info("Post to update: " + post.postTitle);
		if(!post.postTitle.equals(nTitle) || !(post.postContent).equals(nContent)) {
			User postOwner = post.blogPostHost.blogOwner;
			Update update = new Update(postOwner,post,nTitle,nContent);
			update.save();
			postOwner.newsFeed.add(update);
			postOwner.save();
			post.updates.add(update);
			post.save();
		}
		post.postTitle = nTitle;
		post.save();
		post.postContent = nContent;
		post.save();
		post.postDate = new Date();
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
	
	public static void editPage(Long pageId,String pageLink, String pageTitle,String pageContent)
	{
		Page page = Page.findById(pageId);
		page.pageLink = pageLink;
		page.save();
		page.pageTitle = pageTitle;
		page.save();
		page.pageContent = pageContent;
		page.save();
		page.pageDate = new Date();
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
		String lastEdit = 
				(new SimpleDateFormat("E dd/MM/yyyy 'at' hh:mm:ss")).format(post.postDate);
		render(post,lastEdit);
	}
	
//	public static void newPageDraft(Long id,String pageLink, String pageTitle,String pageContent)
//	{
//		Blog blog = Blog.findById(id);
//		blog.pageDrafts.add((Draft) new Draft(blog,pageLink,pageTitle,pageContent).save());
//		blog.save();
//		index(blog.id);
//	}
	
//-----------------------------------------------------------------------
	
	public static void deletePost(Long id)
	{
		Post post = Post.findById(id);
		Blog blogHost = post.blogPostHost;
		Wiper.removePost(id);
		postManager(blogHost.id);
	}
	
	public static void deletePage(Long id)
	{
		Page page = Page.findById(id);
		Blog blogHost = page.blogPageHost;
		Wiper.removePage(id);
		pageManager(blogHost.id);
	}

	public static void newComment(Long postId,Long pageId,String commentText)
	{
		User commenter = Accounts.getLoggedInUser();
		if(postId != null) {
			Post postHost = Post.findById(postId);
			Comment comment = new Comment(postHost,commenter,commentText);
			comment.save();
			commenter.commentsUser.add(comment);
			commenter.save();
			postHost.commentsPost.add(comment);
			postHost.save();
			
			postView(postId);
		} 
		else {
			Page pageHost = Page.findById(pageId);
			Comment comment = new Comment(pageHost,commenter,commentText);
			comment.save();
			commenter.commentsUser.add(comment);
			commenter.save();
			pageHost.commentsPage.add(comment);
			pageHost.save();
			
			pageView(pageHost.pageLink);
		}
	}
	
	public static void deleteComment(Long commentId)
	{
		Comment comment = Comment.findById(commentId);
		Post post = null;	//Keep track of the host before removing
		Page page = null;
		if(comment.postHost != null) {
			post = comment.postHost;
		}
		else {
			page = comment.pageHost;
		}
		
		Wiper.removeComment(commentId);
		
		if(post != null) {
			postView(post.id);
		}
		else {
			pageView(page.pageLink);
		}
	}
	
//-----------------------------------------------------------------------	
	
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
		Wiper.removeReply(replyId);
		
		if(commentHost.postHost != null) {
			postView(commentHost.postHost.id);
		}
		else {
			pageView(commentHost.pageHost.pageLink);
		}
	}
	
//----------------------------------------------------------------------
	
	public static void getRecentPost(Long id)
	{
		Post post = Post.findById(id);
		renderJSON(JsonParsers.postToJson(post));
	}
	
	public static void checkUpdate(Long id, String nTitle, String nContent, String date)
	{
		Post post = Post.findById(id);
		Logger.info("Post: " + post.postTitle +", new title: " 
		+ nTitle + ", new content: " + nContent + ", date: " + date);
	}
}
