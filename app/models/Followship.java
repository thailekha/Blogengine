package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Followship extends Model {

	@ManyToOne
	public User source;
	
	@ManyToOne
	public User target;
	
	public Followship(User source,User target)
	{
		this.source = source;
		this.target = target;
	}	
}
