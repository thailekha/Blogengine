package models;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.Logger;
import play.db.jpa.Model;

@Entity
public class Blog extends Model {

	@ManyToOne
	public User blogOwner;
	@OneToMany(mappedBy="blogPostHost")
	public List<Post> posts;
	@OneToMany(mappedBy="blogPageHost")
	public List<Page> pages;
	
	@OneToMany
	public List<Draft> postDrafts;
//	@OneToMany
//	public List<Draft> pageDrafts;
	
	public String blogTitle;
	public Date blogDate;
	
	public Blog(User blogOwner, String blogTitle)
	{
		this.blogOwner = blogOwner;
		this.blogTitle = blogTitle;
		blogDate = new Date();
	}
	
	public void newPost(String postTitle,String postContent,boolean isDraft)
	{
		posts.add((Post) new Post(this,postTitle,postContent,isDraft).save());
		Logger.info("Posts size: " + posts.size());
	}
	
	public void newPage(String pageTitle,String pageContent,String pageLink)
	{
		pages.add((Page) new Page(this,pageTitle,pageContent,pageLink).save());
	}
}
