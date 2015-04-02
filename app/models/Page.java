package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Page extends Model {

	@ManyToOne
	public Blog blogPageHost;
	
	@OneToMany
	public List<Comment> commentsPage;
	
	public String pageTitle;
	
	@Lob
	public String pageContent;
	
	public Date pageDate;
	
	public Page(Blog blogPageHost, String pageTitle, String pageContent)
	{
		this.blogPageHost = blogPageHost;
		this.pageTitle = pageTitle;
		this.pageContent = pageContent;
		pageDate = new Date();
	}
	
}
