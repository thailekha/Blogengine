package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Post extends Model {

	@ManyToOne
	public Blog blogHost;
	
	@OneToMany
	public List<Comment> commentsPost;
	
	public String postTitle;
	public String content;
	public Date postDate;
	
	public Post(Blog blogHost,String postTitle, String content)
	{
		this.blogHost = blogHost;
		this.postTitle = postTitle;
		this.content = content;
		postDate = new Date();
	}
}
