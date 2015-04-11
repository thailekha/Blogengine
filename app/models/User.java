package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class User extends Model {
	
	@OneToMany
	public List<Blog> blogs;
	@OneToMany
	public List<Comment> commentsUser;
	@OneToMany
	public List<SubComment> replies;
	@OneToMany
	public List<Page> pages;
	
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
//		Blog blog = new Blog(this, blogTitle);
//		blog.save();
//		blogs.add(blog);
		blogs.add((Blog) new Blog(this,blogTitle).save());
	}
}