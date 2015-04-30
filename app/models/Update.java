package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.Logger;
import play.db.jpa.Model;

@Entity
@Table(name="`Update`")
public class Update extends Model {

	@ManyToOne
	public User updater;
	@ManyToOne
	public Post belong;
	public Date realDate;
	
	public String nTitle;
	@Lob
	public String nContent;	
	
	public Update(User updater,Post belong,String nTitle, String nContent)
	{
		this.updater = updater;
		this.belong = belong;
		this.nTitle = nTitle;
		this.nContent = nContent;
		realDate = new Date();
		Logger.info("New update content: " + nContent);
	}
}
