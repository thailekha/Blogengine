package models;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;
import play.Logger;

@Entity
public class Draft extends Model {
	
	@ManyToOne
	public Blog blogDraftHost;
	
	public String link;
	public String title;
	
	@Lob
	public String content;
	
	public Draft(Blog blogDraftHost, String postTitle, String postContent)
	{
		this.blogDraftHost = blogDraftHost;
		this.link = "";
		this.title = postTitle;
		this.content = postContent;
		Logger.info("New post draft, " + "link: " + this.link + 
				", title: " + this.title + ", content: " + this.content);
	}
	
	public Draft(Blog blogDraftHost,String pageLink, String pageTitle, String pageContent)
	{
		this.blogDraftHost = blogDraftHost;
		this.link = pageLink;
		this.title = pageTitle;
		this.content = pageContent;
		Logger.info("New page draft, " + "link: " + this.link + 
				", title: " + this.title + ", content: " + this.content);
	}
}
