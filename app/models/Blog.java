package models;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Blog extends Model {

	@ManyToOne
	public User blogOwner;
	@OneToMany
	public List<Post> posts;
	
	public String blogTitle;
	public Date blogDate;
	
	public Blog(User blogOwner, String blogTitle)
	{
		this.blogOwner = blogOwner;
		this.blogTitle = blogTitle;
		blogDate = new Date();
	}
}
