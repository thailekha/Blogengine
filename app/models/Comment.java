package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Comment extends Model {

	@ManyToOne
	public Post postHost;
	@ManyToOne
	public User commenter;
	public String commentText;
	public Date commentDate;	
	
	public Comment(Post postHost, User commenter, String commentText, Date commentDate)
	{
		this.postHost = postHost;
		this.commenter = commenter;
		this.commentText = commentText;
		this.commentDate = commentDate;
	}
}
