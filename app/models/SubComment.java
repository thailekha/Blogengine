package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class SubComment extends Model {
	
	@ManyToOne
	public User subCommenter;
	
	@ManyToOne
	public Comment commentHost;
	
	public String subCommentText;
	public Date subCommentDate;
	
	public SubComment(User subCommenter, Comment commentHost, String subCommentText)
	{
		this.subCommenter = subCommenter;
		this.commentHost = commentHost;
		this.subCommentText = subCommentText;
		subCommentDate = new Date();
	}
}
