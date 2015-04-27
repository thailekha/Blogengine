package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class User extends Model {
	
	@OneToMany(mappedBy="target")
	public List<Followship> followers;
	@OneToMany(mappedBy="source")
	public List<Followship> followings;	
	@OneToMany(mappedBy="blogOwner")
	public List<Blog> blogs;
	@OneToMany(mappedBy="commenter")
	public List<Comment> commentsUser;
	@OneToMany(mappedBy="subCommenter")
	public List<SubComment> replies;
	@OneToMany(mappedBy="updater")
	public List<Update> newsFeed;
	
	public String firstName;
	public String lastName;
	public String email;
	public int age;
	public String password;
	
	public Blob profilePicture;
	
	public User(String firstName, String lastName, String email, int age, String password) 
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.age = age;
		this.password = password;
	}
	
	public boolean checkPassword(String password)
	{
		return (this.password.equals(password));
	}
	
	public void newBlog(String blogTitle)
	{
		blogs.add((Blog) new Blog(this,blogTitle).save());
	}
}