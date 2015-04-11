package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.Logger;
import play.db.jpa.Model;

@Entity
public class Post extends Model {

	@ManyToOne
	public Blog blogPostHost;
	
	@OneToMany
	public List<Comment> commentsPost;
	
	public String postTitle;
	
	@Lob
	public String postContent;
	public Date postDate;
	
	public Post(Blog blogPostHost,String postTitle, String postContent)
	{
		this.blogPostHost = blogPostHost;
		this.postTitle = postTitle;
		this.postContent = postContent;
		postDate = new Date();
		Logger.info("New post: " + postTitle);
	}
}
