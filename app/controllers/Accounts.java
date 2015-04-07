package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Accounts extends Controller {

    public static void index() {
        render();
    }
    
    public static void signup() {
    	render();
    }

    public static void login()
    {
    	render();
    }
    
    public static void logout()
    {
    	session.clear();
    	index();
    }
    
    public static void register(String firstName,
    		String lastName, String email, int age, String password) {
    	User user = new User(firstName,lastName,email,age,password);
    	user.save();
    	index();
    }
 
    public static User findByEmail(String email)
    {
    	return User.find("byEmail", email).first();
    }
    
    public static void authenticate(String email, String password)
    {
    	User user = findByEmail(email);
    	if((user != null) && (user.checkPassword(password))) {
    		session.put("logged_in_userid", user.id);
    		Home.index();
    	}
    	else {
    		index();
    	}
    }
    
    public static User getLoggedInUser()
    {
    	if(session.contains("logged_in_userid")) {
    		return User.findById(Long.parseLong(session.get("logged_in_userid")));
    	}
    	else {
    		login();
    		return null;
    	}
    }
}