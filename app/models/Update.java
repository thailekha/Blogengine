package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Update extends Model {

	@ManyToOne
	public User updater;
	
	@ManyToOne
	public Post belong;
	
	public String nTitle;
	public String nContent;
	public String date;
	
	public Update(User updater,Post belong,String nTitle, String nContent, String date)
	{
		this.updater = updater;
		this.belong = belong;
		this.nTitle = nTitle;
		this.nContent = nContent;
		this.date = date;
	}
}
