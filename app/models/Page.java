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
public class Page extends Model {

	public String pageLink;
	public String pageTitle;
	
	@ManyToOne
	public Blog blogPageHost;	
	@OneToMany(mappedBy="pageHost")
	public List<Comment> commentsPage;		
	@Lob
	public String pageContent;
	
	public Date pageDate;
	
	public Page(Blog blogPageHost, String pageTitle, String pageContent, String pageLink)
	{
		this.blogPageHost = blogPageHost;
		this.pageTitle = pageTitle;
		this.pageContent = pageContent;
		pageDate = new Date();
		this.pageLink = pageLink;
		Logger.info("New page link :" + this.pageLink);
	}
	
}
