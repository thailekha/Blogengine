package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class User extends Model {
	
	@OneToMany
	public List<Blog> blogs;
	@OneToMany
	public List<Comment> commentsUser;
//	@OneToMany
//	public List<Page> pages;
	
	public String name;
	public String email;
	public String password;
	
	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
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