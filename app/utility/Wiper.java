package utility;

import java.util.List;

import controllers.Accounts;
import controllers.BlogManager;
import controllers.Home;
import models.Blog;
import models.Comment;
import models.Followship;
import models.Page;
import models.Post;
import models.SubComment;
import models.Update;
import models.User;

/**
 * If a request is sent from any view page to a method in any Controllers
 * class and that method call another (not private) method, the needed
 * task still might be executed but Play will render a blank page.
 * This class is written to avoid that and include remover for instances 
 * in database. Duplicate codes would be minimized 
 * @author Thai Kha Le
 *
 */
public class Wiper {
	
	public static void removeDatabase()
	{
		List<SubComment> replies = SubComment.findAll();
		for(SubComment reply: replies) {
			removeReply(reply.id);
		}
		List<Comment> comments = Comment.findAll();
		for(Comment comment: comments) {
			removeComment(comment.id);
		}
		List<Page> pages = Page.findAll();
		for(Page page: pages) {
			removePage(page.id);
		}
		List<Update> updates = Update.findAll();
		for(Update update: updates) {
			removeUpdate(update.id);
		}
		List<Post> posts = Post.findAll();
		for(Post post: posts) {
			removePost(post.id);
		}
		List<Blog> blogs = Blog.findAll();
		for(Blog blog: blogs) {
			removeBlog(blog.id);
		}
		List<Followship> follows = Followship.findAll();
		for(Followship follow: follows) {
			removeFollowship(follow.id);
		}
		List<User> users = User.findAll();
		for(User user: users)	{
			removeUser(user.id);
		}
	}
	
	/*
     * Note: user can make a comment and subcomment on a
     * page or post that does not belong to him/her.
     * Need to remove those as well.
     */
    public static void removeUser(Long id)
    {
    	User user  = User.findById(id);
    	List<Comment> comments = Comment.find("byCommenter", user).fetch();
    	for(Comment comment: comments) {
    		removeComment(comment.id);
    	}
    	List<SubComment> subComments = SubComment.find("bySubCommenter", user).fetch();
    	for(SubComment reply: subComments) {
    		removeReply(reply.id);
    	}
    	List<Followship> followers = Followship.find("byTarget", user).fetch();
    	for(Followship follow: followers) {
    		removeFollowship(follow.id);
    	}
    	List<Followship> followings = Followship.find("bySource", user).fetch();
    	for(Followship follow: followings) {
    		removeFollowship(follow.id);
    	}
    	List<Blog> blogs = Blog.find("byBlogOwner", user).fetch();
    	for(Blog blog: blogs) {
    		removeBlog(blog.id);
    	}
    	user.delete();
    }
	
	public static void removeFollowship(Long id)
	{
//		User followed = User.findById(id);
//		User source = Accounts.getLoggedInUser();
//		Followship followshipToRemove = null;
//		for(Followship follow: source.followings)
//		{
//			if(follow.source.equals(source) && follow.target.equals(followed)) {
//				followshipToRemove = follow;
//			}
//		}
		Followship followshipToRemove = Followship.findById(id);
		User followed = followshipToRemove.target;
		User source = followshipToRemove.source;		
		source.followings.remove(followshipToRemove);
		source.save();
		followed.followers.remove(followshipToRemove);
		followed.save();
		followshipToRemove.delete();
	}
	
	public static void removeBlog(Long id)
	{
		Blog blog = Blog.findById(id);
		List<Post> posts = Post.find("byBlogPostHost", blog).fetch();
		for(Post post: posts) {
			removePost(post.id);
		}
		List<Page> pages = Page.find("byBlogPageHost", blog).fetch();
		for(Page page: pages) {
			removePage(page.id);
		}		
		User blogOwner = blog.blogOwner;
		blogOwner.blogs.remove(blog);
		blogOwner.save();
		blog.delete();
	}
	
	public static void removePost(Long id)
	{
		Post post = Post.findById(id);
		List<Comment> comments = Comment.find("byPostHost", post).fetch();
		for(Comment comment: comments) {
			removeComment(comment.id);
		}
		List<Update> updates = Update.find("byBelong", post).fetch();
		for(Update update: updates) {
			removeUpdate(update.id);
		}
		Blog blogHost = post.blogPostHost;
		blogHost.posts.remove(post);
		blogHost.save();
		post.delete();
	}
	
	public static void removeUpdate(Long id)
	{
		Update update = Update.findById(id);
		Post fromPost = update.belong;
		fromPost.updates.remove(update);
		fromPost.save();
		User owner = update.updater;
		owner.newsFeed.remove(update);
		owner.save();
		update.delete();
	}
	
	public static void removePage(Long id) {
		Page page = Page.findById(id);
		List<Comment> comments = Comment.find("byPageHost", page).fetch();
		for(Comment comment: comments) {
			removeComment(comment.id);
		}
		Blog blogHost = page.blogPageHost;
		blogHost.pages.remove(page);
		page.delete();
	}
	
	public static void removeComment(Long commentId)
	{
		Comment comment = Comment.findById(commentId);
		List<SubComment> repliesFromComment = SubComment.find("byCommentHost", comment).fetch();
		for(SubComment reply: repliesFromComment) {
			removeReply(reply.id);
		}
		User commenter = comment.commenter;
		commenter.commentsUser.remove(comment);
		commenter.save();
		if(comment.postHost != null) {
			Post post = comment.postHost;
			post.commentsPost.remove(comment);
			post.save();
			comment.delete();
		}
		else {
			Page page = comment.pageHost;
			page.commentsPage.remove(comment);
			page.save();
			comment.delete();
		}
	}
	
	public static void removeReply(Long replyId)
	{
		SubComment reply = SubComment.findById(replyId);
		Comment commentHost = reply.commentHost;
		commentHost.subComments.remove(reply);
		commentHost.save();
		User replier = reply.subCommenter;
		replier.replies.remove(reply);
		replier.save();
		reply.delete();
	}
}
