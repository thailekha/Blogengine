package models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.Logger;
import play.db.jpa.Model;

@Entity
public class Comment extends Model {

	@ManyToOne
	public Post postHost;
	
	@ManyToOne
	public Page pageHost;
	
	@ManyToOne
	public User commenter;
	
	@OneToMany (mappedBy="commentHost", cascade=CascadeType.ALL)
	public List<SubComment> subComments;
	
	public String commentText;
	public Date commentDate;	
	
	public Comment(Post postHost, User commenter, String commentText)
	{
		this.postHost = postHost;
		this.commenter = commenter;
		this.commentText = commentText;
		commentDate = new Date();
		Logger.info("New comment in post: " + commentText);
	}
	
	public Comment(Page pageHost, User commenter, String commentText)
	{
		this.pageHost = pageHost;
		this.commenter = commenter;
		this.commentText = commentText;
		commentDate = new Date();
		Logger.info("New comment in page: " + commentText);
	}
}
